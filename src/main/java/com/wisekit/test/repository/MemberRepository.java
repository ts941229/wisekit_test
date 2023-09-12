package com.wisekit.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisekit.test.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
}
