package com.wisekit.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisekit.test.model.Winner;

public interface WinnerRepository extends JpaRepository<Winner, Long>{

}
