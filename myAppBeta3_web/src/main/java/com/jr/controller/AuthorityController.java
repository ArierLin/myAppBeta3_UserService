package com.jr.controller;

import com.jr.dubboInterface.UserDubboInterface;
import com.jr.entity.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@SuppressWarnings(value="all")
@Controller
@RequestMapping(value="/authority")
public class AuthorityController {
	
	private static Logger log = LoggerFactory.getLogger(AuthorityController.class);

	@Autowired
	private UserDubboInterface userDubboInterface;

//	@Autowired
//	private userDubboInterface userDubboInterface;
	
	@RequestMapping(value="toAuthorityManagementPage")
	public String toAuthorityManagementPage(ModelMap map){
		List<Authority> authorities = userDubboInterface.findAllAuthorities();
		map.put("authorities", authorities);
		return "authorityList";
	}

	
}
