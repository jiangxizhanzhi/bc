package com.cyberpeace.oj.practice.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.PracticeMixinController;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.controller.req.PracticeOperationListReq;
import com.cyberpeace.oj.practice.controller.req.PracticeOperationReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "实践操作练习")
@RestController
@RequestMapping("/oj/practice-operation")
@RequiresAuthentication
public class PracticeOperationController extends PracticeMixinController<PracticeOperation> {

	@Autowired
	PracticeOperationMapper practiceOperationMapper;

	@Autowired
	PracticeCategoryService practiceCategoryService;

	@GetMapping()
	@ApiOperation("获取单个实践操作")
	public BaseRsp<PracticeOperation>
			get(@RequestParam("resource_id") @NotBlank String resourceId) {
		PracticeOperation object = practiceOperationMapper.selectById(resourceId);
		if (object == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		return new BaseRsp<>(object);
	}

	@GetMapping("/list")
	@ApiOperation("获取实践操作列表")
	public BaseRsp<ListRsp<PracticeOperation>> getList(PracticeOperationListReq queryParams) {
		IPage<PracticeOperation> result = practiceOperationMapper.getList(queryParams.toPageParam(),
				queryParams.getPattern(), queryParams.getPracticeSet(), queryParams.getCategory());

		return new BaseRsp<>(PageUtil.getListResult(result));
	}

	@PostMapping()
	@ApiOperation("创建实践操作")
	public BaseRsp<PracticeOperation> create(@RequestBody PracticeOperationReq requestData) {
		PracticeOperation object = new PracticeOperation();
		object.setCreateUserId(getRequestUserId());
		BeanUtils.copyProperties(requestData, object);
		practiceOperationMapper.insert(object);

		return new BaseRsp<>(practiceOperationMapper.selectById(object.getResourceId()));
	}

	@PatchMapping()
	@ApiOperation("更新实践操作")
	public BaseRsp<PracticeOperation> partialUpdate(@RequestBody PracticeOperationReq requestData) {
		PracticeOperation object = new PracticeOperation();
		BeanUtils.copyProperties(requestData, object);
		practiceOperationMapper.updateById(object);

		return new BaseRsp<>(practiceOperationMapper.selectById(object.getResourceId()));
	}

	@DeleteMapping()
	@ApiOperation("批量删除实践操作")
	public BaseRsp<Integer> batchDelete(@RequestBody ResourceDelReq requestData) {
		return new BaseRsp<>(practiceOperationMapper.batchDelete(requestData.getResourceIds()));
	}
}
