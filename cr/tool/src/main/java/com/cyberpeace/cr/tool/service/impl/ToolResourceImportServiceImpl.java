package com.cyberpeace.cr.tool.service.impl;

import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.dao.context.ToolMessage;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具资源导入Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个Spring服务类 `ToolResourceImportServiceImpl`，用于处理工具资源的导入。以下是对其主要功能和结构的简要分析： 
 * ### 主要功能 
 * 1. **导入单个或多个工具**： 
 * - `importResource(String path, String type)` 方法用于从指定路径（可以是目录或ZIP文件）导入工具。 
 * - 如果路径是ZIP文件，则先解压到临时目录。 
 * - 对每个子目录中的工具进行处理。 
 * 2. **通过Excel导入工具**： 
 * - `importResource2(String excelPath, String toolsPath, String publicPhotoPath)` 方法通过读取Excel文件来导入工具。 
 * - Excel文件包含工具的相关信息，如名称、版本、类型等。 
 * - 对每个工具进行导入操作。 
 * ### 关键方法 
 * - **`importTool(File toolDir, String type)`**：处理单个工具目录，解析其中的Markdown文件，并将其内容转换为工具对象。 
 * - **`importTool2(ToolMessage toolMessage, String toolsPath, String publicPhotoPath)`**：根据工具消息对象和路径导入工具。 
 * - **`parseMdFile(File mdFile)`**：解析Markdown文件并提取其内容。 
 * - **`convertTool(ArrayListMultimap<String, String> data, File mdFile, String type)` 和 `convertTool2(ToolMessage toolMessage, ArrayListMultimap<String, String> data, File mdFile, String publicPhotoPath)`**：将Markdown文件的内容转换为工具对象。 
 * - **`setCategory(Tool tool, List<String> category, String type)` 和 `setCategory2(Tool tool, String type, String subType, List<String> category)`**：设置工具的类别。 
 * - **`setImageResourceIdAndToolType(Tool tool, List<String> imageResourceId)`**：设置工具的图像资源ID和类型。 
 * - **`uploadFile(String string, File mdFile, boolean transferName)`**：上传文件并返回文件对象。 
 * ### 辅助方法 
 * - **`data2ToolMessage(List<String> data)`**：将Excel文件中的数据转换为工具消息对象。 
 * - **`removePlatform(List<String> platforms)`**：过滤平台列表，只保留特定的平台。 
 * - **`toStr(List<String> stringList)` 和 `toStr2(List<String> stringList)`**：将字符串列表转换为字符串。 
 * - **`getMdFile(File file)`**：递归查找目录中的Markdown文件。 
 * ### 总结 
 * 该类主要负责处理工具资源的导入逻辑，包括从目录或ZIP文件中读取工具信息，并将其转换为数据库对象。同时，它还支持通过Excel文件批量导入工具。该类使用了多种辅助方法来处理文件解析、数据转换和类别设置等任务。 
**/
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
	public void importResource(String path, String type) {
		boolean isValidType = false;
		for (Tool.Type value : Tool.Type.values()) {
			if (value.getCode().equals(type)) {
				isValidType = true;
				break;
			}
		}
		if (!isValidType) {
			// 处理 type 不在枚举中的情况，例如返回错误信息
			log.error("[TOOL] invalid training category: {}", type);
			throw new BusinessException(CrBusinessErrorCode.INVALID_TRAINING_CATEGORY);
		}

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
				Tool tool = importTool(subFile, type);
				log.info("tool=[{}] import success", subFile.getName());
			} catch (Exception e) {
				log.error("tool=[{}] import error,", subFile.getName(), e);
			}
		}

		log.info("tool import finish!");
	}

	@Override
	public void importResource2(String excelPath, String toolsPath, String publicPhotoPath) {

		// 导入文件路径校验
		if (!excelPath.endsWith(".xlsx")) {
			throw new BusinessException(CrBusinessErrorCode.NOT_PATH);
		}
		File excelFile = new File(excelPath);
		if (excelFile.isDirectory()) {
			throw new BusinessException(CrBusinessErrorCode.NOT_PATH);
		}

		//解析工具名单
		ExcelDataHandler excelDataHandler = new ExcelDataHandler(excelPath, "Sheet1");
		List<List<String>> sheetData = excelDataHandler.getSheetData(CommonConstant.ONE);

		excelDataHandler.closeIO();

		// 数据类型转换
		for (int i = 0 ; i < sheetData.size(); i++){
			List<String> data = sheetData.get(i);
			ToolMessage toolMessage = data2ToolMessage(data);
			try {
				log.info("tool=[{}] start import", toolMessage.getToolName());
				Tool t = importTool2(toolMessage, toolsPath, publicPhotoPath);
				log.info("tool=[{}] import success", toolMessage.getToolName());
			} catch (Exception e) {
				log.error("tool=[{}] import error,", toolMessage.getToolName(), e);
			}
		}


		log.info("tool import finish!");
	}

	private ToolMessage data2ToolMessage(List<String> data) {
		return ToolMessage.builder().
		toolName(data.get(0)).version(data.get(1)).type(data.get(8)).subType(data.get(2)).platform(data.get(3))
				.language(data.get(4)).title(data.get(6)).message(data.get(7)).build();
	}

	/**
	 * 将markdown中数据导成tool数据
	 *
	 * @param toolDir 单个tool所在目录
	 * @return
	 */
	private Tool importTool(File toolDir, String type) {
		File mdFile = getMdFile(toolDir);
		if (mdFile == null) {
			//目录下没有找到md文件在
			log.error("md file not found in directory{}", toolDir.getPath());
			throw new BusinessException(CrBusinessErrorCode.NOT_MD_PATH);
		}

		ArrayListMultimap<String, String> mdData = parseMdFile(mdFile);

		Tool tool = convertTool(mdData, mdFile, type);
		tool.setType(type);

		if (StringUtils.isBlank(tool.getResourceId())) {
			toolService.save(tool);
		}

		return tool;
	}

	private Tool importTool2(ToolMessage toolMessage, String toolsPath, String publicPhotoPath) {

		String toolPath = toolsPath + File.separator + toolMessage.getToolName();
		File toolDir = new File(toolPath);
		File mdFile = getMdFile(toolDir);
		if (mdFile == null) {
			//目录下没有找到md文件在
			log.error("md file not found in directory{}", toolDir.getPath());
			throw new BusinessException(CrBusinessErrorCode.NOT_MD_PATH);
		}

		ArrayListMultimap<String, String> mdData = parseMdFile(mdFile);

		Tool tool = convertTool2(toolMessage, mdData, mdFile, publicPhotoPath);

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
	private Tool convertTool(ArrayListMultimap<String, String> data, File mdFile, String type) {
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
		setCategory(tool, category, type);

		List<String> manual = data.get("官方手册");
		tool.setManual(toStr(manual));

		List<String> imageResourceId = data.get("镜像");
		setImageResourceIdAndToolType(tool, imageResourceId);

		tool.setVisible(true);

		List<String> platforms = data.get("平台");
		platforms = this.removePlatform(platforms);
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
		tool.setTag(JsonUtil.arrayToString(tag));

		List<String> version = data.get("版本号");
		setVersionAndFiles(tool, version, mdFile);
		return tool;
	}

	private Tool convertTool2(ToolMessage toolMessage, ArrayListMultimap<String, String> data, File mdFile, String publicPhotoPath) {
		Tool tool = new Tool();
		List<String> title = data.get(ParseMarkdown.TITLE);
		tool.setName(toStr(title));

		// 重复工具不导入
		Tool dbTool = toolService.getByName(tool.getName());
		if (dbTool != null) {
			log.info("tool=[{}] already existed, skip actual import", dbTool.getName());
			return dbTool;
		}

		setCover2(tool, data.get("LOGO"), mdFile, publicPhotoPath);

		List<String> homepage = data.get("官方地址");
		tool.setHomepage(toStr(homepage));

		List<String> category = data.get("分类");
		setCategory2(tool, toolMessage.getType(), toolMessage.getSubType(), category);

		List<String> manual = data.get("官方手册");
		tool.setManual(toStr(manual));

		List<String> imageResourceId = data.get("镜像");
		setImageResourceIdAndToolType(tool, imageResourceId);

		tool.setVisible(true);

		List<String> platforms = data.get("平台");
		platforms = this.removePlatform(platforms);
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
		tool.setTag(toStr2(tag));
		tool.setDisabled(false);

		List<String> version = data.get("版本号");
		setVersionAndFiles(tool, version, mdFile);
		return tool;
	}

	/**
	 * 去除Win,linux,others之外的平台
	 *
	 * @param platforms 平台
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	private List<String> removePlatform(List<String> platforms) {
		List<String> res = Lists.newArrayList();
		for (String platform : platforms) {
			if (platform.equalsIgnoreCase("Windows") || platform.equalsIgnoreCase("Linux")
					|| platform.equalsIgnoreCase("Others")) {
				res.add(platform);
			}
		}
		return res;
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
	private void setCategory(Tool tool, List<String> category, String type) {
		List<String> resourceIds = Lists.newArrayList();
		for (String s : category) {
			ToolCategory toolCategory = toolCategoryService.getByName(s, type);

			if (null == toolCategory) {
				toolCategory = toolCategoryService.saveCategory(s, type);
			}

			resourceIds.add(toolCategory.getResourceId());
		}
		tool.setCategory(Joiner.on(",").join(resourceIds));
	}

	private static Integer toolTypeNum = 0;
	private void setCategory2(Tool tool, String type, String subType, List<String> category) {
		// 先确定大的分类
		if ("探测类".equals(type)){
			type = "probe";
		}else if ("防御类".equals(type)){
			type = "defense";
		}else if ("攻击类".equals(type)){
			type = "attack";
		}else if ("控制类".equals(type)){
			type = "control";
		}else {
			// 其他分类，随机分
			if (0 == toolTypeNum){
				type = "probe";
			}else if (1 == toolTypeNum){
				type = "defense";
			}else if (2 == toolTypeNum){
				type = "attack";
			}else{
				type = "control";
			}

			if (toolTypeNum < 4){
				toolTypeNum ++;
			}else {
				toolTypeNum = 0;
			}
		}
		tool.setType(type);

		// 匹配大的分类下面小的分类
		List<ToolCategory> toolCategories= toolCategoryService.getByType(type);

		ToolCategory targetToolCategory = toolCategories.get(0);
		double maxSimilarity = stringSimilarity(targetToolCategory.getCnName(), subType);
		for (ToolCategory toolCategory : toolCategories){
			double similarity = stringSimilarity(toolCategory.getCnName(), subType);
			if (similarity > maxSimilarity){
				targetToolCategory = toolCategory;
				maxSimilarity = similarity;
			}
		}

		tool.setCategory(targetToolCategory.getResourceId());
	}

	/**
	 * 计算两个字符串重复率
	 */
	public static double stringSimilarity(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();
		int common = 0;
		for (int i = 0; i < len1; i++) {
			for (int j = 0; j < len2; j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					common++;
					break;
				}
			}
		}
		return (double) common / (double) Math.max(len1, len2);
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
	 * @param mdFile   md 文件
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
	 * @param mdFile md 文件
	 */
	private void setCover(Tool tool, List<String> logo, File mdFile) {
		ToolFile toolFile = uploadFile(toStr(logo), mdFile, true);
		if (toolFile == null) {
			tool.setCover("");
		} else {
			tool.setCover(toolFile.getSavePath());
		}
	}

	private void setCover2(Tool tool, List<String> logo, File mdFile, String publicPhotoPath) {
		ToolFile toolFile = uploadFile(toStr(logo), mdFile, true);
		if (toolFile == null) {
			tool.setCover("");
		} else {
			String coverPath = toolFile.getSavePath();
			if (StringUtils.isNotBlank(coverPath)){
				tool.setCover(coverPath);
			}else {
				tool.setCover(publicPhotoPath);
			}
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

	private String toStr2(List<String> stringList) {
		if (stringList == null || stringList.isEmpty()) {
			return "[]";
		}
		StringBuilder result = new StringBuilder("[");
		for (String s : stringList){
			result.append("\"").append(s).append("\"").append(',');
		}
		result.append(']');
		return result.toString().replace(",]", "]");
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

