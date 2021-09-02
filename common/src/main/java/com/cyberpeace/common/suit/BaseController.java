package com.cyberpeace.common.suit;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.ObjUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 支持新增、删除、单条查询、修改(部分修改)
 * @param <Entity> 实体类
 * @param <AddInput> 新增请求模型
 * @param <ModifyInput> 修改请求模型
 * @param <Output> 单条查询输出模型
 */
@RestController
public abstract class BaseController<Entity extends BaseEntity, AddInput, ModifyInput, Output> {

	protected abstract IService<Entity> getService();

	protected abstract Entity getOr404(String resourceId);

	protected abstract Entity getEntityFromAddInput(AddInput input);

	protected abstract Entity getEntityFromModifyInput(ModifyInput input, Entity originEntity);

	protected abstract Output getOutputFromEntity(Entity entity);

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

		Entity originEntity = getOr404(resourceId);

		Entity entity = getEntityFromModifyInput(input, originEntity);

		getService().updateById(entity);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<Output> retrieve(String resource_id) {
		Entity t = getOr404(resource_id);
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
