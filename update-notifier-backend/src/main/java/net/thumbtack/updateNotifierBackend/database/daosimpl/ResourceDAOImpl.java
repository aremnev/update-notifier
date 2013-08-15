package net.thumbtack.updateNotifierBackend.database.daosimpl;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import net.thumbtack.updateNotifierBackend.database.daos.ResourceDAO;
import net.thumbtack.updateNotifierBackend.database.entities.Resource;
import net.thumbtack.updateNotifierBackend.database.mappers.ResourceMapper;

public class ResourceDAOImpl implements ResourceDAO {

	private final ResourceMapper mapper;

	public ResourceDAOImpl(SqlSession session) {
		mapper = session.getMapper(ResourceMapper.class);
	}

	public boolean add(Resource resource) {
		return mapper.add(resource) > 0;
	}

	public Resource get(long userId, long resourceId) {
		return mapper.get(userId, resourceId);
	}

	public List<Resource> getByUserIdAndTags(Long userId, List<Long> tagIds) {
		return mapper.getByUserIdAndTags(makeString(tagIds));
	}

	public List<Resource> getAllForUser(long userId) {
		return mapper.getAllForUser(userId);
	}

	public Set<Resource> getByscheduleCode(byte scheduleCode) {
		// TODO Does it return collection of resources?
		return mapper.getByscheduleCode(scheduleCode);
	}

	public boolean edit(Resource resource) {
		return mapper.update(resource) > 0;
	}

	public boolean updateAfterCheck(Long id, Integer hash) {
		return mapper.updateAfterUpdate(id, hash) > 0;
	}

	public boolean exists(long resourceId) {
		return mapper.check(resourceId);
	}
	
	public boolean delete(Resource resource) {
		return mapper.delete(resource) > 0;
	}

	public boolean deleteByTags(long userId, List<Long> tagIds) {
		return mapper.deleteByTags(userId, makeString(tagIds)) > 0;
	}
	
	public boolean deleteAll(long userId) {
		return mapper.deleteAll(userId) > 0;
	}

	private <T> String makeString(List<T> array) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (T item : array) {
			stringBuilder.append(item);
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

}