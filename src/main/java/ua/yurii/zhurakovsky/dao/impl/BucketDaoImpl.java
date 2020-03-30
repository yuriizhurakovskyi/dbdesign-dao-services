package ua.yurii.zhurakovsky.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.yurii.zhurakovsky.dao.BucketDao;
import ua.yurii.zhurakovsky.domain.Bucket;
import ua.yurii.zhurakovsky.utils.ConnectionUtils;

public class BucketDaoImpl implements BucketDao {

	private static String CREATE = "insert into bucket(`user_id`, `product_id`, `purchase_date`) values (?,?,?)";
	private static String READ_BY_ID = "select * from bucket where id = ?";
	private static String READ_ALL = "select * from bucket";
	private static String DELETE_BY_ID = "delete from bucket where id = ?";

	@Override
	public Bucket create(Bucket bucket) {
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, bucket.getUserId());
			preparedStatement.setInt(2, bucket.getProductId());
			preparedStatement.setDate(3, new Date(bucket.getPurchaseDate().getTime()));
			preparedStatement.executeUpdate();
			try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
				bucket.setId(rs.getInt(1));
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return bucket;
	}

	@Override
	public Bucket read(Integer id) {
		Bucket bucket = null;
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(READ_BY_ID)) {
			preparedStatement.setInt(1, id);
			try (ResultSet result = preparedStatement.executeQuery()) {
				result.next();
				Integer bucketId = result.getInt("id");
				Integer userId = result.getInt("user_id");
				Integer productId = result.getInt("product_id");
				java.util.Date purchaseDate = result.getDate("purchase_date");
				bucket = new Bucket(bucketId, userId, productId, purchaseDate);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return bucket;
	}

	@Override
	public Bucket update(Bucket bucket) {
		throw new IllegalStateException("There is no update for bucket");
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
	public List<Bucket> readAll() {
		List<Bucket> buckets = new ArrayList<>();
		try (Connection connection = ConnectionUtils.openConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL)) {
			try (ResultSet result = preparedStatement.executeQuery()) {
				while (result.next()) {
					Integer bucketId = result.getInt("id");
					Integer userId = result.getInt("user_id");
					Integer productId = result.getInt("product_id");
					java.util.Date purchaseDate = result.getDate("purchase_date");
					buckets.add(new Bucket(bucketId, userId, productId, purchaseDate));
				}
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return buckets;
	}

}
