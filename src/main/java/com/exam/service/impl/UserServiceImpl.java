package com.exam.service.impl;

import static java.lang.Boolean.TRUE;
import static org.springframework.data.domain.PageRequest.of;

import java.util.Collection;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User local = this.userRepository.findByUsername(user.getUsername());
		
		if(local !=null) {
			System.out.println("User is already there!!");
			log.info("Saving new server : {}",local.getUsername());
			throw new Exception("User is already there!!");
		}else {
			
			for(UserRole ur:userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			local = this.userRepository.save(user);
		}
		
		return local;
	}

	@Override
	public User getUserByName(String name) {
		log.info("Fetching user by name: {}",name);
		return this.userRepository.findByUsername(name);
	}

	@Override
	public Boolean deleteUser(Long userId) {
		log.info("Deleting user by id: {}",userId);
		this.userRepository.deleteById(userId);
		return TRUE;
	}

	@Override
	public Collection<User> getAll(int limit) {
		return this.userRepository.findAll(of(0,limit)).toList();
	}

}
