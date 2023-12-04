package com.sanskar.messagingapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.sanskar.messagingapp.exception.ChatException;
import com.sanskar.messagingapp.exception.MessageException;
import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.Chat;
import com.sanskar.messagingapp.model.Messages;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.repository.MessageRepository;
import com.sanskar.messagingapp.request.SendMessageRequest;

public class MessageServiceImplementation implements MessageService{
	
	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	
	
	public MessageServiceImplementation(MessageRepository messageRepository, UserService userService,
			ChatService chatService) {
		super();
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}

	public MessageServiceImplementation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Messages sendMessage(SendMessageRequest req) throws UserException, ChatException {
		// TODO Auto-generated method stub
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		
		Messages message = new Messages();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimeStamp(LocalDateTime.now());
		
		return message;
	}

	@Override
	public List<Messages> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException {
		// TODO Auto-generated method stub
		Chat chat = chatService.findChatById(chatId);
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("Invalid message access by :"+chatId);
		}
		List<Messages> messages = messageRepository.findByChatId(chatId);
		
		return messages;
	}

	@Override
	public Messages findMessageById(Integer messageId) throws MessageException {
		Optional<Messages> opt=messageRepository.findById(messageId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("Message not found by id :"+messageId);
	}

	@Override
	public void deleteMessageById(Integer messageId,User reqUser) throws MessageException, UserException {
		Messages messages = findMessageById(messageId);
		if(messages.getUser().getId()==reqUser.getId()) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("You cant delete other user message");
	}

}
