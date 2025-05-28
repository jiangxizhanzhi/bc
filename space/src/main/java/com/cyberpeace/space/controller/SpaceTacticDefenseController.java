package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 空间战法防御Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Api(tags = "战法库-防御战法")
@RestController
@RequestMapping("/space/tactic/defense")
public class SpaceTacticDefenseController {

	/**
	 * 防御Service
	 *
	 * @see SpaceTacticDefenseService
	 */
	@Autowired
	private SpaceTacticDefenseService defenseService;

	/**
	 * 列表攻击
	 *
	 * @param req 请求体
	 * @return 基本 rsp< list rsp< 防御列表 bo>>
	 * @throws
	 * @see SpaceTacticPageReq
	 * @see BaseRsp<ListRsp<DefenseListBo>>
	 */
	@ApiOperation("防御战法列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<DefenseListBo>> listAttack(SpaceTacticPageReq req) {
		ListRsp<DefenseListBo> res = defenseService.page(req);
		return new BaseRsp(res);
	}

	/**
	 * 新增攻击
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see DefenseAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("新增防御战法")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody DefenseAddReq req) {
		defenseService.add(req);
		return new BaseRsp();
	}

	/**
	 * 更新一个
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see DefenseModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("更新防御战法")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody DefenseModifyReq req) {
		SpaceTacticDefense old=defenseService.getById(req.getResourceId());
		if(Boolean.TRUE.equals(old.getBuiltin())){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		defenseService.updateDefense(req);
		return new BaseRsp();
	}

	/**
	 * 删除
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see IdListReq
	 * @see BaseRsp
	 */
	@ApiOperation("删除防御战法")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		List<SpaceTacticDefense> defenses=defenseService.listByIds(req.getIds());
		for(SpaceTacticAttack defense:defenses){
			if (Boolean.TRUE.equals(defense.getBuiltin())) {
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
		}
		defenseService.deleteBatch(req.getIds());
		return new BaseRsp();
	}

	/**
	 * 获取adetail
	 *
	 * @param resource_id 资源id
	 * @return Base RSP< 防御细节>
	 * @throws
	 * @see String
	 * @see BaseRsp<DefenseDetail>
	 */
	@ApiOperation("获取防御战法详情")
	@GetMapping("")
	public BaseRsp<DefenseDetail>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		DefenseDetail detail = defenseService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	/**
	 * Pagec
	 *
	 * @param reqBase 请求体基础
	 * @return 基础RSP<列表RSP < 战法防御类别博>>
	 * @throws
	 * @see PageReqBase
	 * @see BaseRsp<ListRsp<TacticDefenseCategoryBo>>
	 */
	@ApiOperation("防御战法战术分页")
	@GetMapping("/category/page")
	public BaseRsp<ListRsp<TacticDefenseCategoryBo>> pageC(PageReqBase reqBase) {
		ListRsp<TacticDefenseCategoryBo> rsp = defenseService.pageCategory(reqBase);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 添加
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see TacticDefenseCategoryAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("新增防御战法战术")
	@PostMapping("/category")
	public BaseRsp addC(@RequestBody TacticDefenseCategoryAddReq req) {
		defenseService.addDefenseCategory(req);
		return new BaseRsp();
	}

	/**
	 * 更新c
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see TacticDefenseCategoryModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("更新防御战法战术")
	@PatchMapping("/category")
	public BaseRsp updateC(@RequestBody TacticDefenseCategoryModifyReq req) {
		defenseService.updateDefenseCategory(req);
		return new BaseRsp();
	}

	/**
	 * 删除
	 *
	 * @param req 请求体
	 * @return 基础响应
	 * @throws
	 * @see IdListReq
	 * @see BaseRsp
	 */
	@ApiOperation("删除防御战法战术")
	@DeleteMapping("/category")
	public BaseRsp deleteC(@RequestBody IdListReq req) {
		List <String> resourceIds =defenseService.listByCategoryids(req.getIds());
		if (resourceIds!= null && !resourceIds.isEmpty()) {
			throw new BusinessException(ErrorCode.RELATED_DATA_EXIST);
		}
		defenseService.deleteCategory(req.getIds());
		return new BaseRsp();
	}

	/**
	 * 获取cdetail
	 *
	 * @param resource_id 资源id
	 * @return Base RSP< 防御类别 BO>
	 * @throws
	 * @see String
	 * @see BaseRsp<DefenseCategoryBo>
	 */
	@ApiOperation("防御战法战术详情")
	@GetMapping("/category")
	public BaseRsp<DefenseCategoryBo>
			getCDetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
        DefenseCategoryBo bo = defenseService.getCategoryDetail(resource_id);
        return new BaseRsp<>(bo);
	}

	/**
	 * 列表防御战法战术全部
	 *
	 * @return {@code BaseRsp<List<StagedDefenseCategory>> }
	 */
	@ApiOperation("防御战法战术全部")
	@GetMapping("/category/list")
	public BaseRsp<List<StagedDefenseCategory>> listC() {
		List<TacticDefenseCategoryBo> bos = defenseService.listCategory();
		List<StagedDefenseCategory> rsp = Lists.newArrayList();

		StagedDefenseCategory pre = new StagedDefenseCategory();
		pre.setStage(SpaceCommonConstant.TacticStage.PRE);
		pre.setCategories(StreamApiUtils.filter(bos,
				bo -> bo.getStage().equals(SpaceCommonConstant.TacticStage.PRE)));
		rsp.add(pre);

		StagedDefenseCategory in = new StagedDefenseCategory();
		in.setStage(SpaceCommonConstant.TacticStage.IN);
		in.setCategories(StreamApiUtils.filter(bos,
				bo -> bo.getStage().equals(SpaceCommonConstant.TacticStage.IN)));
		rsp.add(in);

		StagedDefenseCategory post = new StagedDefenseCategory();
		post.setStage(SpaceCommonConstant.TacticStage.POST);
		post.setCategories(StreamApiUtils.filter(bos,
				bo -> bo.getStage().equals(SpaceCommonConstant.TacticStage.POST)));
		rsp.add(post);

		return new BaseRsp<>(rsp);
	}

	/**
	 * 按类别列出
	 *
	 * @param category_id 类别id
	 * @return {@code BaseRsp<List<DefenseDetail>> }
	 */
	@ApiOperation("根据战术获取战法")
	@GetMapping("/list")
	public BaseRsp<List<DefenseDetail>>
			listByCategory(@ApiParam(value = "战术id", name = "category_id") String category_id) {
		List<DefenseDetail> res = defenseService.listByCategoryId(category_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取防御框架
	 *
	 * @return 基础RSP<列表 < 空间框架级别>>
	 * @throws
	 * @see BaseRsp<List<SpaceFrameLevel>>
	 */
	@ApiOperation("获取防御战法框架")
	@GetMapping("/frame")
	public BaseRsp<List<SpaceFrameLevel>> getDefenseFrame() {
		SpaceFrameLevel level = defenseService.getFrame();
		return new BaseRsp<>(level.getChildren());
	}
}
