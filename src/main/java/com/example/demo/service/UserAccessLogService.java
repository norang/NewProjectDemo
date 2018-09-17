package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserAccessLog;
import com.example.demo.repository.UserAccessLogRepository;

@Service
public class UserAccessLogService{
    @Autowired
    private UserAccessLogRepository userAccessLogRepository;
    
    public void save(UserAccessLog userAccessLog) {
        userAccessLogRepository.save(userAccessLog);
    }

 
    
}
