package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.AddAttackTypeReq;
import com.cyberpeace.situation.controller.req.UpdateAttackTypeReq;
import com.cyberpeace.situation.dao.entity.AttackType;
import com.cyberpeace.situation.dao.mapper.AttackTypeMapper;
import com.cyberpeace.situation.service.AttackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttackTypeServiceImpl extends ServiceImpl<AttackTypeMapper, AttackType> implements AttackTypeService {

	@Autowired
	private AttackTypeMapper attackTypeMapper;

	@Override
	public void addAttackType(AddAttackTypeReq addAttackTypeReq) {
		AttackType attackType = BeanUtils.copy(addAttackTypeReq, AttackType.class);
		attackTypeMapper.insert(attackType);
	}

	@Override
	public void updateAttackType(UpdateAttackTypeReq updateAttackTypeReq) {
		AttackType attackType = BeanUtils.copy(updateAttackTypeReq, AttackType.class);
		attackTypeMapper.updateById(attackType);
	}

	@Override
	public void deleteAttackType(List<String> attackTypeIds) {
		attackTypeMapper.deleteBatchIds(attackTypeIds);
	}

	@Override
	public AttackType getAttackType(String attackTypeId) {
		return attackTypeMapper.selectById(attackTypeId);
	}

	@Override
	public ListRsp<AttackType> getListAttackType(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<AttackType> attackTypeIPage = attackTypeMapper.selectAll(page, pageReqBase.getPattern());
		return PageUtil.getListResult(attackTypeIPage);
	}

}
