package ua.yurii.zhurakovsky.service.impl;

import java.util.List;

import ua.yurii.zhurakovsky.dao.BucketDao;
import ua.yurii.zhurakovsky.dao.impl.BucketDaoImpl;
import ua.yurii.zhurakovsky.domain.Bucket;
import ua.yurii.zhurakovsky.service.BucketService;

public class BucketServiceImpl implements BucketService {
	private BucketDao bucketDao;

	public BucketServiceImpl() {
		bucketDao = new BucketDaoImpl();
	}

	@Override
	public Bucket create(Bucket bucket) {
		return bucketDao.create(bucket);
	}

	@Override
	public Bucket read(Integer id) {
		return bucketDao.read(id);
	}

	@Override
	public Bucket update(Bucket bucket) {
		return bucketDao.update(bucket);
	}

	@Override
	public void delete(Integer id) {
		bucketDao.delete(id);
	}

	@Override
	public List<Bucket> readAll() {
		return bucketDao.readAll();
	}
}
