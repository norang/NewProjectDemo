package com.example.demo.model;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "UserAccessLog")
public class UserAccessLog {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "int(10)unsigned")
    private Long userAccessLogId;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
    private Date accessDatetime = new Date();
	
	@NotNull
	@Size(max = 50)
    private String userIpAddress;
	
	@NotNull
	@Column(columnDefinition = "CHAR(1) default 'N'")
	private char status;
	
	@ManyToOne
	@JoinColumn(name="id")
	private User accessUser;

   
	
    public UserAccessLog(User user, Timestamp accessDatetime, String userIpAddress, char status) {
		super();
		this.accessUser = user;
		this.accessDatetime = accessDatetime;
		this.userIpAddress = userIpAddress;
		this.status = status;
	}

	public Long getUserAccessLogId() {
        return userAccessLogId;
    }

    public void setUserAccessLogId(Long userAccessLogId) {
        this.userAccessLogId = userAccessLogId;
    }

	public Date getAccessDatetime() {
		return accessDatetime;
	}

	public void setAccessDatetime(Date accessDatetime) {
		this.accessDatetime = accessDatetime;
	}

	public String getUserIpAddress() {
		return userIpAddress;
	}

	public void setUserIpAddress(String userIpAddress) {
		this.userIpAddress = userIpAddress;
	}

	public User getUser() {
		return accessUser;
	}

	public void setUser(User user) {
		this.accessUser = user;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}


}