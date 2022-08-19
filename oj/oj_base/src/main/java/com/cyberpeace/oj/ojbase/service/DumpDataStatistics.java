package com.cyberpeace.oj.ojbase.service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.oj.ojbase.constant.DumpModule;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.cyberpeace.oj.ojbase.service.impl.OjBaseServiceImpl.register;

@Service(value = "DumpDataStatistics")
public class DumpDataStatistics {

	@PostConstruct
	public void register() {
		if (this.getCode() != null) {
			register.put(this.getCode(), this);
		}
	}

	public DumpModule getCode() {
		return null;
	}

	public String getCourseFile(String resource_id) {
		throw new BusinessException(OjBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
