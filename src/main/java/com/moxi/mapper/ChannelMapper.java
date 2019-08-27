package com.moxi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.moxi.domain.Channel;

@Mapper
public interface ChannelMapper {
	
	@Select({
		"<script>",
		"SELECT COUNT(*) FROM channel c ",
		"</script>"
	})
	int count(Channel channel);
	
	@Results({
		 @Result(property="id",column="id",id=true),
		 @Result(property="name",column="name"),
		 @Result(property="code",column="code"),
		 @Result(property="status",column="status"),
		 @Result(property="company",column="company"),
		 @Result(property="createTime",column="create_time")
	})
	@Select({
		"<script>",
		"SELECT * FROM channel ",
		"WHERE status >= 0 ",
		"<when test='searchKey != null'>",
			"AND name LIKE CONCAT('%',#{searchKey},'%')",
		"</when>",
		"<when test='status != null'>",
			"AND status = #{status}",
		"</when>",
		"LIMIT #{start},#{end}",
		"</script>"
	})
	List<Channel> list(Channel channel);
	

	@Select("SELECT * FROM channel WHERE id = #{id};")
	Channel findById(Channel channel);
	
	@Insert("INSERT INTO `channel` (`name`, `code`, `status`, `company`, `describe`, `create_time`) VALUES (#{name}, #{code}, #{status}, #{company}, #{describe}, #{createTime})")
	int insert(Channel channel);
	
	@Update("UPDATE `channel` SET `name`=#{name}, `code`= #{code}, `status`=#{status}, `company`=#{company}, `describe`=#{describe} WHERE (`id`=#{id})")
	int update(Channel channel);
	
	@Update("UPDATE `channel` SET `status`=#{status} WHERE (`id`=#{id})")
	int updateStatus(Channel channel);
	
}
