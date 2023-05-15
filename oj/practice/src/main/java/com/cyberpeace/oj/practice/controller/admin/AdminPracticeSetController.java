package com.cyberpeace.oj.practice.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.ojbase.controller.AuthAndPublicController;
import com.cyberpeace.oj.practice.controller.req.*;
import com.cyberpeace.oj.practice.controller.res.PracticeSetRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.List;

@Api(tags = "oj-后台习题集相关api")
@RestController
@RequestMapping("/oj/admin/practice/set")
@RequiresAuthentication
@Slf4j
public class AdminPracticeSetController extends AuthAndPublicController<PracticeSetService, PracticeSet> {

	@Autowired
	private PracticeSetService practiceSetService;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private UtilService utilService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("习题集新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_EXERCISES)
	public BaseRsp<Integer> add(@RequestBody @Validated(Create.class) PracticeSetAddReq setAddReq) {

		String setId = practiceSetMapper.selectByName(setAddReq.getName());
		if (StringUtils.isNotBlank(setId)) {
			log.error("Practice is exist! practice_name=[{}]", setAddReq.getName());
			throw new BusinessException(ErrorCode.REPEAT_PRACTICE_SET_NANE);
		}

		PracticeSet practiceSet = BeanUtils.copy(setAddReq, PracticeSet.class);
		practiceSet.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		practiceSet.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setPracticeCount(CommonConstant.ZERO);

		// 文件路径处理
		practiceSet.setLogo(utilService.replaceFileUrl(setAddReq.getLogo()));

		if (StringUtils.isBlank(practiceSet.getLogo())) {
			practiceSet.setLogo(practiceSetService.createImg(practiceSet.getName()));
		}

		int result = practiceSetMapper.insert(practiceSet);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.EXERCISE,
				OperationType.CREATE, practiceSet.getName(),
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.PRACTICE_SET.getMsg(), practiceSet.getName()));

		return new BaseRsp(result);
	}

	@ApiOperation("习题集更新")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_EXERCISES)
	public BaseRsp<Boolean> update(@RequestBody @Validated(Update.class) PracticeSetUpdateReq updateReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		PracticeSet practiceSet = BeanUtils.copy(updateReq, PracticeSet.class);
		PracticeSet originalPracticeSet = practiceSetMapper.selectById(updateReq.getResourceId());
		if (StringUtils.isNotBlank(updateReq.getLogo())) {
			// 文件路径处理
			practiceSet.setLogo(utilService.replaceFileUrl(updateReq.getLogo()));
		} else if (StringUtils.isNotBlank(updateReq.getName()) && originalPracticeSet.getLogo().startsWith("logo")) {
			practiceSet.setLogo(practiceSetService.createImg(practiceSet.getName()));
		}

		Boolean result = practiceSetService.updateByAuth(practiceSet, authContext);

		if (result){
			practiceSet = practiceSetMapper.selectById(updateReq.getResourceId());
			remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.EXERCISE,
					OperationType.UPDATE, practiceSet.getName(),
					LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.PRACTICE_SET.getMsg(), practiceSet.getName()));
		}

		return new BaseRsp(result);
	}

	@ApiOperation("习题集详细信息查询")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_EXERCISES)
	public BaseRsp<AdminPracticeSetBo> get(@ApiParam(name = "resource_id",
			value = "习题集编号") @RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		AdminPracticeSetBo setBo = practiceSetService.selectById(resourceId, authContext);
		return new BaseRsp<>(setBo);
	}

	@ApiOperation("批量删除习题集")
	@DeleteMapping("")
	@RequiresPermissions(MenuConstant.CMS_EXERCISES)
	public BaseRsp<Integer> delete(@RequestBody @Valid ResourceDelReq delReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		// 删除之前校验
		practiceSetService.enableDelete(delReq.getResourceIds(), authContext);
		List<PracticeSet> practiceSetList = practiceSetService.listByIds(delReq.getResourceIds());

		int result = practiceSetMapper.deleteBatchIds(delReq.getResourceIds());

		practiceSetList.forEach(practiceSet -> {
			if (practiceSet != null) {
				String practiceSetName = practiceSet.getName();

				remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.EXERCISE,
						OperationType.DELETE, practiceSetName,
						LogContent.CR_FORMAT.format(OperationType.DELETE, LogContent.PRACTICE_SET.getMsg(), practiceSetName));
			}
		});

		return new BaseRsp(result);
	}

	@ApiOperation("分页查询习题集")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.CMS_EXERCISES, MenuConstant.CMS_THEORY_PRACTICE,
			MenuConstant.CMS_PRACTICE_OPERATION }, logical = Logical.OR)
	public BaseRsp<ListRsp<AdminPracticeSetBo>> list(PracticeSetPageReq setPageReq) {

		// 设置默认排序
		Page page = setPageReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		AuthContext auth = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		IPage<AdminPracticeSetBo> iPage = practiceSetService.selectByTypeAndName(page,
				setPageReq.getPracticeType(), setPageReq.getPattern(), auth);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	@GetMapping("/all")
	@ApiOperation("获取全部的习题集信息")
	@RequiresPermissions(MenuConstant.CMS_EXERCISES)
	public BaseRsp<List<PracticeSetRes>>
			allList(@ApiParam(name = "practice_type", value = "习题集类型 0=理论基础 2=实操练习") @RequestParam(
					value = "practice_type", required = false) Integer practiceType) {

		List<PracticeSet> practiceSets = practiceSetMapper.selectByType(practiceType,
				CommonConstant.FALSE);
		List<PracticeSetRes> setRes = BeanUtils.deepCopyList(practiceSets, PracticeSetRes.class);
		return new BaseRsp(setRes);

	}

	@ApiOperation("习题集资源数据导出")
	@GetMapping("/batch_dumps")
	@RequiresPermissions(MenuConstant.CMS_EXERCISES)
	public BaseRsp<Boolean> exportPracticeSet(
			@ApiParam(name = "ids",
					value = "习题集编号") @RequestParam("ids") @NotEmpty List<String> ids,
			HttpServletResponse response) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		File dumpFile = practiceSetService.dumpPracticeSet(ids, authContext);
		FileUtil.setResponse(dumpFile, true, response);

		return new BaseRsp(Boolean.TRUE);
	}

	@ApiOperation("习题集资源数据导入")
	@PostMapping("/batch_loads")
	@RequiresPermissions(MenuConstant.CMS_EXERCISES)
	public BaseRsp<Boolean> importPracticeSet(@Validated LoadResourceReq loadReq) {

		FileUtil.validateFileSuffix(loadReq.getFile().getOriginalFilename(),
				FileTypeContant.RESOURCE_DATA_IMPORT_TYPE);

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OTHER.getCode(),
				false, loadReq.getFile(), true);
		String realPath = utilService.getRealPath(attachmentBo.getFilePath());
		// 文件加载
		Boolean result;
		try {
			result = practiceSetService.loadPracticeSet(realPath, UserInfoContext.getUserContext().getUserId());
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				throw e;
			}
			log.error("error file:{}:{}", realPath, e);
			throw new BusinessException(CrBusinessErrorCode.INVALID_RESOURCE_ZIP);
		}
		return new BaseRsp(result);
	}

}
