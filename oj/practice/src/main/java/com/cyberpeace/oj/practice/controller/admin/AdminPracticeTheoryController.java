package com.cyberpeace.oj.practice.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.*;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.practice.controller.req.*;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeTheoryImportService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.AdminTheoryBo;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@Api(tags = "oj-后台基础理论习题相关api")
@RestController
@RequestMapping("oj/admin/theory")
@RequiresAuthentication
public class AdminPracticeTheoryController extends PublicController<PracticeTheoryService, PracticeTheory> {

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private PracticeTheoryImportService practiceTheoryImportService;

	@ApiOperation("理论基础习题新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_THEORY_PRACTICE)
	public BaseRsp<Integer>
			add(@RequestBody @Validated({ Create.class }) TheoryAddReq theoryAddReq) {

		PracticeTheory practiceTheory = BeanUtils.copy(theoryAddReq, PracticeTheory.class);
		practiceTheory.setTitle(theoryAddReq.getContent());

		practiceTheory.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		replaceAnswer(practiceTheory);

		Integer result = practiceTheoryService.create(practiceTheory, authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.THEORY,
				OperationType.CREATE, LogContent.THEORY.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.THEORY.getMsg(), theoryAddReq.getContent()));

		return new BaseRsp(result);
	}

	/**
	 * 替换答案
	 * @param practiceTheory
	 */
	private void replaceAnswer(PracticeTheory practiceTheory) {
		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
				.getCode().intValue()) {
			practiceTheory.setAnswer(practiceTheory.getBlank());
		}
	}

	@ApiOperation("理论基础习题修改")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_THEORY_PRACTICE)
	public BaseRsp<Integer>
			update(@RequestBody @Validated({ Update.class }) TheoryUpdateReq theoryUpdateReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		PracticeTheory practiceTheory = BeanUtils.copy(theoryUpdateReq, PracticeTheory.class);

		int result = practiceTheoryService.updateTheory(practiceTheory, authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.THEORY,
				OperationType.UPDATE, LogContent.THEORY.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.THEORY.getMsg(), theoryUpdateReq.getContent()));

		return new BaseRsp(result);
	}

	@ApiOperation("获取理论基础习题详情")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_THEORY_PRACTICE)
	public BaseRsp<AdminTheoryBo> get(@RequestParam("resource_id") String resourceId) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(resourceId);
		AdminTheoryBo theoryRes = BeanUtils.copy(practiceTheory, AdminTheoryBo.class);

		return new BaseRsp<>(theoryRes);
	}

	@ApiOperation("基础理论习题资源删除")
	@DeleteMapping("")
	@RequiresPermissions(MenuConstant.CMS_THEORY_PRACTICE)
	public BaseRsp<Integer> delete(@RequestBody @Validated ResourceDelReq delReq) {

		List<PracticeTheory> practiceTheoryList = practiceTheoryService.listByIds(delReq.getResourceIds());

		// 试用账号校验
		for (PracticeTheory practiceTheory : practiceTheoryList) {
			AuthContextUtils.validateBuiltin(!practiceTheory.getCreateUserId().equals(UserInfoContext.getUserContext().getUserId()));
		}

		Integer result = practiceTheoryService.batchDelete(delReq.getResourceIds());

		practiceTheoryList.forEach(practiceTheory -> {

			remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.THEORY,
					OperationType.DELETE, LogContent.THEORY.getMsg(),
					LogContent.CR_FORMAT.format(OperationType.DELETE, LogContent.THEORY.getMsg(), practiceTheory.getContent()));
		});

		return new BaseRsp<>(result);
	}

	@ApiOperation("分页查询理论基础习题")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_THEORY_PRACTICE)
	public BaseRsp<ListRsp<PracticeTheoryListBo>> list(AdminTheoryListReq listReq) {

		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		List<String> categoryIds = practiceCategoryService
				.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);

		TheoryListQueryBo listQuery = BeanUtils.copy(listReq, TheoryListQueryBo.class);
		listQuery.setCategoryIds(categoryIds);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		IPage<PracticeTheoryListBo> boIPage = practiceTheoryService.pageByQuery(page, listQuery,
				authContext);

		return new BaseRsp<>(PageUtil.getListResult(boIPage));

	}

	@ApiOperation("理论习题资源导入")
	@PostMapping("/import")
	@PermissionOperation(TrialButtonConstant.CMS_THEORY_IMPORT)
	@RequiresPermissions(MenuConstant.CMS_THEORY_PRACTICE)
	public BaseRsp<Boolean> importTheory(@Validated TheoryImportReq theoryImportReq) {

		FileUtil.validateSuffix(theoryImportReq.getFile(), FileSunffixConstant.XLSX.getDesc(),
				FileSunffixConstant.XLS.getDesc());

		// 文件先上传临时目录
		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OTHER.getCode(),
				false, theoryImportReq.getFile(), true);
		String realPath = utilService.getRealPath(attachmentBo.getFilePath());

		// 执行资源数据导入
		Boolean result = practiceTheoryImportService.importTheory(theoryImportReq.getPracticeSetId(),
				realPath, UserInfoContext.getUserContext().getUserId());
		// 文件删除
		new File(realPath).deleteOnExit();
		return new BaseRsp<>(result);

	}

}
