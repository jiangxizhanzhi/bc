package com.cyberpeace.ai.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.ai.model.vo.*;
import com.cyberpeace.ai.model.vo.course.CourseListDataVO;
import com.cyberpeace.ai.model.vo.practice.PracticeDataVO;
import com.cyberpeace.ai.model.vo.practice.PracticeTheoryDataVO;
import com.cyberpeace.ai.model.vo.train.TrainListDataVO;
import com.cyberpeace.ai.model.vo.train.TrainStatisticsDataVO;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "用户画像报告接口")
@RestController
@RequestMapping("/ai/user_profile")
public class UserProfileController {

	@ApiOperation("根据维度获取用户画像报告数据")
	@GetMapping("/info")
	public BaseRsp<UserProfileReportVO> getUserProfileReport(
			@ApiParam(value = "维度", required = true) @RequestParam String dimension) {

		UserProfileReportVO vo = getUserProfileData(dimension);

		return new BaseRsp<>(vo);
	}

	@ApiOperation("保存用户画像报告")
	@PostMapping("/report")
	public BaseRsp<Boolean> saveUserProfileReport(@RequestBody UserProfileReportVO req) {
		boolean result = true;
		return new BaseRsp<>(result);
	}

	@ApiOperation("获取用户画像报告列表")
	@GetMapping("/report/list")
	public BaseRsp<List<UserProfileReportListVO>> getUserProfileReportList() {
		List<UserProfileReportListVO> list = Lists.newArrayList();
		list.add(new UserProfileReportListVO() {

			{
				setReportId("20230405-0001");
				setReportName("2023年4月5日用户画像报告");
				setCreateTime("2023-04-05 10:00:00");
			}
		});
		return new BaseRsp<>(list);
	}

	@ApiOperation("根据报告ID获取用户画像报告详情")
	@GetMapping("/report/detail")
	public BaseRsp<UserProfileReportVO> getUserProfileReportDetail(
			@ApiParam(value = "报告ID", required = true) @RequestParam String reportId) {
		UserProfileReportVO vo = getUserProfileData(reportId);
		vo.setReportId(reportId);
		vo.setReportName("2023年4月5日用户画像报告");
		vo.setSummaryData("总结数据");
		vo.setSuggestionData("建议数据");
		return new BaseRsp<>(vo);
	}

	private UserProfileReportVO getUserProfileData(String dimension) {

		// 构造假数据
		UserProfileReportVO vo = new UserProfileReportVO();
		vo.setUserName("Daniel");
		vo.setDimension(dimension);

		if (dimension.contains("1")) {

			vo.setDimension("课程学习");

			// 课程学习数据
			UserProfileCourseDataVO courseData = new UserProfileCourseDataVO();
			List<CourseListDataVO> courseList = Arrays.asList(new CourseListDataVO() {

				{
					setName("Web安全基础");
					setModuleName("基础理论");
					setStatus("已完成");
					setDuration(8);
					setFinishDate("2024-06-05");
				}
			}, new CourseListDataVO() {

				{
					setName("SQL注入实战");
					setModuleName("漏洞攻击");
					setStatus("已完成");
					setDuration(12);
					setFinishDate("2025-03-10");
				}
			}, new CourseListDataVO() {

				{
					setName("XSS跨站脚本");
					setModuleName("漏洞攻击");
					setStatus("进行中");
					setDuration(4);
					setFinishDate(null);
				}
			}, new CourseListDataVO() {

				{
					setName("网站典型安全");
					setModuleName("数据安全");
					setStatus("未完成");
					setDuration(6);
					setFinishDate("2025-04-10");
				}
			});
			courseData.setCourseList(courseList);
			courseData.setStudyStatistics(new com.cyberpeace.rtks.service.bo.RoleStudyBo() {

				{
					setTotalCourses(23);
					setFinishedCourses(13);
					setFinishedTheoryLessons(10);
					setUnFinishedTheoryLessons(2);
					setFinishedPracticeLessons(8);
					setUnFinishedPracticeLessons(1);
				}
			});
			courseData.setKnowledgePoints(Arrays.asList(new PointDataVO() {

				{
					setContent("计算机网络概念和协议");
					setMastery("20%");
				}
			}, new PointDataVO() {

				{
					setContent("复杂数据结构的知识");
					setMastery("30%");
				}
			}, new PointDataVO() {

				{
					setContent("计算机算法的知识");
					setMastery("10%");
				}
			}));
			vo.setCourseData(courseData);

		}

		if (dimension.contains("2")) {

			vo.setDimension("题目练习");
			// 题目练习数据
			UserProfilePracticeDataVO practiceData = new UserProfilePracticeDataVO();
			ProgressStatisticsRes theoryOverallStats = new ProgressStatisticsRes();
			theoryOverallStats
					.setSimple(new com.cyberpeace.oj.practice.service.bo.ProgressProportion() {

						{
							setDifficulty(1);
							setCount(12);
							setSum(20);
						}
					});
			theoryOverallStats
					.setMedium(new com.cyberpeace.oj.practice.service.bo.ProgressProportion() {

						{
							setDifficulty(2);
							setCount(6);
							setSum(20);
						}
					});
			theoryOverallStats
					.setDifficulty(new com.cyberpeace.oj.practice.service.bo.ProgressProportion() {

						{
							setDifficulty(3);
							setCount(2);
							setSum(20);
						}
					});
			theoryOverallStats
					.setSum(new com.cyberpeace.oj.practice.service.bo.ProgressProportion() {

						{
							setDifficulty(0);
							setCount(20);
							setSum(60);
						}
					});
			practiceData.setTheoryOverallStats(theoryOverallStats);
			practiceData.setTheoryDirectionStats(Arrays.asList(new PracticeTheoryDataVO() {

				{
					setDirection("选择题");
					setCount(300);
					setPracticeCount(20);
					setErrorCount(6);
				}
			}, new PracticeTheoryDataVO() {

				{
					setDirection("填空题");
					setCount(400);
					setPracticeCount(21);
					setErrorCount(7);
				}
			}));
			practiceData.setPracticalOverallStats(
					Arrays.asList(new CategoryProgressRes("Mobile", "1", 20, 30),
							new CategoryProgressRes("Pwn", "2", 21, 40),
							new CategoryProgressRes("Web", "3", 20, 30),
							new CategoryProgressRes("Misc", "4", 21, 40),
							new CategoryProgressRes("Crypto", "5", 20, 30),
							new CategoryProgressRes("Reverse", "6", 21, 40)));
			practiceData.setPracticalDirectionStats(Arrays.asList(new PracticeDataVO() {

				{
					setDirection("Web");
					setCount(20);
					setSolved(15);
				}
			}, new PracticeDataVO() {

				{
					setDirection("Pwn");
					setCount(21);
					setSolved(16);
				}
			}));
			vo.setPracticeData(practiceData);
		}

		if (dimension.contains("3")) {
			vo.setDimension("综合训练");

			// 综合训练数据
			UserProfileTrainDataVO trainData = new UserProfileTrainDataVO();
			TrainStatisticsDataVO trainStats = new TrainStatisticsDataVO();
			trainStats.setIncomplete(Arrays.asList(
					new com.cyberpeace.cr.crbase.service.context.statistics.TrainingCountRsp(1,
							"红蓝对抗", 3),
					new com.cyberpeace.cr.crbase.service.context.statistics.TrainingCountRsp(1,
							"团队训练", 2),
					new com.cyberpeace.cr.crbase.service.context.statistics.TrainingCountRsp(1,
							"单兵训练", 1)));
			trainStats.setCompleted(Arrays.asList(
					new com.cyberpeace.cr.crbase.service.context.statistics.TrainingCountRsp(2,
							"红蓝对抗", 4),
					new com.cyberpeace.cr.crbase.service.context.statistics.TrainingCountRsp(2,
							"团队训练", 6),
					new com.cyberpeace.cr.crbase.service.context.statistics.TrainingCountRsp(2,
							"单兵训练", 3)));
			HashMap<String, Integer> typeMap = new HashMap<>();
			typeMap.put("综合场景", 12);
			typeMap.put("系统仿真", 3);
			trainStats.setTrainingTypeMap(typeMap);
			trainData.setOverallStats(trainStats);
			trainData.setTaskCompletion(Arrays.asList(new TrainListDataVO() {

				{
					setName("安全防御演练");
					setType("单兵训练");
					setTotal(15);
					setCompleted(12);
					setCompletionRate("80%");
				}
			}, new TrainListDataVO() {

				{
					setName("分布式攻防演练");
					setType("团队训练");
					setTotal(3);
					setCompleted(2);
					setCompletionRate("60%");
				}
			}, new TrainListDataVO() {

				{
					setName("靶场工控实验云实战");
					setType("红蓝对抗");
					setTotal(4);
					setCompleted(3);
					setCompletionRate("80%");
				}
			}, new TrainListDataVO() {

				{
					setName("某品牌实验室攻防演练");
					setType("团队训练");
					setTotal(4);
					setCompleted(1);
					setCompletionRate("25%");
				}
			}));
			vo.setTrainingData(trainData);
		}

		if (dimension.contains("4")) {
			vo.setDimension("岗位评估");
			// 岗位评估数据
			UserProfileEvaluationDataVO evaluationData = new UserProfileEvaluationDataVO();
			evaluationData.setLevel("网络安全框架/保护和防御/漏洞评估和管理");
			evaluationData.setKnowledgePoints(Arrays.asList(new PointDataVO() {

				{
					setContent("计算机组成原理");
					setMastery("20%");
				}
			}, new PointDataVO() {

				{
					setContent("基础网络安全");
					setMastery("30%");
				}
			}));
			evaluationData.setSkillPoints(Arrays.asList(new PointDataVO() {

				{
					setContent("攻防演练能力");
					setMastery("40%");
				}
			}, new PointDataVO() {

				{
					setContent("入侵检测");
					setMastery("10%");
				}
			}));
			vo.setEvaluationData(evaluationData);
		}

		return vo;
	}
}