package net.thumbtack.updateNotifierBackend.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ResourceTagMapper {

	String ADD = "INSERT INTO resource_tag VALUES (#{id},#{tagId})";
	String GET_TAG_IDS_FOR_RESOURCE = "SELECT tag_id FROM resource_tag WHERE resource_id = #{id}";
	String DEL = "DELETE FROM resource_tag WHERE resource_id = #{id}";
	
	@Insert(ADD)
	int add(@Param(value = "id") long id, @Param(value = "tagId") long tagId);

	@Select(GET_TAG_IDS_FOR_RESOURCE)
	List<Long> get(long id);

	@Delete(DEL)
	int delete(long resourceId);
	
}
