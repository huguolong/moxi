package com.moxi.mapper;
import com.moxi.domain.ClickRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClickRecordMapper {
	
	
	@Select(
		"SELECT COUNT(1) as clickNum, COUNT(DISTINCT idfa) as pcClickNum "+
		"FROM click_record_1 "+
		"WHERE app_id = #{appId} and create_time < #{startTime}"
	)
	Map<String,Long> countClickNum(@Param("appId")String appId, @Param("startTime")String startTime);
	
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
	@Select("select * from click_record_1 where id = #{id}")
	ClickRecord findById(Integer id);

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
	ClickRecord findByIdBack(Integer id);

	@Select("select count(1) from click_record_1 where app_id = #{appId}")
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
		"SELECT * FROM click_record_1 ",
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
			"select * from click_record_1 ",
			"where create_time BETWEEN #{startTime} and #{endTime} ",
			"and (result is null OR result = ' ')",
			"</script>"
	})
	List<ClickRecord> listByTime(Map param);
	
	@Update("UPDATE `click_record_1` SET `is_activation`=#{isActivation} WHERE `id` = #{id}")
	int updateByIsActivation(ClickRecord click);

	@Update("UPDATE `click_record_1` SET `result`=#{result} WHERE `id` = #{id}")
	int updateByResult(ClickRecord click);

	@Update("UPDATE `click_record` SET `is_activation`=#{isActivation} WHERE `id` = #{id}")
	int updateByIsActivation1(ClickRecord click);

	@Update("UPDATE `click_record` SET `result`=#{result} WHERE `id` = #{id}")
	int updateByResult1(ClickRecord click);

	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	@Insert("INSERT INTO `click_record_1` (`req_url`, `req_param`, `app_id`, `channel_id`, `idfa`, `ua`, `ip`, `callback_address`, `is_activation`, `create_time`, `result`,`channel_code`) VALUES (#{reqUrl}, #{reqParam},#{appId},#{channelId},#{idfa} ,#{ua} ,#{ip} ,#{callbackAddress} ,#{isActivation} ,#{createTime} ,#{result},#{channelCode})")
	int insert(ClickRecord click);

	@Select({
		"<script>",
		"SELECT a.timeDay, a.clickNum, a.pcClickNum, IFNULL(b.activationNum,0) as activationNum, IFNULL(b.noticeNum,0) as noticeNum ",
		"FROM (SELECT COUNT(1) AS clickNum, COUNT(DISTINCT idfa) AS pcClickNum, date_format(cr.create_time, '%Y-%m-%d') AS timeDay ",
		"FROM click_record_1 cr WHERE cr.app_id = #{appId} AND date_format(cr.create_time, '%Y-%m-%d') = #{date} GROUP BY timeDay ) a ",
		"LEFT JOIN ( ",
		"SELECT t.timeDay, SUM(t.is_notice) AS noticeNum, sum(t.is_activation) AS activationNum ",
		"FROM ( SELECT date_format(ar.create_time, '%Y-%m-%d') AS timeDay,(CASE ar.is_notice WHEN 1 THEN 1 WHEN 2 THEN 0 END ) AS is_activation, ar.is_notice ",
		"FROM activation_record ar JOIN click_record_1 cr ON cr.id = ar.click_id ",
		"WHERE cr.app_id = #{appId} AND date_format(ar.create_time, '%Y-%m-%d') = #{date}) t GROUP BY t.timeDay ",
		") b ON b.timeDay = a.timeDay ",
		"</script>"
	})
	List<Map<String,Object>> countAppClickInfo(Map param);

	@Select({
		"<script>",
		"SELECT a.code, a.name as channelName,a.timeDay, a.clickNum, a.pcClickNum, IFNULL(b.activationNum,0) as activationNum, IFNULL(b.noticeNum,0) as noticeNum ",
		"FROM (SELECT c.code, c.name,COUNT(1) AS clickNum, COUNT(DISTINCT idfa) AS pcClickNum, date_format(cr.create_time, '%Y-%m-%d') AS timeDay ",
		"FROM click_record_1 cr LEFT JOIN channel c on cr.channel_code = c.code WHERE cr.app_id = #{appId} AND date_format(cr.create_time, '%Y-%m-%d') = #{date} GROUP BY c.code,timeDay ) a ",
		"LEFT JOIN ( ",
		"SELECT t.code,t.timeDay, SUM(t.is_notice) AS noticeNum, sum(t.is_activation) AS activationNum ",
		"FROM ( SELECT c.code,date_format(ar.create_time, '%Y-%m-%d') AS timeDay,(CASE ar.is_notice WHEN 1 THEN 1 WHEN 2 THEN 0 END ) AS is_activation, ar.is_notice ",
		"FROM activation_record ar JOIN click_record_1 cr ON cr.id = ar.click_id LEFT JOIN channel c on cr.channel_code = c.code ",
		"WHERE cr.app_id = #{appId} AND date_format(ar.create_time, '%Y-%m-%d') = #{date}) t GROUP BY t.code,t.timeDay ",
		") b ON b.timeDay = a.timeDay and b.code = a.code ORDER BY a.`code`,a.timeDay asc",
		"</script>"
	})
	List<Map<String,Object>> countAppClickInfoByChannel(Map param);




}
