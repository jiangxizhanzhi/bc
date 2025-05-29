package com.cyberpeace.space.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.space.service.TheoryKService;
import com.cyberpeace.space.service.context.ListKReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.List;

/**
 * 理论KSERVICE 服务实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see TheoryKService
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **功能概述** 
 * 该代码实现了 `TheoryKService` 接口的逻辑，主要负责处理理论 K 的相关业务逻辑。以下是核心功能： 
 * - **分页查询课时列表**：根据请求参数动态构建查询条件并返回课时列表。 
 * - **批量删除课时**：通过 ID 列表删除指定的课时记录。 
 * - **获取课时详情**：根据课时 ID 查询课时详细信息，并补充相关附件、场景配置等附加信息。 
 * --- 
 * #### 2. **关键类与依赖** 
 * - **依赖注入**： 
 * - `CourseLessonService` 和 `CourseLessonMapper`：用于操作课时实体。 
 * - `TransactionTemplate`：事务支持。 
 * - `AttachmentService`：用于处理课时相关的附件。 
 * - `UtilService`：提供文件 URL 处理工具。 
 * - `RemoteTopoService`：远程调用用于获取实验场景数据。 
 * - **核心类**： 
 * - `ListRsp<T>`：封装分页结果。 
 * - `AdminLessonBo`：管理员课时业务对象。 
 * - `ListKReq`：分页请求参数。 
 * --- 
 * #### 3. **方法分析** 
 * ##### 3.1 **`pageK(ListKReq req)`** 
 * - **功能**：分页查询课时列表。 
 * - **逻辑**： 
 * 1. 将请求参数转换为分页对象。 
 * 2. 根据请求参数动态设置查询条件（如模糊匹配课时名称）。 
 * 3. 调用 `courseLessonMapper.selectPage` 执行分页查询。 
 * 4. 将查询结果转换为 `AdminLessonBo` 并封装到 `ListRsp` 返回。 
 * ##### 3.2 **`deleteBatch(List<String> ids)`** 
 * - **功能**：批量删除课时记录。 
 * - **逻辑**：直接调用 `courseLessonService.batchDelete` 方法完成批量删除。 
 * ##### 3.3 **`getDetail(String lessonId)`** 
 * - **功能**：获取指定课时的详细信息。 
 * - **逻辑**： 
 * 1. 根据课时 ID 查询课时基本信息。 
 * 2. 如果课时不存在，抛出资源未找到异常。 
 * 3. 补充附件信息：通过 `attachmentService` 查询附件并填充到课时对象中。 
 * 4. 根据课时类型补充不同数据： 
 * - 实验场景数据：调用 `remoteTopoService` 获取场景配置。 
 * - 视频和讲义数据：通过 `utilService` 处理文件路径和名称。 
 * - 办公文档和 Markdown 文件处理：根据类型生成对应的文件 URL 和名称。 
 * 5. 知识点数据解析：将知识点字符串数组转为 JSON 对象。 
 * --- 
 * #### 4. **设计特点** 
 * - **模块化设计**：各功能模块职责清晰，便于扩展和维护。 
 * - **依赖注入**：通过 Spring 注入依赖，解耦了具体实现。 
 * - **异常处理**：在课时不存在或查询失败时，通过 `BusinessException` 抛出异常。 
 * - **工具类辅助**：使用 `BeanUtils`、`JsonUtil`、`PageUtil` 等工具类简化业务逻辑。 
 * --- 
 * #### 5. **潜在优化点** 
 * - **注释不足**：部分代码缺少必要的注释，建议增加对复杂逻辑的说明。 
 * - **硬编码问题**：某些常量值（如 `CommonConstant.ONE`）可能需要进一步抽象为配置项。 
 * - **性能优化**：批量查询附件时，可考虑缓存机制以减少数据库压力。 
 * --- 
 * #### 总结 
 * 该代码实现了一个功能全面且结构清晰的服务类，能够满足理论 K 的分页查询、批量删除及详情获取需求。通过合理的依赖注入和工具类辅助，代码具有较高的可读性和扩展性。 
**/
@Service
@Slf4j
public class TheoryKServiceImpl implements TheoryKService {

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * pagek
	 *
	 * @param req 请求体
	 * @return 列表RSP<管理课时列表博>
	 * @throws
	 * @see ListKReq
	 * @see ListRsp<AdminLessonListBo>
	 */
	@Override
	public ListRsp<AdminLessonListBo> pageK(ListKReq req) {
		Page page = req.toPageParam();
		LambdaQueryWrapper<CourseLesson> wrapper = new LambdaQueryWrapper<>();
		if (null != req.getJob()) {
//			wrapper.eq(CourseLesson::getJob, req.getJob());
		}
		if (null != req.getHandicap()) {
//			wrapper.eq(CourseLesson::getHandicap, req.getHandicap());
		}
		if (StringUtils.isNotBlank(req.getPattern())) {
			wrapper.like(CourseLesson::getName, req.getPattern());
		}
		IPage<CourseLesson> iPage = courseLessonMapper.selectPage(page, wrapper);
		ListRsp rsp = PageUtil.getListResult(iPage);
		rsp.setList(courseLessonService.lesson2AdminBo(iPage.getRecords()));
		return rsp;
	}

	/**
	 * 删除批量
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
	@Override
	public void deleteBatch(List<String> ids) {
		courseLessonService.batchDelete(ids);
	}

	/**
	 * 获取详情
	 *
	 * @param lessonId 课时id
	 * @return 管理员课程bo
	 * @throws
	 * @see String
	 * @see AdminLessonBo
	 */
	@Override
	public AdminLessonBo getDetail(String lessonId) {

		CourseLesson lesson = courseLessonMapper.selectById(lessonId);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 附件查询
		AdminLessonBo lessonInfoBo = BeanUtils.copy(lesson, AdminLessonBo.class);
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				false);
		lessonInfoBo.setLessonFile(attachmentBos);

		// 习题数据查询
		// 场景数据查询
		if (lesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getTopoId())) {
			 JSONObject topoData = remoteTopoService.getTopoById(lesson.getTopoId());
			 lessonInfoBo.setSceneConfig(topoData);
		}

		// 视频和讲义数据处理
		lessonInfoBo.setVideoUrl(utilService.handlerFileUrl(lesson.getVideoUrl()));
		if (null != lesson.getHandoutType() && lesson.getHandoutType()
				.intValue() == CourseLesson.HandoutType.PDF.getCode().intValue()) {
			lessonInfoBo.setHandoutUrl(utilService.handlerFileUrl(lesson.getHandoutUrl()));
			lessonInfoBo.setHandoutName(StringUtils.substring(lesson.getHandoutUrl(),
					lesson.getHandoutUrl().lastIndexOf(File.separatorChar) + CommonConstant.ONE));
		}

		// office讲义处理
		if (null != lesson.getHandoutType()
				&& CourseLesson.HandoutType.isOfficeFile(lesson.getHandoutType())
				&& StringUtils.isNotBlank(lesson.getHandoutUrl())) {
			lessonInfoBo.setHandoutUrl(utilService.officeUrl(lesson.getHandoutUrl(), lessonId));
			lessonInfoBo.setHandoutName(StringUtils.substring(lesson.getHandoutUrl(),
					lesson.getHandoutUrl().lastIndexOf(File.separatorChar) + CommonConstant.ONE));
		}

		// md讲义文件名称处理
		if (null != lesson.getHandoutType() && lesson.getHandoutType()
				.intValue() == CourseLesson.HandoutType.MD.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getOriginMarkdownFile())) {
			lessonInfoBo.setHandoutName(StringUtils.substring(lesson.getOriginMarkdownFile(),
					lesson.getOriginMarkdownFile().lastIndexOf(File.separatorChar)
							+ CommonConstant.ONE));
		}

		// 知识点数据转换
		lessonInfoBo.setKnowledge(JsonUtil.toJsonObjectArray(lesson.getKnowledge(), String.class));

		return lessonInfoBo;
	}
}

