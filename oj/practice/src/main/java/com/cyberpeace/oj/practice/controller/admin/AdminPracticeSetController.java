package com.cyberpeace.oj.practice.controller.admin;

import javax.validation.Valid;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.controller.res.PracticeSetRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.ojbase.controller.AuthAndPublicController;
import com.cyberpeace.oj.practice.controller.req.PracticeSetAddReq;
import com.cyberpeace.oj.practice.controller.req.PracticeSetPageReq;
import com.cyberpeace.oj.practice.controller.req.PracticeSetUpdateReq;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

	@ApiOperation("习题集新增")
	@PostMapping("")
	public BaseRsp<Integer> add(@RequestBody @Validated(Create.class) PracticeSetAddReq setAddReq) {

		PracticeSet practiceSet = BeanUtils.copy(setAddReq, PracticeSet.class);
		practiceSet.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		practiceSet.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		int result = practiceSetMapper.insert(practiceSet);

		return new BaseRsp(result);
	}

	@ApiOperation("习题集更新")
	@PatchMapping("")
	public BaseRsp update(@RequestBody @Validated(Update.class) PracticeSetUpdateReq updateReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		PracticeSet practiceSet = BeanUtils.copy(updateReq, PracticeSet.class);

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
		List<String> permissionIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(permissionIds)
				|| !permissionIds.containsAll(delReq.getResourceIds())) {
			log.error("Batch delete practice set failed! Because not permission! practice_set_ids=",
					delReq.getResourceIds().toString());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		int result = practiceSetMapper.deleteBatchIds(delReq.getResourceIds());
		return new BaseRsp(result);
	}

	@ApiOperation("分页查询习题集")
	@GetMapping("/list")
	public ListRsp<PracticeSetRes> list(PracticeSetPageReq setPageReq) {

		Page page = setPageReq.toPageParam();
		AuthContext auth = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		IPage<AdminPracticeSetBo> iPage = practiceSetService.selectByTypeAndName(page,
				setPageReq.getPracticeType(), setPageReq.getPattern(), auth);

		IPage<PracticeSetRes> resIPage = iPage.convert(
				adminPracticeSetBo -> BeanUtils.copy(adminPracticeSetBo, PracticeSetRes.class));
		return PageUtil.getListResult(resIPage);
	}
}
