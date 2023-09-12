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
								, name = "entry_seq_generator"
								, sequenceName = "entry_seq")
@Getter
public class Entry {

	@Builder(toBuilder = true)
	protected Entry(long id, long user_id, long event_id, String entry_date) {
		this.id = id;
		this.user_id = user_id;
		this.event_id = event_id;
		this.entry_date = entry_date;
	}
	
	@Id
	@GeneratedValue(generator = "entry_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	private long user_id;
	private long event_id;
	private String entry_date;
	
}
