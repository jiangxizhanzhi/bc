package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.dao.entity.BaseEntity;

import io.swagger.annotations.ApiOperation;

/**
 * 支持新增、删除、单条查询、修改(部分修改)、列表查询
 * @param <Entity> 实体类
 * @param <AddInput> 新增请求模型
 * @param <ModifyInput> 修改请求模型
 * @param <ListRequest> 列表查询请求模型
 * @param <Output> 单条查询输出模型
 * @param <ListOutput> 列表查询输出模型(单条) 可以等同于Output
 */
@RestController
public abstract class BaseListController<Entity extends BaseEntity, AddInput, ModifyInput, ListRequest, Output, ListOutput> extends BaseController<Entity, AddInput, ModifyInput, Output> {

	abstract ListOutput getListOutputFromEntity(Entity entity);

	abstract IPage<Entity> getPage(ListRequest listReq);

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<ListOutput>> list(ListRequest listReq) {
		IPage<Entity> page = getPage(listReq);

		List<ListOutput> outputs = new ArrayList<>();
		for (Entity record : page.getRecords()) {
			outputs.add(getListOutputFromEntity(record));
		}

		ListRsp<ListOutput> listRsp = new ListRsp<>();
		listRsp.setList(outputs);
		listRsp.setTotal(page.getTotal());

		return new BaseRsp<>(listRsp);
	}
}
