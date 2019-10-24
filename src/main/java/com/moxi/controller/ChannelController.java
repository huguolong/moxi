package com.moxi.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.moxi.cache.AppRecallCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moxi.domain.Channel;
import com.moxi.mapper.ChannelMapper;
import com.moxi.model.ResObject;
import com.moxi.common.config.Constant;
import com.moxi.util.PageUtil;

@Controller
public class ChannelController {
	
	@Resource
	private ChannelMapper channelService;

	@Resource
	private AppRecallCache appRecallCache;
	
	@RequestMapping("/admin/channelManage_{pageCurrent}_{pageSize}_{pageCount}")
	public String channelManage(Channel channel,@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,@PathVariable Integer pageCount, Model model) {
		
		//判断
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = channelService.count(channel);
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		
		//查询
		channel.setStart((pageCurrent - 1)*pageSize);
		channel.setEnd(pageSize);
		List<Channel> list = channelService.list(channel);
		
		//输出
		model.addAttribute("list", list);
		
		String pageHTML = PageUtil.getPageContent("channelManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
		model.addAttribute("pageHTML",pageHTML);
		model.addAttribute("channel",channel);
		
		return "lianyue/channelManage";
		
	}
	
	/**
	 * 渠道新增、修改跳转
	 * @param model
	 * @param newsCategory
	 * @return
	 */
	@GetMapping("/admin/channelEdit")
	public String channelEdit(Model model,Channel channel) {
		
		if(null != channel && null != channel.getId() && channel.getId() != 0){
			Channel info = channelService.findById(channel);
			model.addAttribute("info",info);
		}else {
			model.addAttribute("info",new Channel());
		}
		return "lianyue/channelEdit";
	}
	
	/**
	 * 渠道新增、修改提交
	 * @param model
	 * @param newsCategory
	 * @param imageFile
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/admin/channelEdit")
	public String channelEditPost(Model model,Channel channel,HttpSession httpSession) {
		
		if(null != channel && null != channel.getId() && channel.getId() != 0){
			//清除缓存中的回调率
			appRecallCache.delAppRecall(channel.getCode());
			channelService.update(channel);
		} else {
			channelService.insert(channel);
		}
		return "redirect:channelManage_0_0_0";
		
	}
	
	@ResponseBody
	@PostMapping("/admin/channelEditstatus")
	public ResObject<Object> channelEditstatus(Channel channel) {
		channelService.updateStatus(channel);
		ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
		return object;
	}
}
