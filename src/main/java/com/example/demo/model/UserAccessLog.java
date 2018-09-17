package com.example.demo.model;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;
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
	
	@ManyToOne
	@JoinColumn(name="id")
	private User user = new User();

   
	
    public UserAccessLog(User user, Timestamp accessDatetime, String userIpAddress) {
		super();
		this.user = user;
		this.accessDatetime = accessDatetime;
		this.userIpAddress = userIpAddress;
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
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
    

}