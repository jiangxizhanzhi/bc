package com.cyberpeace.oj.practice.service.md;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.MdConvert;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
import com.cyberpeace.common.mdparser.convert.NoConvert;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * Markdown理论解析 器
 *
 * @author chenzhuo
 * 仅支持标题和列表 列表仅支持二级@date 2024/11/08@version 1.0.0
 * @date 2024/12/04
 * @since 1.0.0
 */
@Slf4j
public class MdTheoryParser {

	/**
	 * head 符号
	 *
	 * @see String
	 */
	private String HEAD_SYMBOL = "#";

	/**
	 * 内容
	 *
	 * @see List<String>
	 */
	private List<String> content;

	/**
	 * 标题地图
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> titleMap;

	/**
	 * 解析器映射
	 *
	 * @see Map<Class, MdContentParser>
	 */
	private Map<Class, MdContentParser> parserMap;

	/**
	 * Markdown理论解析 器
	 *
	 * @param file 文件
	 * @throws
	 * @see String
	 */
	public MdTheoryParser(String file) {
		setFile(file);
		collectParse();
	}

	/**
	 * Markdown理论解析 器
	 *
	 * @throws
	 */
	public MdTheoryParser() {
		collectParse();
	}

	/**
	 * 设置文件
	 *
	 * @param file 文件
	 * @throws
	 * @see String
	 */
	public void setFile(String file) {
		if (StringUtils.isBlank(file) || !new File(file).exists()) {
			log.error("File not exist! file_path=[{}]", file);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL, file);
		}

		content = FileUtil.listContent(new File(file));
		titleMap = new ConcurrentHashMap<>();
	}

	/**
	 * 读
	 *
	 * @param cls CLS
	 * @return {@code T }
	 * @throws
	 * @see Class<T>
	 * @see T
	 */
	public synchronized <T> T read(Class<T> cls) {
		try {
			return doRead(cls);
		} catch (IllegalAccessException e) {
			log.error("Markdown file to entity failed! cls=[{}], catch error:", cls.getName(), e);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL, cls.getName());
		}
	}

	/**
	 * 写入
	 *
	 * @param obj  对象
	 * @param file 文件
	 * @throws
	 * @see Object
	 * @see File
	 */
	public synchronized void write(Object obj, File file) {
		doWrite(obj, file);
	}

	/**
	 * 做写入
	 *
	 * @param obj  对象
	 * @param file 文件
	 * @throws
	 * @see Object
	 * @see File
	 */
	private void doWrite(Object obj, File file) {
		File f= FileUtil.mkdirs(file.getParentFile().getAbsolutePath());
		if (file.exists()) {
			if (!file.delete()) {
				log.warn("Failed to delete file");
			}
		}

		Field[] fields = ObjUtil.getFields(obj);
		if (fields.length <= CommonConstant.ZERO) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error("File create failed! file_path=[{}]", file.getAbsolutePath());
			}
			return;
		}

		FileWriter fw = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;

		try {
			osw = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
			bw = new BufferedWriter(osw);

			for (Field field : fields) {
				MdHeadLine annotation = field.getAnnotation(MdHeadLine.class);
				if (null == annotation) {
					continue;
				}

				String title = getAssignStr(annotation.level(), HEAD_SYMBOL) + CommonConstant.SPACE
						+ annotation.name();

				Object value = ObjUtil.getFieldValueByName(field.getName(), obj);
				if (ObjectUtils.isNotEmpty(value)) {
					Class<? extends MdConvertEnum> convert = annotation.convert();
					if (convert != NoConvert.class) {
						value = MdConvert.convertValue(value, convert);
					}

					MdContentParser parser = parserMap.get(annotation.parser());
					value = parser.write(value);
				} else {
					value = LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
				}

				bw.write(title);
				bw.write(String.valueOf(value));
			}

			bw.flush();
		} catch (Exception e) {
			log.error("Markdown content write failed！file_path=[{}], catch error:",
					file.getAbsolutePath(), e);
			if (!file.delete()) {
				log.warn("Failed to delete file");
				// 可以选择抛出异常或进行其他处理
			}
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		} finally {
			// 按创建顺序的逆序关闭资源
			List<IOException> closeExceptions = new ArrayList<>();

			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					closeExceptions.add(e);
					log.error("Failed to close BufferedWriter", e);
				}
			}

			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					closeExceptions.add(e);
					log.error("Failed to close OutputStreamWriter", e);
				}
			}

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					closeExceptions.add(e);
					log.error("Failed to close FileWriter", e);
				}
			}

			// 处理收集到的异常
			if (!closeExceptions.isEmpty()) {
				if (closeExceptions.size() > 1) {
					log.warn("Multiple resources failed to close properly");
				}
			}
		}
	}

	/**
	 * 做阅读
	 *
	 * @param cls CLS
	 * @return {@code T }
	 * @throws @throws IllegalAccessException 非法访问异常
	 * @see Class<T>
	 * @see T
	 */
	private <T> T doRead(Class<T> cls) throws IllegalAccessException {
		mdKeyMap();
		Object instance = BeanUtils.instantiateClass(cls);

		Field[] fields = ObjUtil.getFields(cls);
		for (Field field : fields) {
			MdHeadLine annotation = field.getAnnotation(MdHeadLine.class);
			if (null == annotation) {
				continue;
			}

			MdContentParser parser = parserMap.get(annotation.parser());
			if (null == parser) {
				continue;
			}

			String matchLine = getAssignStr(annotation.level(), HEAD_SYMBOL) + CommonConstant.SPACE
					+ annotation.name();

			Object parse = parser.parse(titleMap.get(matchLine));

			if (ObjectUtils.isNotEmpty(parse)) {
				Class<? extends MdConvertEnum> convert = annotation.convert();
				if (convert != NoConvert.class) {
					parse = MdConvert.convertKey(parse, convert);
				}
			}

			field.setAccessible(true);
			field.set(instance, parse);
		}

		return (T) instance;
	}

	/**
	 * 解析markdown文件 返回json数据
	 * 第一步通过#、##解析题型，第二步通过###解析每道题目
	 *
	 * @throws
	 */
	public void mdKeyMap() {

		// 遍历content数据
		String key = CommonConstant.BLANK_STR;
		Integer startIndex = CommonConstant.NEGATIVE_ONE;

		for (int index = 0; index < content.size(); index++) {

			if (content.get(index).matches(PatternConstant.PARSER_THEORY_MD_TITLE)) {
				// 标题数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinLineSeparatorList(content, startIndex, index);
					titleMap.put(key, value);
				}

				// 数据校验
				key = content.get(index).trim();
				startIndex = index + CommonConstant.ONE;
			}

			if (index + 1 == content.size()) {
				String value = ListUtil.joinLineSeparatorList(content, startIndex, content.size());
				titleMap.put(key, value);
			}
		}
	}

	/**
	 * 解析键映射
	 *
	 * @param singles     单打
	 * @param markdownTag Markdown 标签
	 * @return {@code Map<String, String> }
	 * @throws
	 * @see String
	 * @see String
	 * @see Map<String, String>
	 */
	public static Map<String, String> parseKeyMap(String singles, String markdownTag) {

		Map<String, String> titleMap = Maps.newTreeMap();

		if (StringUtils.isBlank(singles)) {
			return titleMap;
		}

		String[] split = singles.split("\n");
		List<String> content = Arrays.asList(split);

		// 遍历content数据
		String key = CommonConstant.BLANK_STR;
		Integer startIndex = CommonConstant.NEGATIVE_ONE;

		for (int index = 0; index < content.size(); index++) {

			if (content.get(index).matches(markdownTag)) {
				// 标题数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinLineSeparatorList(content, startIndex, index);
					titleMap.put(key, value);
				}

				// 数据校验
				key = content.get(index).trim();
				startIndex = index + CommonConstant.ONE;
			}

			if (index + 1 == content.size()) {
				String value = ListUtil.joinLineSeparatorList(content, startIndex, content.size());
				titleMap.put(key, value);
			}
		}

		return titleMap;
	}

	/**
	 * 设置相对文件路径
	 *
	 * @param newPath 新建路径
	 * @param line    线
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	public static String setRelativeFilePath(String newPath, String line) {

		// match img like ![](./img/1.png) ---> ./img/1.png
		File imgFile = new File(newPath + File.separator + "img");
		if (!imgFile.exists()) {
			imgFile.mkdirs();
		}

		List<String> imgs = LocalStringUtil.getAllMatchStr(line,
				"!\\[[^]]*]\\((/cpms.+\\.(?:jpg|jpeg|png|bmp|webp|gif))\\)", 1);
		int j = 0;
		for (String img : imgs) {
			if (!img.isEmpty()) {

				String newImg = "./img/" + (j + 1) + "." + FileUtil.getFileSuffix(img);
				line = line.replace(img, newImg);
				String s = FileUtil.copyFile(img,
						imgFile + File.separator + (j + 1) + "." + FileUtil.getFileSuffix(img),
						false);
				j++;
			}

		}
		return line;
	}

	/**
	 * 设置新增功能文件路径
	 *
	 * @param path 路径
	 * @param line 线
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	public static String setNewFIlePath(String path, String line) {
		if (line.contains(ConfigUtil.getContextProperty("cr.local.file.prefix"))) {
			return line;
		}

		// match img like ![](./img/1.png) ---> ./img/1.png
		List<String> imgs = LocalStringUtil.getAllMatchStr(line,
				"!\\[[^]]*]\\((\\./img/.+\\.(?:jpg|jpeg|png|bmp|webp|gif))\\)", 1);
		for (String img : imgs) {
			if (!img.isEmpty()) {
				// copy & rename img
				// remove initial dot .
				String originalPath = path + img.substring(1);
				// rename
				String imgId = IDGeneratorUtil.getID();
				String newFileName = imgId + "." + img.split("\\.", 3)[2];
				String dest = ConfigUtil.getContextProperty("cr.local.file.prefix")
						+ AttachmentPath.UPLOAD_IMAGE.getOriginDesc() + newFileName;// todo
				// copy
				String s = FileUtil.copyFile(originalPath, dest, CommonConstant.FALSE);

				line = line.replace(img, dest);
			}

		}
		return line;

	}

	/**
	 * 获取分配str
	 *
	 * @param length 长度
	 * @param str    str
	 * @return {@code String }
	 * @throws
	 * @see int
	 * @see String
	 * @see String
	 */
	private String getAssignStr(int length, String str) {

		String assignStr = str;
		for (int i = 1; i < length; i++) {
			assignStr += str;
		}

		return assignStr;
	}

	/**
	 * 内置解析器
	 *
	 * @throws
	 */
	private void collectParse() {
		Map<String, MdContentParser> beansOfType = SpringContextUtil
				.getBeansOfType(MdContentParser.class);
		parserMap = StreamApiUtils.toMap(beansOfType.values(), MdContentParser::getClass,
				mdContentParser -> mdContentParser);
	}

	/**
	 * 新增解析 器
	 *
	 * @param cls             CLS
	 * @param mdContentParser MD 内容解析器
	 * @throws
	 * @see Class
	 * @see MdContentParser
	 */
	public void addParser(Class cls, MdContentParser mdContentParser) {
		parserMap.put(cls, mdContentParser);
	}

}
