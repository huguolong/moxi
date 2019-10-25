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
		 @Result(property="createTime",column="create_time"),
			@Result(property="callbackRatio",column="callback_ratio"),
			@Result(property="type",column="type")
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


	@Results({
			@Result(property="id",column="id",id=true),
			@Result(property="name",column="name"),
			@Result(property="code",column="code"),
			@Result(property="status",column="status"),
			@Result(property="company",column="company"),
			@Result(property="createTime",column="create_time"),
			@Result(property="callbackRatio",column="callback_ratio"),
			@Result(property="type",column="type")
	})
	@Select("SELECT * FROM channel WHERE id = #{id}")
	Channel findById(Channel channel);

	@Results({
			@Result(property="id",column="id",id=true),
			@Result(property="name",column="name"),
			@Result(property="code",column="code"),
			@Result(property="status",column="status"),
			@Result(property="company",column="company"),
			@Result(property="createTime",column="create_time"),
			@Result(property="callbackRatio",column="callback_ratio")
	})
	@Select("SELECT * FROM channel WHERE code = #{code}")
	Channel findByCode(Channel channel);
	
	@Insert("INSERT INTO `channel` (`name`, `code`, `status`, `company`, `describe`,`callback_ratio`,`type`, `create_time`) VALUES (#{name}, #{code}, #{status}, #{company}, #{describe},${callbackRatio},#{type}, #{createTime})")
	int insert(Channel channel);
	
	@Update("UPDATE `channel` SET `name`=#{name}, `code`= #{code}, `status`=#{status}, `company`=#{company}, `describe`=#{describe},`callback_ratio`=#{callbackRatio},`type`=#{type} WHERE (`id`=#{id})")
	int update(Channel channel);
	
	@Update("UPDATE `channel` SET `status`=#{status} WHERE (`id`=#{id})")
	int updateStatus(Channel channel);
	
}
