package com.cyberpeace.evaluate.controller;

import java.util.List;

import com.cyberpeace.common.exception.evaluate.ItemStoppedException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.evaluate.controller.req.ValidationInstanceAddReq;
import com.cyberpeace.evaluate.controller.req.ValidationItemModifyReq;
import com.cyberpeace.evaluate.controller.req.ValidationItemTriggerReq;
import com.cyberpeace.evaluate.controller.req.ValidationSubToolReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.evaluate.dao.entity.TestIndexOut;
import com.cyberpeace.evaluate.service.ValidationInstanceService;
import com.cyberpeace.evaluate.service.bo.TestInstanceBO;
import com.cyberpeace.evaluate.service.bo.TestInstanceSimplfyBO;
import com.cyberpeace.evaluate.service.context.TestResultContext;

@Api(tags = "es-系统评估实例相关api")
@RestController
@RequestMapping("/validation/instance")
@RequiresAuthentication
public class ValidationInstanceController {

	@Autowired
	private ValidationInstanceService validationInstanceService;

	/**
	 * 开启一个新的测试实例
	 * @param validationInstanceAddReq
	 * @return
	 */
	@PostMapping("/start")
	@ApiOperation("用户点击开始测试")
	public BaseRsp startValidationInstance(
			@RequestBody ValidationInstanceAddReq validationInstanceAddReq) {
		validationInstanceAddReq.validate();

		String instanceId = validationInstanceService
				.startNewInstance(validationInstanceAddReq.getTestInfoId());

		BaseRsp<String> rsp = new BaseRsp<>();
		rsp.setData(instanceId);

		return rsp;
	}

	/**
	 * 触发工具动作执行。
	 *
	 * @return
	 */
	@PostMapping("/item")
	@ApiOperation("开始执行测试项")
	public BaseRsp
			triggerValidationItem(@RequestBody ValidationItemTriggerReq validationItemTriggerReq) {
		validationItemTriggerReq.validate();

		validationInstanceService.triggerValidationItem(validationItemTriggerReq);

		return new BaseRsp();
	}

	@PatchMapping("/item")
	@ApiOperation("修改测试想项")
	public BaseRsp
			modifyValidationItem(@RequestBody ValidationItemModifyReq validationItemModifyReq) {
		validationItemModifyReq.validate();

		validationInstanceService.updateInstanceItem(validationItemModifyReq);

		return new BaseRsp();
	}

	/**
	 * 触发工具动作对应的子动作运行
	 * @return
	 */
	@PostMapping("/index")
	@ApiOperation("触发工具动作对应的子动作运行")
	public BaseRsp<String> triggerSubTool(@RequestBody ValidationSubToolReq validationSubToolReq) {
		validationSubToolReq.validate();

		BaseRsp<String> rsp = new BaseRsp<>();
		try {
			String result = validationInstanceService.triggerTool(validationSubToolReq);

			// 结果要base64解码，因为python运行的返回结果进行了编码
			// result = Base64Util.decode(result);
			rsp.setData(result);
		} catch (ItemStoppedException e) {
			rsp.setData(ConfigUtil.getContextProperty(e.getClass().getName()));
		}

		return rsp;
	}

	/**
	 * 保存测试项对应的测试指标结果
	 * @return
	 */
	@PostMapping("/index/count")
	@ApiOperation("保存或者更新测试的指标项数据")
	public BaseRsp saveIndex(@RequestBody TestIndexOut testIndexOut) {
		validationInstanceService.updateTestIndex(testIndexOut);

		return new BaseRsp();
	}

	/**
	 * 结束当前测试实例，并全量保存测试结果
	 * @param context
	 * @return
	 */
	@PostMapping("/end")
	@ApiOperation("结束当前测试实例，并全量保存测试结果")
	public BaseRsp endValidationInstance(@RequestBody TestResultContext context) {
		validationInstanceService.endValidationInstance(context);
		return new BaseRsp();
	}

	@GetMapping("/{info_id}")
	@ApiOperation("进入评估")
	public BaseRsp<TestInstanceBO>
			getInstanceData(@ApiParam("评估id") @PathVariable("info_id") String infoId) {
		TestInstanceBO data = validationInstanceService.getInstance(infoId);
		BaseRsp<TestInstanceBO> rsp = new BaseRsp<>();
		rsp.setData(data);

		return rsp;
	}

	@GetMapping("/all/{info_id}")
	@ApiOperation("获取系统评估方案所有的评估实例信息")
	public BaseRsp<List<TestInstanceSimplfyBO>>
			getAllInstance(@ApiParam("系统评估id") @PathVariable("info_id") String infoId) {
		List<TestInstanceSimplfyBO> data = validationInstanceService.getAllInstance(infoId);

		BaseRsp<List<TestInstanceSimplfyBO>> rsp = new BaseRsp<>();
		rsp.setData(data);

		return rsp;
	}
}
