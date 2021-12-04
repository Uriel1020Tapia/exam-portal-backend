package com.exam.controller;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.exception.NotFoundException;
import com.exam.model.Response;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UserController.PATH_USER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

	 public static final String PATH_USER = "/user";
	 public static final String GET_USER = "/";
	 public static final String CREATE_USER = "/create";
	 public static final String ID_USER = "/{username}";
	 
	@Autowired
	private UserService userService;
	
	  @GetMapping(GET_USER)
	  public ResponseEntity<Response> getUsers(){
	      return ResponseEntity.ok(
	                Response.builder()
	                        .timeStamp(now())
	                        .data(of("users", this.userService.getAll(10)))
	                        .message("Users retrieved")
	                        .status(OK)
	                        .statusCode(OK.value())
	                        .build()
	    		  );
	  }
	//creating user
	@PostMapping(CREATE_USER)
	public ResponseEntity<Response> createUser(@RequestBody User user) throws Exception {
		
		Set<UserRole> roles = new HashSet<>();
		Role role = new Role();
		role.setRoleId(45L);
		role.setRoleName("NORMAL");
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		roles.add(userRole);
//		
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("user", this.userService.createUser(user, roles)))
                        .message("User created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
             );
		
	}
	
	@GetMapping(ID_USER)
	public  ResponseEntity<Response> getUser(@PathVariable("username") String username) {
		
		User user = this.userService.getUserByName(username);
		if(user == null ) {
			 throw new NotFoundException("Invalid user username : " + username);
		}
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("user", user))
                        .message("User retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .developerMessage("urtaav")
                        .build()
             );
	}
	
	@DeleteMapping(ID_USER)
	public ResponseEntity<Response> deleteUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("deleted", this.userService.deleteUser(userId)))
                        .message("User deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
		
}
