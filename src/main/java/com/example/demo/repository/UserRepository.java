package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    User findByUsernameLike(String username);
    
    User findByUsernameIn(String username);
    
    User findByUsernameAndIsLock(String username, char isLock);
    
   
    @Modifying
    @Query("update User u set u.failedLoginAttempt = ?1 ,  isLock = ?2 where u.username = ?3")
    int updateUserSetFailedloginattemptAndIslockForUsername(int failedLoginAttempt, char isLock, String username);
    
    @Modifying
    @Transactional
    @Query("update User u set u.id = ?1 where u.username = ?2")
    int updateUserSetIdForUsername(long id, String username);
    
}
