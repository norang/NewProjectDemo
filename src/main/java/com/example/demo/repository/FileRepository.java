package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.File;


@Repository
public interface FileRepository extends JpaRepository<File, Long>{
	
	Optional<File> findById(Long id);
}
