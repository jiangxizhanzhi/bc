package com.cyberpeace.rtks.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.rtks.controller.req.DeleteReq;
import com.cyberpeace.rtks.dao.entity.BaseEntity;
import com.cyberpeace.rtks.service.BaseService;

import io.swagger.annotations.ApiOperation;

@RestController
public abstract class BaseController<Entity extends BaseEntity, Input, AddInput extends Input, ModifyInput extends Input, Output, Service extends BaseService<Entity>> {

	abstract Service getService();

	abstract Entity getEntityFromAddInput(AddInput input);

	abstract Entity getEntityFromModifyInput(ModifyInput input, Entity originEntity);

	abstract Output getOutputFromEntity(Entity entity);

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<Output> create(@RequestBody @Validated AddInput input) {
		Entity entity = getEntityFromAddInput(input);

		getService().save(entity);

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<Output> update(@RequestBody @Validated ModifyInput input) {
		String resourceId = ObjUtil.getCertainFieldValue(input, "resourceId");

		Entity originEntity = getService().getOr404(resourceId);

		Entity entity = getEntityFromModifyInput(input, originEntity);

		getService().updateById(entity);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<Output> retrieve(String resource_id) {
		Entity t = getService().getOr404(resource_id);
		return new BaseRsp<>(getOutputFromEntity(t));
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		performBatchDelete(deleteReq.getIds());
		return new BaseRsp<>();
	}

	protected void performBatchDelete(List<String> ids) {
		getService().removeByIds(ids);
	}

}
