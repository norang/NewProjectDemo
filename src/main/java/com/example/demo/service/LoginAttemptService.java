package com.example.demo.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {
 
    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
 
    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
          expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }
 
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }
 
    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }
    
    public void resetAttempt (String key) { 
        try { 
            attemptsCache.put (key, 0); 
        } catch (Exception e) {

        } 
    }
 
    public boolean isBlocked(String key) {
        try {
        	logger.info("IP: {}, Login attempt: {}, isLock:{}", key, attemptsCache.get(key), attemptsCache.get(key) >= MAX_ATTEMPT);
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
    
    
}