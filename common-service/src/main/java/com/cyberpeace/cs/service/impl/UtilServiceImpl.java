package com.cyberpeace.cs.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.impl.RemoteServerEnvService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Service
public class UtilServiceImpl implements UtilService {

	private String FDFS_PREFIX;

	private String FDFS_START_STR;

	private String LOCAL_PREFIX;

	private String LOCAL_PARENT_PATH;

	private String HANDOUT_INFO_URL;

	private String HANDOUT_CONTENT_URL;

	@Autowired
	private RemoteServerEnvService remoteServerEnvService;

	@PostConstruct
	public void init() {
		FDFS_PREFIX = ConfigUtil.getContextProperty("common.fastdfs.url.prefix");
		FDFS_START_STR = ConfigUtil.getContextProperty("common.fastdfs.url.start.str");
		LOCAL_PREFIX = ConfigUtil.getContextProperty("cr.local.file.prefix");
		LOCAL_PARENT_PATH = ConfigUtil.getContextProperty("cr.local.upload.path");
		HANDOUT_INFO_URL = ConfigUtil.getContextProperty("office.handout.info");
		HANDOUT_CONTENT_URL = ConfigUtil.getContextProperty("office.handout.content");
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
	public String officeUrl(String fileUrl, String fileId) {

		if (StringUtils.isBlank(fileUrl)) {
			return fileUrl;
		}

		String fileName = fileUrl;
		int lastIndexOf = fileUrl.lastIndexOf(File.separator);
		if (lastIndexOf != CommonConstant.NEGATIVE_ONE) {
			fileName = fileUrl.substring(lastIndexOf + CommonConstant.ONE);
		}

		return remoteServerEnvService.getOfficeHost() + "/view/?host="
				+ remoteServerEnvService.getFileServerHost() + "&getFileInfo=" + HANDOUT_INFO_URL
				+ "&downloadFile=" + HANDOUT_CONTENT_URL + "&fileId=" + fileId + "&fileName="
				+ fileName;
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

			// officeUrl 返回null 用于后续做判断
			if (fileUrl.startsWith(remoteServerEnvService.getOfficeHost())) {
				return null;
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

	@Override
	public String handlerMarkdownFile(String content, String destFilePath) {

		//获取过滤掉代码块的markdown内容
		String filterCodeContent = MdUtil.filterCodeContent(content);

		// 匹配src图片
		List<String> matchSrcList = LocalStringUtil.getMatchUrlStr(filterCodeContent,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);

		List<String> realPathList = LocalStringUtil.getMatchUrlStr(filterCodeContent,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);
		realPathList.addAll(matchSrcList);

		// 文件拷贝到nginx可转发目录 同时生成nginx转发地址
		List<String> nginxUrlList = StreamApiUtils.map(realPathList, realPath -> {

			String fileSuffix = FileUtil.getFileSuffix(realPath);
			String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
			String destPath = destFilePath + File.separatorChar + fileName;

			FileUtil.copyFile(realPath, destPath, false);
			String fileUrl = replaceFileUrl(destPath);
			return handlerFileUrl(fileUrl);
		});

		return LocalStringUtil.replaceAll(content, realPathList, nginxUrlList);
	}

	@Override
	public String replaceMarkdownFile(String content) {

		//获取过滤掉代码块的markdown内容
		String filterCodeContent = MdUtil.filterCodeContent(content);

		// 将文件中的绝对路径替换为nginx可访问路径
		List<String> realPathList = LocalStringUtil.getMatchUrlStr(filterCodeContent,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);

		List<String> matchSrcList = LocalStringUtil.getMatchUrlStr(filterCodeContent,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);

		List<String> matchZipRarSrcList = LocalStringUtil.getMatchStr(filterCodeContent,
				PatternConstant.MARKDOWN_ZIP_RAR_URL, 2);

		realPathList.addAll(matchSrcList);

		realPathList.addAll(matchZipRarSrcList);

		List<String> nginxUrlList = StreamApiUtils.map(realPathList, realPath -> {
			String fileUrl = replaceFileUrl(realPath);
			return handlerFileUrl(fileUrl);
		});
		//比对内容为过滤代码块后的内容，实际替换是未过滤的内容
		return LocalStringUtil.replaceAll(content, realPathList, nginxUrlList);
	}
}
