package com.sanskar.messagingapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sanskar.messagingapp.exception.ChatException;
import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.Chat;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.request.GroupChatRequest;
import com.sanskar.messagingapp.request.SingleChatRequest;
import com.sanskar.messagingapp.response.ApiResponse;
import com.sanskar.messagingapp.service.ChatService;
import com.sanskar.messagingapp.service.UserService;

@RestController("/api/chats")
public class ChatController {

	private ChatService chatService;
	private UserService userService;
	public ChatController(ChatService chatService, UserService userService) {
		super();
		this.chatService = chatService;
		this.userService = userService;
	}
	
	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,@RequestHeader("Authorization") String jwt) throws UserException{
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
		
	}
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupChatHandler(@RequestBody GroupChatRequest groupChatRequest,@RequestHeader("Authorization") String jwt) throws UserException{
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createGroup(groupChatRequest, reqUser);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
		
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		
		Chat chat = chatService.findChatById(chatId);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
		
	}
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findALlChatByUserIdHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		User reqUser = userService.findUserProfile(jwt);
		List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
		return new ResponseEntity<List<Chat>>(chats,HttpStatus.OK);
		
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);		
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFromGrouptHandler(@PathVariable Integer chatId,@PathVariable Integer userId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.removeFromGroup(chatId, userId, reqUser);
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);		
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		User reqUser = userService.findUserProfile(jwt);
		 ApiResponse res = new ApiResponse("Chat has been deleted", true);
		 return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}
	
	
	
}
