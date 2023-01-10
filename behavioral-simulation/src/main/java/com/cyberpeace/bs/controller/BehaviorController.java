package com.cyberpeace.bs.controller;

import com.cyberpeace.bs.controller.req.IdListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Api(tags = "行为模拟-行为管理")
@RestController
@RequestMapping("/bs/behavior")
// @RequiresAuthentication
public class BehaviorController {

	@Autowired
	LcService lcService;

	@ApiOperation("获取行为列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<LcBehavior>> pageBehavior(BehaviorPageReq pageReq) {
		ListRsp<LcBehavior> behaviorListRsp = lcService.getLcBehaviorList(pageReq);
		return new BaseRsp<>(behaviorListRsp);
	}

	@ApiOperation("获取变量列表")
	@GetMapping("/variable")
	public BaseRsp<VariableListBo> getVariables(@ApiParam(value = "行为id", name = "id") String id) {
		VariableListBo res = lcService.getVariableList(id);
		return new BaseRsp(res);
	}

	@ApiOperation("编辑行为变量")
	@PutMapping("/variable")
	public BaseRsp updateVariables(@Validated @RequestBody VariableModifyReq req) {
		lcService.updateVariable(req);
		return new BaseRsp();
	}

	@ApiOperation("批量删除行为接口")
	@DeleteMapping("/")
	public BaseRsp<Boolean> deleteBehavior(@Validated @RequestBody IdListReq req) {
		Boolean rsp = lcService.deleteBehavior(req.getIds());
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("导出行为")
	@PostMapping("/export")
	public void exportFile(HttpServletResponse response, @Validated @RequestBody IdListReq req)
			throws IOException {
		ServletOutputStream out = null;
		InputStream inputStream = null;
		try {
			// 与服务器建立连接
			inputStream = lcService.getBehaviorExportStream(req.getIds());
			try {
				// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
				response.setContentType("multipart/form-data");
			} catch (Exception e) {
				e.printStackTrace();
			}
			out = response.getOutputStream();
			// 读取文件流
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
