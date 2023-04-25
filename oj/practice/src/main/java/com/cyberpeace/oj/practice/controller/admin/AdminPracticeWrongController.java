package com.cyberpeace.oj.practice.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.res.PracticeWrongRes;
import com.cyberpeace.oj.practice.service.PracticeWrongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "oj-错题集相关api")
@RestController
@RequestMapping("/oj/admin/practice/wrong")
@RequiresAuthentication
public class AdminPracticeWrongController {

	@Autowired
	private PracticeWrongService practiceWrongService;

	@ApiOperation("分页查询所有错题")
	@GetMapping("/list")
	public BaseRsp<ListRsp<PracticeWrongRes>> getPracticeWringSet(PageReqBase pageReqBase) {
		if (pageReqBase.getSort() == null) {
			pageReqBase.setSort("-submitTime");
		}
		Page page = pageReqBase.toPageParam();
		List<PracticeWrongRes> practiceWrongResList = practiceWrongService.getAllPracticeWrong();
		return new BaseRsp<>(PageUtil.getListResult(practiceWrongResList, page, practiceWrongResList.size()));
	}

	@ApiOperation("批量删除错题集")
	@DeleteMapping()
	public BaseRsp<Boolean> deletePracticeWrong(@RequestBody @Validated DeleteReq deleteReq) {
		practiceWrongService.getBaseMapper().deleteBatchIds(deleteReq.getIds());
		return new BaseRsp<>(true);
	}
}
