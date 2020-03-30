package ua.yurii.zhurakovsky.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.yurii.zhurakovsky.dao.UserDao;
import ua.yurii.zhurakovsky.domain.User;
import ua.yurii.zhurakovsky.utils.ConnectionUtils;

public class UserDaoImpl implements UserDao {

	private static String CREATE = "insert into user(`email`, `first_name`, `last_name`, `role`) values (?, ?, ?, ?)";
	private static String READ_BY_ID = "select * from user where id = ?";
	private static String READ_ALL = "select * from user";
	private static String UPDATE_BY_ID = "update user set email=? first_name=? last_name=? role=?   where id = ?";
	private static String DELETE_BY_ID = "delete from user where id = ?";

	@Override
	public User create(User user) {
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.executeUpdate();
			try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
				rs.next();
				user.setId(rs.getInt(1));
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User read(Integer id) {
		User user = null;
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(READ_BY_ID)) {
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			Integer userId = result.getInt("id");
			String email = result.getString("email");
			String firstName = result.getString("first_name");
			String lastName = result.getString("last_name");
			String role = result.getString("role");
			user = new User(userId, email, firstName, lastName, role);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User update(User user) {
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.executeUpdate();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void delete(Integer id) {
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> readAll() {
		List<User> users = new ArrayList<>();
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL)) {
			try (ResultSet result = preparedStatement.executeQuery()) {
				while (result.next()) {
					Integer userId = result.getInt("id");
					String email = result.getString("email");
					String firstName = result.getString("first_name");
					String lastName = result.getString("last_name");
					String role = result.getString("role");
					users.add(new User(userId, email, firstName, lastName, role));
				}
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

}
