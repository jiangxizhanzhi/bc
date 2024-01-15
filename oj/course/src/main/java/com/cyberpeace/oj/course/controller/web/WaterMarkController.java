package com.cyberpeace.oj.course.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.auth.constant.LicenseEnum;
import com.cyberpeace.auth.constant.SystemModelEnum;
import com.cyberpeace.auth.service.AuthService;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.course.controller.res.WaterMarkProgressRes;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.itextpdf.text.DocumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Api(tags = "添加水印")
@RestController
@RequestMapping("/oj/watermark")
@Slf4j
public class WaterMarkController {

	@Autowired
	CourseLessonMapper courseLessonMapper;

	private static Integer progress = 0;

	private static final String REPAIR_FILE = ConfigUtil.getContextProperty("repair.file.path");
	@Autowired
	private AuthService authService;
	@Autowired
	private UtilService utilService;
	@GetMapping("/registerverifyinfo")
	@ApiOperation("水印权限验证")
	public BaseRsp<Boolean> registerVerifyInfo(@RequestParam("verifyCode") String verifyCode){

		LicenseEnum registerResult = authService.registerVerifyInfo(240,verifyCode, SystemModelEnum.MODEL_SY.getName());
		return new BaseRsp<>(LicenseEnum.SUCCESS.equals(registerResult));
	}

	@ApiOperation("检查")
	@GetMapping("/check")
	public BaseRsp<Boolean> check(@ApiParam(name = "mark",value = "水印名称") String mark) {
		// mark只能包含英文空格
		mark = mark.replaceAll(" ", "");
		return new BaseRsp<>(isEnglishString(mark));
	}

	public static boolean isEnglishString(String str) {
		String regex = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	@ApiOperation("打水印")
	@GetMapping("/mark")
	public BaseRsp<Boolean> listComments(@ApiParam(name = "mark",value = "水印名称") String mark) {
		LambdaQueryWrapper<CourseLesson> filterQuery = new LambdaQueryWrapper<>();
		filterQuery.isNotNull(CourseLesson::getHandoutUrl);
		filterQuery.ne(CourseLesson::getHandoutType, CourseLesson.HandoutType.MD.getCode());
		List<CourseLesson> courseLessons = courseLessonMapper.selectList(filterQuery);
		progress = 0;
		for (int i = 0; i < courseLessons.size(); i++) {
			CourseLesson courseLesson = courseLessons.get(i);
			String out = "";
			String in = "";
			try {
				String handoutUrl = courseLesson.getHandoutUrl2();
				if (StringUtils.isEmpty(handoutUrl)) {
					continue;
				}
				if (handoutUrl.endsWith(".pdf")) {
					String name = new File(handoutUrl).getName();
					out = REPAIR_FILE+"/oj/handout/"+courseLesson.getResourceId()+"/"+name;
					in = utilService.handlerFileUrl(handoutUrl);
					WaterMarkingUtils.setPdfWatermark(in,out , mark);
					courseLesson.setHandoutUrl(out);
					courseLessonMapper.updateById(courseLesson);
				}
				progress = i * 100 / courseLessons.size();
			} catch (Exception e) {
				log.error("pdf mark false handoutUrl {} ,out {}",
						in,out);
				log.error(e.getMessage(),e);
			}
		}
		progress = 100;
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询进度")
	@GetMapping("/queryProgress")
	public BaseRsp<WaterMarkProgressRes> queryProgress() {
		WaterMarkProgressRes waterMarkProgressRes = new WaterMarkProgressRes();
		waterMarkProgressRes.setProgress(progress);
		return new BaseRsp<>(waterMarkProgressRes);
	}

	public List<String> getPdfList(String path) {
		List<String> list = FileUtil.listFileNamesByDir(path, false);
		return list.stream().filter(a -> a.endsWith(".pfg")).collect(Collectors.toList());
	}

	@ApiOperation("打水印测试")
	@GetMapping("/mark/test")
	public BaseRsp<Boolean> markTest(@ApiParam(name = "mark",value = "水印名称") String mark,
									 @ApiParam(name = "in",value = "文件路径") String in,
	@ApiParam(name = "out",value = "水印文件路径") String out) throws DocumentException, IOException {
		WaterMarkingUtils.setPdfWatermark(in,out , mark);
		return new BaseRsp<>(true);
	}
}
