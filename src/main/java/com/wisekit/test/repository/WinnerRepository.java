package com.wisekit.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wisekit.test.model.TodayWinner;
import com.wisekit.test.model.Winner;

public interface WinnerRepository extends JpaRepository<Winner, Long>{
	
	@Query("select count(w) from Winner w left outer join w.entry e where w.rank = :rank and e.entry_date = :entry_date")
	int countByRankAndEntryDate(@Param("rank") int rank, @Param("entry_date") String entry_date);

}
