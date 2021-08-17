package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.dao.entity.Level;

public interface LevelService extends IService<Level> {

	/**
	 * 给定一个resource_id，校验该Frame是否存在，如果不存在则抛出异常
	 * */
	void validateLevelId(String frameId);

}
