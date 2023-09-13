package com.wisekit.test.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(allocationSize = 1
								, initialValue = 1
								, name = "winner_seq_generator"
								, sequenceName = "winner_seq")
@Getter
@NoArgsConstructor
public class Winner {
	
	@Builder(toBuilder = true)
	protected Winner(long id, Entry entry, int rank) {
		this.id = id;
		this.entry = entry;
		this.rank = rank;
	}
	
	@Id
	@GeneratedValue(generator = "winner_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "entry_id")
	private Entry entry;
	
	private int rank;
	
}
