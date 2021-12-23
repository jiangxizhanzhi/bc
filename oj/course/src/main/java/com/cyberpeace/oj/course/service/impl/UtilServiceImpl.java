package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.oj.course.service.UtilService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class UtilServiceImpl implements UtilService {

	private String FDFS_PREFIX;

	private String FDFS_START_STR;

	private String LOCAL_PREFIX;

	@PostConstruct
	public void init() {
		FDFS_PREFIX = ConfigUtil.getContextProperty("common.fastdfs.url.prefix");
		FDFS_START_STR = ConfigUtil.getContextProperty("common.fastdfs.url.start.str");
		LOCAL_PREFIX = ConfigUtil.getContextProperty("cr.local.upload.path");
	}

	@Override
	public String handlerFileUrl(String fileUrl) {

		if (StringUtils.isNotBlank(fileUrl)) {
			if (StringUtils.isNotBlank(FDFS_PREFIX) && fileUrl.startsWith(FDFS_START_STR)) {
				return FDFS_PREFIX + fileUrl;
			} else {
				if (StringUtils.isNotBlank(LOCAL_PREFIX) && !fileUrl.startsWith(LOCAL_PREFIX)) {
					return LOCAL_PREFIX + File.pathSeparator + fileUrl;
				}
			}
		}

		return fileUrl;
	}
}
