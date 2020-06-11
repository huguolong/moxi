package com.moxi.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.moxi.cache.AppRecallCache;
import com.moxi.mapper.*;
import com.moxi.service.IApplicationService;
import com.moxi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.moxi.domain.AppInfo;
import com.moxi.domain.Channel;
import com.moxi.domain.ClickRecord;
import com.moxi.model.ResObject;
import com.moxi.util.CommonUtil;
import com.moxi.common.config.Constant;
import com.moxi.util.PageUtil;

/**
 * app应用管理
 * @author Administrator
 *
 */

@Controller
public class ApplicationController {

	@Resource
	private ApplicationMapper applicationMapper;
	@Resource
	private ChannelMapper channelService;
	@Resource
	private ClickRecordMapper clickRecordService;
	@Resource
	private ActivationRecordMapper activationRecordService;
	@Resource
    private AppStatisticsMapper appStatisticsMapper;
	@Resource
    private IApplicationService applicationService;

	@Autowired
	private AppRecallCache appRecallCache;
	
	
	@RequestMapping("/admin/applicationManage_{pageCurrent}_{pageSize}_{pageCount}")
	public String applicationManage(AppInfo info,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,@PathVariable Integer pageCount, Model model) {
		
		//判断
		if(pageSize == 0) {pageSize = 10;}
		if(pageCurrent == 0) {pageCurrent = 1;}
		int rows = applicationMapper.count(info);
		if(pageCount == 0) {pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;}
		
		//查询
		info.setStart((pageCurrent - 1)*pageSize);
		info.setEnd(pageSize);
		List<AppInfo> list = applicationMapper.list(info);
		
		//渠道
		Channel channel = new Channel();
		channel.setStart(0);
		channel.setEnd(Integer.MAX_VALUE);
		List<Channel> channels = channelService.list(channel);
		
		//输出
		model.addAttribute("list", list);
		model.addAttribute("channels", channels);
		
		String pageHTML = PageUtil.getPageContent("applicationManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
		model.addAttribute("pageHTML",pageHTML);
		model.addAttribute("info",info);
		
		return "lianyue/applicationManage";
	}
	
	/**
	 * 应用新增、修改跳转
	 * @param model
	 * @return
	 */
	@GetMapping("/admin/appinfoEdit")
	public String channelEdit(Model model,AppInfo info) {
		
		//渠道
		Channel channel = new Channel();
		channel.setStart(0);
		channel.setEnd(Integer.MAX_VALUE);
		channel.setStatus(1);
		List<Channel> channels = channelService.list(channel);
		model.addAttribute("channels",channels);
		if(null != info && null != info.getId() && info.getId() != 0){
			AppInfo appinfo = applicationMapper.findById(info);
			model.addAttribute("info",appinfo);
		}else {
			model.addAttribute("info",new AppInfo());
		}
		return "lianyue/appinfoEdit";
	}
	
	/**
	 * 应用新增、修改提交
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/admin/appinfoEdit")
	public String channelEditPost(Model model,AppInfo info,HttpSession httpSession) {
		if(null != info && null != info.getId() && info.getId() != 0){
			//清除缓存中的回调率
			appRecallCache.delAppRecall(info.getAppId());
            applicationMapper.update(info);
		} else {
            applicationMapper.insert(info);
		}
		return "redirect:applicationManage_0_0_0";
		
	}
	
	@ResponseBody
	@PostMapping("/admin/appinfoEditstatus")
	public ResObject<Object> channelEditstatus(AppInfo info) {
        applicationMapper.updateStatus(info);
		ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
		return object;
	}
	
	/**
	 * 应用点击记录
	 */

	@RequestMapping("/admin/appClickRecord_{pageCurrent}_{pageSize}_{pageCount}")
	public String appClickRecord(ClickRecord click,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,@PathVariable Integer pageCount, Model model) {
		
		//判断
		if(pageSize == 0) {pageSize = 10;}
		if(pageCurrent == 0){ pageCurrent = 1;}
		int rows = clickRecordService.count(click.getAppId());
		if(pageCount == 0) {pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;}
		
		//查询
		click.setStart((pageCurrent - 1)*pageSize);
		click.setEnd(pageSize);
		List<ClickRecord> list = clickRecordService.list(click);
		//输出
		model.addAttribute("list", list);
		String pageHTML = PageUtil.getPageContent("appClickRecord_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
		model.addAttribute("pageHTML",pageHTML);
		
		return "lianyue/clickRecord";
	}
	
	/**
	 * 查询应用获取统计信息
	 */
	@RequestMapping("/admin/statisticsManage_{pageCurrent}_{pageSize}_{pageCount}")
	public String statisticsManage(AppInfo info,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,@PathVariable Integer pageCount, Model model) {
		
		
		//判断
		if(pageSize == 0) {pageSize = 10;}
		if(pageCurrent == 0) {pageCurrent = 1;}
		int rows = applicationMapper.count(info);
		if(pageCount == 0) {pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;}
		
		//查询
		info.setStart((pageCurrent - 1)*pageSize);
		info.setEnd(pageSize);
		List<AppInfo> list = applicationMapper.list(info);

		//渠道
		Channel channel = new Channel();
		channel.setStart(0);
		channel.setEnd(Integer.MAX_VALUE);
		List<Channel> channels = channelService.list(channel);
		
		
		//输出
		model.addAttribute("list", list);
		model.addAttribute("channels", channels);
		String pageHTML = PageUtil.getPageContent("statisticsManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
		model.addAttribute("pageHTML",pageHTML);
		model.addAttribute("info",info);
		
		return "lianyue/statisticsManage";
	}


	@RequestMapping("/admin/statisticsDetail")
	public String statisticsManage(String appId,String month, Model model) {

		if(StringUtils.isEmpty(month)){
			month = CommonUtil.getCurrentMonth();
		}
		Map<String,String> param = new HashMap<>();
		param.put("appId",appId);
		param.put("month",month);
		List<Map<String,Object>> list = appStatisticsMapper.getTotalStatistics(param);
        Map<String,Object> all = new HashMap<>();
        all.put("timeDay","总和");
        Long click=0L,pcClick=0L,activation=0L,notice=0L;
		for (Map<String,Object> map:list){
            int clickNum = (Integer) map.get("clickNum");
            int pcClickNum = (Integer) map.get("pcClickNum");
            int activationNum = (Integer) map.get("activationNum");
            int noticeNum = (Integer)  map.get("noticeNum");
            click+=clickNum;
            pcClick+=pcClickNum;
            activation+=activationNum;
            notice+=noticeNum;
        }
        all.put("clickNum",click);
		all.put("pcClickNum",pcClick);
        all.put("activationNum",activation);
        all.put("noticeNum",notice);
        all.put("conversion", CommonUtil.getPercent(activation, pcClick));

		//输出
		model.addAttribute("list", list);
		model.addAttribute("appId", appId);
		model.addAttribute("month", month);

		return "lianyue/statisticsDetail";
	}

	@RequestMapping("/admin/channelStatisticsDetail")
	public String channelStatisticsManage(String appId,String month, Model model) {

		if(StringUtils.isEmpty(month)){
			month = CommonUtil.getCurrentMonth();
		}
		Map<String,String> param = new HashMap<>();
		param.put("appId",appId);
		param.put("month",month);
		List<Map<String,Object>> list = appStatisticsMapper.getChannelStatistics(param);

		//输出
		model.addAttribute("dataList", list);
		model.addAttribute("appId", appId);
		model.addAttribute("month", month);

		return "lianyue/channelStatisticsDetail";
	}

    @ResponseBody
    @GetMapping("/application/init")
    public ResObject<Object> initStatisticsApp(String day) {
		if(StringUtil.isNull(day)){
			applicationService.pressAllStatisticsApp();
		}else{
			applicationService.pressDayStatisticsApp(day);
		}
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }



}
