package demo.dao;

import java.util.List;

import demo.entity.User;

public interface UserDAO {
	
	List<User> getAllUsers(); 
    User getUserById(int id);
}
