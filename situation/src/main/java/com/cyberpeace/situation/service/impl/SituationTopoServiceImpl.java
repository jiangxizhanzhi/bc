package com.cyberpeace.situation.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cyberpeace.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.SituationTaskReq;
import com.cyberpeace.integratation.service.bo.situation.TopoHistoryBo;
import com.cyberpeace.situation.controller.req.EventMsgReq;
import com.cyberpeace.situation.controller.req.SituationTopoReq;
import com.cyberpeace.situation.dao.entity.SituationTopo;
import com.cyberpeace.situation.dao.mapper.SituationTopoMapper;
import com.cyberpeace.situation.service.SituationTopoService;
import com.cyberpeace.situation.service.context.ConfigContext;
import com.cyberpeace.situation.service.context.TopoParaContext;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
@Service
public class SituationTopoServiceImpl extends ServiceImpl<SituationTopoMapper, SituationTopo> implements SituationTopoService {

	@Autowired
	private SituationTopoMapper situationTopoMapper;

	@Autowired
	private RemoteSituationService remoteSituationService;

	Cache<String, String> featureCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	Cache<String, List<TopoHistoryBo>> topoCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	@Override
	public ConfigContext getSituationTopoConfig(String resourceId) {
		SituationTopo situationTopo = situationTopoMapper.selectById(resourceId);
		if (situationTopo == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return BeanUtils.copy(situationTopo, ConfigContext.class);
	}

	@Override
	public ConfigContext createSituationTopoConfig(SituationTopoReq situationTopoReq) {
		SituationTopo situationTopo = BeanUtils.copy(situationTopoReq, SituationTopo.class);
		situationTopo.setResourceId(IDGeneratorUtil.getID());
		situationTopoMapper.insert(situationTopo);
		return BeanUtils.copy(situationTopo, ConfigContext.class);
	}

	@Override
	public ConfigContext updateSituationTopoConfig(SituationTopoReq situationTopoReq) {
		String resourceId = situationTopoReq.getResourceId();
		SituationTopo situationTopo = situationTopoMapper.selectById(resourceId);
		if (situationTopo == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SituationTopo copy = BeanUtils.copy(situationTopoReq, SituationTopo.class);
		situationTopoMapper.updateById(copy);
		return BeanUtils.copy(copy, ConfigContext.class);
	}

	@Override
	public Integer deleteSituationTopoConfig(List<String> idList) {
		return situationTopoMapper.deleteBatchIds(idList);
	}

	@Override
	public List<ConfigContext> getAllConfig() {
		List<SituationTopo> topoConfigs = situationTopoMapper
				.selectList(new LambdaQueryWrapper<>());
		return JsonUtil.toJsonObjectArray(JsonUtil.toJsonString(topoConfigs), ConfigContext.class);
	}

	@Override
	public TopoParaContext getCurrentTopo(EventMsgReq eventReq) {
		String featureId = eventReq.getFeatureId();
		String topoId = getCurrentTopoId(getTopoHistory(eventReq),TimeUtil.toDate1(eventReq.getQueryTime()));
		if (featureCache.getIfPresent(featureId) == null
				|| featureCache.getIfPresent(featureId) != topoId) {
			if (topoId != null) {
				featureCache.put(featureId, topoId);
				return new TopoParaContext(topoId);
			}
		}
		return null;
	}

	private List<TopoHistoryBo> getTopoHistory(EventMsgReq eventReq) {

		if (topoCache.getIfPresent(eventReq.getSubjectId()) == null) {
			SituationTaskReq situationTaskReq = new SituationTaskReq();
			situationTaskReq.setTask_id(eventReq.getTaskId());
			situationTaskReq.setSub_range_id(eventReq.getSubRangeId());
			situationTaskReq.setSubject_id(eventReq.getSubjectId());
			List<TopoHistoryBo> topoHistoryBos = remoteSituationService
					.getTopoHistory(situationTaskReq);
			topoCache.put(eventReq.getSubjectId(), topoHistoryBos);
		}
		return topoCache.getIfPresent(eventReq.getSubjectId());
	}

	private String getCurrentTopoId(List<TopoHistoryBo> topoHistorys, Date currentTime) {
		String topoId = null;
		for (TopoHistoryBo history : topoHistorys) {
			if (history.getUpdateTime() != null && currentTime.after(history.getUpdateTime())) {
				topoId = history.getUpdateId();
			}
		}
		return topoId;
	}
}
