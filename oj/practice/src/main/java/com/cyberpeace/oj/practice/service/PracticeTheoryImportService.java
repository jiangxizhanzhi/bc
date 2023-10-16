package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;

import java.util.List;

public interface PracticeTheoryImportService extends IService<PracticeTheory> {

	/**
	 * 解析课后练习信息-理论习题-markdown格式
	 * @param path 课时文件路径
	 * @param mdTheoryPractice 读取到的md内容
	 * @return
	 */
	List<PracticeTheory> parseLessonTheoryMd(String path, MdTheoryPractice mdTheoryPractice);

	/**
	 * 资管理论习题导入
	 * @param path
	 * @return
	 */
	List<PracticeTheory> parseRmTheory(String path, PracticeSet practiceSet);

	/**
	 * 解析课后习题-理论习题
	 * @param excelPath
	 * @return
	 */
	List<PracticeTheory> parseLessonTheoryExcel(String excelPath);

	/**
	 * 理论习题资源导入
	 * @param practiceSetId
	 * @param realPath
	 * @return
	 */
	String importTheory(String practiceSetId, String realPath, String userId);

}
