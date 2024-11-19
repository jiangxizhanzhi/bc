package com.cyberpeace.rtks.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.res.PercentRes;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.bo.KnowledgeStudyPercentBo;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;
import com.cyberpeace.rtks.service.bo.StudyRecordBo;
import com.cyberpeace.rtks.service.bo.skill.SkillTrainingRecordBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * RTKS评估Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@RestController
@RequestMapping("/rtks/statistic")
@Api(tags = "RTKS-作战能力评估")
@RequiresAuthentication
public class RtksEvaluationController {

	/**
	 * RTKS统计Service
	 *
	 * @see RtksStatisticService
	 */
	@Autowired
	private RtksStatisticService rtksStatisticService;

	/**
	 * RTKS态势Service
	 *
	 * @see RtksSituationService
	 */
	@Autowired
	private RtksSituationService rtksSituationService;

	/**
	 * 获取岗位研究记录
	 *
	 * @return {@code BaseRsp<RoleStudyBo> }
	 * @throws
	 * @see BaseRsp<RoleStudyBo>
	 */
	@ApiOperation("岗位知识点学习进度统计")
	@GetMapping("/role/knowledge/study_record")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<RoleStudyBo> getRoleStudyRecord() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleStudyBo> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			RoleStudyBo studyBo = rtksStatisticService.roleKnowledgeStudyRecord(existRoleId,
					userId);
			rsp.setData(studyBo);
		}
		return rsp;
	}

	/**
	 * 获取岗位知识点火柴
	 *
	 * @return {@code BaseRsp<List<RtksRadarBo>> }
	 * @throws
	 * @see BaseRsp<List<RtksRadarBo>>
	 */
	@ApiOperation("岗位知识点六棱图统计")
	@GetMapping("/role/knowledge/radar")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<List<RtksRadarBo>> getRoleKnowledgeMatch() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<List<RtksRadarBo>> baseRsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			List<RtksRadarBo> radarBos = rtksStatisticService.calculateRadar(existRoleId, userId);
			baseRsp.setData(radarBos);
		}
		return baseRsp;
	}

	/**
	 * 获取知识点过程由岗位
	 *
	 * @return {@code BaseRsp<PercentRes> }
	 * @throws
	 * @see BaseRsp<PercentRes>
	 */
	@ApiOperation("知识点完成进度折线图")
	@GetMapping("/role/knowledge/percent")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<PercentRes> getKnowledgeProcessByRole() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();

		BaseRsp<PercentRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			// 数据计算
			List<KnowledgeStudyPercentBo> percentBos = rtksStatisticService
					.calculateKnowledgePercent(existRoleId, userId);
			rsp.setData(percentFormatterTran(percentBos));
		}

		return rsp;
	}

	/**
	 * Percent Formatter tran
	 *
	 * @param percentBos
	 * @return
	 * @throws
	 * @see List<KnowledgeStudyPercentBo>
	 * @see PercentRes
	 */
	private PercentRes percentFormatterTran(List<KnowledgeStudyPercentBo> percentBos) {
		List<String> xNames = percentBos.stream().map(KnowledgeStudyPercentBo::getKnowledgeId)
				.collect(Collectors.toList());
		List<Integer> yPercents = percentBos.stream().map(KnowledgeStudyPercentBo::getPercent)
				.collect(Collectors.toList());

		// 结果返回
		PercentRes res = new PercentRes();
		res.setXNames(xNames);
		res.setYPercents(yPercents);
		return res;
	}

	/**
	 * 获取火柴由岗位
	 *
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see BaseRsp<String>
	 */
	@ApiOperation("岗位匹配度计算")
	@GetMapping("/role/match")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<String> getMatchByRole() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String name = UserInfoContext.getUserContext().getName();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		String roleName = UserInfoContext.getUserContext().getRoleName();
		BaseRsp<String> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			// 计算并记录匹配度
			Integer percent = rtksSituationService.recordSituation(userId, name, existRoleId,
					roleName);
			rsp.setData(percent + "");
		}
		return rsp;
	}

	/**
	 * 获取研究记录
	 *
	 * @param startTime 启动时间
	 * @param endTime   结束时间
	 * @return {@code BaseRsp<StudyRecordBo> }
	 * @throws
	 * @see String
	 * @see String
	 * @see BaseRsp<StudyRecordBo>
	 */
	@ApiOperation("岗位课程学习记录")
	@GetMapping("/role/course/study")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	@ApiImplicitParams({ @ApiImplicitParam(name = "start_time", value = "开始时间", dataTypeClass = String.class),
			@ApiImplicitParam(name = "end_time", value = "结束时间", dataTypeClass = String.class) })
	public BaseRsp<StudyRecordBo> getStudyRecord(@RequestParam("start_time") String startTime,
			@RequestParam("end_time") String endTime) {
		boolean startValidate = TimeUtil.isValidDate(startTime);
		boolean endValidate = TimeUtil.isValidDate(endTime);
		if (!startValidate || !endValidate) {
			throw new BusinessException(RtksBusinessErrorCode.TIME_FORMAT_ERROR);
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<StudyRecordBo> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(roleId)) {
			StudyRecordBo studyRecordBo = rtksStatisticService.calculateStudyRecord(userId, roleId,
					TimeUtil.toDate1(startTime), TimeUtil.toDate1(endTime));
			rsp.setData(studyRecordBo);
		}
		return rsp;
	}

	/**
	 * 获取任务训练记录
	 *
	 * @param startTime 启动时间
	 * @param endTime   结束时间
	 * @return {@code BaseRsp<SkillTrainingRecordBo> }
	 * @throws
	 * @see String
	 * @see String
	 * @see BaseRsp<SkillTrainingRecordBo>
	 */
	@ApiOperation("岗位评估-岗位技能训练记录")
	@GetMapping("/role/mission/study")
	@RequiresPermissions(value = MenuConstant.WEB_PERSON_CENTER)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "start_time", value = "开始时间", dataTypeClass = String.class),
			@ApiImplicitParam(name = "end_time", value = "结束时间", dataTypeClass = String.class) })
	public BaseRsp<SkillTrainingRecordBo> getMissionTrainingRecord(
			@RequestParam("start_time") String startTime,
			@RequestParam("end_time") String endTime) {
		if (!TimeUtil.isValidDate(startTime) || !TimeUtil.isValidDate(endTime)) {
			throw new BusinessException(RtksBusinessErrorCode.TIME_FORMAT_ERROR);
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		if (StringUtils.isBlank(roleId)) {
			return new BaseRsp<>();
		}
		SkillTrainingRecordBo recordBo = rtksStatisticService.calculateTrainingRecord(userId,
				roleId, TimeUtil.toDate1(startTime), TimeUtil.toDate1(endTime));
		return new BaseRsp<>(recordBo);
	}

}
