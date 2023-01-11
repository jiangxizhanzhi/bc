package com.cyberpeace.bs.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.bs.controller.req.IdListReq;
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
		CloseableHttpResponse res = lcService.getBehaviorExportRsp(req.getIds());
		try {
			if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("远程错误: " + res.getEntity().getContent().toString());
			}
			// 7.拿到响应结果实体
			HttpEntity entity = res.getEntity();
			if (entity == null) {
				throw new RuntimeException("Empty response");
			}
			// 8.获取响应类型
			Header contentType = entity.getContentType();
			// 获取响应头部信息
			if (!contentType.toString().contains("application/zip")) {
				String resultMsg = EntityUtils.toString(entity);
				JSONObject resultJson = JSONObject.parseObject(resultMsg);
				throw new RuntimeException(resultJson.getString("msg"));
			}
			inputStream = entity.getContent();
			// 1.设置文件ContentType类型
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", String.format(
					"attachment; filename=behavior_export_%d.zip", System.currentTimeMillis()));
			out = response.getOutputStream();
			// 读取文件流
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			// 8.释放资源
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		} finally {
			try {
				// 9.关闭连接
				res.close();
				if (inputStream != null) {
					inputStream.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@ApiOperation("导入行为")
	@PutMapping("/import")
	public BaseRsp importBehavior(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BusinessException(ErrorCode.EMPTY_FILE);
		}
		Object rsp = lcService.importBehavior(file);
		return new BaseRsp(rsp);
	}

}
