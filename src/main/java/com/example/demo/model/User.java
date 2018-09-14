package com.example.demo.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
    private Long id;
    private String username;
    private String password;
    private String title;
    private String passwordConfirm;
    private Set<Role> roles;
    private int failedLoginAttempt;
    private char isLock;
    private String lockedIp;
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
    @Column(nullable = false, columnDefinition = "TINYINT(3)unsigned default 0")
	public int getFailedLoginAttempt() {
		return failedLoginAttempt;
	}

	public void setFailedLoginAttempt(int failedLoginAttempt) {
		this.failedLoginAttempt = failedLoginAttempt;
	}
	
	@Column(nullable = false, columnDefinition = "CHAR(1) default 'N'")
	public char getIsLock() {
		return isLock;
	}

	public void setIsLock(char isLock) {
		this.isLock = isLock;
	}

	@Column(name="lockedIP", columnDefinition = "varchar(50)")
	public String getLockedIp() {
		return lockedIp;
	}

	public void setLockedIp(String lockedIp) {
		this.lockedIp = lockedIp;
	}


    
    
}