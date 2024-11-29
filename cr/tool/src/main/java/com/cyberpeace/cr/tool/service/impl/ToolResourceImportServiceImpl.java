package com.cyberpeace.cr.tool.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import com.cyberpeace.cr.tool.service.ToolFileService;
import com.cyberpeace.cr.tool.service.ToolResourceImportService;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.utils.ParseMarkdown;
import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

import net.lingala.zip4j.exception.ZipException;

/**
 * 工具资源导入Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
public class ToolResourceImportServiceImpl implements ToolResourceImportService {

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	/**
	 * 工具文件Service
	 */
	@Autowired
	private ToolFileService toolFileService;

	/**
	 * 工具类别Service
	 */
	@Autowired
	private ToolCategoryService toolCategoryService;

	/**
	 * 工具Service
	 */
	@Autowired
	private ToolService toolService;

	/**
	 * 导入单个或多个工具
	 *
	 * @param path 服务器工具存放目录
	 */
	@Override
	public void importResource(String path) {
		// 兼容目录和单个压缩包
		try {
			if (path.endsWith(".zip")) {
				path = ZipUtil.unZipToTmpPath(path);
			}
		} catch (ZipException e) {
			// 解压失败
			log.error("[TOOL] decompression failed：{}", path);
			throw new BusinessException(CrBusinessErrorCode.ZIP_PATH_NOT_EXTRA);
		}
		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null) {
			// 没有找到文件目录
			log.error("[TOOL] file directory not found{}", path);
			throw new BusinessException(CrBusinessErrorCode.NOT_PATH);
		}

		// 查找目录下tool文件
		for (File subFile : files) {
			if (!subFile.isDirectory()) {
				continue;
			}

			try {
				log.info("tool=[{}] start import", subFile.getName());
				importTool(subFile);
				log.info("tool=[{}] import success", subFile.getName());
			} catch (Exception e) {
				log.error("tool=[{}] import error,", subFile.getName(), e);
			}
		}

		log.info("tool import finish!");
	}

	/**
	 * 将markdown中数据导成tool数据
	 *
	 * @param toolDir 单个tool所在目录
	 * @return
	 */
	private Tool importTool(File toolDir) {
		File mdFile = getMdFile(toolDir);
		if (mdFile == null) {
			//目录下没有找到md文件在
			log.error("md file not found in directory{}", toolDir.getPath());
			throw new BusinessException(CrBusinessErrorCode.NOT_MD_PATH);
		}

		ArrayListMultimap<String, String> mdData = parseMdFile(mdFile);

		Tool tool = convertTool(mdData, mdFile);

		if (StringUtils.isBlank(tool.getResourceId())) {
			toolService.save(tool);
		}

		return tool;
	}

	/**
	 * 解析markdown文件
	 *
	 * @param mdFile markdown文件对象
	 * @return
	 */
	private ArrayListMultimap<String, String> parseMdFile(File mdFile) {
		ParseMarkdown parseMarkdown = new ParseMarkdown(mdFile);
		parseMarkdown.handler();
		return parseMarkdown.getData();
	}

	/**
	 * 给tool设置属性
	 *
	 * @param data   markdown中的数据
	 * @param mdFile markdown文件
	 * @return
	 */
	private Tool convertTool(ArrayListMultimap<String, String> data, File mdFile) {
		Tool tool = new Tool();
		List<String> title = data.get(ParseMarkdown.TITLE);
		tool.setName(toStr(title));
		Tool dbTool = toolService.getByName(tool.getName());
		if (dbTool != null) {
			log.info("tool=[{}] already existed, skip actual import", dbTool.getName());
			return dbTool;
		}

		setCover(tool, data.get("LOGO"), mdFile);

		List<String> homepage = data.get("官方地址");
		tool.setHomepage(toStr(homepage));

		List<String> category = data.get("分类");
		setCategory(tool, category);

		List<String> manual = data.get("官方手册");
		tool.setManual(toStr(manual));

		List<String> imageResourceId = data.get("镜像");
		setImageResourceIdAndToolType(tool, imageResourceId);

		tool.setVisible(true);

		List<String> platforms = data.get("平台");
		tool.setPlatforms(toStr(platforms));

		List<String> language = data.get("语言支持");
		setLanguage(tool, language);

		List<String> introduction = data.get("描述");
		setIntroduction(tool, introduction);

		List<String> cracked = data.get("是否授权");
		if (cracked == null) {
			cracked = data.get("是否破解");
		}
		setCracked(tool, cracked);

		List<String> tag = data.get("tag");
		tool.setTag(toStr(tag));

		List<String> version = data.get("版本号");
		setVersionAndFiles(tool, version, mdFile);
		return tool;
	}

	/**
	 * 设置介绍
	 *
	 * @param tool         工具
	 * @param introduction 介绍
	 */
	private void setIntroduction(Tool tool, List<String> introduction) {
		String s = toStr(introduction);
		tool.setIntroduction(s);
	}

	/**
	 * 设置语言
	 *
	 * @param tool         工具
	 * @param languageList 语言列表
	 */
	private void setLanguage(Tool tool, List<String> languageList) {
		for (int i = 0; i < languageList.size(); i++) {
			if (languageList.get(i).equals("zh")) {
				languageList.set(i, "cn");
			}
		}
		tool.setLanguage(toStr(languageList));
	}

	/**
	 * 设置类别
	 *
	 * @param tool     工具
	 * @param category 类别
	 */
	private void setCategory(Tool tool, List<String> category) {
		List<String> resourceIds = Lists.newArrayList();
		for (String s : category) {
			ToolCategory toolCategory = toolCategoryService.getByName(s);

			if (null == toolCategory) {
				toolCategory = toolCategoryService.saveCategory(s);
			}

			resourceIds.add(toolCategory.getResourceId());
		}
		tool.setCategory(Joiner.on(",").join(resourceIds));
	}

	/**
	 * 设置图像资源id和工具类型
	 *
	 * @param tool            工具
	 * @param imageResourceId 图像资源id
	 */
	private void setImageResourceIdAndToolType(Tool tool, List<String> imageResourceId) {
		String imageName = toStr(imageResourceId);
		if (!StringUtils.isBlank(imageName)) {
			// TODO: 2021/9/6 set 靶标信息
			tool.setImageResourceId("");
			tool.setToolType(Tool.ONLINE);
		} else {
			tool.setToolType(Tool.OFFLINE);
			tool.setImageResourceId("");
		}
	}

	/**
	 * 设置版本和文件
	 *
	 * @param tool     工具
	 * @param versions 版本
	 * @param mdFile   markdown文件
	 */
	private void setVersionAndFiles(Tool tool, List<String> versions, File mdFile) {
		if (versions.isEmpty()) {
			tool.setVersion("");
			return;
		}

		// version和附件，默认是成对出现的
		if (versions.size() > 1) {
			tool.setVersion(getVersion(versions.get(versions.size() - 2)));
		} else if (versions.size() == 1) {
			tool.setVersion(getVersion(versions.get(versions.size() - 1)));
			return;
		} else {
			return;
		}

		List<String> attachments = Lists.newArrayList();
		for (int i = 1; i < versions.size(); i = i + 2) {
			attachments.add(versions.get(i));
		}

		// 上传版本对应的安装包
		ArrayList<Integer> versionFiles = Lists.newArrayList();
		for (String filePath : attachments) {
			ToolFile toolFile = uploadFile(filePath, mdFile, false);
			if (toolFile != null) {
				versionFiles.add(toolFile.getId());
			}
		}
		String files = Joiner.on(",").join(versionFiles);
		tool.setFiles(files);
	}

	/**
	 * 获取版本
	 *
	 * @param version 版本
	 * @return {@code String }
	 */
	private String getVersion(String version) {
		String[] ss = StringUtils.split(version, " ");
		if (null == ss || ss.length == 0) {
			return "";
		}

		for (String s : ss) {
			if (s.startsWith("v")) {
				return s;
			}
		}
		return "";
	}

	/**
	 * 设置破获
	 *
	 * @param tool    工具
	 * @param cracked 破获
	 */
	private void setCracked(Tool tool, List<String> cracked) {
		String s = toStr(cracked);
		boolean isCracked = s.equals("是");
		tool.setCracked(isCracked);
	}

	/**
	 * 设置盖
	 *
	 * @param tool   工具
	 * @param logo   图标
	 * @param mdFile markdown文件
	 */
	private void setCover(Tool tool, List<String> logo, File mdFile) {
		ToolFile toolFile = uploadFile(toStr(logo), mdFile, true);
		if (toolFile == null) {
			tool.setCover("");
		} else {
			tool.setCover(toolFile.getSavePath());
		}
	}

	/**
	 * 上传文件
	 *
	 * @param string       需要解析的文件路径
	 * @param mdFile       markdown文件
	 * @param transferName true表示使用随机文件名
	 * @return
	 */
	private ToolFile uploadFile(String string, File mdFile, boolean transferName) {
		// 兼容 [IDA_Pro_v7.0(MacOS).zip](./zip/IDA_Pro_v7.0(MacOS).zip) 格式的数据
		// [Eziriz.IntelliLock.v2.7.5.1.Cracked.by.yoza[UpK].rar.7z](./zip/Eziriz.IntelliLock.v2.7.5.1.Cracked.by.yoza[UpK].rar.7z)
		Pattern compile = Pattern.compile("]\\((.*)\\)");
		Matcher matcher = compile.matcher(string);
		if (!matcher.find()) {
			return null;
		}

		String group = matcher.group(1);
		String replace = group.replace("/", File.separator).replaceFirst("\\.", "");
		String filePath = mdFile.getParentFile().getPath() + replace;

		return toolFileService.saveFile(filePath, transferName);
	}

	/**
	 * 到 str
	 *
	 * @param stringList 字符串列表
	 * @return {@code String }
	 */
	private String toStr(List<String> stringList) {
		if (stringList == null) {
			return "";
		}
		return String.join(",", stringList);
	}

	/**
	 * 获取目录中的md文件
	 *
	 * @param file 初始目录
	 * @return
	 */
	private File getMdFile(File file) {
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		List<File> dirs = Lists.newArrayList();
		// 优先遍及当前目录，不做递归
		for (File toolFile : files) {
			if (toolFile.isDirectory()) {
				dirs.add(toolFile);
			} else {
				if (toolFile.getName().endsWith(".md")) {
					return toolFile;
				}
			}
		}

		for (File dir : dirs) {
			File f = getMdFile(dir);
			if (null != f) {
				return f;
			}
		}

		return null;
	}
}
