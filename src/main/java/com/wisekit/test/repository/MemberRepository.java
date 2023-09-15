package com.wisekit.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wisekit.test.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	@Query("select m from Member m left outer join m.entries e left join e.winner w where e.entry_date = :entry_date and w.rank = :rank")
	List<Member> findAllByEntryDateAndRank(@Param("entry_date") String entry_date, @Param("rank") int rank);
	
	@Query("select m from Member m left outer join m.entries e left join e.winner w where w.rank = :rank")
	List<Member> findAllByRank(@Param("rank") int rank);
	
}
