package com.muma.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_no")
	private Integer boardNo;

	@Column(name = "nick_name")
	private String nickName;

	@Column(name = "music_genre")
	private Integer musicGenre;

	@Column(name = "title")
	private String title;

	@Column(name = "contents")
	private String contents;

	@Column(name = "member_id")
	private String memberId;

	@Column(name = "created_time")
	@CreationTimestamp
	private Date createdTime;

	@Column(name = "updated_time")
	@UpdateTimestamp
	private Date updatedTime;

	@Column(name = "likes")
	private Integer likes;

	@Column(name = "dis_likes")
	private Integer dislikes;

	@PrePersist
	public void prePersist() {
		this.likes = this.likes == null ? 0 : this.likes;
		this.dislikes = this.dislikes == null ? 0 : this.dislikes;
	}



}

