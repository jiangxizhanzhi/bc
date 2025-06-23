package com.cyberpeace.common.util;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Sets;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.options.MutableDataSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * markdown操作工具类
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 功能概述： 
 * 该代码是一个用于处理 Markdown 文件的工具类 `MdUtil`，提供了多种功能，包括解析 Markdown 文件、生成 JSON 数据、将 Markdown 内容转换为 HTML、替换文件路径等。它广泛使用了正则表达式、文件操作以及字符串处理。 
 * --- 
 * #### 主要功能模块： 
 * 1. **Markdown 解析与 JSON 生成**： 
 * - 方法 `parseJsonMarkdown(File file)`：解析 Markdown 文件内容，并将其转换为 JSON 格式的键值对。 
 * - 使用正则表达式匹配标题行（如 `# 标题`），并提取其后的文本内容作为值。 
 * - 提供了辅助方法 `validateMarkdownContent` 来验证标题格式是否正确。 
 * 2. **Markdown 内容写入文件**： 
 * - 方法 `writeMdToFile(File file, String content)`：将 Markdown 内容写入文件，并处理图片路径的替换。 
 * - 使用了 `dumpMarkdownImages` 方法来解析图片路径，将相对路径替换为绝对路径，并将图片复制到指定目录。 
 * 3. **图片路径替换**： 
 * - 方法 `dumpMarkdownImages`：从 Markdown 内容中提取图片路径，并根据配置替换为绝对路径。 
 * - 使用正则表达式匹配图片路径（如 `![alt](path/to/image.png)` 和 `<img src="...">`）。 
 * - 处理文件复制逻辑，确保图片被正确拷贝到目标目录。 
 * 4. **Markdown 转 HTML**： 
 * - 方法 `mdToHtmlForApiDoc` 和 `mdToHtml`：将 Markdown 内容转换为 HTML 格式。 
 * - 支持多种扩展功能（如自动链接、表格、任务列表、目录等），并可以通过 `MutableDataSet` 配置这些扩展。 
 * 5. **过滤代码块**： 
 * - 方法 `filterCodeContent`：从 Markdown 内容中移除代码块（支持多行和单行代码块）。 
 * - 使用正则表达式匹配代码块标记（如 ``` 和 `）。 
 * 6. **文件读取与保存**： 
 * - 方法 `getContentWithOutCode`：读取 Markdown 文件内容并过滤掉代码块。 
 * - 方法 `saveAsFileWriter`：将字符串内容写入文件。 
 * --- 
 * #### 关键技术点： 
 * - **正则表达式**：用于匹配标题、图片路径、代码块等。 
 * - **文件操作**：通过 `FileUtil` 操作文件路径、读取文件内容、创建目录、复制文件等。 
 * - **JSON 处理**：使用 `JSONObject` 将 Markdown 数据转换为 JSON 格式。 
 * - **灵活扩展**：通过 `MutableDataSet` 配置 Markdown 转换的扩展功能。 
 * - **异常处理**：在文件不存在或路径错误时抛出自定义异常 `BusinessException`。 
 * --- 
 * #### 总结： 
 * 该工具类功能全面，涵盖了 Markdown 文件的解析、转换、路径处理等多个方面，适用于需要对 Markdown 文件进行复杂操作的场景。代码结构清晰，注释详细，易于维护和扩展。 
**/
@Slf4j
public class MdUtil {

	/**
	 * md util （中路实用程序）
	 */
	private MdUtil() {}

	/**
	 * 解析markdown文件 返回json数据
	 *
	 * @param file markdown文件 不做文件存在判断 请在业务代码中判断
	 * @return
	 */
	public static JSONObject parseJsonMarkdown(File file) {

		// 获取markdown行数据
		List<String> content = FileUtil.listContent(file);
		// 新建json对象存储数据
		JSONObject valueObj = new JSONObject();

		// 遍历content数据
		String key = CommonConstant.BLANK_STR;
		Integer startIndex = CommonConstant.NEGATIVE_ONE;

		for (int index = 0; index < content.size(); index++) {

			if (content.get(index).matches(PatternConstant.PARSER_MD_TITLE)) {
				// 标题数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(content, startIndex, index);
					valueObj.put(key, value);
				}

				// 数据校验
				key = validateMarkdownContent(content.get(index), file.getAbsolutePath());
				startIndex = index + CommonConstant.ONE;
			}

			if (index + 1 == content.size()) {
				String value = ListUtil.joinList(content, startIndex, content.size());
				valueObj.put(key, value);
			}
		}

		return valueObj;
	}

	/**
	 * 校验行内容
	 *
	 * @param lineContent 行内容
	 * @param path        路径
	 * @return {@code String }
	 */
	private static String validateMarkdownContent(String lineContent, String path) {
		String[] titleArray = lineContent.split(" ");
		if (titleArray.length < CommonConstant.TWO) {
			log.error("parseJsonMarkdown: markdown_path=[{}]", path);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		}

		return titleArray[1].trim();
	}

	/**
	 * md内容写入文件 对应的文件写入md同级的img目录
	 *
	 * @param file
	 * @param content 内容
	 */
	public static void writeMdToFile(File file, String content) {
		String mdContent = dumpMarkdownImages(file, content);
		FileUtil.writeToDestFile(mdContent, file.getAbsolutePath());
	}

	/**
	 * 解读markdown文件内容 替换文件路径 由相对路径替换为绝对路径 用于后续文件复制然后替换
	 *
	 * @param mdFile  md文件
	 * @param content md内容
	 * @return
	 */
	public static String dumpMarkdownImages(File mdFile, String content) {
        String img = new File(mdFile.getParentFile().getAbsolutePath(), "img").getAbsolutePath();
		File file= FileUtil.mkdirs(img);

		// 匹配文件路径
		String withOutCode = filterCodeContent(content);
		List<String> matchStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);

		// 匹配文件src地址
		List<String> matchSrcStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);
		matchStrList.addAll(matchSrcStrList);

		// markdown中引用重复文件, 避免String.replace替换多次, 转为Set（zentao 9894）
		Set<String> matchStrSet = Sets.newHashSet(matchStrList);

		// 文件路径替换
		for (String imagePath : matchStrSet) {
			// 部分markdown文件中文件路径 存在./xxx/xxx.png 所以要把逗点给去掉
            String realPath = getRealPath(imagePath);
            File source = new File(realPath);
            if (!source.exists()) {
				log.error("source is not   exist! realPath = [{}]",realPath);
                throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, realPath);
            }

            File dist = new File(img, source.getName());
            if (!dist.exists()) {
				String s = FileUtil.copyFile(source.getAbsolutePath(), dist.getAbsolutePath(), false);
            }

            content = content.replace(imagePath, "img/" + dist.getName());
		}

		return content;
	}

	/**
	 * 获取真正路径
	 *
	 * @param fileUrl 文件 URL
	 * @return {@code String }
	 */
	private static String getRealPath(String fileUrl) {

        String LOCAL_PREFIX = ConfigUtil.getContextProperty("cr.local.file.prefix");
        String LOCAL_PARENT_PATH = ConfigUtil.getContextProperty("cr.local.upload.path");

        if (StringUtils.isNotBlank(fileUrl)) {
            if (fileUrl.startsWith(LOCAL_PARENT_PATH)) {
                return fileUrl;
            }

            if (fileUrl.startsWith(LOCAL_PREFIX)) {
                return fileUrl.replace(LOCAL_PREFIX, LOCAL_PARENT_PATH + File.separator);
            }

            return LOCAL_PARENT_PATH + File.separator + fileUrl;

        }

        return fileUrl;
    }


	/**
	 * 解读markdown文件内容 替换文件路径 由相对路径替换为绝对路径 用于后续文件复制然后替换
	 *
	 * @param markdownPath
	 * @return
	 */
	public static String parseMarkdown(String markdownPath) {
		Set<String> matchImgPath = getMatchImgPath(markdownPath);

		String currentDir = new File(markdownPath).getParentFile().getAbsolutePath();

		String imgReplaced = replaceImgContent(markdownPath, matchImgPath, "");
		List<String> matchZipRarPath = getMatchZipRarPath(markdownPath);
		return replaceZipOrRarContent(currentDir, imgReplaced, matchZipRarPath, "");
	}

	/**
	 * 匹配md文件中的压缩包相对路径地址
	 *
	 * @param markdownPath
	 * @return
	 */
	private static List<String> getMatchZipRarPath(String markdownPath) {
		// 获取markdown文件内容
		File markdownFile = new File(markdownPath);
		String withOutCode = getContentWithOutCode(markdownFile);

		// 匹配文件路径
		List<String> matchStrList = LocalStringUtil.getMatchStr(withOutCode,
				PatternConstant.MARKDOWN_ZIP_RAR_URL, 2);
		// 递归匹配
		List<String> finalMatchList = new ArrayList<>();
		for(String matchStr : matchStrList) {
			finalMatchList.addAll(matchZipRarPath(matchStr));
		}

		return finalMatchList;
	}

	/**
	 * 匹配 Zip Rar 路径
	 *
	 * @param content 内容
	 * @return {@code List<String> }
	 */
	private static List<String> matchZipRarPath(String content) {
		if(!content.startsWith(CommonConstant.PERIOD)
				&& !content.startsWith(File.separator)) {
			List<String> matchPathList = LocalStringUtil.getMatchStr(content,
					PatternConstant.MARKDOWN_ZIP_RAR_URL, 2);
			for(String matchPath : matchPathList) {
				List<String> result = matchZipRarPath(matchPath);
			}
		} else {
			List<String> matchPathList = new ArrayList<>();
			matchPathList.add(content);
			return matchPathList;
		}
		return new ArrayList<>();
	}

	/**
	 * 匹配md文件中的相对路径地址
	 *
	 * @param markdownPath
	 * @return
	 */
	private static Set<String> getMatchImgPath(String markdownPath) {
		// 获取markdown文件内容
		File markdownFile = new File(markdownPath);
		String withOutCode = getContentWithOutCode(markdownFile);

		// 匹配文件路径
		List<String> matchStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);
		// 匹配文件src地址
		List<String> matchSrcStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);

		matchStrList.addAll(matchSrcStrList);
		return Sets.newHashSet(matchStrList);
	}

	/**
	 * 解读markdown文件内容 替换文件路径 由相对路径替换为绝对路径 用于后续文件复制然后替换
	 *
	 * @param markdownPath
	 * @param handoutPath  讲义路径
	 * @return
	 */
	public static String parseMarkdown(String markdownPath, String handoutPath) {
		// 获取md文件中的图片路径
		Set<String> matchStrSet = getMatchImgPath(markdownPath);
		return replaceImgContent(markdownPath, matchStrSet, handoutPath);
	}

	/**
	 * 替换 ZIP 或 RAR 内容
	 *
	 * @param currentDir   当前目录
	 * @param content      内容
	 * @param matchStrList match str 列表
	 * @param handoutPath  讲义路径
	 * @return {@code String }
	 */
	public static String replaceZipOrRarContent(String currentDir,
												String content,
												List<String> matchStrList,
												String handoutPath) {
		// 获取当前文件夹
		for(String matchStr : matchStrList) {
			String relativePath = matchStr;
			if(matchStr.startsWith(CommonConstant.PERIOD)) {
				relativePath = matchStr.replaceFirst(CommonConstant.BACKSLASH + CommonConstant.PERIOD,
						CommonConstant.EMPTY_STRING);
			}

			File replaceFile = new File(currentDir, relativePath);
			if (!replaceFile.exists()) {
				log.error("File not exist! file_path=[{}]", replaceFile.getAbsolutePath());
				String path = StringUtils.isBlank(handoutPath) ? replaceFile.getAbsolutePath()
						: handoutPath + CommonConstant.LEFT_OBLIQUE_LINE + matchStr;
				throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, path);
			}
			content = content.replace(matchStr, replaceFile.getAbsolutePath());
		}

		return content;
	}

	/**
	 * 将md文件中的相对路径替换为绝对路径
	 *
	 * @param markdownPath
	 * @param matchStrSet
	 * @param handoutPath
	 * @return
	 */
	private static String replaceImgContent(String markdownPath, Set<String> matchStrSet, String handoutPath) {

		// 获取当前文件夹
		String currentDir = new File(markdownPath).getParentFile().getAbsolutePath();
		File markdownFile = new File(markdownPath);
		String content = FileUtil.getContent(markdownFile);

		// 文件路径./xxx/xxx.png或/xxx/xxx.png -> xxx/xxx.png
		Set<String> imagePathSet = new HashSet<>();
		for (String imagePath : matchStrSet) {
			String relativePath = imagePath;
			if (imagePath.startsWith(CommonConstant.LEFT_PERIOD_OBLIQUE_LINE)) {
				relativePath = imagePath.replaceFirst(CommonConstant.LEFT_PERIOD_OBLIQUE_LINE,CommonConstant.EMPTY_STRING);
			}
			if (imagePath.startsWith(CommonConstant.LEFT_OBLIQUE_LINE)) {
				relativePath = imagePath.replaceFirst(CommonConstant.LEFT_OBLIQUE_LINE,CommonConstant.EMPTY_STRING);
			}
			imagePathSet.add(relativePath);
		}
		log.info("replaceImgContent matchStrSet:{}; imagePathSet:{}", matchStrSet, imagePathSet);
		// 文件路径替换
		for (String imagePath : imagePathSet) {

			File replaceFile = new File(currentDir, imagePath);
			if (!replaceFile.exists()) {
				log.error("File not exist! file_path=[{}]", replaceFile.getAbsolutePath());
				String path = StringUtils.isBlank(handoutPath) ? replaceFile.getAbsolutePath()
						: handoutPath + CommonConstant.LEFT_OBLIQUE_LINE + imagePath;
				throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, path);
			}
			content = content.replace(CommonConstant.LEFT_PERIOD_OBLIQUE_LINE + imagePath, CommonConstant.LEFT_OBLIQUE_LINE + imagePath);
			content = content.replace(CommonConstant.LEFT_OBLIQUE_LINE + imagePath, imagePath);
			content = content.replace(imagePath, replaceFile.getAbsolutePath());
		}
		return content;
	}


	/**
	 * markdown内容过滤掉代码块
	 *
	 * @param content 内容
	 * @return
	 */
	public static String filterCodeContent(String content) {
		// 匹配```代码块```
		List<String> matchStr = LocalStringUtil.getMatchStr(content, PatternConstant.MULTIPLE_CODING_BLOCK);
		String tmp = LocalStringUtil.replaceToBlank(content, matchStr);

		// 匹配`代码块`｜``代码块``
		List<String> singleMatchStr = LocalStringUtil.getMatchStr(tmp, PatternConstant.SINGLE_CODING_BLOCK);
		return LocalStringUtil.replaceToBlank(tmp, singleMatchStr);
	}

	/**
	 * 附加
	 *
	 * @param content 内容
	 * @param coding  编码
	 * @param builder 建筑工人
	 * @param type    类型
	 * @return boolean
	 */
	private static boolean append(String content, boolean coding, StringBuilder builder, int type) {
		// 如果开始和结尾在一行里 直接跳过此行
		boolean singleLine = content.trim().length() >= CommonConstant.FOUR
				&& content.trim().startsWith(CommonConstant.MD_CODING_BLOCK)
				&& content.trim().endsWith(CommonConstant.MD_CODING_BLOCK)
				&& CommonConstant.ONE.intValue() == type;
		if (singleLine) {
			return coding;
		}

		// 如果此行开始为代码块标识符 则对下面内容进行略过
		boolean omit = (content.trim().startsWith(CommonConstant.MD_CODING_BLOCK)
				|| content.trim().endsWith(CommonConstant.MD_CODING_BLOCK))
				&& CommonConstant.ONE.intValue() == type;
		if (omit) {
			coding = !coding;
		} else {
			if (!coding) {
				String compareContent = content;
				//当前行内存在字符 ` 时，开始裁剪
				while (content.contains(CommonConstant.MD_CODING_BLOCK_ONE)) {
					int firstIndex = content.indexOf(CommonConstant.MD_CODING_BLOCK_ONE, 0);
					compareContent = compareContent.replaceFirst(CommonConstant.MD_CODING_BLOCK_ONE, "");
					//存在两个`符号时，进行裁剪，否则跳出循环
					if (compareContent.contains(CommonConstant.MD_CODING_BLOCK_ONE)) {
						int secondIndex = compareContent.indexOf(CommonConstant.MD_CODING_BLOCK_ONE, 0);
						content = content.replace(content.substring(firstIndex,secondIndex + 2),"");
						compareContent = content;
					}else {
						break;
					}
				}
				builder.append(content + System.lineSeparator());
			}
		}

		return coding;
	}

	/**
	 * 读取md文件内容 不包含代码块
	 *
	 * @param file
	 * @return
	 */
	public static String getContentWithOutCode(File file) {
		String content = FileUtil.getContent(file);
		return filterCodeContent(content);
	}

	/**
	 * 保存如文件作家
	 *
	 * @param content  内容
	 * @param savefile 保存文件
	 * @return {@code File }
	 */
	public static File saveAsFileWriter(String content, String savefile) {
		File file = new File(savefile);
		try (FileWriter fwriter = new FileWriter(savefile);) {
			if (!file.exists()) {
				file.createNewFile();
			}
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return file;
	}

	/**
	 * 将入参Markdown文档转换为html文档
	 *
	 * @param md Markdown
	 * @return {@code String }
	 */
	public static String mdToHtmlForApiDoc(String md) {
		// 按需添加扩展
		MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
				// 自定义扩展，为<pre>标签添加line-numbers的class，用于prism库代码左侧行号展示
//				CodePreLineNumbersExtension.create(),
				AutolinkExtension.create(),
				EmojiExtension.create(),
				StrikethroughExtension.create(),
				TaskListExtension.create(),
				TablesExtension.create(),
				TocExtension.create()
		))
													 // set GitHub table parsing options
													 .set(TablesExtension.WITH_CAPTION, false)
													 .set(TablesExtension.COLUMN_SPANS, false)
													 .set(TablesExtension.MIN_HEADER_ROWS, 1)
													 .set(TablesExtension.MAX_HEADER_ROWS, 1)
													 .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
													 .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
													 .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
													 // setup emoji shortcut options
													 // uncomment and change to your image directory for emoji images if you have it setup
//				.set(EmojiExtension.ROOT_IMAGE_PATH, emojiInstallDirectory())
													 .set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.GITHUB)
													 .set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.IMAGE_ONLY);
		return mdToHtml(md, options);
	}

	/**
	 * MD 转 HTML
	 *
	 * @param md      Markdown
	 * @param options 选项
	 * @return {@code String }
	 */
	private static String mdToHtml(String md, MutableDataSet options) {
		// uncomment to convert soft-breaks to hard breaks
//		options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
		Parser parser = Parser.builder(options).build();
		HtmlRenderer renderer = HtmlRenderer.builder(options).build();
		Document document = parser.parse(md);
		return renderer.render(document);
	}

}

