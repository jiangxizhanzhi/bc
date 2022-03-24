package com.cyberpeace.integratation.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.evaluate.AddTopoException;
import com.cyberpeace.common.model.evaluation.ProbeOriginPageData;
import com.cyberpeace.common.model.evaluation.ToolActionModel;
import com.cyberpeace.common.model.evaluation.ToolActionParamModel;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.LocalClient;
import com.cyberpeace.integratation.model.evaluation.ImageUploadRsp;
import com.cyberpeace.integratation.service.CrService;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrServiceImpl implements CrService {

	@Override
	public ToolActionModel getCrModel(String toolId, Integer type, String actionId) {
		List<BasicNameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("tool_id", toolId));
		nvps.add(new BasicNameValuePair("type", type + ""));

		String url = ConfigUtil.getContextProperty("cr.host")
				+ ConfigUtil.getContextProperty("cr.tool.action");

		List<CrActionModel> result = LocalClient.postWithForm(nvps, url, List.class);

		for (Object item : result) {
			CrActionModel model = JSON.parseObject(item.toString(), CrActionModel.class);

			if (StringUtils.equals(model.getResourceId(), actionId)) {
				return convertToolActionModel(model, toolId);
			}
		}

		return null;
	}

	@Override
	public ProbeOriginPageData getProbeOriginDaraList(String sceneId, String hostId,
			String collectId, String pythonScript, Date startTime, Date endTime, Page pageBase) {
		return null;
	}

	@Override
	public String uploadImage(byte[] imageFile, String fileName) {
		String crHost = ConfigUtil.getContextProperty("cr.host");
		String fileUploadPath = ConfigUtil.getContextProperty("cr.imageUpload");
		String url = crHost + fileUploadPath;

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody("image", imageFile, ContentType.DEFAULT_BINARY, fileName);
		HttpEntity entity = builder.build();

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);

		// TODO 待调整
		String jwtToken = UserInfoContext.getUserContext().getUserId();
		httpPost.setHeader("Authorization", jwtToken);

		HttpResponse response;

		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				// 如果状态码为200,就是正常返回
				String result = EntityUtils.toString(response.getEntity());
				ImageUploadRsp rsp = JSON.parseObject(result, ImageUploadRsp.class);

				log.info("call url=[{}], result=[{}]", url, rsp.getUrl());

				return rsp.getUrl();
			} else {
				throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
			}
		} catch (IOException e) {
			log.error("call url=[{}] error,", url, e);
			throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
		}

	}

	@Override
	public String saveSceneTopo(String topo) {
		String url = ConfigUtil.getContextProperty("cr.host")
				+ ConfigUtil.getContextProperty("cr.common.scenes");

		List<BasicNameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("gather_need", "true"));
		nvps.add(new BasicNameValuePair("scene_config_data", topo));

		JSONObject jsonObject = LocalClient.postWithForm(nvps, url, JSONObject.class);

		String result = jsonObject.getString("resource_id");

		if (StringUtils.isBlank(result)) {
			throw new AddTopoException();
		}

		return result;
	}

	private ToolActionModel convertToolActionModel(CrActionModel model, String toolId) {

		ToolActionModel toolActionModel = new ToolActionModel();

		List<ToolActionParamModel> parentParamModels = Lists.newArrayList();

		// 填充动作下的子动作
		List<ToolActionModel> subModels = Lists.newArrayList();
		toolActionModel.setSubActions(subModels);
		toolActionModel.setParameters(parentParamModels);

		// 填充父动作相关信息
		toolActionModel.setActionId(model.getResourceId());
		toolActionModel.setName(model.getName());
		toolActionModel.setToolId(toolId);
		toolActionModel.setType(model.getType());

		model.getSubOperateData().forEach(crActionSubModel -> {

			List<ToolActionParamModel> subParamModels = Lists.newArrayList();

			// 只有动作才填充子动作
			if (model.getType().intValue() == ToolActionModel.ACTION_TYPE_ACT) {
				// 填充父动作下的子动作信息
				ToolActionModel subModel = new ToolActionModel();

				toolActionModel.setOperateType(ToolActionModel.ACTION_TYPE_ACT);

				subModel.setParameters(subParamModels);
				subModel.setActionId(crActionSubModel.getResourceId());
				subModel.setOperateType(crActionSubModel.getOperateType());
				subModel.setParentActionId(model.getResourceId());
				subModel.setToolId(toolId);
				subModel.setType(model.getType());

				subModels.add(subModel);
			}

			if (CollectionUtils.isNotEmpty(crActionSubModel.getParametersData())) {
				// 填充子动作下的参数
				crActionSubModel.getParametersData().forEach(crActionParamModel -> {
					// 填充参数信息
					ToolActionParamModel paramModel = new ToolActionParamModel();

					paramModel.setIndex(crActionParamModel.getIndex());
					paramModel.setActionId(crActionParamModel.getActionId());
					paramModel.setInputType(crActionParamModel.getType());
					paramModel.setParamName(crActionParamModel.getName());
					paramModel.setParamDesc(crActionParamModel.getDesc());

					if (model.getType().intValue() == ToolActionModel.ACTION_TYPE_COLLECT) {
						toolActionModel.setOperateType(ToolActionModel.ACTION_TYPE_COLLECT);
						parentParamModels.add(paramModel);

						toolActionModel.setActionId(crActionSubModel.getResourceId());
					} else {
						subParamModels.add(paramModel);
					}
				});
			}

		});

		return toolActionModel;
	}
}

@Data
class CrActionModel implements Serializable {

	private String resourceId;

	private String name;

	private Integer type;

	private List<CrActionSubModel> subOperateData;

}

@Data
class CrActionSubModel implements Serializable {

	private String resourceId;

	private Integer operateType;

	private List<CrActionParamModel> parametersData;
}

@Data
class CrActionParamModel implements Serializable {

	private String name;

	private String desc;

	private Integer type;

	private String actionId;

	private Integer index;
}