/**
 * 
 */
package com.intbee.demo.nacos.gateway.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 描述：
 * 
 * <pre>
 * Gateway的路由定义模型
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Dec 27, 2019-11:11:08 AM
 *
 */
@Data
public class GatewayRouteDefinition {
	/**
	 * 路由的Id
	 */
	private String								id;

	/**
	 * 路由断言集合配置
	 */
	private List<GatewayPredicateDefinition>	predicates	= new ArrayList<>();

	/**
	 * 路由过滤器集合配置
	 */
	private List<GatewayFilterDefinition>		filters		= new ArrayList<>();

	/**
	 * 路由规则转发的目标uri
	 */
	private String								uri;

	/**
	 * 路由执行的顺序
	 */
	private int									order		= 0;
}
