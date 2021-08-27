package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Level;

public interface FrameService extends BaseService<Level> {

	/**
	 * 给定一个id的数组，确保这些id对应框架(非层级)都存在，返回去重后的框架id数组
	 * */
	List<String> checkFrameIds(List<String> ids);

}
