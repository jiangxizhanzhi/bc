package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameApplicationMapper;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameReleaseMapper;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceActionFrameReleaseService;
import com.cyberpeace.space.service.context.frame.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceActionFrameReleaseServiceImpl extends ServiceImpl<SpaceActionFrameReleaseMapper, SpaceActionFrameRelease> implements SpaceActionFrameReleaseService {

	@Autowired
	private SpaceActionFrameApplicationMapper applicationMapper;

	@Override
	public void create(SpaceActionFrameReleaseReq req) {
		this.checkRelease(req.getApplicationId(), req.getStep());
		SpaceActionFrameRelease entity = BeanUtils.copy(req, SpaceActionFrameRelease.class);
		SpaceActionFrameApplication application = applicationMapper
				.selectById(req.getApplicationId());
		entity.setFrameName(application.getName());
		entity.setRvbId(application.getRvbId());

		ActionFrameArrangeBo frame = JsonUtil.toJsonObject(application.getFrame(),
				ActionFrameArrangeBo.class);
		for (ActionFrameStep step : frame.getSteps()) {
			if (req.getStep().equals(step.getName())) {
				entity.setTactics(JsonUtil.arrayToString(step.getTactics()));
			}
		}
		this.save(entity);
	}

	@Override
	public List<SpaceActionFrameReleaseBo> listBo(String applicationId) {
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameRelease::getApplicationId, applicationId);
		List<SpaceActionFrameRelease> releaseList = this.list(wrapper);
		if (releaseList.isEmpty()) {
			return Lists.newArrayList();
		}
		Map<String, SpaceActionFrameRelease> releaseMap = StreamApiUtils.toMap(releaseList,
				SpaceActionFrameRelease::getStep, a -> a);
		SpaceActionFrameApplication application = applicationMapper.selectById(applicationId);

		List<SpaceActionFrameReleaseBo> res = Lists.newArrayList();
		ActionFrameArrangeBo actionFrameArrangeBo = JsonUtil.toJsonObject(application.getFrame(), ActionFrameArrangeBo.class);
		for (ActionFrameStep step : actionFrameArrangeBo.getSteps()) {
			SpaceActionFrameReleaseBo bo = new SpaceActionFrameReleaseBo();
			bo.setApplicationId(applicationId);
			bo.setFrameName(application.getName());
			bo.setStep(step.getName());
			if(releaseMap.containsKey(step.getName())){
				bo.setUserId(releaseMap.get(step.getName()).getUserId());
//				bo.setUserName(releaseMap.get(step.getName()).getUserName());
				bo.setProgress("1/1");
			}
			res.add(bo);
		}
		return res;

	}

	@Override
	public List<SpaceActionFrameOrderBo> getMySteps(String rvbId) {
		LambdaQueryWrapper<SpaceActionFrameApplication> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameApplication::getRvbId, rvbId);
		List<SpaceActionFrameApplication> applicationList = applicationMapper.selectList(wrapper);
		if(applicationList.isEmpty()){
			return Lists.newArrayList();
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		Map<String, SpaceActionFrameApplication> applicationMap = StreamApiUtils.toMap(applicationList, SpaceBaseTable::getResourceId, a->a);
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapperRelease = new LambdaQueryWrapper<>();
		wrapperRelease.in(SpaceActionFrameRelease::getApplicationId, applicationMap.keySet());
		wrapperRelease.eq(SpaceActionFrameRelease::getUserId, userId);
		List<SpaceActionFrameRelease> releaseList = this.list(wrapperRelease);


		return null;



	}

	public void checkRelease(String applicationId, String stage){
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameRelease::getApplicationId, applicationId);
		wrapper.eq(SpaceActionFrameRelease::getStep, stage);
		if(this.count(wrapper) > 0){
			throw new BusinessException(SpaceError.STAGE_RELEASED);
		}
	}
}
