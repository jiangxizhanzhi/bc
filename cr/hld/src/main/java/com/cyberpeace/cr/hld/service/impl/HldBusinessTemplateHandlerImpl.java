package com.cyberpeace.cr.hld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldSceneService;

@Service
public class HldBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	@Autowired
	private HldRoomMapper hldRoomMapper;

	@Autowired
	private HldSceneService hldSceneService;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.HLD;
	}

	@Override
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		HldRoom hldRoom = hldRoomMapper
				.selectByBusinessTemplateId(businessTemplate.getResourceId());
		hldSceneService.stopScene(hldRoom.getResourceId(), false);
		crRoleService.clearRoomRoles(hldRoom.getResourceId());
		hldRoomMapper.deleteById(hldRoom);
	}

}
