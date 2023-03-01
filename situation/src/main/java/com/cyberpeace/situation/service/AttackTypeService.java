package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.situation.controller.req.AddAttackTypeReq;
import com.cyberpeace.situation.controller.req.UpdateAttackTypeReq;
import com.cyberpeace.situation.dao.entity.AttackType;

import java.util.List;

public interface AttackTypeService {

	void addAttackType(AddAttackTypeReq addAttackTypeReq);

	void updateAttackType(UpdateAttackTypeReq updateAttackTypeReq);

	void deleteAttackType(List<String> attackTypeIds);

	AttackType getAttackType(String attackTypeId);

	ListRsp<AttackType> getListAttackType(PageReqBase pageReqBase);
}
