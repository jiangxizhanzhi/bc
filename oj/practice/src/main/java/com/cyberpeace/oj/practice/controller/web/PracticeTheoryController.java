package com.cyberpeace.oj.practice.controller.web;

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
import com.cyberpeace.oj.practice.controller.req.WebTheoryListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "理论基础练习")
@RestController
@RequestMapping("/oj/practice-theory")
@RequiresAuthentication
public class PracticeTheoryController {

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@GetMapping("")
	@ApiOperation("查询理论基础练习详细信息")
	public BaseRsp<WebTheoryBo> get(@RequestParam("resource_id") @NotBlank String resourceId) {

		WebTheoryBo theoryBo = practiceTheoryService.selectById(resourceId,
				UserInfoContext.getUserContext().getUserId());

		return new BaseRsp<>(theoryBo);
	}

	@GetMapping("/list")
	@ApiOperation("分页查询理论基础习题")
	public BaseRsp<ListRsp<PracticeTheory>> getList(WebTheoryListReq listReq) {

		Page page = listReq.toPageParam();
		List<String> categoryIds = practiceCategoryService
				.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		TheoryListQueryBo listQueryBo = BeanUtils.copy(listReq, TheoryListQueryBo.class);
		listQueryBo.setCategoryIds(categoryIds);

		IPage<PracticeTheoryListBo> result = practiceTheoryService.pageByQuery(page, listQueryBo,
				authContext);

		return new BaseRsp<>(PageUtil.getListResult(result));
	}

}
