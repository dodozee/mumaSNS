package com.muma.repository;

import java.util.Optional;

import com.muma.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	Optional<Member> findByMemberId(String memberId);
}
