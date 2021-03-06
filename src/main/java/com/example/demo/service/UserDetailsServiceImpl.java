package com.example.demo.service;



import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.CommonUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LoginAttemptService loginAttemptService;
    
    @Autowired
    private HttpServletRequest request;
    
    

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
    	
    	String ip = getClientIP();
    	if (loginAttemptService.isBlocked(ip)) {
           throw new RuntimeException(CommonUtil.ACCOUNT_STATUS_BLOCKED_IP + CommonUtil.DELIMITER + ip);
        }
    	
    	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    	User user = userRepository.findByUsername(username);
    	
    	if (user == null) {
    		throw new UsernameNotFoundException("Invalid User");
    	}

        if (user.getIsLock() == CommonUtil.TRUE || user.getIsLock() == ' ') {
        	throw new RuntimeException(CommonUtil.ACCOUNT_STATUS_BLOCKED_ACCOUNT + CommonUtil.DELIMITER + username);
        }
        
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

    	return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
    
    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
