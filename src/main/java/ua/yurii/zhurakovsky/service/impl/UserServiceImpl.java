package ua.yurii.zhurakovsky.service.impl;

import java.util.List;

import ua.yurii.zhurakovsky.dao.UserDao;
import ua.yurii.zhurakovsky.dao.impl.UserDaoImpl;
import ua.yurii.zhurakovsky.domain.User;
import ua.yurii.zhurakovsky.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao userDao;

	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public User create(User t) {
		return userDao.create(t);
	}

	@Override
	public User read(Integer id) {
		return userDao.read(id);
	}

	@Override
	public User update(User t) {
		return userDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		userDao.delete(id);
	}

	@Override
	public List<User> readAll() {
		return userDao.readAll();
	}

}
