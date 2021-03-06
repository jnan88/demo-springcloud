package com.intbee.demo.nacos.gateway.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.intbee.demo.nacos.gateway.model.GatewayPredicateDefinition;
import com.intbee.demo.nacos.gateway.model.GatewayRouteDefinition;
import com.intbee.demo.nacos.gateway.route.DynamicRouteServiceImpl;

/**
 * 
 * 描述：
 * 
 * @author 天明
 * @version: 0.0.1 2019年6月13日-上午10:01:20
 *
 */

@RestController
@RequestMapping("/route")
public class RouteController {

	@Autowired
	private DynamicRouteServiceImpl dynamicRouteService;

	/**
	 * 增加路由
	 * 
	 * @param gwdefinition
	 * @return
	 */
	@PostMapping("/add")
	public String add(@RequestBody GatewayRouteDefinition gwdefinition) {
		try {
			RouteDefinition definition = assembleRouteDefinition(gwdefinition);
			return this.dynamicRouteService.add(definition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "succss";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id) {
		return this.dynamicRouteService.delete(id);
	}

	@PostMapping("/update")
	public String update(@RequestBody GatewayRouteDefinition gwdefinition) {
		RouteDefinition definition = assembleRouteDefinition(gwdefinition);
		return this.dynamicRouteService.update(definition);
	}

	private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
		RouteDefinition definition = new RouteDefinition();
		List<PredicateDefinition> pdList = new ArrayList<>();
		definition.setId(gwdefinition.getId());
		List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = gwdefinition.getPredicates();
		for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
			PredicateDefinition predicate = new PredicateDefinition();
			predicate.setArgs(gpDefinition.getArgs());
			predicate.setName(gpDefinition.getName());
			pdList.add(predicate);
		}
		definition.setPredicates(pdList);
		URI uri = UriComponentsBuilder.fromHttpUrl(gwdefinition.getUri()).build().toUri();
		definition.setUri(uri);
		return definition;
	}

}
