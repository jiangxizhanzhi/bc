package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;

import java.util.List;

/**
 * 练习理论导入Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/12/04
 * @see IService
 * @since 1.0.0
 */
public interface PracticeTheoryImportService extends IService<PracticeTheory> {

	/**
	 * 解析课后练习信息-理论习题-markdown格式
	 *
	 * @param path             课时文件路径
	 * @param mdTheoryPractice 读取到的md内容
	 * @return
	 * @throws
	 * @see String
	 * @see MdTheoryPractice
	 * @see List<PracticeTheory>
	 */
	List<PracticeTheory> parseLessonTheoryMd(String path, MdTheoryPractice mdTheoryPractice);

	/**
	 * 资管理论习题导入
	 *
	 * @param path
	 * @param practiceSet 习题集
	 * @return
	 * @throws
	 * @see String
	 * @see PracticeSet
	 * @see List<PracticeTheory>
	 */
	List<PracticeTheory> parseRmTheory(String path, PracticeSet practiceSet);

	/**
	 * 解析课后习题-理论习题
	 *
	 * @param excelPath
	 * @return
	 * @throws
	 * @see String
	 * @see List<PracticeTheory>
	 */
	List<PracticeTheory> parseLessonTheoryExcel(String excelPath);

	/**
	 * 理论习题资源导入
	 *
	 * @param practiceSetId
	 * @param realPath
	 * @param userId        用户id
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean importTheory(String practiceSetId, String realPath, String userId);

}
