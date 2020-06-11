package com.moxi.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.moxi.domain.AppInfo;
import com.moxi.domain.Channel;

@Mapper
public interface ApplicationMapper {
	


	@Results({  
	       @Result(property="id",column="id",id=true), 
	       @Result(property="channelId",column="channel_id"),  
	       @Result(property="appId",column="app_id"),
	       @Result(property="appName",column="app_name"),
	       @Result(property="callbackRatio",column="callback_ratio"),
	       @Result(property="reqType",column="req_type"),
	       @Result(property="reqUrl",column="req_url"),
	       @Result(property="reqParam",column="req_param"),
	       @Result(property="reqRatio",column="req_ratio"),
	       @Result(property="status",column="status"),
	       @Result(property="createTime",column="create_time"),
	       @Result(property="channelName",column="channelName"),
	}) 
	@Select("SELECT * FROM application where id = #{id}")
	AppInfo findById(AppInfo info);

	@Results({
			@Result(property="id",column="id",id=true),
			@Result(property="channelId",column="channel_id"),
			@Result(property="appId",column="app_id"),
			@Result(property="appName",column="app_name"),
			@Result(property="callbackRatio",column="callback_ratio"),
			@Result(property="reqType",column="req_type"),
			@Result(property="reqUrl",column="req_url"),
			@Result(property="reqParam",column="req_param"),
			@Result(property="reqRatio",column="req_ratio"),
			@Result(property="status",column="status"),
			@Result(property="createTime",column="create_time"),
			@Result(property="channelName",column="channelName"),
	})
	@Select("SELECT * FROM application where app_id = #{appId}")
	AppInfo findByAppId(AppInfo info);
	 
	@Select({
		"<script>",
		"SELECT COUNT(*) FROM application a ",
		"</script>"
	})
	int count(AppInfo appl);
	
	@Results({  
	       @Result(property="id",column="id",id=true), 
	       @Result(property="channelId",column="channel_id"),  
	       @Result(property="appId",column="app_id"),
	       @Result(property="appName",column="app_name"),
	       @Result(property="callbackRatio",column="callback_ratio"),
	       @Result(property="reqType",column="req_type"),
	       @Result(property="reqUrl",column="req_url"),
	       @Result(property="reqParam",column="req_param"),
	       @Result(property="reqRatio",column="req_ratio"),
	       @Result(property="status",column="status"),
	       @Result(property="createTime",column="create_time"),
	       @Result(property="channelName",column="channelName"),
			@Result(property="clickNum",column="clickNum"),
			@Result(property="pcClickNum",column="pcClickNum"),
			@Result(property="activationNum",column="activationNum"),
			@Result(property="noticeNum",column="noticeNum"),
			@Result(property="conversion",column="conversion")
	}) 
	@Select({
		"<script>",
		"SELECT A.*,c.name as channelName FROM application A ",
		"LEFT JOIN channel c ON A.channel_id = c.id ",
		"WHERE A.status > 0 ",
			"<when test='searchKey != null'>",
				"AND (A.app_name LIKE CONCAT('%',#{searchKey},'%') or A.app_id LIKE CONCAT('%',#{searchKey},'%'))",
			"</when>",
			"<when test='channelId != null'>",
				"AND A.channel_id = #{channelId}",
			"</when>",
		"LIMIT #{start},#{end}",
		"</script>"
	})
	List<AppInfo> list(AppInfo appl);

	@Results({
			@Result(property="id",column="id",id=true),
			@Result(property="channelId",column="channel_id"),
			@Result(property="appId",column="app_id"),
			@Result(property="appName",column="app_name"),
			@Result(property="callbackRatio",column="callback_ratio"),
			@Result(property="reqType",column="req_type"),
			@Result(property="reqUrl",column="req_url"),
			@Result(property="reqParam",column="req_param"),
			@Result(property="reqRatio",column="req_ratio"),
			@Result(property="status",column="status"),
			@Result(property="createTime",column="create_time"),
			@Result(property="channelName",column="channelName"),
	})
	@Select({
		"<script>",
		"SELECT A.* FROM application A WHERE A.status > 0 ",
		"</script>"
	})
	List<AppInfo> getAppList();
	
	@Insert("INSERT INTO `application` ( `channel_id`, `app_id`, `app_name`, `callback_ratio`, `req_type`, `req_url`, `req_param`, `req_ratio`, `status`, `create_time`) "
			+ "VALUES (#{channelId}, #{appId}, #{appName}, #{callbackRatio}, #{reqType}, #{reqUrl}, #{reqParam}, #{reqRatio}, #{status}, #{createTime})")
	int insert(AppInfo info);
	
	@Update("UPDATE `application` SET `channel_id`=#{channelId}, `app_id`=#{appId}, `app_name`=#{appName}, `callback_ratio`=#{callbackRatio}, `req_type`=#{reqType}, `req_url`=#{reqUrl}, `req_param`=#{reqParam}, `req_ratio`=#{reqRatio}, `status`=#{status} WHERE (`id`=#{id})")
	int update(AppInfo info);
	
	@Update("UPDATE `application` SET `status`=#{status} WHERE (`id`=#{id})")
	int updateStatus(AppInfo info);

	@Update("UPDATE `application` SET `clickNum` = #{clickNum}, `pcClickNum` = #{pcClickNum}, `activationNum`=#{activationNum}, `noticeNum`=#{noticeNum}, `conversion`=#{conversion}  WHERE `id`=#{id}")
	int updateStatistics(AppInfo info);
}
