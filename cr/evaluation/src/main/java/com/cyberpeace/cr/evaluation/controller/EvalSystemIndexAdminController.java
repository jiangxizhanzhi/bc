package com.cyberpeace.cr.evaluation.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalNameListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSystemReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalSystemMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.EvalSystemService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "后台-测试靶场-指标体系")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/system")
@Slf4j
public class EvalSystemIndexAdminController {

	@Autowired
	private EvalIndexService evalIndexService;

	@Autowired
	private EvalSystemMapper evalSystemMapper;

	@Autowired
	private EvalSystemService evalSystemService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalRecordMapper evalRecordMapper;

	@ApiOperation("新增体系指标")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystem> addEvalIndex(@RequestBody @Validated EvalSystemReq evalSystemReq) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				EvalSystem evalSystem = saveEvalIndex(evalSystemReq);
				evalSystem.setCreateUserId(UserInfoContext.getUserContext().getUserId());
				evalSystemService.save(evalSystem);
			}
		});

		return new BaseRsp(evalSystemReq);
	}

	@ApiOperation("编辑体系指标")
	@PutMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystem> editEvalIndex(@RequestBody @Validated EvalSystemReq evalSystemReq) {

		String evalSystemId = evalSystemReq.getEvalSystemId();
		LambdaQueryWrapper<EvalRecord> query = new LambdaQueryWrapper<>();
		query.eq(EvalRecord::getSystemId, evalSystemId);
		List<EvalRecord> evalRecords = evalRecordMapper.selectList(query);
		if(evalRecords.size() > 0){
			throw new BusinessException(CrBusinessErrorCode.SYSTEM_BIND_RECORD);
		}

		EvalSystem evalSystem = evalSystemService.getById(evalSystemReq.getEvalSystemId());
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", evalSystemReq.getEvalSystemId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				evalIndexService.deleteBatchByEvalSystemId(evalSystemReq.getEvalSystemId());

				EvalSystem evalSystem = saveEvalIndex(evalSystemReq);

				evalSystemService.updateById(evalSystem);
			}
		});

		return new BaseRsp(evalSystemReq);
	}

	private EvalSystem saveEvalIndex(@Validated @RequestBody EvalSystemReq evalSystemReq) {
		validateReq(evalSystemReq);

		evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getFirstIndexes(),
				EvalIndex.Level.FIRST.getCode()));

		// 保存新的三级指标，并与指标项关联
		List<EvalIndex> thirdIndexList = convertIndexReqToIndex(evalSystemReq.getThirdIndexes(),
				EvalIndex.Level.THIRD.getCode());
		evalIndexService.saveBatch(thirdIndexList);

		// 修改二级指标与新三级指标的关联
		changeChildrenIds(evalSystemReq.getSecondIndexes(), thirdIndexList);

		List<EvalIndex> secondIndexList = convertIndexReqToIndex(evalSystemReq.getSecondIndexes(),
				EvalIndex.Level.SECOND.getCode());
		evalIndexService.saveBatch(secondIndexList);

		EvalSystem evalSystem = BeanUtils.copy(evalSystemReq, EvalSystem.class);
		evalSystem.setResourceId(evalSystemReq.getEvalSystemId());
		// 修改连接线关联
		evalSystem.setConnlist(changeConnlist(evalSystemReq.getConnlist(), thirdIndexList));

		evalSystem.setThirdIndexNum(thirdIndexList.size());
		evalSystem.setSecondIndexNum(secondIndexList.size());
		evalSystem.setFirstIndexNum(1);

		return evalSystem;
	}

	private String changeConnlist(String connlist, List<EvalIndex> thirdIndexList) {
		for (EvalIndex evalIndex : thirdIndexList) {
			connlist = connlist.replace(evalIndex.getEvalIndicatorId(), evalIndex.getResourceId());
		}
		return connlist;
	}

	private void validateReq(EvalSystemReq req) {
		String resourceId = req.getEvalSystemId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalSystemMapper.existByName(req.getName(), resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述长度
		CrCommonValidator.validateLength(req.getDescription(), CrCommonConstants.ZERO_STR_LEN,
				CrCommonConstants.MAX_DESC_LEN, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}

	private void changeChildrenIds(List<IndexContext> secondIndexList,
			List<EvalIndex> thirdIndexList) {
		Map<String, String> thirdIndexMaps = StreamApiUtils.toMap(thirdIndexList,
				EvalIndex::getEvalIndicatorId, evalIndex -> evalIndex.getResourceId());

		for (IndexContext indexContext : secondIndexList) {
			indexContext.setChildrenIds(indexContext.getChildrenIds().stream()
					.map(string -> thirdIndexMaps.get(string)).collect(Collectors.toList()));
		}
	}

	@ApiOperation("查询体系指标")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemRsp> getEvalIndex(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {

		EvalSystem evalSystem = evalSystemService.getById(resource_id);
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", resource_id);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemRsp evalSystemRsp = convertEvalSystemToEvalSystemRsp(evalSystem);
		return new BaseRsp(evalSystemRsp);
	}

	private EvalSystemRsp convertEvalSystemToEvalSystemRsp(EvalSystem evalSystem) {
		EvalSystemRsp evalSystemRsp = BeanUtils.copy(evalSystem, EvalSystemRsp.class);

		evalSystemRsp.setEvalSystemId(evalSystem.getResourceId());
		evalSystemRsp.setFirstIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.FIRST.getCode()));
		evalSystemRsp.setSecondIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.SECOND.getCode()));
		evalSystemRsp.setThirdIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.THIRD.getCode()));

		return evalSystemRsp;
	}

	private List<EvalIndex> convertIndexReqToIndex(List<IndexContext> indexContextList,
			Integer level) {

		List<EvalIndex> evalIndexList = indexContextList.stream().map(indexReq -> {
			EvalIndex evalIndex = BeanUtils.copy(indexReq, EvalIndex.class);

			evalIndex.setResourceId(indexReq.getIndexId());
			evalIndex.setChildrenIds(JsonUtil.arrayToString(indexReq.getChildrenIds()));
			evalIndex.setAggregatingAlgorithmId(indexReq.getAlgorithmId());
			evalIndex.setAggregatingAlgorithmParams(indexReq.getParams());
			evalIndex.setLevel(level);

			// 三级指标特殊，前台传过来的是指标项，这里新建一个，并建立关联关系,修改连接线和子指标列表
			if (EvalIndex.Level.THIRD.getCode().equals(level)) {

				// 三级指标的id等于指标项id，说明是新增需要修改id，否则是编辑不改id
				if (indexReq.getIndexId().equals(indexReq.getEvalIndicatorId())) {
					evalIndex.setResourceId(null);
					evalIndex.setEvalIndicatorId(indexReq.getIndexId());

				}
				evalIndex.setEvalIndicatorId(indexReq.getEvalIndicatorId());

			}

			return evalIndex;
		}).collect(Collectors.toList());

		return evalIndexList;
	}

	@DeleteMapping()
	@ApiOperation("批量删除")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		Boolean result = evalSystemService.batchDelete(deleteReq.getIds());

		return new BaseRsp<>(result);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM,
			MenuConstant.WEB_CR_EVALUATION }, logical = Logical.OR)
	public BaseRsp<ListRsp<EvalSystemRsp>> list(EvalNameListReq listReq) {
		EvalListQuery listQuery = BeanUtils.copy(listReq, EvalListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		listQuery.setName(listReq.getPattern());

		// 默认时间排序
		listQuery.setSort("-create_time");

		IPage<EvalSystem> data = evalSystemService.page(listQuery);

		return new BaseRsp(PageUtil.getListResult(data));
	}
}