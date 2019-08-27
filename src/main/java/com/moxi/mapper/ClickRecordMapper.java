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
		 @Result(property="createTime",column="create_time")
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
	
	@Update("UPDATE `click_record` SET `is_activation`=#{isActivation} WHERE `id` = #{id}")
	int updateByIsActivation(ClickRecord click);

	@Update("UPDATE `click_record` SET `result`=#{result} WHERE `id` = #{id}")
	int updateByResult(ClickRecord click);

	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	@Insert("INSERT INTO `click_record` (`req_url`, `req_param`, `app_id`, `channel_id`, `idfa`, `ua`, `ip`, `callback_address`, `is_activation`, `create_time`, `result`) VALUES (#{reqUrl}, #{reqParam},#{appId},#{channelId},#{idfa} ,#{ua} ,#{ip} ,#{callbackAddress} ,#{isActivation} ,#{createTime} ,#{result} )")
	int insert(ClickRecord click);

}
