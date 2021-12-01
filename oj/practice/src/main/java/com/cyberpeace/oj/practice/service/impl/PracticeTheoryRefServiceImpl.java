package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryRefMapper;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.BlankAnswer;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PracticeTheoryRefServiceImpl extends ServiceImpl<PracticeTheoryRefMapper, PracticeTheoryRef> implements PracticeTheoryRefService {

	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Override
	public List<TheoryLessonBo> selectByPaper(String paperId, Boolean viewAnswer) {

		List<PracticeTheoryRef> theoryRefList = practiceTheoryRefMapper.selectByPracticeIds(paperId,
				null);

		List<TheoryLessonBo> theoryList = StreamApiUtils.simpleMap(theoryRefList,
				practiceTheoryRef -> {
					PracticeTheory practiceTheory = JsonUtil
							.toJsonObject(practiceTheoryRef.getSnapshot(), PracticeTheory.class);
					TheoryLessonBo theoryLesonBo = BeanUtils.copy(practiceTheory,
							TheoryLessonBo.class);

					PracticeCategory category = practiceCategoryMapper
							.selectById(practiceTheory.getCategoryId());
					if (null != category) {
						theoryLesonBo.setDirectionName(category.getName());
					}

					theoryLesonBo.setQuestionType(practiceTheory.getTheoryType());
					theoryLesonBo.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
					if (practiceTheory.getTheoryType()
							.intValue() == PracticeTheory.TheoryType.FILL_BLANK.getCode()
									.intValue()) {
						List<BlankAnswer> answers = JsonUtil
								.toJsonObjectArray(practiceTheory.getAnswer(), BlankAnswer.class);
						theoryLesonBo.setBlankCount(answers.size());
					}

					if (!viewAnswer) {
						theoryLesonBo.setAnswer(null);
					}

					return theoryLesonBo;
				});

		return theoryList;
	}

	@Override
	public PracticeTheory selectByPracticeId(String paperId, String practiceId) {

		PracticeTheoryRef theoryRef = practiceTheoryRefMapper.selectByPracticeId(paperId,
				practiceId);
		PracticeTheory practiceTheory = JsonUtil.toJsonObject(theoryRef.getSnapshot(),
				PracticeTheory.class);

		return practiceTheory;
	}
}
