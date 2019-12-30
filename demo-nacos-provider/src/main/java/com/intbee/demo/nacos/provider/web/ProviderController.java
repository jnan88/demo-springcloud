package com.intbee.demo.nacos.provider.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 描述：
 * 
 * @author 天明
 * @version: 0.0.1 2019年6月13日-上午9:56:23
 *
 */
@Slf4j
@RestController
public class ProviderController {
	@Value("${server.port:8764}")
	int		serverPort;
	@Value("${spring.application.name}")
	String	appName;

	@GetMapping("/hi")
	public String hi(@RequestParam(name = "name", defaultValue = "demo-8762", required = false) String name) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				log.info("header:{}={}", headerName,request.getHeader(headerName));
			}
		}
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		log.info("AttributeNames={}", JSON.toJSONString(headerNames));
		log.info("names={}", JSON.toJSONString(ra.getAttributeNames(RequestAttributes.SCOPE_REQUEST)));
		return "AppName: " + appName + "  Port: " + serverPort + " Data: " + name;
	}
	@GetMapping("/ip/hi")
	public String hi2(@RequestParam(name = "name", defaultValue = "demo-8762", required = false) String name) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				log.info("header:{}={}", headerName,request.getHeader(headerName));
			}
		}
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		log.info("AttributeNames={}", JSON.toJSONString(headerNames));
		log.info("names={}", JSON.toJSONString(ra.getAttributeNames(RequestAttributes.SCOPE_REQUEST)));
		return "AppName: " + appName + "  Port: " + serverPort + " Data: " + name;
	}
}
