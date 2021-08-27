package com.cyberpeace.rtks.controller;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.rtks.controller.req.ListReq;
import com.cyberpeace.rtks.dao.entity.BaseEntity;
import com.cyberpeace.rtks.service.BaseListService;

@RestController
public abstract class ListController<Entity extends BaseEntity, Input, AddInput extends Input, ModifyInput extends Input, Output, ListOutput, Service extends BaseListService<Entity>> extends BaseListController<Entity, Input, AddInput, ModifyInput, Output, ListOutput, Service, ListReq<Entity>> {

	@Override
	IPage<Entity> getPage(ListReq<Entity> listReq) {
		return getService().listEntity(listReq.toPageParam(), listReq.getKeyword());
	}

}
