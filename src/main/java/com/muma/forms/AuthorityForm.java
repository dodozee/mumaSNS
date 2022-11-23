package com.muma.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.muma.entity.Member;

import lombok.Data;


@Data
public class AuthorityForm {

	@NotBlank(message = "아이디는 의무입니다")
	@Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "아이디가 부적절합니다.")
	private String memberId;

	@NotBlank(message = "패스워드도 의무입니다.")
	@Pattern(regexp = "^(?=.*\\d).{4,16}$", message = "패스워드가 부적절합니다.")
	private String password;

	private boolean useAutoLogin;

	public Member toEntity() {
		Member member = new Member();
		member.setMemberId(memberId);
		member.setPassword(password);
		return member;
	}
}
