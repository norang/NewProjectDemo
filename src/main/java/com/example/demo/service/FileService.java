package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.File;
import com.example.demo.repository.FileRepository;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    
    public void save(File file) {
    	fileRepository.save(file);
    }
    
    public List<File> findAll() {
		return fileRepository.findAll();
	}
    
    public Optional<File> findById(String id) {
		return fileRepository.findById(Long.valueOf(id));
	}
    
}
