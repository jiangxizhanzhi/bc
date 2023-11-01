package com.cyberpeace.oj.practice.service.md;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.mdparser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.google.common.collect.Lists;

/**
 * 任务解析
 */
@Service
public class BaseMdParser implements MdContentParser<List<TheoryBaseMd>, String> {

	@Override
	public List<TheoryBaseMd> parse(Object content) {
		if (content == null) {
			return new ArrayList<>();
		}

		String value = (String) content;
		if (StringUtils.isBlank(value.trim())) {
			return new ArrayList<>();
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

	@Override
	public String write(List<TheoryBaseMd> theoryBaseMds) {
		StringBuilder str = new StringBuilder(System.lineSeparator());
		int i = 1;
		for (TheoryBaseMd theoryBaseMd : theoryBaseMds) {

			str.append("### 题目").append(i++).append(System.lineSeparator()).append(System.lineSeparator());
			str.append("- 题目").append(System.lineSeparator()).append(System.lineSeparator());
			str.append("  - 难度").append(System.lineSeparator()).append(System.lineSeparator());
			str.append("    ").append(theoryBaseMd.getDifficulty()).append(System.lineSeparator()).append(System.lineSeparator());
			str.append("  - 方向").append(System.lineSeparator()).append(System.lineSeparator());
			str.append("    ").append(theoryBaseMd.getCategory()).append(System.lineSeparator()).append(System.lineSeparator());
			str.append("  - 题目原文").append(System.lineSeparator()).append(System.lineSeparator());
			str.append("    ").append(theoryBaseMd.getTitle().trim()).append(System.lineSeparator()).append(System.lineSeparator());
			str.append("- 答案").append(System.lineSeparator()).append(System.lineSeparator());
			str.append("  ").append(theoryBaseMd.getAnswer().trim()).append(System.lineSeparator());

		}
		return LocalStringUtil.frontBackLine(str.toString());
	}
}
