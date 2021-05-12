package com.moxi.mapper;

import com.moxi.domain.AppChannel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppChannelMapper {


	@Results({
			@Result(property="id",column="id",id=true),
			@Result(property="appInfoId",column="app_info_id",id=true),
			@Result(property="channelId",column="channel_id",id=true),
			@Result(property="status",column="status"),
			@Result(property="callbackRatio",column="callback_ratio"),
			@Result(property="createTime",column="create_time")
	})
	@Select({
		"<script>",
			"SELECT ac.* FROM app_channel ac ",
			"JOIN channel c ON c.id = ac.channel_id ",
			"JOIN application a ON a.id = ac.app_info_id ",
			"WHERE c.`code` = #{channelCode} AND a.app_id = #{appId} ",
		"</script>"
	})
	AppChannel findByCodeAndApp(Map param);



	@Select({
		"<script>",
			"SELECT ac.id,a.app_id as appId,a.app_name as appName ,c.`name` as channelName, ac.`status`,ac.callback_ratio as callbackRatio ",
			"FROM app_channel ac JOIN application a ON a.id = ac.app_info_id JOIN channel c on c.id = a.channel_id ",
			"where ac.channel_id = #{channelId} ",
		"</script>"
	})
	List<Map<String,Object>> findListById(int channelId);


	@Update({
		"<script>",
		"update app_channel SET callback_ratio = #{callbackRatio} where id = #{id}",
		"</script>"
	})
	int updateAcCalRatio(AppChannel appChannel);

	@Select({
		"<script>",
			"SELECT ac.id,c.`code`,a.app_id FROM app_channel ac ",
			"JOIN channel c ON c.id = ac.channel_id ",
			"JOIN application a ON a.id = ac.app_info_id ",
			"WHERE ac.id = #{id}",
		"</script>"
	})
	Map<String,Object> findInfoById(AppChannel appChannel);
}
