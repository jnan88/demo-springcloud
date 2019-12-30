/**
 * 
 */
package com.intbee.demo.nacos.gateway.filter;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Dec 27, 2019-3:24:24 PM
 *
 */
@Data
@Accessors(chain=true)
public class PgokApiResponse<T> {
	private int		code;
	private String	msg;
	private int		err;
	private String	errmsg;
	private T		data;
}
