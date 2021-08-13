package com.cyberpeace.oj.demo;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Api(tags = "接口demo")
@RestController
@RequestMapping("/test")
public class DemoController {

	@ApiOperation("get方法的demo1")
	@GetMapping("/get1")
	public BaseRsp<String> get1(@ApiParam(value = "查询id", required = true) String resourceId) {
		String result = resourceId + "success1234";

		BaseRsp<String> ret = new BaseRsp<>();
		ret.setData(result);

		Map<String, String> map = Maps.newHashMap();
		map.put("ket1","v");
		String v1 = map.get("key1");
		v1.toLowerCase();

		List<String> lists = new ArrayList<>();
		Map<String, String> map2 = new HashMap<>();



		HashMap<String, String> map1 = Maps.newHashMap();

		return ret;
	}

	@ApiOperation("get方法的demo2")
	@GetMapping("/get2")
	@RequiresRoles("Admin")
	public BaseRsp<OutputDemoModel> get2(@Validated InputDemoModel inputDemoModel) {
		OutputDemoModel model = new OutputDemoModel();
		model.setResourceId("get2");
		model.setName("get2接口的返回");

		BaseRsp<OutputDemoModel> ret = new BaseRsp<>();
		ret.setData(model);

		return ret;
	}

	@ApiOperation("post方法的demo1")
	@PostMapping("/post1")
	public BaseRsp<OutputDemoModel> post1(@Validated @RequestBody InputDemoModel2 inputDemoModel) {
		OutputDemoModel model = new OutputDemoModel();
		model.setResourceId("post1");
		model.setName("post1接口的返回");

		BaseRsp<OutputDemoModel> ret = new BaseRsp<>();
		ret.setData(model);

		return ret;
	}

	@ApiOperation("post方法的demo2，带异常")
	@PostMapping("/post2")
	public BaseRsp<OutputDemoModel> post2(@Validated @RequestBody InputDemoModel2 inputDemoModel) {
		OutputDemoModel model = new OutputDemoModel();
		model.setResourceId("post1");
		model.setName("post1接口的返回");

		// 遇到业务错误需要中断服务
		throw new RuntimeException();
	}

	@ApiOperation("put方法的demo1")
	@PutMapping("/put1")
	public BaseRsp<OutputDemoModel> put1(@Validated @RequestBody InputDemoModel2 inputDemoModel) {
		OutputDemoModel model = new OutputDemoModel();
		model.setResourceId("put1");
		model.setName("put1接口的返回");

		BaseRsp<OutputDemoModel> ret = new BaseRsp<>();
		ret.setData(model);

		return ret;
	}

	@ApiOperation("delete方法的demo1")
	@DeleteMapping("/delete1")
	public BaseRsp<OutputDemoModel>
			delete1(@Validated @RequestBody InputDemoModel2 inputDemoModel) {
		OutputDemoModel model = new OutputDemoModel();
		model.setResourceId("delete1");
		model.setName("delete1接口的返回");

		BaseRsp<OutputDemoModel> ret = new BaseRsp<>();
		ret.setData(model);

		return ret;
	}

}
