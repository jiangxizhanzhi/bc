package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameApplicationMapper;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseReq;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameReleaseMapper;
import com.cyberpeace.space.service.SpaceActionFrameReleaseService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class SpaceActionFrameReleaseServiceImpl extends ServiceImpl<SpaceActionFrameReleaseMapper, SpaceActionFrameRelease> implements SpaceActionFrameReleaseService {

	@ApiModelProperty
	private SpaceActionFrameApplicationMapper applicationMapper;

	@Override
	public void create(SpaceActionFrameReleaseReq req) {
		this.checkRelease(req.getApplicationId(), req.getStage());
		SpaceActionFrameRelease entity = BeanUtils.copy(req, SpaceActionFrameRelease.class);
		this.save(entity);
	}

	@Override
	public List<SpaceActionFrameReleaseBo> listBo(String applicationId) {
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameRelease::getApplicationId, applicationId);
		List<SpaceActionFrameRelease> releaseList = this.list(wrapper);
		List<SpaceActionFrameReleaseBo> boList = BeanUtils.deepCopyList(releaseList, SpaceActionFrameReleaseBo.class);
		SpaceActionFrameApplication application = applicationMapper.selectById(applicationId);
		for (SpaceActionFrameReleaseBo spaceActionFrameReleaseBo : boList) {
			spaceActionFrameReleaseBo.setFrameName(application.getName());
		}

	}

	public void checkRelease(String applicationId, String stage){
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameRelease::getApplicationId, applicationId);
		wrapper.eq(SpaceActionFrameRelease::getStage, stage);
		if(this.count(wrapper) > 0){
			throw new BusinessException(SpaceError.STAGE_RELEASED);
		}
	}
}
