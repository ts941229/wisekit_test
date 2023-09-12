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
								, name = "winner_seq_generator"
								, sequenceName = "winner_seq")
@Getter
public class Winner {
	
	@Builder(toBuilder = true)
	protected Winner(long id, long entry_id, int rank) {
		this.id = id;
		this.entry_id = entry_id;
		this.rank = rank;
	}
	
	@Id
	@GeneratedValue(generator = "winner_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	private long entry_id;
	private int rank;
	
}
