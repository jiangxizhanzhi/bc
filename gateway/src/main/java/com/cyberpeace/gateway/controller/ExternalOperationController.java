package com.cyberpeace.gateway.controller;

import java.util.Arrays;
import java.util.List;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.practice.service.bo.PracticeOperationFileBo;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.gateway.controller.req.ExternalBatchReq;
import com.cyberpeace.gateway.controller.req.OperationPageReq;
import com.cyberpeace.gateway.service.bo.ExternalOperationBo;
import com.cyberpeace.gateway.service.bo.ExternalPracticeCategoryBo;
import com.cyberpeace.gateway.service.bo.ExternalPracticeSetBo;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "外部接口-实操习题相关api")
@RestController
@RequestMapping("/gateway/exercise")
public class ExternalOperationController {

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Autowired
	private UtilService utilService;

	/**
	 * 接口认证(key获取方式后续可能调整)
	 * @param key headers: OPT-OJ-KEY
	 */
	private void allowAccess(String key) {
		if (!key.equals(WebConstant.OJ_HEADER_KEY_VALUE)) {
			throw new UnauthorizedException("secretKey is failed.");
		}
	}

	@ApiOperation("习题集查询")
	@GetMapping("/practice-set/list")
	public BaseRsp practiceSetList(@RequestHeader(WebConstant.OJ_HEADER_KEY) String key) {

		allowAccess(key);
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.OPERATION.getCode(), false);
		List<ExternalPracticeSetBo> practiceSetBos = BeanUtils.deepCopyList(practiceSets,
				ExternalPracticeSetBo.class);
		return new BaseRsp(practiceSetBos);
	}

	@ApiOperation("实操习题分类查询")
	@GetMapping("/category/list")
	public BaseRsp<List<ExternalPracticeCategoryBo>>
			categoryList(@RequestHeader(WebConstant.OJ_HEADER_KEY) String key) {

		allowAccess(key);
		List<PracticeCategory> categoryList = practiceCategoryMapper
				.listByTypeAndName(PracticeCategory.PracticeType.OPERATION.getCode(), null);
		List<ExternalPracticeCategoryBo> categoryBos = BeanUtils.deepCopyList(categoryList,
				ExternalPracticeCategoryBo.class);
		return new BaseRsp(categoryBos);
	}

	@ApiOperation("实操习题批量查询")
	@GetMapping("/batch-get")
	public BaseRsp batchGet(@RequestHeader(WebConstant.OJ_HEADER_KEY) String key,
			ExternalBatchReq batchReq) {

		allowAccess(key);
		List<PracticeOperation> operationList = practiceOperationMapper
				.selectBatchIds(batchReq.getResourceIds());
		List<ExternalOperationBo> operationBos = StreamApiUtils.map(operationList,
				practiceOperation -> entityToExternal(practiceOperation));

		return new BaseRsp(operationBos);
	}

	/**
	 * 实体类转换为外部字段
	 * @param operation
	 * @return
	 */
	private ExternalOperationBo entityToExternal(PracticeOperation operation) {
		ExternalOperationBo externalOperationBo = BeanUtils.copy(operation,
				ExternalOperationBo.class);
		externalOperationBo.setCreateUser(operation.getCreateUserId());

		// 分类
		PracticeCategory practiceCategory = practiceCategoryMapper
				.selectById(operation.getCategoryId());
		if (null != practiceCategory) {
			externalOperationBo.setCategoryInfo(practiceCategory.getName());
		}

		if (StringUtils.hasText(operation.getAttachmentId())) {
			if (operation.getDynamicFlag().intValue() == PracticeOperation.DynamicFlag.RANDOM
					.getCode().intValue()) {
				List<String> fileIds = Arrays
						.asList(operation.getAttachmentId().split(CommonConstant.CODE_COMMA));
				if (!CollectionUtils.isEmpty(fileIds)) {
					List<Attachment> attachments = attachmentMapper.selectBatchIds(fileIds, false);
					List<PracticeOperationFileBo> fileBos = StreamApiUtils.map(attachments,
							file -> {
								PracticeOperationFileBo fileBo = new PracticeOperationFileBo();
								fileBo.setFileUrl(utilService.handlerFileUrl(file.getFilePath()));
								fileBo.setFileName(file.getFileName());
								return fileBo;
							});
					externalOperationBo.setAttachment(fileBos);
				}

			} else {
				Attachment attachment = attachmentMapper.selectById(operation.getAttachmentId());
				String fileUrl = utilService.handlerFileUrl(attachment.getFilePath());
				externalOperationBo.setFileUrl(fileUrl);
				externalOperationBo.setFileName(attachment.getFileName());
			}

		}

		externalOperationBo.setIsDynamicEnv(operation.getDynamicEnv());
		externalOperationBo.setIsDynamicFlag(operation.getDynamicFlag());
		externalOperationBo.setShareMode(operation.getSceneShareMode());

		return externalOperationBo;
	}

	@ApiOperation("实操习题分页查询")
	@GetMapping("/list")
	public BaseRsp pageList(@RequestHeader(WebConstant.OJ_HEADER_KEY) String key,
			OperationPageReq req) {

		allowAccess(key);
		Page page = req.toPageParam();
		// 条件组装
		OperationListQuery listQuery = BeanUtils.copy(req, OperationListQuery.class);
		listQuery.setPracticeSetId(req.getPracticeSet());
		if (StringUtils.hasText(req.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(req.getCategory()));
		}

		// 条件查询
		IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page, listQuery,
				false);
		IPage<ExternalOperationBo> convert = iPage
				.convert(practiceOperation -> entityToExternal(practiceOperation));
		return new BaseRsp(PageUtil.getListResult(convert));
	}

}
