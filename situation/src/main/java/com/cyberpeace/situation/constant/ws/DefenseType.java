package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;

import java.util.Arrays;

public enum DefenseType {

	/**
	 * 防御类型
	 */

	Detection_and_elimination_of_trojan_horse_virus("木马病毒的检测与清除","Detection And Elimination Of Trojan Horse Virus","detection-and-elimination-of-trojan-horse-virus"),
	Detection_and_Transmission_Blocking_of_Worm_Virus("蠕虫病毒的检测与传播阻断","Detection And Transmission Blocking Of Worm Virus","detection-and-transmission-blocking-of-worm-virus");

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	DefenseType(String cnDesc, String enDesc, String keyWord) {
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
	}

	public static <T extends DefenseType> T getEnumByKeyWord(String keyWord, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getKeyWord().equals(keyWord))
					 .findFirst().orElse(null);
	}

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}
}
