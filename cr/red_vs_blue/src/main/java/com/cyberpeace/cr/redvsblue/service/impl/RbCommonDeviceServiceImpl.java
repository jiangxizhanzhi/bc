package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCommonDeviceMapper;
import com.cyberpeace.cr.redvsblue.service.RbCommonDeviceService;
import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

@Service
public class RbCommonDeviceServiceImpl extends ServiceImpl<RbCommonDeviceMapper, RbCommonDevice> implements RbCommonDeviceService {

    @Autowired
    private RbCommonDeviceMapper rbCommonDeviceMapper;

    @Override
    public List<RbCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId) {
        return rbCommonDeviceMapper.selectByCollectUserAndRoomId(roomId, collectUserId);
    }

    @Override
    public void addToCollect(RbCommonDeviceContext commonDeviceContext) {
        RbCommonDevice commonDevice = new RbCommonDevice();
        BeanUtils.copyProperties(commonDeviceContext, commonDevice);

        String userId = UserInfoContext.getUserContext().getUserId();
        commonDevice.setCollectUserId(userId);
        commonDevice.setCreateUserId(userId);
        commonDevice.setCreateTime(new Date());
        rbCommonDeviceMapper.insert(commonDevice);
    }

    @Override
    public void delCollect(String resourceId) {
        rbCommonDeviceMapper.deleteByResourceId(resourceId);
    }

	@Override
	public void deleteByDeviceId(String deviceId) {
		rbCommonDeviceMapper.deleteByDeviceId(deviceId);
	}
}
