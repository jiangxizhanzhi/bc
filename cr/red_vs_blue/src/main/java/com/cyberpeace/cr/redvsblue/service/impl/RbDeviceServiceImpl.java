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
 * 红蓝对抗装置Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
public class RbDeviceServiceImpl extends ServiceImpl<RbDeviceMapper, RbDevice> implements RbDeviceService {

	/**
	 * 红蓝对抗装置Mapper
	 */
	@Autowired
	private RbDeviceMapper rbDeviceMapper;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 获取由房间id和自选装置
	 *
	 * @param roomId   房间id
	 * @param deviceId 装置id
	 * @return {@code List<RbDeviceContext> }
	 */
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

	/**
	 * 获取由房间id和装置
	 *
	 * @param roomId   房间id
	 * @param deviceId 装置id
	 * @return {@code RbDevice }
	 */
	@Override
	public RbDevice getByRoomIdAndDevice(String roomId, String deviceId) {
		return rbDeviceMapper.selectByRoomIdAndDeviceId(roomId, deviceId);
	}

	/**
	 * 创建
	 *
	 * @param roomId   房间id
	 * @param deviceId 装置id
	 * @param userId   用户id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean create(String roomId, String deviceId, String userId) {
		RbDevice device = new RbDevice();
		device.setDeviceId(deviceId);
		device.setRoomId(roomId);
		device.setCreateUserId(userId);
		return rbDeviceMapper.insert(device) > 0;
	}

	/**
	 * 删除由房间id
	 *
	 * @param roomId 房间id
	 */
	@Override
	public void removeByRoomId(String roomId) {
		rbDeviceMapper.deleteByRoomId(roomId);
	}

	/**
	 * 删除由房间id和用户id和装置id
	 *
	 * @param roomId   房间id
	 * @param userId   用户id
	 * @param deviceId 装置id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean removeByRoomIdAndUserIdAndDeviceId(String roomId, String userId,
			String deviceId) {
		rbDeviceMapper.deleteByRoomIdAndUserIdAndDeviceId(roomId, userId, deviceId);
		return true;
	}
}
