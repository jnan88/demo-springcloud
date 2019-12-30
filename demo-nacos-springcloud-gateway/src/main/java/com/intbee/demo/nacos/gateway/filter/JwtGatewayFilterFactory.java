/**
 * 
 */
package com.intbee.demo.nacos.gateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Dec 27, 2019-3:20:36 PM
 *
 */
@Component
@Slf4j
public class JwtGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtGatewayFilterFactory.Config> {
	@Data
	public static class Config {
		private String	appKey;
		private String	appSecret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cloud.gateway.support.ShortcutConfigurable#
	 * shortcutFieldOrder()
	 */
	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("appKey", "appSecret");
	}

	/**
	 * 
	 */
	public JwtGatewayFilterFactory() {
		super(Config.class);
		log.info("Loaded JwtGatewayFilterFactory [Authorize]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory#
	 * apply(java.lang.Object)
	 */
	@Override
	public GatewayFilter apply(Config config) {
		System.out.println(config);
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			HttpHeaders headers = request.getHeaders();
			String token = headers.getFirst("token");
			log.info("token={}", token);
			if (StringUtils.isBlank(token)) {
				// 没有token
				return authErro(response, "请登陆");
			}
			return chain.filter(exchange);
		};
	}

	/**
	 * 认证错误输出
	 * 
	 * @param resp
	 *            响应对象
	 * @param mess
	 *            错误信息
	 * @return
	 */
	private Mono<Void> authErro(ServerHttpResponse resp, String mess) {
		resp.setStatusCode(HttpStatus.UNAUTHORIZED);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String returnStr = JSON.toJSONString(new PgokApiResponse().setMsg(mess));
		DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

}
