package ua.yurii.zhurakovsky.service.impl;

import java.util.List;

import ua.yurii.zhurakovsky.dao.ProductDao;
import ua.yurii.zhurakovsky.dao.impl.ProductDaoImpl;
import ua.yurii.zhurakovsky.domain.Product;
import ua.yurii.zhurakovsky.service.ProductService;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;

	public ProductServiceImpl() {
		productDao = new ProductDaoImpl();
	}

	@Override
	public Product create(Product product) {
		return productDao.create(product);
	}

	@Override
	public Product read(Integer id) {
		return productDao.read(id);
	}

	@Override
	public Product update(Product product) {
		return productDao.update(product);
	}

	@Override
	public void delete(Integer id) {
		productDao.delete(id);
	}

	@Override
	public List<Product> readAll() {
		return productDao.readAll();
	}
}
