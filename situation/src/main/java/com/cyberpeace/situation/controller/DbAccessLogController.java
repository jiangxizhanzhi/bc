package com.cyberpeace.situation.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.SituationDbAccessAddReq;
import com.cyberpeace.situation.controller.req.SituationDbAccessReq;
import com.cyberpeace.situation.dao.entity.SituationDbAccess;
import com.cyberpeace.situation.dao.mapper.SituationDbAccessMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "数据库管理-访问日志")
@RestController
@RequiresAuthentication
@RequestMapping("/db/access")
public class DbAccessLogController {

	@Autowired
	private SituationDbAccessMapper situationDbAccessMapper;

	@ApiOperation("访问日志-列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SituationDbAccess>>
			getDbAccess(@ApiParam @Validated SituationDbAccessReq req) {
		Page page = req.toPageParam();
		IPage<SituationDbAccess> situationDbAccessIPage = situationDbAccessMapper.selectPage(page,
				req);
		return new BaseRsp<>(PageUtil.getListResult(situationDbAccessIPage));
	}

	@ApiOperation("访问日志-新增")
	@GetMapping("/")
	public BaseRsp<SituationDbAccess>
			addDbAccess(@ApiParam @Validated SituationDbAccessAddReq req) {
		SituationDbAccess situationDbAccess = new SituationDbAccess();
		BeanUtils.copyProperties(req, situationDbAccess);
		situationDbAccess.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		int insert = situationDbAccessMapper.insert(situationDbAccess);
		return new BaseRsp<>(situationDbAccess);
	}
}
