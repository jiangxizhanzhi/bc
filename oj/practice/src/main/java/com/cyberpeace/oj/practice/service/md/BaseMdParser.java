package com.cyberpeace.oj.practice.service.md;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.google.common.collect.Lists;

/**
 * 任务解析
 *
 * @author SN
 * @date 2024/11/11
 */
@Service
public class BaseMdParser implements MdContentParser<List<TheoryBaseMd>, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code List<TheoryBaseMd> }
	 */
	@Override
	public List<TheoryBaseMd> parse(Object content) {
		if(content == null){
			return null;
		}

		String value = (String) content;
		if (StringUtils.isBlank(value.trim())) {
			return null;
		}
		List<TheoryBaseMd> theoryBaseMdList = Lists.newArrayList();
		Map<String, String> titleMap = MdTheoryParser.parseKeyMap(value,
				PatternConstant.MARKDOWN_TAG);
		// 获取每一道题目
		Collection<String> singleStrings = titleMap.values();

		for (String singleString : singleStrings) {
			// 获取题目的各种属性

			Map<String, String> anwserMap = MdTheoryParser.parseKeyMap(singleString,
					PatternConstant.ANWSER_SPLIT_STR);

			Map<String, String> attributeMap = MdTheoryParser.parseKeyMap(
					anwserMap.getOrDefault("- 题目", ""), PatternConstant.ATTRIBUTE_SPLIT_STR);

			TheoryBaseMd theoryBaseMd = new TheoryBaseMd();
			theoryBaseMd.setTitle(attributeMap.getOrDefault("- 题目原文", "").trim());
			theoryBaseMd.setCategory(attributeMap.getOrDefault("- 方向", "").trim());
			theoryBaseMd.setDifficulty(attributeMap.getOrDefault("- 难度", "").trim());
			theoryBaseMd.setTitle(attributeMap.getOrDefault("- 题目原文", "").trim());
			theoryBaseMd.setAnswer(anwserMap.getOrDefault("- 答案", ""));

			theoryBaseMdList.add(theoryBaseMd);
		}

		return theoryBaseMdList;
	}

	/**
	 * 写入
	 *
	 * @param theoryBaseMds 理论基础MDS
	 * @return {@code String }
	 */
	@Override
	public String write(List<TheoryBaseMd> theoryBaseMds) {
		String str = System.lineSeparator();
		int i = 1;
		for (TheoryBaseMd theoryBaseMd : theoryBaseMds) {

			str = str + "### 题目" + i++ + System.lineSeparator() + System.lineSeparator();
			str = str + "- 题目" + System.lineSeparator() + System.lineSeparator();
			str = str + "  - 难度" + System.lineSeparator() + System.lineSeparator();
			str = str + "    " + theoryBaseMd.getDifficulty() + System.lineSeparator()
					+ System.lineSeparator();
			str = str + "  - 方向" + System.lineSeparator() + System.lineSeparator();
			str = str + "    " + theoryBaseMd.getCategory() + System.lineSeparator()
					+ System.lineSeparator();
			str = str + "  - 题目原文" + System.lineSeparator() + System.lineSeparator();
			str = str + "    " + theoryBaseMd.getTitle().trim() + System.lineSeparator()
					+ System.lineSeparator();
			str = str + "- 答案" + System.lineSeparator() + System.lineSeparator();
			str = str + "  " + theoryBaseMd.getAnswer().trim() + System.lineSeparator();

		}
		return LocalStringUtil.frontBackLine(str);
	}
}
