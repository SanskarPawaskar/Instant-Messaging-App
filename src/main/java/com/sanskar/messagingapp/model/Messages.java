package com.sanskar.messagingapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Messages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String content;
	
	private LocalDateTime timeStamp;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	public Messages() {
		
	}

	public Messages(Integer id, String content, LocalDateTime timeStamp, User user, Chat chat) {
		super();
		this.id = id;
		this.content = content;
		this.timeStamp = timeStamp;
		this.user = user;
		this.chat = chat;
	}
	

}
