package com.sanskar.messagingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.sanskar.messagingapp.model.Chat;
import com.sanskar.messagingapp.model.User;


public interface ChatRepository extends JpaRepository<Chat, Integer> {
	
	@Query("select c from Chat c join c.users u where u.id=:userId")
	public List<Chat> findChatByUserId(@Param("userId")Integer userId);
	
	@Query("select c from Chat c where c.isGroup=false And :user Member of c.users And :reqUser Member of c.users")
	public Chat findSingleChatByIds(@Param("user") User user, @Param("reqUser") User reqUser);
	
}
