package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.common.util.StreamApiUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.req.KnowledgeCategoryAddReq;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.dao.entity.KnowledgeCategory;
import com.cyberpeace.rtks.service.KnowledgeCategoryService;
import com.cyberpeace.rtks.service.KnowledgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "RTKS-知识点类别管理")
@RestController
@RequestMapping("/rtks/knowledge-category")
@RequiresAuthentication
@Slf4j
public class KnowledgeCategoryController {

	@Autowired
	private KnowledgeCategoryService knowledgeCategoryService;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("查询单条")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<KnowledgeCategory> getKnowledge(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		KnowledgeCategory knowledgeCategory = knowledgeCategoryService.getById(resource_id);

		return new BaseRsp<>(knowledgeCategory);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String>
			addKnowledge(@RequestBody @Validated KnowledgeCategoryAddReq knowledgeCategoryAddReq) {
		validate(knowledgeCategoryAddReq.getContent(), "");
		KnowledgeCategory knowledgeCategory = new KnowledgeCategory();

		BeanUtils.copyProperties(knowledgeCategoryAddReq, knowledgeCategory);
		knowledgeCategoryService.save(knowledgeCategory);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.CREATE, knowledgeCategory.getContent(),
				LogContent.CR_FORMAT.format(OperationType.CREATE,
						LogContent.KNOWLEDGE_CATEGORY.getMsg(), knowledgeCategory.getContent()));

		return new BaseRsp<>();
	}

	private void validate(String content, String resourceId) {
		List<KnowledgeCategory> knowledgeCategories = knowledgeCategoryService
				.getByContent(content.trim());
		if (knowledgeCategories.isEmpty()) {
			return;
		}
		if (knowledgeCategories.size() == 1
				&& knowledgeCategories.get(0).getResourceId().equals(resourceId)) {
			return;
		}

		log.error("knowledgeCategory already exist,knowledgeCategory is [{}]", content);
		throw new BusinessException(RtksBusinessErrorCode.KNOWLEDGE_CATEGORY_NAME_DUPLICATE);

	}

	@ApiOperation("修改单条")
	@PutMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String>
			modifyKnowledge(@RequestBody @Validated KnowledgeCategoryAddReq knowledgeCategoryOrSystemAddReq) {
		validate(knowledgeCategoryOrSystemAddReq.getContent(),
				knowledgeCategoryOrSystemAddReq.getResourceId());
		KnowledgeCategory knowledgeCategory = new KnowledgeCategory();
		BeanUtils.copyProperties(knowledgeCategoryOrSystemAddReq, knowledgeCategory);
		knowledgeCategoryService.updateById(knowledgeCategory);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.UPDATE, knowledgeCategory.getContent(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE,
						LogContent.KNOWLEDGE_CATEGORY.getMsg(), knowledgeCategory.getContent()));

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> deleteKnowledgeCategory(@RequestBody @Validated DeleteReq deleteReq) {
		List<KnowledgeRes> skillList = knowledgeService.getAllKnowledge();
		for (KnowledgeRes knowledgeRes : skillList) {
			if (deleteReq.getIds().contains(knowledgeRes.getCategoryId())) {
				throw new BusinessException(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE);
			}
		}

		List<KnowledgeCategory> knowledgeCategories = knowledgeCategoryService.getBaseMapper()
				.selectBatchIds(deleteReq.getIds());
		knowledgeCategoryService.removeByIds(deleteReq.getIds());

		List<String> columns = StreamApiUtils.getColumn(knowledgeCategories, KnowledgeCategory::getContent);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.DELETE, LogContent.BATCH_DELETE_FORMAT.format(LogContent.KNOWLEDGE_CATEGORY.getMsg()),
				LogContent.CR_FORMAT.format(OperationType.DELETE, LogContent.KNOWLEDGE_CATEGORY.getMsg(), columns.toString()));



		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<KnowledgeCategory>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<KnowledgeCategory> data = knowledgeCategoryService.page(pageReqBase);

		return new BaseRsp(data);
	}
}
