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

/**
 * 理论知识点Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
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

	/**
	 * 该方法用于新增理论知识课时，其主要功能如下：
	 *
	 * 1. 将请求参数 [LessonAddReq](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\controller\req\LessonAddReq.java#L21-L183) 转换为实体类 [CourseLesson](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\dao\entity\CourseLesson.java#L25-L276)；
	 * 2. 将场景配置信息转换为 `JSONObject` 格式；
	 * 3. 调用服务层方法将课时数据、场景配置及空练习列表保存到数据库；
	 * 4. 返回封装了新增课时信息的 [BaseRsp](file://D:\projects\work\ojj\ojj\common\src\main\java\com\cyberpeace\common\model\BaseRsp.java#L12-L45) 响应对象。
	 * @param lessonAddReq
	 * @return
	 */
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

	/**
	 * 该方法用于编辑理论知识课时，主要功能如下：
	 *
	 * 1. 将请求参数 [LessonUpdateReq](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\controller\req\LessonUpdateReq.java#L19-L60) 转换为实体类 [CourseLesson](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\dao\entity\CourseLesson.java#L25-L276)；
	 * 2. 解析请求中的场景配置 [ojSceneConfig](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\controller\req\LessonAddReq.java#L126-L127) 和练习题列表 [practiceList](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\controller\req\LessonAddReq.java#L150-L151)；
	 * 3. 调用 `courseLessonService.updateLesson` 更新课时信息及关联数据；
	 * 4. 返回更新后的课时数据封装在 [BaseRsp](file://D:\projects\work\ojj\ojj\common\src\main\java\com\cyberpeace\common\model\BaseRsp.java#L12-L45) 中。
	 * @param lessonUpdateReq
	 * @return
	 */
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
	 * 该方法将请求对象 [LessonUpdateReq](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\controller\req\LessonUpdateReq.java#L19-L60) 转换为实体类 [CourseLesson](file://D:\projects\work\ojj\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\dao\entity\CourseLesson.java#L25-L276)，并处理视频和讲义的上传或路径替换逻辑。具体功能如下：
	 *
	 * 1. 使用 [BeanUtils.copy]
	 * 2. 设置默认课程ID；
	 * 3. 根据是否有新视频文件或URL更新视频地址；
	 * 4. 根据是否有新讲义文件或URL更新讲义地址。
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
	 * 该方法用于将添加课程章节的请求体转换为课程章节实体类对象
	 * @param lessonAddReq 课程章节添加请求对象，包含要添加的课程章节的相关信息
	 * @return 转换后的课程章节实体类对象
	 */
	public CourseLesson addReqToEntity(LessonAddReq lessonAddReq) {

	    // 使用工具类进行对象属性复制，将请求对象转换为课程章节实体类对象
	    CourseLesson courseLesson = BeanUtils.copy(lessonAddReq, CourseLesson.class);

	    // 设置课程章节的知识点信息
	    courseLesson.setKnowledge(lessonAddReq.getKnowledge());

	    // 设置创建用户的ID，从用户信息上下文中获取当前用户的ID
	    courseLesson.setCreateUserId(UserInfoContext.getUserContext().getUserId());

	    // 设置课程ID为默认策略框架
	    courseLesson.setCourseId(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME);

	    // 上传视频，调用课程章节服务的上传视频方法
	    courseLessonService.uploadVideo(courseLesson, lessonAddReq.getVideo());

	    // 上传讲义，调用课程章节服务的上传讲义方法
	    courseLessonService.uploadHandout(courseLesson, lessonAddReq.getHandout());

	    // 返回转换后的课程章节实体类对象
	    return courseLesson;
	}
}
