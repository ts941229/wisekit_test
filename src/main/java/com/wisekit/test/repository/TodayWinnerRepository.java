package com.wisekit.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wisekit.test.model.TodayWinner;

public interface TodayWinnerRepository extends JpaRepository<TodayWinner, Long>{

	@Query("select t from TodayWinner t where t.today_winner_date = :today")
	TodayWinner findByTodayWinnerDate(@Param("today") String today);
	
	

}
