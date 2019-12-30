/**
 * 
 */
package com.intbee.demo.nacos.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Dec 27, 2019-11:13:53 AM
 *
 */
@Service
@Slf4j
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

	@Autowired
	private RouteDefinitionWriter		routeDefinitionWriter;

	private ApplicationEventPublisher	publisher;

	/**
	 * 增加路由
	 * 
	 * @param definition
	 * @return
	 */
	public String add(RouteDefinition definition) {
		routeDefinitionWriter.save(Mono.just(definition)).subscribe();
		this.publisher.publishEvent(new RefreshRoutesEvent(this));
		return "success";
	}

	/**
	 * 更新路由
	 * 
	 * @param definition
	 * @return
	 */
	public String update(RouteDefinition definition) {
		try {
			log.info("route delete : {}", definition.getId());
			this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
		} catch (Exception e) {
			log.warn("update fail,not find route  routeId: {}", definition);
			return "update fail,not find route  routeId: " + definition.getId();
		}
		try {
			log.info("route save : {}", definition);
			routeDefinitionWriter.save(Mono.just(definition)).subscribe();
			this.publisher.publishEvent(new RefreshRoutesEvent(this));
			return "success";
		} catch (Exception e) {
			log.warn("update route fail: {}", definition);
			return "update route  fail";
		}

	}

	/**
	 * 删除路由
	 * 
	 * @param id
	 * @return
	 */
	public String delete(String id) {
		try {
			this.routeDefinitionWriter.delete(Mono.just(id));
			return "delete success";
		} catch (Exception e) {
			e.printStackTrace();
			return "delete fail";
		}

	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
