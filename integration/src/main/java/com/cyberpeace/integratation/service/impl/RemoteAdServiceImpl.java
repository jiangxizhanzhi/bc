package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RemoteAdServiceImpl implements RemoteAdService {

	@Override
	public List<CalendarCompetitionBo> competitionList(Date startTime, Date endTime,
			String userId) {
		// TODO
		return Lists.newArrayList();
	}

}
