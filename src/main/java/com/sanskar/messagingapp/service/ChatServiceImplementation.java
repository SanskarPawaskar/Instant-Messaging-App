package com.sanskar.messagingapp.service;

import java.util.List;
import java.util.Optional;

import com.sanskar.messagingapp.exception.ChatException;
import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.Chat;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.repository.ChatRepository;
import com.sanskar.messagingapp.request.GroupChatRequest;


public class ChatServiceImplementation implements ChatService {
	
	private ChatRepository chatRepository;
	private UserService userService;
	
	
	public ChatServiceImplementation() {
		// TODO Auto-generated constructor stub
	}
	

	public ChatServiceImplementation(ChatRepository chatRepository,  UserService userService) {
		super();
		this.chatRepository = chatRepository;
		this.userService = userService;
	}


	@Override
	public Chat createChat(User reqUser, Integer userId2) throws UserException {
		// TODO Auto-generated method stub
		User user = userService.findUserById(userId2);
		Chat isChatExist = chatRepository.findSingleChatByIds(user, reqUser);
		
		if(isChatExist !=null) {
			return isChatExist;
		}
		Chat chat = new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);
		chat.getAdmins().add(reqUser);
		
		return chat;
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		// TODO Auto-generated method stub
		Optional<Chat> chat = chatRepository.findById(chatId);
		if(chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("Chat not found with id :"+chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		User user = userService.findUserById(userId);
		List<Chat> chats = findAllChatByUserId(user.getId());
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
		Chat group = new Chat();
		group.setGroup(true);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedBy(reqUser);
		
		for (Integer userId: req.getUserId()) {
			
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
			
		}
		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws UserException, ChatException {
		// TODO Auto-generated method stub
		Optional<Chat> opt =chatRepository.findById(chatId);
		User user = userService.findUserById(userId);
		if(opt.isPresent()) {
			Chat chat = opt.get();
			if(chat.getAdmins().contains(reqUser)) {
				opt.get().getUsers().add(user);
				return chatRepository.save(chat);
			}else {
				throw new UserException("You are not the admin of group");
			}
		}
		throw new ChatException("Chat not found with id :"+chatId);
		
	}

	@Override
	public Chat renameGroup(Integer chatId,String groupName,User reqUser) throws UserException, ChatException {
		// TODO Auto-generated method stub
		Optional<Chat> opt =chatRepository.findById(chatId);
		if(opt.isPresent()) {
			Chat chat = opt.get();
			if(chat.getUsers().contains(reqUser)) {
				chat.setChat_name(groupName);
				chatRepository.save(chat);
			}
			
		}
		throw new UserException("You are not member of this group");
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
		
		Optional<Chat> opt =chatRepository.findById(chatId);
		
		User user = userService.findUserById(userId);
		
		if(opt.isPresent()) {
			Chat chat = opt.get();
			if(chat.getAdmins().contains(reqUser)) {
				opt.get().getUsers().remove(user);
				return chatRepository.save(chat);
			}
			else if(chat.getUsers().contains(reqUser)) {
				if(user.getId()==(reqUser.getId())) {
					opt.get().getUsers().remove(user);
					return chatRepository.save(chat);
				}
			}
		
				throw new UserException("You cant remove this user");
		}
		throw new ChatException("Chat not found with id :"+chatId);
	}

	@Override
	public void deleteChat(Integer userId, Integer chatId) throws UserException, ChatException {
		// TODO Auto-generated method stub
		Optional<Chat> opt = chatRepository.findById(chatId);
		if(opt.isPresent()) {
			Chat chat = opt.get();
			chatRepository.deleteById(chatId);
		}
		
		
	}

}
