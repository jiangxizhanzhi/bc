package com.cyberpeace.oj.practice.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeSetPageReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "oj-前台习题集相关api")
@RestController
@RequestMapping("/oj/practice/set")
@RequiresAuthentication
public class PracticeSetController {

	@Autowired
	private PracticeSetService practiceSetService;

	@ApiOperation("获取习题集")
	@GetMapping("/list")
	public BaseRsp<ListRsp<AdminPracticeSetBo>> getList(@Validated PracticeSetPageReq setPageReq) {

		Page page = setPageReq.toPageParam();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		IPage<AdminPracticeSetBo> boIPage = practiceSetService.selectByTypeAndName(page,
				setPageReq.getPracticeType(), setPageReq.getPattern(), authContext);
		return new BaseRsp<>(PageUtil.getListResult(boIPage));
	}

}
