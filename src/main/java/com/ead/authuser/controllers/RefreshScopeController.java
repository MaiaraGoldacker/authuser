package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*Exemplo de alteração de propriedade no application.properties sem precisar reiniciar o sistema.
 * Para aterar a propriedade, precisa-se dar um POST vazio na url + actuator/refresh
 * */
@RestController
@RefreshScope
public class RefreshScopeController {

	//@Value("${authuser.refreshscope.name}")
	private String name;
	
	@RequestMapping("/refreshscope")
	public String refreshscope() {
		return this.name;
	}
	
}
