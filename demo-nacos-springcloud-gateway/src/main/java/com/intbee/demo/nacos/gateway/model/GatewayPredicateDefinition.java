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
 * 路由断言定义模型
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Dec 27, 2019-11:10:25 AM
 *
 */
@Data
public class GatewayPredicateDefinition {
	/**
	 * 断言对应的Name
	 */
	private String				name;

	/**
	 * 配置的断言规则
	 */
	private Map<String, String>	args	= new LinkedHashMap<>();
}
