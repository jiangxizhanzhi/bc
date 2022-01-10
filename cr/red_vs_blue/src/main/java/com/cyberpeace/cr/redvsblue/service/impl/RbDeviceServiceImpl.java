package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbDeviceMapper;
import com.cyberpeace.cr.redvsblue.service.RbDeviceService;
import com.cyberpeace.cr.redvsblue.service.context.RbDeviceContext;
import com.cyberpeace.integratation.service.RemoteUserService;

/**
 *
 */
@Service
public class RbDeviceServiceImpl extends ServiceImpl<RbDeviceMapper, RbDevice> implements RbDeviceService {

	@Autowired
	private RbDeviceMapper rbDeviceMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public List<RbDeviceContext> getByRoomIdAndOptionalDevice(String roomId, String deviceId) {
		List<RbDevice> devices = rbDeviceMapper.selectByRoomIdAndOptionalDevice(roomId, deviceId);
		// convert userId to username
		List<String> userIds = devices.stream().map(RbDevice::getCreateUserId)
				.collect(Collectors.toList());
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);
		List<RbDeviceContext> contexts = BeanUtils.deepCopyList(devices, RbDeviceContext.class);
		for (RbDeviceContext context : contexts) {
			context.setCreateUser(idNameMap.get(context.getCreateUserId()));
		}
		return contexts;
	}

	@Override
	public RbDevice getByRoomIdAndDevice(String roomId, String deviceId) {
		return rbDeviceMapper.selectByRoomIdAndDeviceId(roomId, deviceId);
	}

	@Override
	public Boolean create(String roomId, String deviceId, String userId) {
		RbDevice device = new RbDevice();
		device.setDeviceId(deviceId);
		device.setRoomId(roomId);
		device.setCreateUserId(userId);
		return rbDeviceMapper.insert(device) > 0;
	}

	@Override
	public void removeByRoomId(String roomId) {
		rbDeviceMapper.deleteByRoomId(roomId);
	}

	@Override
	public Boolean removeByRoomIdAndUserIdAndDeviceId(String roomId, String userId,
			String deviceId) {
		rbDeviceMapper.deleteByRoomIdAndUserIdAndDeviceId(roomId, userId, deviceId);
		return true;
	}
}
