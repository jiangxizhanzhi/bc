
package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameApplicationMapper;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpaceActionFrameApplicationServiceImpl extends ServiceImpl<SpaceActionFrameApplicationMapper, SpaceActionFrameApplication> implements SpaceActionFrameApplicationService {

	@Autowired
	private SpaceActionFrameService frameService;

	@Override
	public void create(SpaceActionFrameApplicationReq req) {
        this.checkUsage(req.getRvbId(), req.getFrameId());
        SpaceActionFrame frame  = frameService.getById(req.getFrameId());
        SpaceActionFrameApplication application = BeanUtils.copy(req, SpaceActionFrameApplication.class);
        application.setResourceId(IDGeneratorUtil.getID());
        application.setFrame(frame.getFrame());
        application.setTag(frame.getTag());
        application.setDescription(frame.getDescription());
        application.setName(frame.getName());
        application.setCategoryId(frame.getCategoryId());
        application.setType(frame.getType());

        this.save(application);
	}

	public void checkUsage(String rvbId, String frameId) {
		LambdaQueryWrapper<SpaceActionFrameApplication> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameApplication::getRvbId, rvbId);
		wrapper.eq(SpaceActionFrameApplication::getFrameId, frameId);
		if (this.count(wrapper) > 0) {
			throw new BusinessException(SpaceError.RVB_FRAME_USED);
		}
	}

}
