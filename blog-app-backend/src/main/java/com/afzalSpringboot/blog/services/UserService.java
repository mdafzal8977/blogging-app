package com.afzalSpringboot.blog.services;

import java.util.List;

import com.afzalSpringboot.blog.payloads.UserDto;
//We don't give entity directly to service,although we can.Rather we will use 
//DTO for data transfer..bcoz field wahi hain in UserDto but hum apne main entity
//me kuch zada kar skte hain like suppose hum kuch dynamically fields calculate 
//krre hain ,jo user se na le re ho. So.UserDto me humko jo user se lena ya dena
//hai whi field use krenge.So,we can say that Dto's data we can expose directly
//but we will not expose entities directly.Entities will only correspond to
//database.AnyWhere data transfer will be done,we will use DTOs
//Also,may be we don't want to send  password to the client so we can remove password field from DTO

public interface UserService {

	UserDto registerNewUser(UserDto user);
	
	
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);

}
