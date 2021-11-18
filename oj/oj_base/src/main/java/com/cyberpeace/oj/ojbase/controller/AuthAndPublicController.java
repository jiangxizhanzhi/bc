package com.cyberpeace.oj.ojbase.controller;

import javax.validation.Valid;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.ojbase.controller.req.ResourceAuthReq;
import com.cyberpeace.oj.ojbase.controller.req.ResourceShareReq;

import io.swagger.annotations.ApiOperation;

public abstract class AuthAndPublicController<T extends IService<E>, E> extends PublicController {

	@PatchMapping("/batch-auth")
	@ApiOperation("资源授权访问")
	public BaseRsp<Boolean> batchAuth(@RequestBody @Validated ResourceAuthReq requestData) {

		Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
			AuthAndPublicData data = new AuthAndPublicData();
			data.setResourceId((String) resourceId);
			data.setReadAuthType(requestData.getReadAuthType());
			if (CollectionUtils.isNotEmpty(requestData.getReadAuthIds())) {
				data.setReadAuthIds(JsonUtil.arrayToString(requestData.getReadAuthIds()));
			}
			return data;
		});

		return new BaseRsp<>(result);
	}

	@PatchMapping("/batch-share")
	@ApiOperation("资源授权操作")
	public BaseRsp<Boolean> batchShare(@RequestBody ResourceShareReq requestData) {

		Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
			AuthAndPublicData data = new AuthAndPublicData();
			data.setResourceId((String) resourceId);
			data.setOperationAuthType(requestData.getOperationAuthType());
			if (CollectionUtils.isNotEmpty(requestData.getOperationAuthIds())) {
				data.setReadAuthIds(JsonUtil.arrayToString(requestData.getOperationAuthIds()));
			}
			return data;
		});
		return new BaseRsp<>(result);
	}

}
