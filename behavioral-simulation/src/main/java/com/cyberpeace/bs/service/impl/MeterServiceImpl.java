package com.cyberpeace.bs.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.MeterService;
import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MeterServiceImpl implements MeterService {

	@Autowired
	LcService lcService;

	@Override
	public List<TargetBo> getMeters(Integer type, String search) {
		if (search == null) {
			search = "";
		}
		if (type == null) {
			type = TargetType.ALL.getCode();
		}

		List<TargetBo> ret = Lists.newArrayList();
		if (!type.equals(TargetType.LONGCE.getCode())) {
			// todo
			List<TargetBo> mpTargets = Lists.newArrayList();
			ret.addAll(mpTargets);
		}
		if (type.equals(TargetType.LONGCE.getCode()) || type.equals(TargetType.ALL.getCode())) {
			List<TargetBo> lcTargets = lcService.getDeviceList(search);
			ret.addAll(lcTargets);
		}

		return ret;
	}
}

enum TargetType implements GenericEnum {

	ALL(0, "全部类型"), DEFAULT(1, "默认"), IXIA(2, "ixia仪表"), LONGCE(3, "龙测仪表");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	TargetType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
