package com.wisekit.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisekit.test.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
