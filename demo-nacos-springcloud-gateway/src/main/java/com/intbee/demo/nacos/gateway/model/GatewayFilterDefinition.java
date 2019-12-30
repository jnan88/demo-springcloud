/**
 * 
 */
package com.intbee.demo.nacos.gateway.model;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

/**
 * 描述：
 * 
 * <pre>
 * 过滤器定义模型
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Dec 27, 2019-11:08:54 AM
 *
 */
@Data
public class GatewayFilterDefinition {

	/**
	 * Filter Name
	 */
	private String				name;

	/**
	 * 对应的路由规则
	 */
	private Map<String, String>	args	= new LinkedHashMap<>();
}
