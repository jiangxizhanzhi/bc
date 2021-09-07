package com.cyberpeace.cr.cebase.service.impl;

import com.cyberpeace.cr.cebase.mapper.CrEventMapper;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.model.crevent.CrEventAddInput;
import com.cyberpeace.cr.cebase.model.crevent.CrEventModifyInput;
import com.cyberpeace.cr.cebase.service.CrEventService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;

@Service
public class CrEventServiceImpl extends ServiceImpl<CrEventMapper, CrEvent> implements CrEventService {

	@Override
	public CrEvent create(CrEventAddInput crEventAddInput, int ceType) {
		CrEvent crEvent = crEventAddInput.toEntity();
		crEvent.setCeType(ceType);
		save(crEvent);

		return crEvent;
	}

	@Override
	public CrEvent update(String crEventId, CrEventModifyInput crEventModifyInput) {
		getOr404(crEventId);

		CrEvent crEvent = crEventModifyInput.toEntity();
		crEvent.setResourceId(crEventId);
		updateById(crEvent);

		return crEvent;
	}

	@Override
	public CrEvent getOr404(String crEventId) {
		CrEvent crEvent = getById(crEventId);
		if (crEvent == null) {
			throw new ResourceNotFoundException("CrEvent" + crEventId + "不存在");
		}
		return crEvent;
	}
}
