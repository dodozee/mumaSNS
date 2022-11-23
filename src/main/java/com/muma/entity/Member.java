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

import lombok.Data;

@Entity
@Table(name = "member")
@Data
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_no")
	private Integer memberNo;

	@Column(name = "member_id")
	private String memberId;

	@Column(name = "password")
	private String password;

	@Column(name = "created_time")
	@CreationTimestamp
	private Date createdTime;

	@Column(name = "updated_time")
	@UpdateTimestamp
	private Date updatedTime;

	@Column(name = "role")
	private String role;

	@Column(name = "session_key")
	private String sessionKey;

	@Column(name = "session_limit_time")
	@CreationTimestamp
	private Date sessionLimitTime;

	@Column(name = "profile_picture")
	private String profilePicture;

	@PrePersist
	public void prePersist() {
		this.sessionKey = this.sessionKey == null ? "unused" : this.sessionKey;
	}
}
