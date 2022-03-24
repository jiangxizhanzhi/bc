package com.cyberpeace.evaluate.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.evaluate.controller.res.FileUploadRsp;
import com.cyberpeace.evaluate.dao.entity.Appendix;
import com.cyberpeace.evaluate.service.CategoryInfoService;
import com.cyberpeace.evaluate.service.bo.CategoryInfoBO;
import com.cyberpeace.integratation.service.CrService;
import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "es-评估通用api")
@RestController
@RequestMapping("/public")
public class WebPublicController {

	@Autowired
	private CrService crService;

	@Autowired
	private CategoryInfoService categoryInfoService;

	/**
	 * 文件上传接口
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/upload")
	@ApiOperation("通用文件上传")
	public BaseRsp<Appendix> uploadFile(@ApiParam(value = "上传文件") MultipartFile file) {
		String originName = file.getOriginalFilename();

		String url = "";
		try {
			url = crService.uploadImage(file.getBytes(), originName);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
		}

		FileUploadRsp fileUploadRsp = FileUploadRsp.builder().appendixUrl(url).build();

		BaseRsp rsp = new BaseRsp();
		rsp.setData(fileUploadRsp);

		return rsp;
	}

	/**
	 * 查询分类列表
	 * @param type
	 * @return
	 */
	@GetMapping("/category/{type}")
	@ApiOperation("获取下拉框分类信息")
	public BaseRsp<List<CategoryInfoBO>> getCategory(@ApiParam(
			value = "分类类型 1010=算法分类 1020=参数类型分类 1030=算法语言分类") @PathVariable Integer type) {

		Preconditions.checkArgument(null != type, "type null");
		List<CategoryInfoBO> infoBOS = categoryInfoService.selectByType(type);
		BaseRsp<List<CategoryInfoBO>> rsp = new BaseRsp<>();
		rsp.setData(infoBOS);
		return rsp;
	}
}