package com.cyberpeace.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * markdown操作工具类
 */
@Slf4j
public class MdUtil {

	private MdUtil() {}

	/**
	 * 解析markdown文件 返回json数据
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
	 * @param lineContent
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
	 * @param file
	 * @param content
	 */
	public static void writeMdToFile(File file, String content) {
		String mdContent = dumpMarkdownImages(file, content);
		FileUtil.writeToDestFile(mdContent, file.getAbsolutePath());
	}

	/**
	 * 解读markdown文件内容 替换文件路径 由相对路径替换为绝对路径 用于后续文件复制然后替换
	 * @param mdFile md文件
     * @param content md内容
	 * @return
	 */
	public static String dumpMarkdownImages(File mdFile, String content) {
        String img = new File(mdFile.getParentFile().getAbsolutePath(), "img").getAbsolutePath();
        FileUtil.mkdirs(img);

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
                throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, realPath);
            }

            File dist = new File(img, source.getName());
            if (!dist.exists()) {
                FileUtil.copyFile(source.getAbsolutePath(), dist.getAbsolutePath(), false);
            }

            content = content.replace(imagePath, "img/" + dist.getName());
		}

		return content;
	}

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
	 * @param markdownPath
	 * @return
	 */
	public static String parseMarkdown(String markdownPath) {
		Set<String> matchImgPath = getMatchImgPath(markdownPath);
		return replaceImgContent(markdownPath, matchImgPath, "");
	}

	/**
	 * 匹配md文件中的相对路径地址
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
	 * @param markdownPath
	 * @return
	 */
	public static String parseMarkdown(String markdownPath, String handoutPath) {
		// 获取md文件中的图片路径
		Set<String> matchStrSet = getMatchImgPath(markdownPath);
		return replaceImgContent(markdownPath, matchStrSet, handoutPath);
	}

	/**
	 * 将md文件中的相对路径替换为绝对路径
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
	 * @param file
	 * @return
	 */
	public static String getContentWithOutCode(File file) {
		String content = FileUtil.getContent(file);
		return filterCodeContent(content);
	}

	public static File saveAsFileWriter(String content, String savefile) {
		FileWriter fwriter = null;
        File file = new File(savefile);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			fwriter = new FileWriter(savefile);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
        return file;
	}
}
