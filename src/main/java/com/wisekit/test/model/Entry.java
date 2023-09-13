package com.wisekit.test.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(allocationSize = 1
								, initialValue = 1
								, name = "entry_seq_generator"
								, sequenceName = "entry_seq")
@Getter
@NoArgsConstructor
public class Entry {

	@Builder(toBuilder = true)
	protected Entry(long id, Member member, Event event, String entry_date) {
		this.id = id;
		this.member = member;
		this.event = event;
		this.entry_date = entry_date;
	}
	
	@Id
	@GeneratedValue(generator = "entry_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "event_id")
	private Event event;
	
	private String entry_date;
	
	@OneToOne(mappedBy = "entry")
	private Winner winner;
}
