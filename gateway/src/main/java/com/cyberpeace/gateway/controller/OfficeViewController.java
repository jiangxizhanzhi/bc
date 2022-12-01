package com.cyberpeace.gateway.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.assess.dao.mapper.AssessModelMapper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.gateway.service.OfficeViewService;
import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.impl.CourseValidateHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "外部接口-office文件展示相关api")
@RestController
@RequestMapping("/gateway/office/")
public class OfficeViewController {

	@Autowired
	private OfficeViewService officeViewService;

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@Autowired
	private UtilService utilService;

	@Autowired
	private AssessMaterialMapper assessMaterialMapper;

	@Autowired
	AssessModelMapper assessModelMapper;

	@ApiOperation("获取课时文件信息")
	@GetMapping("/oj/lesson/handout/info")
	public Map<String, Object> getFileInfo(
			@ApiParam(name = "fileId", value = "课时编号") @RequestParam("fileId") String lessonId) {

		OfficeFileInfoBo officeFileInfoBo = officeViewService.lessonHandoutInfo(lessonId);
		Map<String, Object> dataMap = ObjUtil.obj2map(officeFileInfoBo);
		return dataMap;
	}

	@ApiOperation("获取课时文件流")
	@GetMapping("/oj/lesson/handout")
	public BaseRsp getFileStream(
			@ApiParam(name = "fileId", value = "课时编号") @RequestParam("fileId") String lessonId,
			HttpServletResponse response) throws FileNotFoundException {

		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);
		String realPath = utilService.getRealPath(courseLesson.getHandoutUrl());

		File file = new File(realPath);
		if (!file.exists()) {
			log.error("File not found! real_path=[{}]", realPath);
			throw new FileNotFoundException(realPath);
		}

		FileUtil.setResponse(file, false, response);
		return new BaseRsp<>();
	}

	@ApiOperation("获取评估材料文件信息")
	@GetMapping("/assess/material/handout/info")
	public Map<String, Object> getAssessMaterialFileInfo(@ApiParam(name = "fileId",
			value = "评估材料编号") @RequestParam("fileId") String materialId) {

		OfficeFileInfoBo officeFileInfoBo = officeViewService.assessMaterialHandoutInfo(materialId);
		Map<String, Object> dataMap = ObjUtil.obj2map(officeFileInfoBo);
		return dataMap;
	}

	@ApiOperation("获取评估材料文件流")
	@GetMapping("/assess/material/handout")
	public BaseRsp getAssessMaterialFileStream(
			@ApiParam(name = "fileId", value = "评估材料编号") @RequestParam("fileId") String materialId,
			HttpServletResponse response) throws FileNotFoundException {

		AssessMaterial assessMaterial = assessMaterialMapper.selectById(materialId);
		if (null == assessMaterial) {
			throw new BusinessException("The material ID has no corresponding resource");
		}
		String realPath = utilService.getRealPath(assessMaterial.getAttachment());

		File file = new File(realPath);
		if (!file.exists()) {
			log.error("File not found! real_path=[{}]", realPath);
			throw new FileNotFoundException(realPath);
		}

		FileUtil.setResponse(file, false, response);
		return new BaseRsp<>();
	}

	@ApiOperation("获取模型文件信息")
	@GetMapping("/assess/model/handout/info")
	public Map<String, Object> getAssessModelFileInfo(
			@ApiParam(name = "fileId", value = "评估材料编号") @RequestParam("fileId") String modelId) {

		OfficeFileInfoBo officeFileInfoBo = officeViewService.assessModelHandoutInfo(modelId);
		Map<String, Object> dataMap = ObjUtil.obj2map(officeFileInfoBo);
		return dataMap;
	}

	@ApiOperation("获取模型文件流")
	@GetMapping("/assess/model/handout")
	public BaseRsp getAssessModelFileStream(
			@ApiParam(name = "fileId", value = "评估材料编号") @RequestParam("fileId") String modelId,
			HttpServletResponse response) throws FileNotFoundException {

		AssessModel assessModel = assessModelMapper.selectById(modelId);
		if (null == assessModel) {
			throw new BusinessException("The material ID has no corresponding resource");
		}
		String realPath = utilService.getRealPath(assessModel.getAttachmentUrl());

		File file = new File(realPath);
		if (!file.exists()) {
			log.error("File not found! real_path=[{}]", realPath);
			throw new FileNotFoundException(realPath);
		}

		FileUtil.setResponse(file, false, response);
		return new BaseRsp<>();
	}

}
