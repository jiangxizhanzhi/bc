package com.cyberpeace.cr.crbase.service.context;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.mp.AttackTypeCount;
import com.cyberpeace.cr.crbase.service.context.mp.SceneTrend;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.score.GetScoreRsp;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreTrend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.time.Year;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 训练报告上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TrainReportContext {

	/**
	 * 红色趋势
	 *
	 * @see List<StudentScoreTrend>
	 */
	@ApiModelProperty("红方得分趋势")
	List<StudentScoreTrend> redTrends;

	/**
	 * 蓝色趋势
	 *
	 * @see List<StudentScoreTrend>
	 */
	@ApiModelProperty("蓝方得分趋势")
	List<StudentScoreTrend> blueTrends;

	/**
	 * 团队趋势
	 *
	 * @see List<StudentScoreTrend>
	 */
	@ApiModelProperty("团队得分趋势")
	List<StudentScoreTrend> teamTrends;

	/**
	 * 获取得分RSP
	 *
	 * @see List<GetScoreRsp>
	 */
	@ApiModelProperty("人员得分")
	List<GetScoreRsp> getScoreRsps;

	/**
	 * 红获取得分RSP
	 *
	 * @see List<GetScoreRsp>
	 */
	@ApiModelProperty("红方人员得分")
	List<GetScoreRsp> redGetScoreRsps;

	/**
	 * 蓝获取得分RSP
	 *
	 * @see List<GetScoreRsp>
	 */
	@ApiModelProperty("蓝方人员得分")
	List<GetScoreRsp> blueGetScoreRsps;

	/**
	 * 任务类计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务分类个数")
	private Integer taskKindCount;

	/**
	 * 种类地图
	 *
	 * @see Map<String, Integer>
	 */
	@ApiModelProperty("任务分类：key 名称 value个数")
	private Map<String, Integer> kindMap;

	/**
	 * 红色地图
	 *
	 * @see Map<String, Integer>
	 */
	@ApiModelProperty("红方任务分类：key 名称 value个数")
	private Map<String, Integer> redMap;

	/**
	 * 蓝色地图
	 *
	 * @see Map<String, Integer>
	 */
	@ApiModelProperty("蓝方任务分类：key 名称 value个数")
	private Map<String, Integer> blueMap;

	/**
	 * 用户角色
	 *
	 * @see List<UserRole>
	 */
	@ApiModelProperty("角色信息")
	private List<UserRole> userRoles;

	/**
	 * 房间底信息
	 *
	 * @see RoomBaseInfo
	 */
	@ApiModelProperty("基础信息")
	private RoomBaseInfo roomBaseInfo;

	/**
	 * 教员名称
	 *
	 * @see String
	 */
	private String teacherName;

	/**
	 * 管理名称
	 *
	 * @see String
	 */
	@ApiModelProperty("管理员")
	private String adminName;

	/**
	 * 导演名称
	 *
	 * @see String
	 */
	@ApiModelProperty("导演")
	private String directorName;

	/**
	 * 裁判名称
	 *
	 * @see String
	 */
	@ApiModelProperty("裁判")
	private String refereesName;

	/**
	 * 时间列表
	 *
	 * @see List<Date>
	 */
	private List<Date> timeList = new ArrayList<>();

	/**
	 * 时间字符串列表
	 *
	 * @see List<String>
	 */
	private List<String> timeStringList = new ArrayList<>();

	/**
	 * 攻击类型计数
	 *
	 * @see List<AttackTypeCount>
	 */
	@ApiModelProperty("攻击流量各类别统计")
	private List<AttackTypeCount> attackTypeCount;

	/**
	 * 场景趋势
	 *
	 * @see List<SceneTrend>
	 */
	@ApiModelProperty("场景流量趋势图")
	private List<SceneTrend> sceneTrend;

	public void initScoreTrends(List<StudentScoreTrend> scoreTrends) {
		teamTrends = scoreTrends.stream()
				.filter(a -> CrRole.TeamRoleGroup.STUDENT.getCode().equals(a.getRole()))
				.sorted(Comparator.comparing(StudentScoreTrend::getScoreSum).reversed())
				.collect(Collectors.toList());
		redTrends = scoreTrends.stream()
				.filter(a -> CrRole.RbRoleGroup.RED.getCode().equals(a.getRole()))
				.sorted(Comparator.comparing(StudentScoreTrend::getScoreSum).reversed())
				.collect(Collectors.toList());
		blueTrends = scoreTrends.stream()
				.filter(a -> CrRole.RbRoleGroup.BLUE.getCode().equals(a.getRole()))
				.sorted(Comparator.comparing(StudentScoreTrend::getScoreSum).reversed())
				.collect(Collectors.toList());
		//得分趋势最后只展示前10名
		teamTrends = teamTrends.subList(0,Math.min(10,teamTrends.size()));
        redTrends = redTrends.subList(0,Math.min(10,redTrends.size()));
        blueTrends = blueTrends.subList(0,Math.min(10,blueTrends.size()));
	}

	public void initTimeList(ScoreTrendReq scoreTrendReq) {
		long start = scoreTrendReq.getStartTime().getTime();
		long end = scoreTrendReq.getEndTime().getTime();
		// 需求改回 永远输出年月日格式
		// boolean isSameYear = DateUtil.isSameYear(scoreTrendReq.getStartTime(),
		// scoreTrendReq.getEndTime());
		boolean isSameYear = false;
		// 间隔
		long interval = (end - start) / scoreTrendReq.getScoreCount();
		timeList.add(scoreTrendReq.getStartTime());
		if (isSameYear) {
			timeStringList
					.add(DateUtil.DateToString(scoreTrendReq.getStartTime(), "MM-dd HH:mm:ss"));
		} else {
			timeStringList.add(
					DateUtil.DateToString(scoreTrendReq.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		for (int i = 0; i < scoreTrendReq.getScoreCount() - 1; i++) {
			Date date = new Date(start + interval * (i + 1));
			timeList.add(date);
			if (isSameYear) {
				timeStringList.add(DateUtil.DateToString(date, "MM-dd HH:mm:ss"));
			} else {
				timeStringList.add(DateUtil.DateToString(date, "yyyy-MM-dd HH:mm:ss"));
			}
		}
		timeList.add(scoreTrendReq.getEndTime());
		if (isSameYear) {
			timeStringList.add(DateUtil.DateToString(scoreTrendReq.getEndTime(), "MM-dd HH:mm:ss"));
		} else {
			timeStringList
					.add(DateUtil.DateToString(scoreTrendReq.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
		}

	}

	public void initRbGetScoreRsps(Boolean isRb) {
		this.redGetScoreRsps = this.getScoreRsps.stream()
				.filter(a -> CrRole.RbRoleGroup.RED.getCode().equals(a.getType()))
				.collect(Collectors.toList());
		this.blueGetScoreRsps = this.getScoreRsps.stream()
				.filter(a -> CrRole.RbRoleGroup.BLUE.getCode().equals(a.getType()))
				.collect(Collectors.toList());
		AtomicReference<Integer> index = new AtomicReference<>(1);
		if (isRb) {
			redGetScoreRsps.forEach(a -> a.setIndex(index.getAndSet(index.get() + 1)));
			index.set(1);
			blueGetScoreRsps.forEach(a -> a.setIndex(index.getAndSet(index.get() + 1)));
		} else {
			getScoreRsps.forEach(a -> a.setIndex(index.getAndSet(index.get() + 1)));
		}

	}

	public void initMpData(JSONArray attackTypeCount, JSONArray sceneTrend) {
		this.attackTypeCount = BeanUtils.deepCopyList(attackTypeCount, AttackTypeCount.class);
		this.sceneTrend = BeanUtils.deepCopyList(sceneTrend, SceneTrend.class);

	}
}
