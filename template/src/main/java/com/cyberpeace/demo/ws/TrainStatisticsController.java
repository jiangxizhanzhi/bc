package com.cyberpeace.demo.ws;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingDateCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingRecordRsp;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(tags = "资源统计相关api")
@RestController
@RequestMapping("/cr/common")
@RequiresAuthentication
public class TrainStatisticsController {

	@Autowired
	private CrBaseService crBaseService;

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@ApiOperation("首页训练记录")
	@GetMapping("/training_records")
	public BaseRsp<TrainingRecordRsp>
	getTrainingRecords(@ApiParam @Validated PageReqBase req) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TrainingRecordRsp rsp = crBaseService.getTrainingRecords(userId);
		List<TrainingDateCount> lessonRecordCountByDate = lessonRecordMapper.getLessonRecordCountByDate("");
		List<TrainingDateCount> lessonRecordCountByDatePerson = lessonRecordMapper.getLessonRecordCountByDate(userId);
		Map<String, TrainingDateCount> stringTrainingDateCountMap = StreamApiUtils.toMap(lessonRecordCountByDatePerson, TrainingDateCount::getDate, s -> s);


		List<String> dateList = rsp.getDate();
		List<Integer> allList = rsp.getAll();
		List<Integer> personList = rsp.getPerson();

		List<TrainingDateCount> res = new ArrayList<>();
		for(int i = 0; i < dateList.size(); i++){
			TrainingDateCount trainingDateCount = new TrainingDateCount();
			trainingDateCount.setDate(dateList.get(i));
			trainingDateCount.setAll(allList.get(i));
			trainingDateCount.setCompleted(personList.get(i));
			res.add(trainingDateCount);
		}
		lessonRecordCountByDate.forEach(s-> {
			TrainingDateCount trainingDateCount = new TrainingDateCount();
			String date = s.getDate();
			trainingDateCount.setDate(date);
			Integer all = s.getAll();
			if(!dateList.contains(date)){
				trainingDateCount.setAll(all);
				if(stringTrainingDateCountMap.containsKey(date)){
					TrainingDateCount dateCount = stringTrainingDateCountMap.get(date);
					trainingDateCount.setCompleted(dateCount.getCompleted());
				}
				res.add(trainingDateCount);
			} else {
				all += allList.get(dateList.indexOf(date));
				if(stringTrainingDateCountMap.containsKey(date)){
					TrainingDateCount dateCount = stringTrainingDateCountMap.get(date);
					Integer completed = dateCount.getCompleted();
					completed += personList.get(dateList.indexOf(date));
					trainingDateCount.setCompleted(completed);
				}
				trainingDateCount.setDate(date);
				trainingDateCount.setAll(all);
				res.set(dateList.indexOf(date), trainingDateCount);
			}

		});
		res.sort(Comparator.comparing(TrainingDateCount::getDate));
		List<String> dates = StreamApiUtils.getColumn(res, TrainingDateCount::getDate);
		List<Integer> person = StreamApiUtils.getColumn(res, TrainingDateCount::getCompleted);
		List<Integer> all = StreamApiUtils.getColumn(res, TrainingDateCount::getAll);
		if(dates.isEmpty()){
			return null;
		}
		rsp.setAll(all);
		rsp.setDate(dates);
		rsp.setPerson(person);
		return new BaseRsp<>(rsp);
	}

}
