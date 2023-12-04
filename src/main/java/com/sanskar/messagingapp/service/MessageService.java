package com.sanskar.messagingapp.service;

import java.util.List;

import com.sanskar.messagingapp.exception.ChatException;
import com.sanskar.messagingapp.exception.MessageException;
import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.Messages;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.request.SendMessageRequest;

public interface MessageService {

	public Messages sendMessage(SendMessageRequest req) throws UserException,ChatException;
	
	public List<Messages> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException;
	
	public Messages findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessageById(Integer messageId, User reqUser) throws MessageException, UserException;
	
}
