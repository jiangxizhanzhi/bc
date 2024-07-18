package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.DesEncryptUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.dao.mapper.SystemPropertyMapper;
import com.cyberpeace.cs.service.SystemPropertyService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class SystemPropertyServiceImpl extends ServiceImpl<SystemPropertyMapper, SystemProperty> implements SystemPropertyService {

	@Autowired
	private SystemPropertyMapper systemPropertyMapper;

	private final String expirationDate = "expirationDate";

	private LoadingCache<String, Date> cache = CacheBuilder.newBuilder()
			.expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, Date>() {

				@Override
				public Date load(String key) {
					return getDateFromDb(key);
				}
			});

	@Override
	public boolean getExportBuiltin() {
		SystemProperty systemProperty = systemPropertyMapper.selectByName(SystemProperty.NameEnum.EXPORT_BUILTIN.getDesc());
		return systemProperty != null && Boolean.parseBoolean(systemProperty.getContent());
	}

	@Override
	public Integer getSystemPropertyInt(String name, Integer defaultValue) {
		SystemProperty systemProperty = systemPropertyMapper.selectByName(name);
		return systemProperty != null ? Integer.parseInt(systemProperty.getContent()) : defaultValue;
	}

	@Override
	public SystemProperty getSystemProperty(String name) {
		return systemPropertyMapper.selectByName(name);
	}

	@Override
	public Date getExpirationDate() {
		try {
			return cache.get(expirationDate);
		} catch (Exception e) {
			Throwable t = e.getCause();
			if (t instanceof BusinessException) {
				throw (BusinessException) t;
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateExpDate(String code) {
		if (StringUtils.isBlank(code)) {
			return;
		}
		// validate
		String dateStr = DesEncryptUtils.decrypt(code);
		Date date = parseDate(dateStr);
		// save
		SystemProperty property = systemPropertyMapper.selectByName(expirationDate);
		if (null == property) {
			property = new SystemProperty();
			property.setName(expirationDate);
			property.setDesc("许可证过期日期");
		}
		property.setContent(code);
		this.saveOrUpdate(property);
		// flush cache
		cache.invalidate(expirationDate);
		cache.put(expirationDate, date);
	}

	private Date getDateFromDb(String name) {
		// 查询加密过的过期日
		SystemProperty property = systemPropertyMapper.selectByName(name);
		if (null == property || StringUtils.isBlank(property.getContent())) {
			return TimeUtil.toDate2("1970-01-01");
		}
		// 解密
		String dateStr = DesEncryptUtils.decrypt(property.getContent());
		return parseDate(dateStr);
	}

	private Date parseDate(String dateStr) {
		Date date = TimeUtil.toDate2(dateStr);
		if (null == date) {
			throw new BusinessException(ErrorCode.ILLEGAL_DATE_FORMAT);
		}
		return date;
	}
}
