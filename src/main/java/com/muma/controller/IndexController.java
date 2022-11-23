package com.muma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(path="/")
	public String index() {
		return "forward:/board/list";
	}
}
