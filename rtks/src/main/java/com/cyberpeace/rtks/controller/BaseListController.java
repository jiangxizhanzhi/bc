package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.dao.entity.BaseEntity;
import com.cyberpeace.rtks.service.BaseService;

import io.swagger.annotations.ApiOperation;

@RestController
public abstract class BaseListController<Entity extends BaseEntity, Input, AddInput extends Input, ModifyInput extends Input, Output, ListOutput, Service extends BaseService<Entity>, ListRequest> extends BaseController<Entity, Input, AddInput, ModifyInput, Output, Service> {

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
