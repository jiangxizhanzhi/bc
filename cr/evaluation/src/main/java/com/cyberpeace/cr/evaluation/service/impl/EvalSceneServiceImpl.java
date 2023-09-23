package com.cyberpeace.cr.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.context.scene.ElementFlow;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskFlowListQuery;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvalSceneServiceImpl implements EvalSceneService {

	@Autowired
	private LocalSceneService localSceneService;

	@Override
	public SceneViewContext viewScene(String mpSceneId, String topoId) {

		SceneViewContext ret = new SceneViewContext();
		String currentUserId = UserInfoContext.getUserContext().getUserId();

		// 尝试获取启动的远程场景
		if (mpSceneId != null) {
			JSONObject sceneData = localSceneService.getSceneData(mpSceneId, currentUserId);
			if (sceneData != null) {
				ret.setSceneData(sceneData);
				ret.setMpSceneId(mpSceneId);
				return ret;
			}
		}

		// 获取拓扑结构
		if (topoId != null) {
			JSONObject topoData = localSceneService.getTopoData(topoId)
					.getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
			ret.setTopoId(topoId);
			ret.setTopoData(topoData);
			return ret;

		}

		return null;
	}

	@Override
	public Boolean stopScene(LocalScene localScene) {

		Boolean stopResult = localSceneService.stopScene(localScene);

		// 停止场景失败时的处理
		if (Boolean.FALSE.equals(stopResult)) {
			log.error("Failed to stop scene! localScene = [{}]", JsonUtil.toJsonString(localScene));
			throw new BusinessException(ErrorCode.FAILED_TO_STOP_SCENE);
		}

		return true;
	}

	@Override
	public ElementFlowListContext getElementFlowListContext(
			EvalTaskFlowListQuery evalTaskFlowListQuery, EvalTask evalTask) {
		Page<ElementFlow> page = evalTaskFlowListQuery.toPageParam();

		ElementFlowListContext context = BeanUtils.copy(evalTaskFlowListQuery,
				ElementFlowListContext.class);
		context.setPage(page);
		context.setMpSceneId(evalTask.getMpSceneId());
		context.setTopoId(evalTask.getCopyedTopoId());
		return context;
	}

}
