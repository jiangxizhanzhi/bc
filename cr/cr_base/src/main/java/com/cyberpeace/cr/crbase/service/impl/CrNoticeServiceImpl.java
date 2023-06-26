package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrNoticeMapper;
import com.cyberpeace.cr.crbase.service.CrNoticeService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.WebSocketPushService;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsIdentifier;
import com.cyberpeace.cr.crbase.service.context.mission.TaskWsData;
import com.google.common.collect.Lists;
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
	private WebSocketPushService webSocketPushService;
	@Override
	public List<CrNoticeRsp> queryList(String roomId, boolean isRb) {
		List<CrNotice> list = getList(roomId, isRb);
		List<CrNotice>  updateList = new ArrayList<>();
		for (CrNotice notice:list) {
			boolean update = notice.reoveUserId(UserInfoContext.getUserContext().getUserId());
			if (update) {
				updateList.add(notice);
			}
		}
		this.updateBatchById(updateList);
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
	public void notice(CrNoticeReq crNoticeReq) {
		CrNotice notice = BeanUtils.copy(crNoticeReq, CrNotice.class);
		notice.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		List<CrRole> roles;
		if (!crNoticeReq.getIsRb()) {
			roles = crRoleService.getRolesByRoomAndGroups(crNoticeReq.getRoomId(), Lists.newArrayList(1,2));

		} else {
			List<Integer> groups = new ArrayList<>();
			groups.add(1);
			groups.add(2);
			groups.add(3);
			if (crNoticeReq.getRole()==1) {
				groups.add(4);
				groups.add(5);
			} else if (crNoticeReq.getRole()==2){
				groups.add(4);
			} else {
				groups.add(5);
			}
			roles = crRoleService.getRolesByRoomAndGroups(crNoticeReq.getRoomId(), Lists.newArrayList(groups));
		}
		List<String> userIds = roles.stream().map(CrRole::getUserIdList).flatMap(Collection::stream).filter(a->!UserInfoContext.getUserContext().getUserId().equals(a)).collect(Collectors.toList());
		notice.setNoticeUsers(JsonUtil.toJsonString(userIds));
		this.save(notice);
		webSocketPushService.notice(userIds,MissionWsCode.NOTICE_Code,crNoticeReq.getRoomId(),WsPath.NOTICE);
	}

	@Override
	public Boolean hasNewInfo(String roomId, boolean isRb) {
		List<CrNotice> list = getList(roomId, isRb);
		String userId = UserInfoContext.getUserContext().getUserId();
		for (CrNotice notice : list) {
			if (notice.getNoticeUsers()!=null&&notice.getNoticeUsers().contains(userId)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void remveAndNotice(String resourceId) {
		CrNotice notice = this.getById(resourceId);
		this.removeById(resourceId);
		//删除通知需要通知相关用户删除红点
		List<String> userIds = JsonUtil.toJsonObjectArray(notice.getNoticeUsers(), String.class);
		if (CollectionUtils.isNotEmpty(userIds)) {
			webSocketPushService.notice(userIds,MissionWsCode.NOTICE_Code,notice.getRoomId(),WsPath.NOTICE);
		}
	}

	public List<CrNotice> getList(String roomId, boolean isRb) {
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
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
		return list;
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
				.sorted(Comparator.comparing(CrNotice::getUpdateTime).reversed()).collect(Collectors.toList());
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
				case 1:
				case 2:
				case 3:
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
