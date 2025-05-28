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
