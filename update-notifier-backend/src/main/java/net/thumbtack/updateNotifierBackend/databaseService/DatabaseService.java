package net.thumbtack.updateNotifierBackend.databaseService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.thumbtack.updateNotifierBackend.mappers.UserMapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DatabaseService {
	
	private static final SqlSessionFactory sqlSessionFactory;
	static {
		String resource = "mybatis-cfg.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Great crash should be here! 
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	public DatabaseService() {
//		try {
//			ServiceRegistry serviceRegistry;
//	   	
//			Configuration configuration = new Configuration();
//			configuration.configure();
//			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
//			sqlSessionFactory = configuration.buildSessionFactory(serviceRegistry);
//			sqlSessionFactory = new Configuration().configure().buildSessionFactory(serviceRegistry);
//		} catch (Throwable ex) {
//			throw new ExceptionInInitializerError(ex);
//		}
		getResources();
	}
	
	// Cast from List to List<ResourceInfo>
	public List<Resource> getResources() {
		SqlSession session = sqlSessionFactory.openSession();
		try{
			List<Resource> resourcesList = new LinkedList<Resource>();
			// TODO get resource list
//			resourceInfoList = currentSession
//					.createCriteria(ResourceInfo.class).list();
			
			return resourcesList;
		} finally {
			session.close();
		}
		
	}
	
	public Set<Resource> getResourcesByIdAndTags(Long id, long[] tags) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			//TODO get resources by id and tags
//			AccountInfo account = (AccountInfo) currentSession.get(AccountInfo.class, id);
//			if(account == null) {
//				return null;
//			}
//			return account.getResources();
			return new HashSet<Resource>();
		} finally {
			session.close();
		}
	}
	
	public Long getUserIdByEmail(String email) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			Long userId = mapper.getIdByEmail(email);
			if (userId == null) {
				mapper.addUser(email);
				//TODO what about exception on add?
				userId = mapper.getIdByEmail(email);
			}
			session.commit();
			return userId;
		} finally {
			session.close();
		}
	}
	
	public boolean addResource(long userId, Resource resourceInfo) {
		SqlSession session = sqlSessionFactory.openSession();
		boolean result = false;
		try {
			//TODO
//			ResourceMapper mapper = session.getMapper(ResourceMapper.class);
//			mapper.addResource(userId, resourceInfo);
//			session.commit();
			result = true;
		} finally {
			session.close();
		}
		System.out.println("addResource ");
		return result;
	}
	
	public Set<Tag> getTagsByUser(long userId) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			//TODO
//			AccountInfo account = (AccountInfo) currentSession.get(
//					AccountInfo.class, userId);
//			if (account == null) {
//				return null;
//			}
//			return account.getCategories();
			return new HashSet<Tag>();
		} finally {
			session.close();
		}
	}

	public boolean deleteResource(long resourceId) {
		SqlSession session = sqlSessionFactory.openSession();
		boolean result = false;
		try {
			//TODO
//			currentSession = sqlSessionFactory.openSession();
//			Transaction transaction = currentSession.beginTransaction();
//			currentSession.delete(resourceInfo);
//			transaction.commit();

//			result = true;
		} finally {
			session.close();
		}
		return result;
	}

	public void deleteResourcesByIdAndTags(long userId, long[] tags) {
		// TODO Auto-generated method stub
		
	}

	public void editResource(long userId, long resourceId, Resource fromJson) {
		// TODO Auto-generated method stub
		
	}

	public Resource getResource(long userId, long resourceId) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public Set<Tag> getTags(long userId) {
		return null;
		// TODO Auto-generated method stub
		
		
	}

	public Set<Resource> getResourcesBySheduleCode(int sheduleCode) {
		return null;	
	}
	
}
