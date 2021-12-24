package com.cyberpeace.cs.service.impl;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cs.service.UtilService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class UtilServiceImpl implements UtilService {

	private String FDFS_PREFIX;

	private String FDFS_START_STR;

	private String LOCAL_PREFIX;

	private String LOCAL_PARENT_PATH;

	@PostConstruct
	public void init() {
		FDFS_PREFIX = ConfigUtil.getContextProperty("common.fastdfs.url.prefix");
		FDFS_START_STR = ConfigUtil.getContextProperty("common.fastdfs.url.start.str");
		LOCAL_PREFIX = ConfigUtil.getContextProperty("cr.local.file.prefix");
		LOCAL_PARENT_PATH = ConfigUtil.getContextProperty("cr.local.upload.path");
	}

	@Override
	public String handlerFileUrl(String fileUrl) {

		if (StringUtils.isNotBlank(fileUrl)) {
			if (StringUtils.isNotBlank(FDFS_PREFIX) && fileUrl.startsWith(FDFS_START_STR)) {
				return FDFS_PREFIX + fileUrl;
			} else {
				if (StringUtils.isNotBlank(LOCAL_PREFIX) && !fileUrl.startsWith(LOCAL_PREFIX)) {
					return LOCAL_PREFIX + fileUrl;
				}
			}
		}

		return fileUrl;
	}

	@Override
	public String replaceFileUrl(String fileUrl) {

		if (StringUtils.isNotBlank(fileUrl)) {
			if (fileUrl.startsWith(LOCAL_PARENT_PATH)) {
				return fileUrl.replace(LOCAL_PARENT_PATH + File.separator, "");
			}

			if (fileUrl.startsWith(LOCAL_PREFIX)) {
				return fileUrl.replace(LOCAL_PREFIX, "");
			}

			if (fileUrl.startsWith(FDFS_PREFIX)) {
				return fileUrl.replace(FDFS_PREFIX, "");
			}
		}

		return fileUrl;
	}

	@Override
	public String getRealPath(String fileUrl) {

		if (StringUtils.isNotBlank(fileUrl)) {
			if (fileUrl.startsWith(LOCAL_PARENT_PATH)) {
				return fileUrl;
			}

			if (fileUrl.startsWith(FDFS_PREFIX)) {
				// fastdfs-host替换前缀
			}

			if (fileUrl.startsWith(FDFS_START_STR)) {
				// fastdfs-host + fileUrl
			}

			if (fileUrl.startsWith(LOCAL_PREFIX)) {
				return fileUrl.replace(LOCAL_PREFIX, LOCAL_PARENT_PATH + File.separator);
			}

			return LOCAL_PARENT_PATH + File.separator + fileUrl;

		}

		return fileUrl;
	}
}
