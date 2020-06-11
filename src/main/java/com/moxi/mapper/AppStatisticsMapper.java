package com.moxi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppStatisticsMapper {

    /**
     * 添加渠道统计数据
     */
    @Insert("INSERT INTO app_channel_statistics (`app_id`, `code`, `channelName`, `month`, `timeDay`, `clickNum`, `pcClickNum`, `activationNum`, `noticeNum`, `conversion`, `create_time`) " +
            "VALUES (#{appId}, #{code}, #{channelName}, #{month}, #{timeDay}, #{clickNum}, #{pcClickNum}, #{activationNum}, #{noticeNum}, #{conversion}, NOW())")
    int insertChannelStatistics(Map param);

    /**
     * 添加数据统计数据
     */
    @Insert("INSERT INTO app_total_statistics (`app_id`, `month`, `timeDay`, `clickNum`, `pcClickNum`, `activationNum`, `noticeNum`, `conversion`, `create_time`) " +
            "VALUES (#{appId}, #{month}, #{timeDay}, #{clickNum}, #{pcClickNum}, #{activationNum}, #{noticeNum}, #{conversion}, NOW())")
    int insertTotalStatistics(Map param);

    /**
     * 按月份查询渠道统计数据
     */
    @Select({
            "<script>",
            "SELECT * FROM app_channel_statistics WHERE app_id = #{appId} AND `month` = #{month} ORDER BY code,timeDay ASC",
            "</script>"
    })
    List<Map<String,Object>> getChannelStatistics(Map param);

    /**
     * 按月份查询总数统计数据
     */
    @Select({
            "<script>",
            "SELECT * FROM app_total_statistics WHERE app_id = #{appId} AND `month` = #{month} ORDER BY timeDay ASC",
            "</script>"
    })
    List<Map<String,Object>> getTotalStatistics(Map param);

}
