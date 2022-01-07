package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.practice.controller.req.AdminTheoryListReq;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.controller.req.TheoryAddReq;
import com.cyberpeace.oj.practice.controller.req.TheoryUpdateReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.AdminTheoryBo;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

	@ApiOperation("理论基础习题新增")
	@PostMapping("")
	public BaseRsp<Integer>
			add(@RequestBody @Validated({ Create.class }) TheoryAddReq theoryAddReq) {

		PracticeTheory practiceTheory = BeanUtils.copy(theoryAddReq, PracticeTheory.class);
		practiceTheory.setTitle(theoryAddReq.getContent());
		practiceTheory.setDifficulty(Difficulty.PRIMARY.getCode());

		practiceTheory.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		replaceAnswer(practiceTheory);

		Integer result = practiceTheoryService.create(practiceTheory, authContext);

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
	public BaseRsp<Integer>
			update(@RequestBody @Validated({ Update.class }) TheoryUpdateReq theoryUpdateReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		PracticeTheory practiceTheory = BeanUtils.copy(theoryUpdateReq, PracticeTheory.class);

		int result = practiceTheoryService.updateTheory(practiceTheory, authContext);

		return new BaseRsp(result);
	}

	@ApiOperation("获取理论基础习题详情")
	@GetMapping("")
	public BaseRsp<AdminTheoryBo> get(@RequestParam("resource_id") String resourceId) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(resourceId);
		AdminTheoryBo theoryRes = BeanUtils.copy(practiceTheory, AdminTheoryBo.class);

		return new BaseRsp<>(theoryRes);
	}

	@ApiOperation("基础理论习题资源删除")
	@DeleteMapping("")
	public BaseRsp<Integer> delete(@RequestBody @Validated ResourceDelReq delReq) {

		Integer result = practiceTheoryService.batchDelete(delReq.getResourceIds());
		return new BaseRsp<>(result);
	}

	@ApiOperation("分页查询理论基础习题")
	@GetMapping("/list")
	public BaseRsp<ListRsp<PracticeTheoryListBo>> list(AdminTheoryListReq listReq) {

		Page page = listReq.toPageParam();
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

}
