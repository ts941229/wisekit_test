package com.wisekit.test.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(allocationSize = 1
								, initialValue = 1
								, name = "member_seq_generator"
								, sequenceName = "member_seq")
@Getter
@NoArgsConstructor
public class Member {

	@Builder(toBuilder = true)
	protected Member(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@GeneratedValue(generator = "member_seq_generator", strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@OneToMany(mappedBy = "member")
	List<Entry> entries = new ArrayList<Entry>();
	
}
