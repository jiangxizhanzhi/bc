package com.cyberpeace.oj.practice.controller.admin;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.controller.AuthAndPublicController;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.practice.controller.req.PracticeSetAddReq;
import com.cyberpeace.oj.practice.controller.req.PracticeSetUpdateReq;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "oj-后台习题集相关api")
@RestController
@RequestMapping("/oj/admin/practice/set")
@RequiresAuthentication
public class AdminPracticeSetController extends AuthAndPublicController<PracticeSetService, PracticeSet> {

	@Autowired
	private PracticeSetService practiceSetService;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@ApiOperation("习题集新增")
	@PostMapping("")
	public BaseRsp<Integer>
			add(@RequestBody @Validated({ PracticeSetAddReq.class }) PracticeSetAddReq setAddReq) {

		PracticeSet practiceSet = BeanUtils.copy(setAddReq, PracticeSet.class);
		int result = practiceSetMapper.insert(practiceSet);

		return new BaseRsp(result);
	}

	@ApiOperation("习题集更新")
	@PatchMapping("")
	public BaseRsp update(@RequestBody @Validated({
			PracticeSetUpdateReq.class }) PracticeSetUpdateReq updateReq) {
		PracticeSet practiceSet = BeanUtils.copy(updateReq, PracticeSet.class);
		int result = practiceSetMapper.updateById(practiceSet);
		return new BaseRsp(result);
	}

	@ApiOperation("习题集详细信息查询")
	@GetMapping("")
	public BaseRsp<AdminPracticeSetBo> get(@ApiParam(name = "resource_id",
			value = "习题集编号") @RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		AdminPracticeSetBo setBo = practiceSetService.selectById(resourceId, authContext);
		return null;
	}

	@ApiOperation("批量删除习题集")
	@DeleteMapping("")
	public BaseRsp<Integer> delete(@RequestBody @Valid ResourceDelReq delReq) {
		int result = practiceSetMapper.deleteBatchIds(delReq.getResourceIds());
		return new BaseRsp(result);
	}
}
