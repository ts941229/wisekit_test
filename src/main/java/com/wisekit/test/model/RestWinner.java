package com.wisekit.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;

@Entity
@SequenceGenerator(allocationSize = 1
								, initialValue = 1
								, name = "rest_winner_seq_generator"
								, sequenceName = "rest_winner_seq")
@Getter
public class RestWinner {
	
	@Builder(toBuilder = true)
	protected RestWinner(long id, long event_id, int first_place, int second_place, int third_place, int fourth_place,
			int fifth_place) {
		this.id = id;
		this.event_id = event_id;
		this.first_place = first_place;
		this.second_place = second_place;
		this.third_place = third_place;
		this.fourth_place = fourth_place;
		this.fifth_place = fifth_place;
	}
	
	@Id
	@GeneratedValue(generator = "rest_winner_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	private long event_id;
	private int first_place;
	private int second_place;
	private int third_place;
	private int fourth_place;
	private int fifth_place;
	
}
