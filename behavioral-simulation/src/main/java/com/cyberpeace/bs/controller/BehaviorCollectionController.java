package com.cyberpeace.bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.controller.req.PageReqWithType;
import com.cyberpeace.bs.service.BehaviorCollectionService;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.colletion.BehaviorCollectionDetail;
import com.cyberpeace.bs.service.bo.colletion.BehaviorCollectionListBo;
import com.cyberpeace.bs.service.bo.colletion.CollectionAddReq;
import com.cyberpeace.bs.service.bo.colletion.CollectionModifyReq;
import com.cyberpeace.bs.utils.BsValidator;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 行为集合
 *
 * @author SN
 * @date 2023/07/27
 */
@Api(tags = "行为模拟-行为集合")
@RestController
@RequestMapping("/bs/collection")
public class BehaviorCollectionController {

	@Autowired
	private BehaviorCollectionService behaviorCollectionService;

	/**
	 * 列表
	 *
	 * @return {@code BaseRsp<>}
	 */
	@ApiOperation("获取行为集合分页")
	@GetMapping("/list")
	public BaseRsp<ListRsp<BehaviorCollectionListBo>> list(PageReqWithType pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<BehaviorCollectionListBo> res = behaviorCollectionService.list(page,
				pageReq.getAccess_os(), pageReq.getPattern());
		return new BaseRsp(res);
	}

	/**
	 * 详情
	 *
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("行为集合详情")
	@GetMapping("/")
	public BaseRsp<BehaviorCollectionDetail> detail(
			@ApiParam(value = "集合id", name = "resource_id", required = true) String resource_id) {
		BsValidator.validateId(resource_id);
		BehaviorCollectionDetail detail = behaviorCollectionService.getDetail(resource_id);
		return new BaseRsp(detail);
	}

	/**
	 * 创建
	 *
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("创建行为集合")
	@PostMapping("/")
	public BaseRsp create(@Validated @RequestBody CollectionAddReq addReq) {
		addReq.validate();
		behaviorCollectionService.create(addReq);
		return new BaseRsp();
	}

	/**
	 * 删除
	 *
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("删除行为集合")
	@DeleteMapping("/")
	public BaseRsp delete(@Validated @RequestBody IdListReq req) {
		behaviorCollectionService.deleteByIds(req.getIds());
		return new BaseRsp();
	}

	/**
	 * 更新
	 *
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("编辑行为集合")
	@PatchMapping("/")
	public BaseRsp update(@Validated @RequestBody CollectionModifyReq modifyReq) {
		modifyReq.validate();
		BsValidator.validateId(modifyReq.getResourceId());
		behaviorCollectionService.modify(modifyReq);
		return new BaseRsp();
	}

	/**
	 * 获取集合包含的行为
	 *
	 * @return {@code BaseRsp<List<Behavior>>}
	 */
	@ApiOperation("获取集合包含的行为")
	@PostMapping("/behavior")
	public BaseRsp<List<Behavior>> getBehaviors(@Validated @RequestBody IdListReq idListReq) {
		List<Behavior> behaviorList = behaviorCollectionService.getBehaviorList(idListReq.getIds());
		return new BaseRsp<>(behaviorList);
	}

}
