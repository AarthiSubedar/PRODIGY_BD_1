package com.example.myfirstapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myfirstapp.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final Map<String,User> userStore = new HashMap<>();

	@PostMapping
	 public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        userStore.put(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
	
	@GetMapping
	public ResponseEntity<?> getAllUsers(){
		 return new ResponseEntity<>(new ArrayList<>(userStore.values()), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
		User user = userStore.remove(id);
		if(user == null) {
			return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("User deleted Successfully.",HttpStatus.OK);
		
	}
	
	// Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userStore.get(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @Valid @RequestBody User updatedUser) {
        User existingUser = userStore.get(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAge(updatedUser.getAge());
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }
}
