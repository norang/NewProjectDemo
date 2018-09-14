package com.example.demo.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findById(Long.parseLong(user.getTitle())));
        user.setRoles(roles);
        
        
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void addFailedLoginAttempt(String username) {
		User user = findByUsername(username);
		
		if (user!=null) {
			
			if (user.getFailedLoginAttempt() <3 ) {
				
				if (user.getIsLock() == CommonUtil.FALSE) {
					logger.info("addFailedLoginAttempt: OldFailedLoginAttempt = {}, NewFailedLoginAttempt = {}", user.getFailedLoginAttempt(), user.getFailedLoginAttempt()+1);
					user.setFailedLoginAttempt(user.getFailedLoginAttempt()+1);
					
					if (user.getFailedLoginAttempt() >= CommonUtil.MAX_FAIL_ATTEMPT)
						user.setIsLock(CommonUtil.TRUE);
					
					userRepository.save(user);
				}
			}else if (user.getIsLock() == CommonUtil.FALSE) {
				user.setIsLock(CommonUtil.TRUE);
				userRepository.save(user);
			}
		}
	}
	
	@Override
	public void resetFailedLoginAttempt(String username) {
		User user = findByUsername(username);
		
		if (user!=null) {
			logger.info("resetFailedLoginAttempt: OldFailedLoginAttempt = {}, NewFailedLoginAttempt = {}", user.getFailedLoginAttempt(), 0);
			user.setFailedLoginAttempt(0);
			user.setIsLock(CommonUtil.FALSE);
			userRepository.save(user);
		}
	}
    
}
