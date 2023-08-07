package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRuleMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.rb.CrEvalScoreMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.rb.CrEvalTaskSubmitMapper;
import com.cyberpeace.cr.evaluation.service.TrainEvalService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
public class TrainEvalServiceImpl extends ServiceImpl<EvalRuleMapper, EvalRule> implements TrainEvalService {

	// @Autowired
	// private CrEvalTrainTaskMapper crEvalTaskMapper;
	@Autowired
	private MissionMapper missionMapper;

//	@Autowired
//	private CrRoleMapper crRoleMapper;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrEvalTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private CrEvalScoreMapper crEvalScoreMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private MissionService missionService;

	@Override
	public ListRsp<EvalRule> pageList(EvalRuleListReq req) {
		LambdaQueryWrapper<EvalRule> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(EvalRule::getName, req.getName());
		Page<EvalRule> page = page(req.toPageParam(), queryWrapper);
		return PageUtil.getListResult(page);
	}

	@Override
	public Boolean create(EvalRuleReq req) {

		return null;
	}

	@Override
	public Boolean update(EvalRuleReq req) {

		return null;
	}

	@Override
	public EvalRule get(String resourceId) {
		return getById(resourceId);
	}

	@Override
	public Boolean delete(List<String> ids) {
		return removeByIds(ids);
	}

	@Override
	public IPage<MissionEvaluationContext>
			getRbMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery) {
		missionEvaluationQuery.setSort("-evaluate_time");
		missionEvaluationQuery.setPer_page(10000);

		Page page = missionEvaluationQuery.toPageParam();
		IPage<MissionEvaluationContext> data = crTaskSubmitMapper.listRbCrTaskSubmit(page, missionEvaluationQuery);
		return setMission(data);
	}

	private IPage<MissionEvaluationContext> setMission(IPage<MissionEvaluationContext> data) {
		List<String> missionIds = data.getRecords().stream()
				.map(MissionEvaluationContext::getMissionHash).distinct()
				.collect(Collectors.toList());

		List<Mission> missions = Lists.newArrayList();
		if (missionIds.size() > 0) {
			missions = missionMapper.batchSelect(missionIds);
		}
		Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getResourceId,
				pc -> pc);

		data.convert(missionEvaluationContext -> {
			missionEvaluationContext
					.setMission(missionMap.get(missionEvaluationContext.getMissionHash()));

			return missionEvaluationContext;
		});
		return data;
	}

}
