package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

public interface UserService {

	User save(User user);

	List<User> findAll();

	void deleteUser(Long id);

}
