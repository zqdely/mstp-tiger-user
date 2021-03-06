package com.threepicture.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threepicture.dao.model.User;
import com.threepicture.feignClient.ProductFeignClient;
import com.threepicture.server.impl.ProductService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Deprecated //测试类已经废弃
@RestController//控制器注解(@Controller会将返回的信息当作URL处理。@RestController直接将信息当作Content处理。)
@RequestMapping("/query-cust")//url注解
public class ProductController {
	
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	//注入
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductFeignClient productFeignClient;
	
	@RequestMapping("/queryAllUser")
	public List<User> queryAllUser(){
		logger.info("进入controller...");
		return this.productService.queryAllUser();
	}
	
	@RequestMapping("/queryUserById/{id}")
	public Map<String, Object> queryUserById(@PathVariable String id){
//		if(!CollectionUtils.isEmpty(custIfo))
		return this.productService.queryUserById(id);
		
	}
	
	@RequestMapping("/queryProductAllInfo")
	public Object getProductAllInfo(){
		return this.productFeignClient.getproductAllInfo();
	}
	
	@RequestMapping("/test")
	@HystrixCommand(fallbackMethod = "fallBackInfo")
	public String getException() throws Exception{
		throw new Exception("测试hystrix断路功能...");
	}
	
	public String fallBackInfo(){
		return "服务暂时不可用，请稍后再试";
	}

}
