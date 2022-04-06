package com.cyberpeace.cr.hld.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.service.context.HldRolesConfig;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import com.cyberpeace.cr.hld.service.context.HldTaskContext;
import com.cyberpeace.cr.hld.service.context.TaskRoleConfig;

public interface HldRoomService extends IService<HldRoom> {

	HldRoom create(HldRoomContext hldRoomContext);

	HldRoom update(HldRoomContext hldRoomContext, HldRoom hldRoom);

	void checkUserPermission(String roomId, String userId);

	List<TaskRoleConfig> getTaskRoleConfig(HldTaskContext hldTaskContext);

	List<TaskRoleConfig> getRoomRoleConfig(HldRolesConfig hldRolesConfig);

	BaseInfo getHldBaseInfo(HldTaskContext hldTaskContext);

	CrRole getUserRole(String roomId, String userId);

	JSONObject getTaskInfo(String taskId);

	String getHldRoleName(String crRoleName);

}
