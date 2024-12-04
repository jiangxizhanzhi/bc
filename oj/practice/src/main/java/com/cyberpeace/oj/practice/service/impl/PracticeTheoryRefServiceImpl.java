package com.cyberpeace.oj.practice.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryRefMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.BlankAnswer;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import com.cyberpeace.oj.practice.service.md.MdTheoryParser;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;

import lombok.extern.slf4j.Slf4j;

/**
 * 实践理论裁判Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see PracticeTheoryRefService
 * @since 1.0.0
 */
@Service
@Slf4j
public class PracticeTheoryRefServiceImpl extends ServiceImpl<PracticeTheoryRefMapper, PracticeTheoryRef> implements PracticeTheoryRefService {

	/**
	 * 实践理论裁判Mapper
	 *
	 * @see PracticeTheoryRefMapper
	 */
	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	/**
	 * 实践类别Mapper
	 *
	 * @see PracticeCategoryMapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 实践设置Service
	 *
	 * @see PracticeSetService
	 */
	@Autowired
	private PracticeSetService practiceSetService;

	/**
	 * 查询由纸
	 *
	 * @param paperId    纸id
	 * @param viewAnswer 视图答案
	 * @return {@code List<TheoryLessonBo> }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<TheoryLessonBo>
	 */
	@Override
	public List<TheoryLessonBo> selectByPaper(String paperId, Boolean viewAnswer) {

		List<PracticeTheoryRef> theoryRefList = practiceTheoryRefMapper.selectByPracticeIds(paperId,
				null);

		List<TheoryLessonBo> theoryList = StreamApiUtils.map(theoryRefList, practiceTheoryRef -> {
			PracticeTheory practiceTheory = JsonUtil.toJsonObject(practiceTheoryRef.getSnapshot(),
					PracticeTheory.class);
			TheoryLessonBo theoryLesonBo = BeanUtils.copy(practiceTheory, TheoryLessonBo.class);
			// 设置原习题数据资源编号
			// theoryLesonBo.setResourceId(practiceTheoryRef.getOriginId());

			PracticeCategory category = practiceCategoryMapper
					.selectById(practiceTheory.getCategoryId());
			if (null != category) {
				theoryLesonBo.setDirectionName(category.getName());
			}

			// 设置习题类型和题型
			theoryLesonBo.setQuestionType(practiceTheory.getTheoryType());
			theoryLesonBo.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
			theoryLesonBo.setRefId(practiceTheoryRef.getResourceId());
			if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
					.getCode().intValue()) {
				List<BlankAnswer> answers = JsonUtil.toJsonObjectArray(practiceTheory.getAnswer(),
						BlankAnswer.class);
				theoryLesonBo.setBlankCount(answers.size());
			}

			// 设置答案是否展示
			if (!viewAnswer) {
				theoryLesonBo.setAnswer(null);
			}

			return theoryLesonBo;
		});

		return theoryList;
	}

	/**
	 * 查询由实践id
	 *
	 * @param paperId    纸id
	 * @param practiceId 实践id
	 * @return {@code PracticeTheory }
	 * @throws
	 * @see String
	 * @see String
	 * @see PracticeTheory
	 */
	@Override
	public PracticeTheory selectByPracticeId(String paperId, String practiceId) {

		PracticeTheoryRef theoryRef = practiceTheoryRefMapper.selectByPracticeId(paperId,
				practiceId);

		return JsonUtil.toJsonObject(theoryRef.getSnapshot(), PracticeTheory.class);
	}

	/**
	 * 导出课程练习
	 *
	 * @param pathname 路径
	 * @param paperId  纸id
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public void exportLessonPractice(String pathname, String paperId) {
		List<PracticeTheoryRef> theoryRefList = practiceTheoryRefMapper.selectByPracticeIds(paperId,
				null);
		List<PracticeTheory> theoryList = StreamApiUtils.map(theoryRefList, practiceTheoryRef -> {
			PracticeTheory practiceTheory = JsonUtil.toJsonObject(practiceTheoryRef.getSnapshot(),
					PracticeTheory.class);
			return practiceTheory;
		});

		MdTheoryParser mdTheoryParser = new MdTheoryParser();

		MdTheoryPractice mdTheory = practiceSetService.exportMdTheoryPractice(pathname, theoryList);

		if (theoryList.size() > 0) {
			mdTheoryParser.write(mdTheory, new File(
					pathname + File.separator + ResourceImportConstant.PRACTICE_FILE_NAME));
		}

	}
}
