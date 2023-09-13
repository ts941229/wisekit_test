package com.wisekit.test.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(allocationSize = 1
								, initialValue = 1
								, name = "rest_winner_seq_generator"
								, sequenceName = "rest_winner_seq")
@Getter
@NoArgsConstructor
public class RemainingWinner {
	
	@Builder(toBuilder = true)
	protected RemainingWinner(long id, Event event, int first_place, int second_place, int third_place, int fourth_place) {
		this.id = id;
		this.event = event;
		this.first_place = first_place;
		this.second_place = second_place;
		this.third_place = third_place;
		this.fourth_place = fourth_place;
	}
	
	@Id
	@GeneratedValue(generator = "rest_winner_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "event_id")
	private Event event;
	
	private int first_place;
	private int second_place;
	private int third_place;
	private int fourth_place;
	
}
