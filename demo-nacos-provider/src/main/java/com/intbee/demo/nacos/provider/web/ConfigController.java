package com.intbee.demo.nacos.provider.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 描述：
 * 
 * @author 天明
 * @version: 0.0.1 2019年6月13日-上午10:29:33
 *
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

	@Value("${useLocalCache:false}")
	private boolean useLocalCache;

	@RequestMapping("/get1")
	public boolean get() {
		return useLocalCache;
	}

	@Value("${mysql.url:testURL}")
	String userName;

	@RequestMapping("/get2")
	public String simple() {
		return "mysql.url = " + userName;
	}
}
