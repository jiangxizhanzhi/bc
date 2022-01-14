package com.cyberpeace.oj.practice.controller.admin;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.practice.controller.req.*;
import com.cyberpeace.oj.practice.controller.res.PracticeSetRes;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.AuthAndPublicController;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

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

	@ApiOperation("习题集新增")
	@PostMapping("")
	public BaseRsp<Integer> add(@RequestBody @Validated(Create.class) PracticeSetAddReq setAddReq) {

		String setId = practiceSetMapper.selectByName(setAddReq.getName());
		if (StringUtils.isNotBlank(setId)) {
			log.error("Practice is exist! practice_name=[{}]", setAddReq.getName());
			throw new BusinessException(ErrorCode.DUPLICATE_KEY);
		}

		PracticeSet practiceSet = BeanUtils.copy(setAddReq, PracticeSet.class);
		practiceSet.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		practiceSet.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setPracticeCount(CommonConstant.ZERO);

		// 文件路径处理
		practiceSet.setLogo(utilService.replaceFileUrl(setAddReq.getLogo()));

		int result = practiceSetMapper.insert(practiceSet);

		return new BaseRsp(result);
	}

	@ApiOperation("习题集更新")
	@PatchMapping("")
	public BaseRsp update(@RequestBody @Validated(Update.class) PracticeSetUpdateReq updateReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		PracticeSet practiceSet = BeanUtils.copy(updateReq, PracticeSet.class);
		// 文件路径处理
		practiceSet.setLogo(utilService.replaceFileUrl(updateReq.getLogo()));

		Boolean result = practiceSetService.updateByAuth(practiceSet, authContext);
		return new BaseRsp(result);
	}

	@ApiOperation("习题集详细信息查询")
	@GetMapping("")
	public BaseRsp<AdminPracticeSetBo> get(@ApiParam(name = "resource_id",
			value = "习题集编号") @RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		AdminPracticeSetBo setBo = practiceSetService.selectById(resourceId, authContext);
		return new BaseRsp<>(setBo);
	}

	@ApiOperation("批量删除习题集")
	@DeleteMapping("")
	public BaseRsp<Integer> delete(@RequestBody @Valid ResourceDelReq delReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		// 删除之前校验
		practiceSetService.enableDelete(delReq.getResourceIds(), authContext);

		int result = practiceSetMapper.deleteBatchIds(delReq.getResourceIds());
		return new BaseRsp(result);
	}

	@ApiOperation("分页查询习题集")
	@GetMapping("/list")
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
	public BaseRsp exportPracticeSet(
			@ApiParam(name = "ids", value = "习题集编号") @NotEmpty List<String> ids,
			HttpServletResponse response) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		File dumpFile = practiceSetService.dumpPracticeSet(ids, authContext);
		FileUtil.setResponse(dumpFile, true, response);

		return new BaseRsp();
	}

	@ApiOperation("习题集资源数据导入")
	@PostMapping("/batch_loads")
	public BaseRsp importPracticeSet(@Validated LoadResourceReq loadReq) {

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OTHER.getCode(),
				false, loadReq.getFile(), true);
		String realPath = utilService.getRealPath(attachmentBo.getFilePath());

		// 文件加载
		Boolean result = practiceSetService.loadPracticeSet(realPath,
				UserInfoContext.getUserContext().getUserId());
		return new BaseRsp(result);
	}

}
