package com.example.demo.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "user")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    
    @Transient
    private String title;
    
    @Transient
    private String passwordConfirm;
    
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    
    @OneToMany(mappedBy = "accessUser")
    private Set<UserAccessLog> userAccessLogs;
    
    @NotNull
    @Column(columnDefinition = "TINYINT(3)unsigned default 0")
    private int failedLoginAttempt;
    
    @NotNull
    @Column(columnDefinition = "CHAR(1) default 'N'")
    private char isLock;
    
    @Column(name="lockedIP", columnDefinition = "varchar(50)")
    private String lockedIp;
    

    
    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getFailedLoginAttempt() {
		return failedLoginAttempt;
	}

	public void setFailedLoginAttempt(int failedLoginAttempt) {
		this.failedLoginAttempt = failedLoginAttempt;
	}
	
	
	public char getIsLock() {
		return isLock;
	}

	public void setIsLock(char isLock) {
		this.isLock = isLock;
	}

	
	public String getLockedIp() {
		return lockedIp;
	}

	public void setLockedIp(String lockedIp) {
		this.lockedIp = lockedIp;
	}
	
	
	public Set<UserAccessLog> getUserAccessLogs() {
		return userAccessLogs;
	}

	public void setUserAccessLogs(Set<UserAccessLog> userAccessLogs) {
		this.userAccessLogs = userAccessLogs;
	}


    
    
}