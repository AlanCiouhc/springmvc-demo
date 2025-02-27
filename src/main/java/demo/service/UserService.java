package demo.service;

import java.util.List;

import demo.entity.User;

public interface UserService {

	List<User> getAllUsers(); 
    User getUserById(int id);
}
