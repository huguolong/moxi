package com.moxi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.moxi.domain.ClickRecord;

@Mapper
public interface ClickRecordMapper {
	
	
	@Select({
		"<script>",
		"SELECT ",
		"COUNT(1) as clickNum, COUNT(DISTINCT idfa) as pcClickNum ",
		"FROM click_record ",
		"WHERE app_id = #{appId} ",
		"</script>"
	})
	Map<String,Long> countClickNum(String appId);
	
	@Results({
		 @Result(property="id",column="id",id=true),
		 @Result(property="reqUrl",column="req_url"),
		 @Result(property="reqParam",column="req_param"),
		 @Result(property="appId",column="app_id"),
		 @Result(property="channelId",column="channel_id"),
		 @Result(property="idfa",column="idfa"),
		 @Result(property="result",column="result"),
		 @Result(property="callbackAddress",column="callback_address"),
		 @Result(property="isActivation",column="is_activation"),
		 @Result(property="createTime",column="create_time"),
			@Result(property="channelCode",column="channel_code")
	})
	@Select("select * from click_record where id = #{id}")
	ClickRecord findById(Integer id);

	@Select("select count(1) from click_record where app_id = #{appId}")
	int count(String appId);
	
	@Results({
		 @Result(property="id",column="id"),
		 @Result(property="reqUrl",column="req_url"),
		 @Result(property="reqParam",column="req_param"),
		 @Result(property="appId",column="app_id"),
		 @Result(property="channelId",column="channel_id"),
		 @Result(property="idfa",column="idfa"),
		 @Result(property="result",column="result"),
		 @Result(property="callbackAddress",column="callback_address"),
		 @Result(property="isActivation",column="is_activation"),
		 @Result(property="createTime",column="create_time")
	})
	@Select({
		"<script>",
		"SELECT * FROM click_record ",
		"WHERE app_id = #{appId} ",
		"LIMIT #{start},#{end}",
		"</script>"
	})
	List<ClickRecord> list(ClickRecord click);



	@Results({
			@Result(property="id",column="id"),
			@Result(property="appId",column="app_id"),
			@Result(property="idfa",column="idfa"),
			@Result(property="ip",column="ip"),
			@Result(property="ua",column="ua"),
			@Result(property="callbackAddress",column="callback_address")
	})
	@Select({
			"<script>",
			"select * from click_record ",
			"where create_time BETWEEN #{startTime} and #{endTime} ",
			"and (result is null OR result = ' ')",
			"</script>"
	})
	List<ClickRecord> listByTime(Map param);
	
	@Update("UPDATE `click_record` SET `is_activation`=#{isActivation} WHERE `id` = #{id}")
	int updateByIsActivation(ClickRecord click);

	@Update("UPDATE `click_record` SET `result`=#{result} WHERE `id` = #{id}")
	int updateByResult(ClickRecord click);

	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	@Insert("INSERT INTO `click_record` (`req_url`, `req_param`, `app_id`, `channel_id`, `idfa`, `ua`, `ip`, `callback_address`, `is_activation`, `create_time`, `result`,`channel_code`) VALUES (#{reqUrl}, #{reqParam},#{appId},#{channelId},#{idfa} ,#{ua} ,#{ip} ,#{callbackAddress} ,#{isActivation} ,#{createTime} ,#{result},#{channelCode})")
	int insert(ClickRecord click);

	@Select({
		"<script>",
		"SELECT timeDay,count(1) AS clickNum,COUNT(DISTINCT idfa) AS pcClickNum,sum(is_activation) AS activationNum ",
		"FROM ( SELECT cr.id,cr.idfa,cr.is_activation,date_format(cr.create_time, '%Y-%m-%d') AS timeDay ",
		"FROM click_record cr WHERE cr.app_id = #{appId} and date_format(cr.create_time,'%Y-%m') = #{month} ",
		") t GROUP BY timeDay ",
		"UNION All ",
		"SELECT '总和' as timeDay,count(1) as clickNum,COUNT(DISTINCT idfa) AS pcClickNum,sum(is_activation) AS activationNum ",
		"FROM click_record cr WHERE cr.app_id = #{appId} and date_format(cr.create_time,'%Y-%m') = #{month} ",
		"</script>"
	})
	List<Map<String,Object>> countAppClickInfo(Map param);
}
