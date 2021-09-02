package com.cyberpeace.common.suit;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 支持新增、删除、单条查询、修改(部分修改)、列表查询
 * @param <Entity> 实体类
 * @param <AddInput> 新增请求模型
 * @param <ModifyInput> 修改请求模型
 * @param <Output> 单条查询输出模型
 * @param <ListOutput> 列表查询输出模型(单条) 可以等同于Output
 */
@RestController
public abstract class ListController<Entity extends BaseEntity, AddInput, ModifyInput, Output, ListOutput> extends BaseListController<Entity, AddInput, ModifyInput, ListReq<Entity>, Output, ListOutput> {

	protected abstract IPage<Entity> listEntity(Page<Entity> page, String keyword);

	@Override
	protected IPage<Entity> getPage(ListReq<Entity> listReq) {
		return listEntity(listReq.toPageParam(), listReq.getKeyword());
	}

}
