package com.sanskar.messagingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanskar.messagingapp.exception.ChatException;
import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.Chat;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.request.GroupChatRequest;

@Service
public interface ChatService {
	
	public Chat createChat(User reqUser, Integer userId2) throws UserException;
	
	public Chat findChatById(Integer chatId) throws ChatException;
	
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
	
	public Chat createGroup(GroupChatRequest req,User reqUser) throws UserException;
	
	public Chat addUserToGroup(Integer userId,Integer chatId,User reqUser) throws UserException, ChatException;
	
	public Chat renameGroup(Integer chatId,String groupName,User reqUser ) throws UserException, ChatException;

	public Chat removeFromGroup(Integer chatId ,Integer userId, User reqUser) throws UserException, ChatException;
	
	public void deleteChat(Integer userId,Integer chatId) throws UserException, ChatException;
	
	
	

}
