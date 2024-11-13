package com.cyberpeace.cs.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.service.HandoutVideoService;
import com.cyberpeace.cs.service.LiveService;
import com.cyberpeace.cs.service.context.rtc.LiveStatus;
import com.cyberpeace.cs.service.context.rtc.WebRTCReq;
import com.cyberpeace.cs.service.context.video.VideoProgressReportReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 视频Controller
 *
 * @author SN
 * @date 2024/11/11
 */
@Api(tags = "视频进度相关api")
@Slf4j
@RestController
@RequestMapping("/cs/video")
@RequiresAuthentication
public class VideoController {

	/**
	 * 讲义视频Service
	 */
	@Autowired
	private HandoutVideoService handoutVideoService;

	/**
	 * 直播Service
	 */
	@Autowired
	private LiveService liveService;

	/**
	 * 发布视频进度
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("上报视频检查点进度")
	@PostMapping("/check/report")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp postVideoProgress(@RequestBody @Valid VideoProgressReportReq req) {
		handoutVideoService.updateProgress(req);
		return new BaseRsp();
	}

	/**
	 * 获取框架图像
	 *
	 * @param response 响应
	 * @param path     路径
	 * @param sec      秒
	 * @param resize   调整
	 */
	@ApiOperation("获取指定视频指定时间的帧截图")
	@GetMapping("/frame")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public void getFrameImage(HttpServletResponse response,
			@ApiParam(value = "视频文件路径", name = "path") String path,
			@ApiParam(value = "视频时间（秒）", name = "sec") Integer sec,
			@ApiParam(value = "是否压缩图片", name = "resize") Boolean resize) {
		File image = handoutVideoService.getThumbnail(path, sec, resize);
		FileUtil.setResponse(image, false, response);
	}

	/**
	 * 获取发布网址
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("获取WebRTC推流连接")
	@PostMapping("/rtc/publish")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<String> getPublishUrl(@RequestBody @Valid WebRTCReq req) {
		String publishUrl = liveService.getPublishUrl(req);
		return new BaseRsp<>(publishUrl);
	}

	/**
	 * 获取播放网址
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<LiveStatus> }
	 */
	@ApiOperation("获取WebRTC拉流连接")
	@PostMapping("/rtc/play")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<LiveStatus> getPlayUrl(@RequestBody @Valid WebRTCReq req) {
		LiveStatus status = liveService.getLive(req);
		return new BaseRsp<>(status);
	}
}
