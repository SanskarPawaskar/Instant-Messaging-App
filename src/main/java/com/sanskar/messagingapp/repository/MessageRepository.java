package com.sanskar.messagingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sanskar.messagingapp.model.Messages;

public interface MessageRepository extends JpaRepository<Messages, Integer> {
	
	@Query("select m form Messages m join m.chat where c.id=:chatId")
	public List<Messages> findByChatId(@Param("chatId")Integer chatId);

}
