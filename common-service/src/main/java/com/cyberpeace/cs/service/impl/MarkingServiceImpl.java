package com.cyberpeace.cs.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cs.controller.res.MarkScore;
import com.cyberpeace.cs.dao.entity.Marking;
import com.cyberpeace.cs.dao.mapper.MarkingMapper;
import com.cyberpeace.cs.service.MarkingService;

/**
 *
 */
@Service
public class MarkingServiceImpl extends ServiceImpl<MarkingMapper, Marking> implements MarkingService {

	@Autowired
	MarkingMapper markingMapper;

	@Override
	public void mark(String userId, String businessId, Integer score) {

		markingMapper.deleteByUserIdAndBusinessId(userId, businessId);

		Marking marking = new Marking();
		marking.setMarkType(Marking.ConstantsType.COURSE.getCode());
		marking.setScore(score);
		marking.setUserId(userId);
		marking.setBusinessId(businessId);

		save(marking);

	}

	@Override
	public MarkScore getMarkScoreByUserIdAndBusinessId(String userId, String businessId) {
		MarkScore markScore = new MarkScore();

		List<Marking> markings = markingMapper.getByBusinessId(businessId);
		if (markings.size() > 0) {

			double asDouble = markings.stream().mapToDouble(Marking::getScore).average()
					.getAsDouble();

			List<Marking> currentMarking = markings.stream()
					.filter(marking -> marking.getUserId().equals(userId))
					.collect(Collectors.toList());

 			markScore.setMarkAverageScore( new DecimalFormat("0.0").format(asDouble)
					) ;
			markScore.setMarkScore(currentMarking != null && currentMarking.size() > 0
					? currentMarking.get(0).getScore()
					: null);
		}

		return markScore;
	}
}
