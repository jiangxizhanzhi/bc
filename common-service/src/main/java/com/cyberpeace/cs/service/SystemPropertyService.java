package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.SystemProperty;

import java.util.Date;

public interface SystemPropertyService extends IService<SystemProperty> {

	boolean getExportBuiltin();

	Integer getSystemPropertyInt(String name, Integer defaultValue);

	SystemProperty getSystemProperty(String name);

    Date getExpirationDate();

	void updateExpDate(String code);
}
