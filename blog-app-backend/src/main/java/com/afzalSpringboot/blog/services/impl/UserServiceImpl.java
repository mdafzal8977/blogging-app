package com.afzalSpringboot.blog.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.afzalSpringboot.blog.config.AppConstants;
import com.afzalSpringboot.blog.entities.Role;
import com.afzalSpringboot.blog.entities.User;
import com.afzalSpringboot.blog.exceptions.ResourceNotFoundException;
import com.afzalSpringboot.blog.payloads.UserDto;
import com.afzalSpringboot.blog.repositories.RoleRepo;
import com.afzalSpringboot.blog.repositories.UserRepo;
import com.afzalSpringboot.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtotoUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		
		List<User> users = this.userRepo.findAll();
		ArrayList<UserDto> userDtos = new ArrayList<>();
		Iterator<User> itr = users.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			userDtos.add(userToDto(user));
		}
		return userDtos;
		/*List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;*/
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		this.userRepo.deleteById(userId);
		 //this.userRepo.delete(user);

	}
	// Here we generally use Model- Mappers(library) but here for instance I am
		// creating these two methods which will do the needful as model-mappers.
		// These 2 methods are for inter-conversion between Entities and DTOs
		// These methods are kept public so that we can access it outside of the class
		// as well
		// One suggestion from me---you can make it as a util method and static
		// and put it in util package
		/*public User dtotoUser(UserDto userDto) {
			User user = new User();
			user.setId(userDto.getId());
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setAbout(userDto.getAbout());
			return user;
		}

		public UserDto userToDto(User user) {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setPassword(user.getPassword());
			userDto.setAbout(user.getAbout());
			return userDto;

		}*/
		//Using ModelMapper
		//This ModelMapper dependency would require a bean to be declared in a Configuration class(i.e class annotated with @Configufration)
		//@SpringBootApplication also consists of @Configuration,so I declared bean over there only
		public User dtotoUser(UserDto userDto) {
			User user=this.modelMapper.map(userDto, User.class);
			return user;	
		}
		public UserDto userToDto(User user) {
			UserDto userDto=this.modelMapper.map(user, UserDto.class);
			return userDto;
		}


//A bit different than create usere.
//Here we will encode the password as well as assign roles
	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

}
