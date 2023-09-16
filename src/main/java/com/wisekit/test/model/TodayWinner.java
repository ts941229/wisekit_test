package com.wisekit.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(allocationSize = 1
								, initialValue = 1
								, name = "today_winner_seq_generator"
								, sequenceName = "today_winner_seq")
@NoArgsConstructor
@Getter
public class TodayWinner {

	@Builder(toBuilder = true)
	protected TodayWinner(Long id, String today_winner_date, int first_place, int second_place, int third_place) {
		this.id = id;
		this.today_winner_date = today_winner_date;
		this.first_place = first_place;
		this.second_place = second_place;
		this.third_place = third_place;
	}
	
	@Id
	@GeneratedValue(generator = "today_winner_seq_generator", strategy = GenerationType.IDENTITY)
	private Long id;

	private String today_winner_date;
	private int first_place;
	private int second_place;
	private int third_place;
	
}
