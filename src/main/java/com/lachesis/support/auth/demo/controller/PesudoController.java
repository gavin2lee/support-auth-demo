package com.lachesis.support.auth.demo.controller;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.demo.vo.Greeting;

@RestController
@RequestMapping("/greetings")
public class PesudoController {
	private AtomicLong count = new AtomicLong();
	
	@RequestMapping("/greeting")
	public Greeting getGreeting(@RequestParam(name="toname") String toName){
		
		return createNewGreeting(toName);
	}
	
	private Greeting createNewGreeting(String counterparty){
		Greeting g = new Greeting();
		g.setId(String.valueOf(count.getAndIncrement()));
		g.setGreeting(UUID.randomUUID().toString());
		g.setCounterparty(counterparty);
		
		return g;
	}
}
