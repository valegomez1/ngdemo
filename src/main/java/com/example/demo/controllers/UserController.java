package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.QueryResult;
import com.example.demo.util.RestResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {

	@Autowired
	protected UserService userService;
	
	protected  ObjectMapper mapper;
	
	@RequestMapping(value="/saveOrUpdate", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String userJson)
			throws JsonParseException, JsonMappingException, IOException{
		
		this.mapper = new ObjectMapper();
		User user = this.mapper.readValue(userJson, User.class);
		
		if(!this.validate(user)){
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Los campos obligatorios no estan completos");
		}
		
		this.userService.save(user);
		
		return new RestResponse(HttpStatus.OK.value(), "Operacion exitsa");
	}
	
	@RequestMapping(value="/getUsers", method = RequestMethod.GET)
	public List<User> getUsers(){
		return this.userService.findAll();
	}
	
	@RequestMapping(value="/deleteUser", method = RequestMethod.POST)
	public void deleteUser(@RequestBody String userJson) throws Exception{
		this.mapper = new ObjectMapper();
		User user = this.mapper.readValue(userJson, User.class);
		
		if(user.getId()==null){
			throw new Exception("El id es nulo");
		}
		this.userService.deleteUser(user.getId());
	}
	
	
	private boolean validate(User user){
		boolean isValid = true;
		if(StringUtils.trimToNull(user.getFirstName()) == null){
			isValid = false;
		}
		
		if(StringUtils.trimToNull(user.getFirstSurname()) == null){
			isValid = false;
		}
		
		if(StringUtils.trimToNull(user.getAddress()) == null){
			isValid = false;
		}
		
		return isValid;
	}
}
