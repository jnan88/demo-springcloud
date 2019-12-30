/**
 * 
 */
package com.intbee.demo.nacos.gateway.route;

import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Dec 27, 2019-11:14:42 AM
 *
 */
@Component
@Slf4j
public class DynamicRouteServiceImplByNacos {

	@Autowired
	private DynamicRouteServiceImpl	dynamicRouteService;
	@Autowired
	private NacosConfigProperties	nacosConfigProperties;
	/**
	 * TODO 配置没起作用
	 */
	@Value("${gatewayDataId:intbee-api-gateway.yml}")
	private String					gatewayDataId;
	/**
	 * TODO 配置没起作用
	 */
	@Value("${gatewayGroupId:DEFAULT_GROUP}")
	private String					gatewayGroupId;

	@PostConstruct
	public void init() {
		log.info("dynamicRouteByNacosListener:{},{}", gatewayDataId, gatewayGroupId);
		dynamicRouteByNacosListener(gatewayDataId, gatewayGroupId);
	}

	/**
	 * 监听Nacos Server下发的动态路由配置
	 * 
	 * @param dataId
	 * @param group
	 */
	public void dynamicRouteByNacosListener(String dataId, String group) {
		log.info("dynamicRouteByNacosListener:{},{}", dataId, group);
		try {
			ConfigService configService = nacosConfigProperties.configServiceInstance();
			String content = configService.getConfig(dataId, group, 5000);
			log.info("Nacos Routes ={}", content);
			JSON.parseArray(content, RouteDefinition.class).forEach(x -> dynamicRouteService.update(x));

			configService.addListener(dataId, group, new Listener() {
				@Override
				public void receiveConfigInfo(String content) {
					if (log.isDebugEnabled()) {
						log.debug("receiveConfigInfo={}", content);
					}
					JSON.parseArray(content, RouteDefinition.class).forEach(x -> dynamicRouteService.update(x));
				}

				@Override
				public Executor getExecutor() {
					return null;
				}

			});

		} catch (NacosException e) {
			log.warn("dynamicRouteByNacosListener fail:{},{}", dataId, group, e.getMessage());
		}
	}

}
