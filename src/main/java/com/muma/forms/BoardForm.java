package com.muma.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.muma.entity.Board;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BoardForm {

	private Integer boardNo;




	@NotBlank
	@Length(min = 1, max = 20)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{1,20}$")
	private String nickName;

	@NotNull(message = "장르를 선텍하세요")
	private Integer musicGenre;

	@NotBlank(message = "제목을 입력하세요")
	private String title;

	@NotBlank(message = "내용을 입력하세요")
	private String contents;

	@Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Unsuitable inputed ID")
	private String memberId;

	public Board toEntity() {
		Board board = new Board();

		board.setNickName(this.nickName);
		board.setMusicGenre(this.musicGenre);
		board.setTitle(this.title);
		board.setContents(this.contents);
		board.setMemberId(((this.memberId == null || (this.memberId.equals(""))) ? "" : this.memberId));

		return board;
	}
}
