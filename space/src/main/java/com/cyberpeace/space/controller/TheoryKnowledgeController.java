package com.cyberpeace.space.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.course.controller.req.LessonAddReq;
import com.cyberpeace.oj.course.controller.req.LessonUpdateReq;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.service.TheoryKService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.ListKReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "知识空间-理论知识库")
@RestController
@RequestMapping("/space/theory")
@Slf4j
public class TheoryKnowledgeController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private TheoryKService theoryKService;

	@Autowired
	private UtilService utilService;

	@ApiOperation("理论知识库列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<AdminLessonListBo>> getLessonList(ListKReq req) {

		ListRsp<AdminLessonListBo> res = theoryKService.pageK(req);

		return new BaseRsp(res);
	}

	@ApiOperation("删除理论知识")
	@DeleteMapping("/")
	public BaseRsp delete(@RequestBody IdListReq req) {
		theoryKService.deleteBatch(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("查看理论知识")
	@GetMapping("")
	public BaseRsp<AdminLessonBo>
			getDetail(@ApiParam(value = "id", name = "resource_id") String resource_id) {
		AdminLessonBo lessonDetail = theoryKService.getDetail(resource_id);
		return new BaseRsp(lessonDetail);
	}

	@ApiOperation("新增理论知识")
	@PostMapping("")
	public BaseRsp<CourseLesson> addK(@RequestBody LessonAddReq lessonAddReq) {

		// 数据转换
		CourseLesson courseLesson = addReqToEntity(lessonAddReq);
		JSONObject sceneConfig = JsonUtil.toJsonObject(lessonAddReq.getOjSceneConfig(),
				JSONObject.class);

		// 数据新增
		CourseLesson lesson = courseLessonService.addLesson(courseLesson, sceneConfig,
				Lists.newArrayList());

		return new BaseRsp(lesson);
	}

	@ApiOperation("编辑理论知识")
	@PatchMapping("")
	public BaseRsp<CourseLesson> modify(@RequestBody LessonUpdateReq lessonUpdateReq) {
		// 修改数据转换 进行视频和讲义地址是否修改判断
		CourseLesson courseLesson = updateReqToEntity(lessonUpdateReq);

		// 练习题数据空字符串则不修改 将paper_id设置进课时数据体中

		JSONObject topoConfig = JsonUtil.toJsonObject(lessonUpdateReq.getOjSceneConfig(),
				JSONObject.class);
		List<PaperPracticeContext> contextList = JsonUtil
				.toJsonObjectArray(lessonUpdateReq.getPracticeList(), PaperPracticeContext.class);

		// 执行数据更新
		CourseLesson lesson = courseLessonService.updateLesson(courseLesson, topoConfig,
				contextList);
		return new BaseRsp(lesson);
	}

	/**
	 * 更新请求转换为实体类
	 * @param lessonUpdateReq
	 * @return
	 */
	private CourseLesson updateReqToEntity(LessonUpdateReq lessonUpdateReq) {
		CourseLesson courseLesson = BeanUtils.copy(lessonUpdateReq, CourseLesson.class);
		courseLesson.setCourseId(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME);

		// 有文件路径 并且文件不为空 则修改文件
		if (StringUtils.isBlank(lessonUpdateReq.getVideoUrl())
				&& null != lessonUpdateReq.getVideo()) {
			courseLessonService.uploadVideo(courseLesson, lessonUpdateReq.getVideo());

		} else if (null == lessonUpdateReq.getVideo()
				&& StringUtils.isBlank(lessonUpdateReq.getVideoUrl())) {
			// 文件为空 url为空则置空
			courseLesson.setVideoUrl("");
		} else {
			courseLesson.setVideoUrl(utilService.replaceFileUrl(lessonUpdateReq.getVideoUrl()));
		}

		// 有讲义地址并且讲义不为空 则修改文件
		if (StringUtils.isBlank(lessonUpdateReq.getHandoutUrl())
				&& null != lessonUpdateReq.getHandout()) {
			courseLessonService.uploadHandout(courseLesson, lessonUpdateReq.getHandout());

			// 讲义为空 并且讲义地址为空 则置空
		} else if (StringUtils.isBlank(lessonUpdateReq.getHandoutUrl())
				&& null == lessonUpdateReq.getHandout()) {
			courseLesson.setHandoutUrl("");
		} else {
			courseLesson.setHandoutUrl(utilService.replaceFileUrl(lessonUpdateReq.getHandoutUrl()));
		}

		return courseLesson;
	}

	/**
	 * 请求体转换为实体类
	 * @param lessonAddReq
	 * @return
	 */
	public CourseLesson addReqToEntity(LessonAddReq lessonAddReq) {

		// 知识点转换
		CourseLesson courseLesson = BeanUtils.copy(lessonAddReq, CourseLesson.class);
		courseLesson.setKnowledge(lessonAddReq.getKnowledge());
		courseLesson.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		courseLesson.setCourseId(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME);

		courseLessonService.uploadVideo(courseLesson, lessonAddReq.getVideo());
		courseLessonService.uploadHandout(courseLesson, lessonAddReq.getHandout());

		return courseLesson;
	}
}
