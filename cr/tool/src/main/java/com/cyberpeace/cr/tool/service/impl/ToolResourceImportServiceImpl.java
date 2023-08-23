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

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
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

@Service
public class ToolResourceImportServiceImpl implements ToolResourceImportService {

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	@Autowired
	private ToolFileService toolFileService;

	@Autowired
	private ToolCategoryService toolCategoryService;

	@Autowired
	private ToolService toolService;

	/**
	 * 导入单个或多个工具
	 * @param path 服务器工具存放目录
	 * @return
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
		tool.setCreateUserId(CommonConstant.ADMIN_ID);
		tool.setReadAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		tool.setReadAuthIds(CommonConstant.EMPTY_LIST_JSON);
		tool.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		tool.setOperationAuthIds(CommonConstant.EMPTY_LIST_JSON);

		if (StringUtils.isBlank(tool.getResourceId())) {
			toolService.save(tool);
		}

		return tool;
	}

	/**
	 * 解析markdown文件
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
	 * @param data markdown中的数据
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

	private void setIntroduction(Tool tool, List<String> introduction) {
		String s = toStr(introduction);
		tool.setIntroduction(s);
	}

	private void setLanguage(Tool tool, List<String> languageList) {
		for (int i = 0; i < languageList.size(); i++) {
			if (languageList.get(i).equals("zh")) {
				languageList.set(i, "cn");
			}
		}
		tool.setLanguage(toStr(languageList));
	}

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

	private void setVersionAndFiles(Tool tool, List<String> versions, File mdFile) {
		tool.setVersion("");
		// version和附件，默认是成对出现的
		if (versions.isEmpty() || versions.size() < 2) {
			return;
		}

		String attachmentVersion;
		// 上传版本对应的安装包
		ArrayList<Integer> versionFiles = Lists.newArrayList();
		for (int i = 0; i < versions.size() - 1; i = i + 2) {
			String filePath = versions.get(i + 1);
			attachmentVersion = getVersion(versions.get(i));
			String path = uploadFile(filePath, mdFile);
			if (null != path) {
				String filename = toolFileService.saveFile(path, false);
				ToolFile toolFile = toolFileService.newToolFile(filename, filename,
						attachmentVersion);
				versionFiles.add(toolFile.getId());
			}
		}

		String files = Joiner.on(",").join(versionFiles);
		tool.setFiles(files);
	}

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
		return CommonConstant.TOOL_DEFAULT_VERSION;
	}

	private void setCracked(Tool tool, List<String> cracked) {
		String s = toStr(cracked);
		boolean isCracked = s.equals("是");
		tool.setCracked(isCracked);
	}

	private void setCover(Tool tool, List<String> logo, File mdFile) {
		String filePath = uploadFile(toStr(logo), mdFile);
		if (filePath == null) {
			tool.setCover(CommonConstant.TOOL_DEFAULT_LOGO);
		} else {
			String fileName = toolFileService.saveFile(filePath, true);
			ToolFile toolFile = toolFileService.newToolFile(fileName, fileName);
			tool.setCover(toolFile.getSavePath());
		}
	}

	/**
	 * 上传文件
	 * @param string 需要解析的文件路径
	 * @param mdFile markdown文件
	 * @return 需要复制的文件路径
	 */
	private String uploadFile(String string, File mdFile) {
		// 兼容 [IDA_Pro_v7.0(MacOS).zip](./zip/IDA_Pro_v7.0(MacOS).zip) 格式的数据
		// [Eziriz.IntelliLock.v2.7.5.1.Cracked.by.yoza[UpK].rar.7z](./zip/Eziriz.IntelliLock.v2.7.5.1.Cracked.by.yoza[UpK].rar.7z)
		Pattern compile = Pattern.compile("]\\((.*)\\)");
		Matcher matcher = compile.matcher(string);
		if (!matcher.find()) {
			return null;
		}

		String group = matcher.group(1);
		String replace = group.replace("/", File.separator).replaceFirst(".", "");
		String filePath = mdFile.getParentFile().getPath() + replace;

		return filePath;

	}

	private String toStr(List<String> stringList) {
		if (stringList == null) {
			return "";
		}
		return String.join(",", stringList);
	}

	/**
	 * 获取目录中的md文件
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
