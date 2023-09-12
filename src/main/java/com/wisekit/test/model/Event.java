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
								, name = "event_seq_generator"
								, sequenceName = "event_seq")
@Getter
@NoArgsConstructor
public class Event {
	
	@Builder(toBuilder = true)
	protected Event(long id, String name, int period, String start_date, String end_date) {
		this.id = id;
		this.name = name;
		this.period = period;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	@Id
	@GeneratedValue(generator = "event_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private int period;
	private String start_date;
	private String end_date;
	
}
