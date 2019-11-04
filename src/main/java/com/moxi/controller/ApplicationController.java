package com.moxi.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.moxi.cache.AppRecallCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moxi.domain.AppInfo;
import com.moxi.domain.Channel;
import com.moxi.domain.ClickRecord;
import com.moxi.mapper.ActivationRecordMapper;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.mapper.ChannelMapper;
import com.moxi.mapper.ClickRecordMapper;
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
	private ApplicationMapper applicationService;
	@Resource
	private ChannelMapper channelService;
	@Resource
	private ClickRecordMapper clickRecordService;
	@Resource
	private ActivationRecordMapper activationRecordService;

	@Autowired
	private AppRecallCache appRecallCache;
	
	
	@RequestMapping("/admin/applicationManage_{pageCurrent}_{pageSize}_{pageCount}")
	public String applicationManage(AppInfo info,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,@PathVariable Integer pageCount, Model model) {
		
		//判断
		if(pageSize == 0) {pageSize = 10;}
		if(pageCurrent == 0) {pageCurrent = 1;}
		int rows = applicationService.count(info);
		if(pageCount == 0) {pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;}
		
		//查询
		info.setStart((pageCurrent - 1)*pageSize);
		info.setEnd(pageSize);
		List<AppInfo> list = applicationService.list(info);
		
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
			AppInfo appinfo = applicationService.findById(info);
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
			applicationService.update(info);
		} else {
			applicationService.insert(info);
		}
		return "redirect:applicationManage_0_0_0";
		
	}
	
	@ResponseBody
	@PostMapping("/admin/appinfoEditstatus")
	public ResObject<Object> channelEditstatus(AppInfo info) {
		applicationService.updateStatus(info);
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
		int rows = applicationService.count(info);
		if(pageCount == 0) {pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;}
		
		//查询
		info.setStart((pageCurrent - 1)*pageSize);
		info.setEnd(pageSize);
		List<AppInfo> list = applicationService.list(info);
		
		for(int i=0;i<list.size();i++) {
			String appId = list.get(i).getAppId();
			Map<String,Long> m = clickRecordService.countClickNum(appId);
			list.get(i).setClickNum(m.get("clickNum"));
			list.get(i).setPcClickNum(m.get("pcClickNum"));
			Map<String,Object> res = activationRecordService.countActivationNum(appId);
			list.get(i).setActivationNum((long)res.get("activationNum"));
			if(res.containsKey("noticeNum")){
				long noticeNum = 0L;
				noticeNum= ((BigDecimal) res.get("noticeNum")).longValue();
				list.get(i).setNoticeNum(noticeNum);
			}

			list.get(i).setConversion(CommonUtil.getPercent(list.get(i).getActivationNum(), list.get(i).getPcClickNum()));
		}
		
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
		List<Map<String,Object>> list = clickRecordService.countAppClickInfo(param);
		for(Map<String,Object> map : list){
			long activationNum = 0;
			if(map.containsKey("activationNum")){
				activationNum = ((BigDecimal) map.get("activationNum")).longValue();
			}else{
				map.put("activationNum",activationNum);
			}
			long pcClickNum = (Long)map.get("pcClickNum");
			map.put("conversion",CommonUtil.getPercent(activationNum, pcClickNum));
			if(!map.containsKey("noticeNum")){
				map.put("noticeNum","0");
			}
		}

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
		List<Map<String,Object>> list = clickRecordService.countAppClickInfoByChannel(param);
		for(Map<String,Object> map : list){
			long activationNum = 0;
			if(map.containsKey("activationNum")){
				activationNum = ((BigDecimal) map.get("activationNum")).longValue();
			}else{
				map.put("activationNum",activationNum);
			}
			long pcClickNum = (Long)map.get("pcClickNum");
			map.put("conversion",CommonUtil.getPercent(activationNum, pcClickNum));
			long noticeNum = 0L;
			if(map.containsKey("noticeNum")){
				noticeNum = ((Double)map.get("noticeNum")).longValue();
			}
			map.put("noticeNum",noticeNum);

		}
//		Map<String,List<Map<String,Object>>> timeDayMap = new HashMap<>();
//
//		for(Map<String,Object> map : list){
//			String timeDay = (String)map.get("timeDay");
//			List<Map<String,Object>> data = new ArrayList<>();
//			if(timeDayMap.containsKey(timeDay)){
//				data = timeDayMap.get(timeDay);
//			}
//
//			int activationNum = 0;
//			if(map.containsKey("activationNum")){
//				activationNum = ((BigDecimal) map.get("activationNum")).intValue();
//			}else{
//				map.put("activationNum",activationNum);
//			}
//			long pcClickNum = (Long)map.get("pcClickNum");
//			map.put("conversion",CommonUtil.getPercent(activationNum, pcClickNum));
//
//			data.add(map);
//			timeDayMap.put(timeDay,data);
//		}

//		List<Map<String,Object>> dataList = new ArrayList<>();
//
//		for(Map.Entry<String,List<Map<String,Object>>> entry:timeDayMap.entrySet()){
//			Map<String,Object> map = new HashMap<>();
//			map.put("timeDay",entry.getKey());
//			List<Map<String,Object>> data = entry.getValue();
//			StringBuffer clickNum = new StringBuffer();
//			StringBuffer pcClickNum = new StringBuffer();
//			StringBuffer activationNum = new StringBuffer();
//			StringBuffer conversion = new StringBuffer();
//			for(Map<String,Object> d : data ){
//				String channelName = (String) d.get("channelName");
//				clickNum.append(channelName).append(":").append(d.get("clickNum")).append("\\n");
//				pcClickNum.append(channelName).append(":").append(d.get("pcClickNum")).append("\\n") ;
//				activationNum.append(channelName).append(":").append(d.get("activationNum")).append("\\n") ;
//				conversion.append(channelName).append(":").append(d.get("conversion")).append("\\n") ;
//			}
//			map.put("clickNum",clickNum.toString());
//			map.put("pcClickNum",clickNum.toString());
//			map.put("activationNum",activationNum.toString());
//			map.put("conversion",conversion.toString());
//			dataList.add(map);
//		}



		//输出
		model.addAttribute("dataList", list);
		model.addAttribute("appId", appId);
		model.addAttribute("month", month);

		return "lianyue/channelStatisticsDetail";
	}



}
