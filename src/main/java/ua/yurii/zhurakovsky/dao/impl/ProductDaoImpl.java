package ua.yurii.zhurakovsky.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.yurii.zhurakovsky.dao.ProductDao;
import ua.yurii.zhurakovsky.domain.Product;
import ua.yurii.zhurakovsky.utils.ConnectionUtils;

public class ProductDaoImpl implements ProductDao {

	private static String CREATE = "insert into product(`name`, `description`, `price`) values (?, ?, ?)";
	private static String READ_BY_ID = "select * from product where id = ?";
	private static String READ_ALL = "select * from product";
	private static String UPDATE_BY_ID = "update product set name=? description=? price=?  where id = ?";
	private static String DELETE_BY_ID = "delete from product where id = ?";

	@Override
	public Product create(Product product) {
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.executeUpdate();
			try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
				product.setId(rs.getInt(1));
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product read(Integer id) {
		Product product = null;
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(READ_BY_ID)) {
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			Integer productId = result.getInt("id");
			String name = result.getString("name");
			String description = result.getString("description");
			Double price = result.getDouble("price");
			product = new Product(productId, name, description, price);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product update(Product product) {
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.executeUpdate();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return product;
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
	public List<Product> readAll() {
		List<Product> products = new ArrayList<>();
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL)) {
			try (ResultSet result = preparedStatement.executeQuery()) {
				while (result.next()) {
					Integer productId = result.getInt("id");
					String name = result.getString("name");
					String description = result.getString("description");
					Double price = result.getDouble("price");
					products.add(new Product(productId, name, description, price));
				}
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return products;
	}

}
