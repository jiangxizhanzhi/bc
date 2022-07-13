package com.cyberpeace.common.mdparser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.annotation.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.MdConvert;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
import com.cyberpeace.common.mdparser.convert.NoConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.common.mdparser.parser.ListParser;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.mdparser.parser.StringParser;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.ListUtil;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenzhuo 
 * 仅支持标题和列表 并且仅支持二级
 */
@Slf4j
public class MdParser {

	private String HEAD_SYMBOL = "#";

	private List<String> content;

	private Map<String, String> titleMap;

	private Map<Class, MdContentParser> parserMap = new ConcurrentHashMap<>();

	public MdParser(String file) {
		if (StringUtils.isBlank(file) || !new File(file).exists()) {
			log.error("File not exist! file_path=[{}]", file);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		}

		content = FileUtil.listContent(new File(file));
		titleMap = new ConcurrentHashMap<>();
		collectParse();
	}

	public <T> T read(Class<T> cls) {
		return doRead(cls);
	}

	@SneakyThrows
	private <T> T doRead(Class<T> cls) {
		mdKeyMap();
		Object instance = BeanUtils.instantiateClass(cls);

		Field[] fields = cls.getDeclaredFields();
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

			Class<? extends MdConvertEnum> convert = annotation.convert();
			if (convert != NoConvert.class) {
				parse = MdConvert.convertValue(parse, convert);
			}

			Class<? extends MdConvertEnum> reConvert = annotation.reConvert();
			if (reConvert != NoConvert.class) {
				parse = MdConvert.convertKey(parse, reConvert);
			}

			field.setAccessible(true);
			field.set(instance, parse);
		}

		return (T) instance;
	}

	/**
	 * 解析markdown文件 返回json数据
	 * @return
	 */
	public void mdKeyMap() {

		// 遍历content数据
		String key = CommonConstant.BLANK_STR;
		Integer startIndex = CommonConstant.NEGATIVE_ONE;

		for (int index = 0; index < content.size(); index++) {

			if (content.get(index).matches(PatternConstant.MARKDOWN_TITLE_MATCH)) {
				// 标题数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(content, startIndex, index);
					titleMap.put(key, value);
				}

				// 数据校验
				key = content.get(index).trim();
				startIndex = index + CommonConstant.ONE;
			}

			if (index + 1 == content.size()) {
				String value = ListUtil.joinList(content, startIndex, content.size());
				titleMap.put(key, value);
			}
		}
	}

	private String getAssignStr(int length, String str) {

		String assignStr = str;
		for (int i = 1; i < length; i++) {
			assignStr += str;
		}

		return assignStr;
	}

	/**
	 * 内置解析器
	 */
	private void collectParse() {
		parserMap.put(StringParser.class, new StringParser());
		parserMap.put(IntegerParser.class, new IntegerParser());
		parserMap.put(ListParser.class, new ListParser());
	}

	public void addParser(Class cls, MdContentParser mdContentParser) {
		parserMap.put(cls, mdContentParser);
	}

}
