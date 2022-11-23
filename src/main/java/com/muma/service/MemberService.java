package com.muma.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muma.entity.Member;
import com.muma.constant.MemberRole;
import com.muma.forms.AuthorityForm;
import com.muma.repository.MemberRepository;
import com.muma.util.EncUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private EncUtil enc;

	public Member createMember(AuthorityForm form) {

		Optional<Member> memberOp = memberRepository.findByMemberId(form.getMemberId());
		if (!memberOp.isEmpty()) {
			log.warn("{} is already exist!!", form.getMemberId());
			return null;
		}

		Member member = form.toEntity();
		member.setPassword(enc.generateSHA512(member.getPassword()));
		member.setRole(MemberRole.COMMON.value);

		member = memberRepository.save(member);
		return member;
	}


	public boolean existMemberId(String memberId) {
		Optional<Member> result = memberRepository.findByMemberId(memberId);
		return result.isEmpty() ? false : true;
	}

	public Optional<Member> getMemberInfoByMemberId(String memberId) {
		return memberRepository.findByMemberId(memberId);
	}


	public void loginProcess(AuthorityForm form,
							 HttpServletRequest req, HttpServletResponse res, Model model) {


		Optional<Member> memberOp = memberRepository.findByMemberId(form.getMemberId());
		if (memberOp.isEmpty()) {
			model.addAttribute("message", form.getMemberId()+" 존재하지 않습니다!!");
			model.addAttribute("redirectUrl", "/auth/join");
			return;
		}


		if (!memberOp.get().getPassword().equals(enc.generateSHA512(form.getPassword()))) {
			model.addAttribute("message", "비밀번호가 맞지않습니다.!!");
			model.addAttribute("redirectUrl", "/auth/login");
			return;
		}


		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(300000);
		session.setAttribute("member", memberOp.get());

		// Update latest login time.
		memberOp.get().setUpdatedTime(new Date());
		memberRepository.save(memberOp.get());
		log.info("Save login user info {} : ", memberOp.get().toString());

		model.addAttribute("message", "로그인 성공했습니다 축하드립니다!!");
		model.addAttribute("redirectUrl", "/");
	}

}
