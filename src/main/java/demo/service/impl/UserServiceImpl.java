package demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.dao.UserDAO;
import demo.entity.User;
import demo.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
    private UserDAO userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
}
