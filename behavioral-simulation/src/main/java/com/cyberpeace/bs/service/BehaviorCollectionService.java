package com.cyberpeace.bs.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.colletion.BehaviorCollectionDetail;
import com.cyberpeace.bs.service.bo.colletion.BehaviorCollectionListBo;
import com.cyberpeace.bs.service.bo.colletion.CollectionAddReq;
import com.cyberpeace.bs.service.bo.colletion.CollectionModifyReq;
import com.cyberpeace.common.model.ListRsp;

public interface BehaviorCollectionService {

	/**
	 * 分页列表
	 *
	 * @param page     分页
	 * @param accessOs 设备类型
	 * @param pattern  关键词
	 * @return {@code ListRsp<BehaviorCollectionListRsp>}
	 */
	ListRsp<BehaviorCollectionListBo> list(Page page, Integer accessOs, String pattern);

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BehaviorCollectionDetail}
	 */
	BehaviorCollectionDetail getDetail(String resourceId);

	/**
	 * 创建
	 *
	 * @param addReq 新增请求体
	 */
	void create(CollectionAddReq addReq);

	/**
	 * 删除通过id列表
	 *
	 * @param ids id列表
	 */
	void deleteByIds(List<String> ids);

	/**
	 * 修改行为集合
	 *
	 * @param modifyReq 修改请求体
	 */
	void modify(CollectionModifyReq modifyReq);

	/**
	 * 获取集合包含的行为列表
	 *
	 * @param collectionIds 集合id列表
	 * @return {@code List<Behavior>}
	 */
	List<Behavior> getBehaviorList(List<String> collectionIds);
}
