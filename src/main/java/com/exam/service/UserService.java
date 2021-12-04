package com.exam.service;

import java.util.Collection;
import java.util.Set;

import com.exam.model.User;
import com.exam.model.UserRole;

public interface UserService {

	//get all
	Collection<User> getAll(int limit);
	
	//creating user
	public User createUser(User user,Set<UserRole> userRoles) throws Exception;
	
	//get user by name
	public User getUserByName(String name);
	
	//Delete user by id
	public Boolean deleteUser(Long userId);
}
