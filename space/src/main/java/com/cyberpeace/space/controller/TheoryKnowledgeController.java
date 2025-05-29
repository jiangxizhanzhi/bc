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
/**
 * 这段代码定义了一个名为 `TheoryKnowledgeController` 的 Spring MVC 控制器类，用于管理理论知识点相关的操作。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **理论知识点管理**：提供了一系列接口来管理和操作理论知识点（如列表查询、详情查看、新增、编辑、删除等）。 
 * 2. **数据转换与服务调用**：通过内部方法将请求参数转换为对应的实体类，并调用底层的服务层完成业务逻辑。 
 * --- 
 * ### 类的主要成员变量 
 * - 注入了多个服务类，例如： 
 * - `CourseService`：课程相关服务。 
 * - `CourseLessonService`：课程课时相关服务。 
 * - `TheoryKService`：理论知识点相关服务。 
 * - `UtilService`：通用工具类服务。 
 * --- 
 * ### 主要方法分析 
 * #### 1. **理论知识点列表查询** 
 * ```java 
 * @GetMapping("/page") 
 * public BaseRsp<ListRsp<AdminLessonListBo>> getLessonList(ListKReq req) 
 * ``` 
 * - **功能**：根据分页请求参数 `ListKReq` 查询理论知识点列表。 
 * - **实现**：调用 `theoryKService.pageK(req)` 方法获取分页结果并返回。 
 * --- 
 * #### 2. **删除理论知识点** 
 * ```java 
 * @DeleteMapping("/") 
 * public BaseRsp delete(@RequestBody IdListReq req) 
 * ``` 
 * - **功能**：根据传入的 ID 列表批量删除理论知识点。 
 * - **实现**：调用 `theoryKService.deleteBatch(req.getIds())` 完成删除操作。 
 * --- 
 * #### 3. **查看理论知识点详情** 
 * ```java 
 * @GetMapping("") 
 * public BaseRsp<AdminLessonBo> getDetail(@ApiParam(value = "id", name = "resource_id") String resource_id) 
 * ``` 
 * - **功能**：根据知识点 ID 查询详细信息。 
 * - **实现**：调用 `theoryKService.getDetail(resource_id)` 获取详情并返回。 
 * --- 
 * #### 4. **新增理论知识点** 
 * ```java 
 * @PostMapping("") 
 * public BaseRsp<CourseLesson> addK(@RequestBody LessonAddReq lessonAddReq) 
 * ``` 
 * - **功能**：新增一条理论知识点记录。 
 * - **实现**： 
 * 1. 调用 `addReqToEntity()` 方法将请求参数转换为实体类。 
 * 2. 将场景配置转换为 JSON 对象。 
 * 3. 调用 `courseLessonService.addLesson()` 完成新增操作。 
 * --- 
 * #### 5. **编辑理论知识点** 
 * ```java 
 * @PatchMapping("") 
 * public BaseRsp<CourseLesson> modify(@RequestBody LessonUpdateReq lessonUpdateReq) 
 * ``` 
 * - **功能**：编辑现有理论知识点记录。 
 * - **实现**： 
 * 1. 调用 `updateReqToEntity()` 方法将请求参数转换为实体类。 
 * 2. 解析场景配置和练习题列表。 
 * 3. 调用 `courseLessonService.updateLesson()` 完成更新操作。 
 * --- 
 * #### 6. **辅助方法** 
 * ##### `addReqToEntity()` 
 * - 功能：将 `LessonAddReq` 请求参数转换为 `CourseLesson` 实体类。 
 * - 关键步骤： 
 * 1. 使用工具类 `BeanUtils.copy` 复制属性。 
 * 2. 设置知识点、用户 ID 和课程 ID。 
 * 3. 调用服务方法上传视频和讲义。 
 * ##### `updateReqToEntity()` 
 * - 功能：将 `LessonUpdateReq` 请求参数转换为 `CourseLesson` 实体类。 
 * - 关键步骤： 
 * 1. 同样使用工具类 `BeanUtils.copy` 复制属性。 
 * 2. 检查视频和讲义字段是否需要更新，并调用服务方法上传文件或替换 URL。 
 * --- 
 * ### 总结 
 * - **模块化设计**：通过分离请求参数转换和业务逻辑，提高了代码的可读性和可维护性。 
 * - **服务依赖**：充分利用了注入的服务类，减少了重复代码。 
 * - **灵活性**：支持多种操作（增删改查），满足理论知识点管理的需求。 
**/
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

