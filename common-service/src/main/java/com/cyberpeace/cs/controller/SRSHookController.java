package com.cyberpeace.cs.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.VideoUtil;
import com.cyberpeace.cs.service.HandoutVideoService;
import com.cyberpeace.cs.service.LiveService;
import com.cyberpeace.cs.service.context.rtc.SrsCallbackReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * SRS钩子Controller
 *
 * @author SN
 * @date 2024/11/11
 */
@Api(tags = "回调接口")
@Slf4j
@RestController
@RequestMapping("/cs/callback")
public class SRSHookController {

	/**
	 * 直播Service
	 */
	@Autowired
	private LiveService liveService;

	/**
	 * 讲义视频Service
	 */
	@Autowired
	private HandoutVideoService handoutVideoService;

	/**
	 * SRS 回调
	 *
	 * @param req 请求体
	 * @return {@code Integer }
	 */
	@ApiOperation("SRS回调函数")
	@PostMapping("/srs")
	public Integer srsCallback(@RequestBody @Valid SrsCallbackReq req) {
		liveService.handleCallback(req);
		// 指定格式
		return 0;
	}

	/**
	 * 获取视频长度
	 *
	 * @param name 名称
	 * @return {@code BaseRsp<Long> }
	 */
	@ApiOperation("")
	@GetMapping("/length")
	public BaseRsp<Long> getVideoLength(String name) {
		Long length = VideoUtil.readDuration("/cpms/upload/attachment/" + name + ".mp4");
		return new BaseRsp<>(length);
	}

	/**
	 * 获取帧图像
	 *
	 * @param response 响应
	 * @param path     路径
	 * @param sec      秒
	 * @param resize   调整
	 */
	@ApiOperation("获取指定视频指定时间的帧截图")
	@GetMapping("/frame")
	public void getFrameImage(HttpServletResponse response,
			@ApiParam(value = "视频文件路径", name = "path") String path,
			@ApiParam(value = "视频时间（秒）", name = "sec") Integer sec,
			@ApiParam(value = "是否压缩图片", name = "resize") Boolean resize) {
		File image = handoutVideoService.getThumbnail(path, sec, resize);
		FileUtil.setResponse(image, false, response);
	}

}
