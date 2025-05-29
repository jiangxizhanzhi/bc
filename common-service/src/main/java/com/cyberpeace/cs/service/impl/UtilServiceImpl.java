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

/**
 * utilService实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 1. **类概述** 
 * - 类名：`UtilServiceImpl` 
 * - 功能：实现 `UtilService` 接口，提供一系列与文件路径、URL处理相关的工具方法。 
 * - 注解：`@Service` 表明这是一个 Spring 的服务类。 
 * #### 2. **字段** 
 * - 定义了多个常量字段（如 `FDFS_PREFIX`, `LOCAL_PREFIX` 等），用于存储配置值。这些值通过 `ConfigUtil.getContextProperty()` 方法在初始化时从配置文件中加载。 
 * - 自动注入了一个 `RemoteServerEnvService` 对象，用于获取远程服务器的环境信息。 
 * #### 3. **方法** 
 * ##### 3.1 初始化方法 (`init`) 
 * - 使用 `@PostConstruct` 注解，在类实例化后自动调用。 
 * - 从配置文件中加载多个常量值，避免硬编码。 
 * ##### 3.2 `handlerFileUrl` 
 * - 根据传入的文件 URL，判断其是否以特定前缀（如 `FDFS_PREFIX` 或 `LOCAL_PREFIX`）开头，并返回对应的完整 URL。 
 * ##### 3.3 `officeUrl` 
 * - 构造一个 Office 文件预览的 URL，包含文件信息和服务端信息。 
 * ##### 3.4 `replaceFileUrl` 
 * - 替换文件 URL 的前缀或路径。例如，将 `LOCAL_PREFIX` 替换为相对路径，或将 `FDFS_PREFIX` 替换为空字符串。 
 * ##### 3.5 `getRealPath` 
 * - 获取文件的实际路径。根据文件 URL 的前缀（如 `LOCAL_PREFIX` 或 `FDFS_PREFIX`），返回完整的文件路径。 
 * ##### 3.6 `handlerMarkdownFile` 
 * - 处理 Markdown 文件，包括： 
 * - 过滤代码块。 
 * - 匹配并替换图片路径。 
 * - 将图片复制到目标路径，并生成 Nginx 可访问的 URL。 
 * ##### 3.7 `replaceMarkdownFile` 
 * - 替换 Markdown 文件中的图片路径，将其替换为 Nginx 可访问的 URL。 
 * #### 4. **依赖** 
 * - 使用了多个工具类（如 `MdUtil`, `LocalStringUtil`, `StreamApiUtils` 等）来处理字符串和文件操作。 
 * - 配置管理通过 `ConfigUtil` 实现。 
 * #### 5. **总结** 
 * - 该类主要用于文件路径和 URL 的处理，提供了多种实用的方法，适用于文件上传、下载、预览等场景。 
 * - 代码结构清晰，依赖注入和配置管理良好，便于扩展和维护。 
**/
@Service
public class UtilServiceImpl implements UtilService {

	/**
	 * FDFS 前缀
	 */
	private String FDFS_PREFIX;

	/**
	 * FDFS 启动 STR
	 */
	private String FDFS_START_STR;

	/**
	 * 本地前缀
	 */
	private String LOCAL_PREFIX;

	/**
	 * 本地父路径
	 */
	private String LOCAL_PARENT_PATH;

	/**
	 * 讲义信息 URL
	 */
	private String HANDOUT_INFO_URL;

	/**
	 * 讲义内容 URL
	 */
	private String HANDOUT_CONTENT_URL;

	/**
	 * 远程服务器环境Service
	 */
	@Autowired
	private RemoteServerEnvService remoteServerEnvService;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		FDFS_PREFIX = ConfigUtil.getContextProperty("common.fastdfs.url.prefix");
		FDFS_START_STR = ConfigUtil.getContextProperty("common.fastdfs.url.start.str");
		LOCAL_PREFIX = ConfigUtil.getContextProperty("cr.local.file.prefix");
		LOCAL_PARENT_PATH = ConfigUtil.getContextProperty("cr.local.upload.path");
		HANDOUT_INFO_URL = ConfigUtil.getContextProperty("office.handout.info");
		HANDOUT_CONTENT_URL = ConfigUtil.getContextProperty("office.handout.content");
	}

	/**
	 * 处理程序文件 URL
	 *
	 * @param fileUrl 文件 URL
	 * @return {@code String }
	 */
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

	/**
	 * Office URL
	 *
	 * @param fileUrl 文件 URL
	 * @param fileId  文件id
	 * @return {@code String }
	 */
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

		String officeViewUrl = remoteServerEnvService.getOfficeHost() + "/view/?host="
				+ remoteServerEnvService.getFileServerHost() + "&getFileInfo=" + HANDOUT_INFO_URL
				+ "&downloadFile=" + HANDOUT_CONTENT_URL + "&fileId=" + fileId + "&fileName="
				+ fileName;

		return officeViewUrl;
	}

	/**
	 * 替换文件 URL
	 *
	 * @param fileUrl 文件 URL
	 * @return {@code String }
	 */
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

	/**
	 * 获取真正路径
	 *
	 * @param fileUrl 文件 URL
	 * @return {@code String }
	 */
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

	/**
	 * Handler Markdown 文件
	 *
	 * @param content      内容
	 * @param destFilePath 目标文件路径
	 * @return {@code String }
	 */
	@Override
	public String handlerMarkdownFile(String content, String destFilePath) {

		//获取过滤掉代码块的markdown内容
		String filterCodeContent = MdUtil.filterCodeContent(content);

		// 匹配src图片
		List<String> matchSrcList = LocalStringUtil.getMatchUrlStr(filterCodeContent,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);

		List<String> realPathList = LocalStringUtil.getMatchUrlStr(filterCodeContent,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);
//		List<String> realPathList = StreamApiUtils.map(matchList, realPath -> LocalStringUtil
//				.getFirstMatchStr(realPath, PatternConstant.MARKDOWN_IMAGE_URL));
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

	/**
	 * 替换 Markdown 文件
	 *
	 * @param content 内容
	 * @return {@code String }
	 */
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

