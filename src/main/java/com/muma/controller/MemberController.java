package com.muma.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.muma.dto.ResponseDTO;
import com.muma.entity.Member;
import com.muma.constant.ResponseInfo;
import com.muma.forms.AuthorityForm;
import com.muma.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("auth")
@Slf4j
public class MemberController {

	@Autowired
	private MemberService memberService;


	@RequestMapping(method = RequestMethod.GET, path = "/join")
	public String joinMember(@ModelAttribute AuthorityForm form) {

		return "auth/join";
	}


	@RequestMapping(method = RequestMethod.POST, path = "/join")
	public String joinMember(@ModelAttribute @Validated AuthorityForm form, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message", String.join(", ", errors));
			model.addAttribute("redirectUrl", "/auth/join");
			return "/common/message";
		}

        Member member = memberService.createMember(form);

		if (member == null) {
			model.addAttribute("message", form.getMemberId()+" is already exist!!");
			model.addAttribute("redirectUrl", "/auth/join");
			return "/common/message";
		}

		model.addAttribute("message", member.getMemberId()+"has been joined!!");
		model.addAttribute("redirectUrl", "/auth/login");

		return "/common/message";
	}


	@PostMapping(path = "/checkId")
	@ResponseBody
	public ResponseEntity<?> checkId(
			@RequestParam(required = true,
			name = "memberId")
			@Email
			@NotBlank
			String memberId
			) {

		log.info("Input memberId : {}", memberId);

		return ResponseEntity.ok(ResponseDTO.builder()
				.resultCode(ResponseInfo.SUCCESS.getResultCode())
				.message(ResponseInfo.SUCCESS.getMessage())
				.data(memberService.existMemberId(memberId))
				.build()
				);
	}


	@RequestMapping(method = RequestMethod.GET, path = "/login")
	public String loginForm() {
		return "auth/login";
	}

	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public String doLogin(@ModelAttribute @Validated AuthorityForm form, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res, Model model) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message", String.join(", ", errors));
			model.addAttribute("redirectUrl", "/auth/join");
			return "/common/message";
		}

		log.info("LoginForm Data : {} ", form.toString());
		memberService.loginProcess(form, req, res, model);

		return "/common/message";
	}


	@RequestMapping(method = RequestMethod.GET, path = "/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res, Model model) {

		HttpSession session = req.getSession();
		Object obj = session.getAttribute("member");

		if (obj != null) {
			session.removeAttribute("member");
			session.invalidate();
		}

		model.addAttribute("message", "Logout Success!!");
		model.addAttribute("redirectUrl", "/");

		return "/common/message";
	}

}
