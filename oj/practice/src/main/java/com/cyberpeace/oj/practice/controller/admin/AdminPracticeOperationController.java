package com.cyberpeace.oj.practice.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.practice.controller.req.*;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.OperationBo;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "oj-后台实践操作练习相关api")
@RestController
@RequestMapping("/oj/admin/practice/operation")
@RequiresAuthentication
public class AdminPracticeOperationController extends PublicController<PracticeOperationService, PracticeOperation> {

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@ApiOperation("详细信息查询")
	@GetMapping("")
	public BaseRsp<OperationBo> get(@ApiParam(name = "resource_id",
			value = "资源编号") @RequestParam("resource_id") String resourceId) {

		OperationBo operationBo = practiceOperationService.selectById(resourceId);
		return new BaseRsp(operationBo);
	}

	@ApiOperation("实践操作习题新增")
	@PostMapping("")
	public BaseRsp add(@RequestBody @Validated(OperationAddReq.class) OperationAddReq addReq) {

		PracticeOperation addOperation = convertReq(addReq);
		Integer result = practiceOperationService.addPracticeOperation(addOperation,
				addReq.getOjSceneConfig());

		return new BaseRsp(result);
	}

	/**
	 * 将实践操作增加和删除的请求值转换为实体类
	 * @param addReq
	 * @return
	 */
	private PracticeOperation convertReq(OperationAddReq addReq) {

		PracticeOperation practiceOperation = BeanUtils.copy(addReq, PracticeOperation.class);
		practiceOperation.setKnowledge(JsonUtil.toJsonString(addReq.getKnowledge()));
		practiceOperation.setAnswer(JsonUtil.toJsonString(addReq.getAnswer()));

		return practiceOperation;
	}

	@ApiOperation("实践操作习题修改")
	@PatchMapping("")
	public BaseRsp<Integer> update(@RequestBody @Validated OperationUpdateReq updateReq) {

		PracticeOperation updateOperation = convertReq(updateReq);
		Integer result = practiceOperationService.updateOperation(updateOperation,
				updateReq.getOjSceneConfig());
		return new BaseRsp(result);
	}

	@ApiOperation("实践操作习题删除")
	@DeleteMapping("")
	public BaseRsp<Integer> delete(@RequestBody @Validated ResourceDelReq delReq) {

		int result = practiceOperationMapper.deleteBatchIds(delReq.getResourceIds());
		return new BaseRsp<>(result);
	}

	@ApiOperation("分页查询实践操作习题")
	@GetMapping("/list")
	public BaseRsp<ListRsp<AdminOperationListBo>> list(AdminOperationListReq listReq) {

		OperationListQueryBo listQuery = BeanUtils.copy(listReq, OperationListQueryBo.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		List<String> categoryIds = practiceCategoryService
				.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);
		listQuery.setCategoryIds(categoryIds);

		Page page = listReq.toPageParam();

		IPage<AdminOperationListBo> iPage = practiceOperationService.pageByQuery(page, listQuery,
				authContext);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	@ApiOperation("批量公开/隐藏题解")
	@PatchMapping("/writeup_public")
	public BaseRsp<Boolean> writeupPublic(@RequestBody @Validated ResourcePublicReq publicReq) {

		List<PracticeOperation> operations = StreamApiUtils.simpleMap(publicReq.getResourceIds(),
				resourceId -> {
					PracticeOperation operation = new PracticeOperation();
					operation.setResourceId(resourceId);
					operation.setWriteupPublic(publicReq.getIsPublic());
					return operation;
				});

		boolean result = practiceOperationService.updateBatchById(operations);
		return new BaseRsp<>(result);

	}

	@ApiOperation("获取场景拓扑信息")
	@GetMapping("/scene_topo")
	public BaseRsp sceneTopo() {
		// todo 场景信息获取
		return null;
	}

}
