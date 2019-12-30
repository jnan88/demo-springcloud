# 基于Nacos实现Springcloud Gateway动态路由配置
## 简述
本项目主要依赖一下版本

- Nacos 1.1.4+
- SpringCloud Greenwich.x
- com.alibaba.cloud 2.1.1.RELEASE

```
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-gateway</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			<dependency>
				<groupId>com.alibaba.cloud</groupId>
				<artifactId>spring-cloud-alibaba-dependencies</artifactId>
				<version>2.1.1.RELEASE</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
```

## Nacos 配置

```
[{
    "filters": [{"name":"Jwt","args": {
            "appKey":"appKey",
            "appSecret":"appSecret"
        }}],
    "id": "jd_route",
    "order": 0,
    "predicates": [{
        "args": {
            "pattern": "/jd1"
        },
        "name": "Path"
    }],
    "uri": "http://www.jd.com"
},
{
    "filters": [{"name":"RewritePath","args":{"regexp":"/test","replacement":"/ip/hi"}}],
    "id": "intbee_route",
    "order": 0,
    "predicates": [{
        "args": {
            "path":"/ip/**"
        },
        "name": "Path"
    }],
    "uri": "lb://demo-nacos-provider"
}
]
```

### 踩坑
#### @NacosConfigListener 无法正常使用
需要使用NacosConfigProperties主动进行注册监听

#### 自定义filter无法正常使用
对名称有要求，名称为：xxxGatewayFilterFactory=xxx，例如JwtGatewayFilterFactory使用时名称为Jwt

查看路由：
http://localhost:9901/actuator/gateway/routes
