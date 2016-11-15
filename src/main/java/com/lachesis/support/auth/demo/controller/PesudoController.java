package com.lachesis.support.auth.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lachesis.support.auth.demo.vo.Greeting;

@RestController
@RequestMapping("/greetings")
public class PesudoController {
	private AtomicLong count = new AtomicLong();
	
	@RequestMapping("/greeting")
	public Greeting getGreeting(@RequestParam(name="toname") String toName, HttpServletRequest request){
		
		String token = determineToken(request);
		String ip = determineIpAddress(request);
		
		Map<String,String> vars = new HashMap<String,String>();
		vars.put("token", token);
		vars.put("ip", ip);
		
		RestTemplate  restTemplate = new RestTemplate();
		
		String result = restTemplate.getForObject(
		        "http://localhost:9090/authcenter/v1/authentication", String.class, vars);
		
		
		System.out.println("RESULT:"+result);
		
		String owner = "dummy";
		return createNewGreeting(toName, owner);
	}
	
	private String determineToken(HttpServletRequest request){
		String authrization = request.getHeader("Authorization");
		String token = authrization.substring(6);
		
		return token;
	}
	
	private String determineIpAddress(HttpServletRequest request){
		return request.getRemoteAddr();
	}
	
	private Greeting createNewGreeting(String counterparty, String owner){
		Greeting g = new Greeting();
		g.setId(String.valueOf(count.getAndIncrement()));
		g.setGreeting(UUID.randomUUID().toString());
		g.setCounterparty(counterparty);
		g.setOwner(owner);
		
		return g;
	}
}
