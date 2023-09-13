package com.wisekit.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wisekit.test.model.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long>{

	@Query("select count(e.entry_date) from Entry e where e.entry_date = :entry_date and e.event.id = :event_id")
	int countByEntryDateAndEventId(@Param("entry_date") String entry_date, @Param("event_id") long event_id);

	@Query("select e from Entry e where e.entry_date = :entry_date and e.event.id = :event_id")
	List<Entry> findAllByEntryDateAndEventId(@Param("entry_date") String entry_date, @Param("event_id") long event_id);

}
