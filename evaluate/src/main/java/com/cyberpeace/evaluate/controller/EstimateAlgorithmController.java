package com.cyberpeace.evaluate.controller;

import java.util.Arrays;
import java.util.List;

import com.cyberpeace.evaluate.controller.req.DeleteAlgorithmReq;
import com.cyberpeace.evaluate.controller.res.EstimateAlgorithmRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.controller.req.AlgorithmPageReq;
import com.cyberpeace.evaluate.controller.req.EstimateAlgorithmReq;
import com.cyberpeace.evaluate.dao.EstimateAlgorithmDao;
import com.cyberpeace.evaluate.dao.entity.AlgorithmParam;
import com.cyberpeace.evaluate.dao.entity.EstimateAlgorithm;
import com.cyberpeace.evaluate.service.EstimateAlgorithmService;
import com.google.common.base.Preconditions;

@Api(tags = "es-评估算法相关api")
@RestController
@RequestMapping("/estimate/algorithm")
@RequiresAuthentication
public class EstimateAlgorithmController {

	@Autowired
	private EstimateAlgorithmService estimateAlgorithmService;

	@Autowired
	private EstimateAlgorithmDao estimateAlgorithmDao;

	/**
	 * 新增算法
	 * @param req
	 * @return
	 */
	@PostMapping("/create")
	@ApiOperation("新增算法")
	public BaseRsp<String> addAlgorithm(@RequestBody EstimateAlgorithmReq req) {

		String algorithmId = estimateAlgorithmService.add(req);
		BaseRsp<String> rsp = new BaseRsp<>();
		rsp.setData(algorithmId);
		return rsp;
	}

	/**
	 * 修改算法
	 * @param req
	 * @return
	 */
	@PatchMapping("/create/{algorithm_id}")
	@ApiOperation("修改算法")
	public BaseRsp<String> modifyAlgorithm(@RequestBody EstimateAlgorithmReq req,
			@PathVariable("algorithm_id") String algorithmId) {

		Preconditions.checkArgument(StringUtils.isNotBlank(algorithmId));
		req.setEstimateAlgorithmId(algorithmId);
		String modifyId = estimateAlgorithmService.add(req);
		BaseRsp<String> rsp = new BaseRsp<>();
		rsp.setData(modifyId);
		return rsp;
	}

	/**
	 * 查询算法列表
	 * @return
	 */
	@GetMapping("/{type}")
	@ApiOperation("根据类型查询算法列表")
	public BaseRsp<List<EstimateAlgorithm>> algorithmList(
			@ApiParam(name = "type", value = "算法类型") @PathVariable("type") String type) {

		Preconditions.checkArgument(StringUtils.isNotBlank(type), "type null");
		List<EstimateAlgorithm> algorithms = estimateAlgorithmService.selectByType(type);
		BaseRsp<List<EstimateAlgorithm>> rsp = new BaseRsp<>();
		rsp.setData(algorithms);
		return rsp;
	}

	@GetMapping("/list")
	@ApiOperation("分页查询算法列表")
	public ListRsp<EstimateAlgorithm> list(AlgorithmPageReq req) {
		req.validate();
		Page page = req.toPageParam();
		ListRsp<EstimateAlgorithm> algorithms = estimateAlgorithmService
				.selectPageByType(req.getType(), page);
		return algorithms;
	}

	/**
	 * 删除算法
	 * @param req
	 * @return
	 */
	@DeleteMapping("/destroy")
	@ApiOperation("算法删除")
	public BaseRsp deleteAlgorithm(DeleteAlgorithmReq req) {

		Preconditions.checkArgument(StringUtils.isNotBlank(req.getAlgorithmIds()),
				"algorithm_id null");
		List<String> ids = Arrays.asList(req.getAlgorithmIds().split(","));
		estimateAlgorithmService.delete(ids);
		return new BaseRsp();
	}

	/**
	 * 根据编号查询算法信息
	 * @param algorithmId
	 * @return
	 */
	@GetMapping("/create/{algorithm_id}")
	@ApiOperation("根据编号查询算法信息")
	public BaseRsp<EstimateAlgorithmRsp> getAlgorithm(@ApiParam(name = "algorithm_id",
			value = "算法编号") @PathVariable("algorithm_id") String algorithmId) {

		Preconditions.checkArgument(StringUtils.isNotBlank(algorithmId), "algorithm_id null");
		EstimateAlgorithm estimateAlgorithm = estimateAlgorithmDao.selectById(algorithmId);
		if (null == estimateAlgorithm) {
			return new BaseRsp<>();
		}
		EstimateAlgorithmRsp algorithmRsp = new EstimateAlgorithmRsp();
		BeanUtils.copyProperties(estimateAlgorithm, algorithmRsp);
		List<AlgorithmParam> paramList = JSONArray.parseArray(estimateAlgorithm.getParams(),
				AlgorithmParam.class);
		algorithmRsp.setParams(paramList);
		BaseRsp<EstimateAlgorithmRsp> rsp = new BaseRsp<>();
		rsp.setData(algorithmRsp);
		return rsp;
	}

}
