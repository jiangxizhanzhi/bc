package com.cyberpeace.iov.service;

import javax.servlet.http.HttpServletRequest;

import com.cyberpeace.iov.service.bo.index.RightDemo;
import com.cyberpeace.iov.service.bo.index.TargetCountBo;

public interface IovIndexService {

	RightDemo getDemo(HttpServletRequest request);

	TargetCountBo getTargetCount();

}
