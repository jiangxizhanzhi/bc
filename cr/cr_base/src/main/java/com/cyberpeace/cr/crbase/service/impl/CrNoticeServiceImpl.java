package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrNoticeMapper;
import com.cyberpeace.cr.crbase.service.CrNoticeService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsIdentifier;
import com.cyberpeace.cr.crbase.service.context.mission.TaskWsData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CrNoticeServiceImpl extends ServiceImpl<CrNoticeMapper, CrNotice> implements CrNoticeService {

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Override
	public List<CrNoticeRsp> queryList(String roomId, boolean isRb) {
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId,
				UserInfoContext.getUserContext().getUserId());
		// 角色映射
		List<Integer> roles = dealRole(crRole.getRoleGroup(), isRb);
		QueryWrapper<CrNotice> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(CrNotice::getRoomId, roomId);
		// 如果不是导演或是团队直接不过滤
		if (!roles.contains(1) || !isRb) {
			roles.add(1);
			queryWrapper.lambda().in(CrNotice::getRole, roles);
		}
		List<CrNotice> list = this.list(queryWrapper);
		list = topDeal(list);
		List<String> userIds = list.stream().filter(a -> a.getCreateUserId() != null).map(CrNotice::getCreateUserId).collect(Collectors.toList());
		Map<String, UserData> userMap = crRoleService.getUserDatas(userIds).stream().collect(Collectors.toMap(UserData::getResourceId, Function.identity()));
		List<CrNoticeRsp> rspList = BeanUtils.deepCopyList(list, CrNoticeRsp.class);
		for (CrNoticeRsp noticeRsp : rspList) {
			noticeRsp.setUserData(userMap.get(noticeRsp.getCreateUserId()));
		}
		return rspList;
	}

	@Override
	public void notice(String roomId) {
		sendWsData(new TaskWsData(MissionWsCode.NOTICE_Code), roomId);

	}

	private <T> void sendWsData(T data, String businessId) {
		MissionWsIdentifier identifier = new MissionWsIdentifier();
		identifier.setBusinessId(businessId);
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		identifier.setPath(WsPath.NOTICE);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		webSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 置顶处理
	 * @param list
	 * @return
	 */
	public List<CrNotice> topDeal(List<CrNotice> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		List<CrNotice> topList = list.stream()
				.filter(a -> a.getIsTop() != null && a.getIsTop() == 1)
				.sorted(Comparator.comparing(CrNotice::getCreateTime).reversed()).collect(Collectors.toList());
		List<CrNotice> notTopList = list.stream()
				.filter(a -> a.getIsTop() == null || a.getIsTop() != 1)
				.sorted(Comparator.comparing(CrNotice::getCreateTime).reversed()).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(notTopList)) {
			topList.addAll(notTopList);
		}
		return topList;
	}

	public List<Integer> dealRole(Integer role, boolean isRb) {
		List<Integer> list = new ArrayList<>();
		if (isRb) {
			switch (role) {
				case 2:
					list.add(1);
					break;// 导演
				case 4:
					list.add(2);
					break;// 红方
				case 5:
					list.add(3);
					break;// 蓝方
			}
		}
		return list;
	}
}
