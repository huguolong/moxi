package com.moxi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.moxi.domain.ActivationRecord;

import java.util.Map;

@Mapper
public interface ActivationRecordMapper {
	
	@Select(
		"SELECT COUNT(1) as activationNum,SUM(if(a.is_notice = 1,1,0)) as noticeNum "+
		"FROM activation_record a "+
		"JOIN click_record_2 c ON c.id = a.click_id "+
		"WHERE c.app_id = #{appId} and a.create_time < #{startTime}"
	)
	Map<String,Object> countActivationNum(@Param("appId")String appId,@Param("startTime")String startTime);

	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	@Insert("INSERT INTO `activation_record` (`click_id`, `req_url`, `req_param`, `result`, `is_notice`, `create_time`) "
			+ "VALUES (#{clickId}, #{reqUrl}, #{reqParam}, #{result}, #{isNotice}, #{createTime})")
	int insert(ActivationRecord info);
	
	@Update("UPDATE `activation_record` SET `is_notice` = #{isNotice} WHERE id = #{id}")
	int updateIsNotice(ActivationRecord activation);

	@Update("UPDATE `activation_record` SET `is_notice` = #{isNotice},`result` = #{result}  WHERE id = #{id}")
	int updateByResult(ActivationRecord activation);
	
	@Select("select * from activation_record where click_id = #{clickId}")
	ActivationRecord findByClickIdOrIdfa(@Param("clickId")Integer clickId);
}
