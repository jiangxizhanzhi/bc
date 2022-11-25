package com.cyberpeace.bs.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.cyberpeace.bs.service.bo.simulator.BehaviorConfig;
import com.cyberpeace.bs.service.bo.simulator.IpBo;
import com.cyberpeace.common.util.StreamApiUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.service.bo.BehaviorStatistic;
import com.cyberpeace.bs.service.bo.behavior.BehaviorSimple;
import com.cyberpeace.bs.service.bo.model.UserModelBase;
import com.cyberpeace.bs.service.bo.model.UserModelBo;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.google.common.collect.Maps;
import org.apache.shiro.util.CollectionUtils;

/**
 * @author SN
 */
public class BsUtil {

	private static final String JSON_LIST_INITIAL = "[";

	private static final String EMPTY_STRING = "";

	private static final int SECONDS_PER_MINUTE = 60;

	/**
	 * 解析存储的行为列表json
	 * @param json 行为列表json
	 * @return 行为列表
	 */
	public static List<BehaviorSimple> parseBehaviorList(String json) {
		if (StringUtils.isBlank(json)) {
			return Lists.newArrayList();
		}
		if (!json.startsWith(JSON_LIST_INITIAL)) {
			throw new BusinessException(BsErrorCode.INVALID_BEHAVIOR_CONF);
		}
		return JsonUtil.toJsonObjectArray(json, BehaviorSimple.class);
	}

	/**
	 * 按 start time 给行为列表排序
	 * @param behaviorSimpleList 待排序的行为列表
	 */
	public static <T extends BehaviorSimple> void sortBehaviorList(List<T> behaviorSimpleList) {
		behaviorSimpleList.sort(Comparator.comparing(T::getStartTime));
	}

	/**
	 * 解析存储的行为列表json
	 * @param json 行为列表json
	 * @return 起止时间，行为个数
	 */
	public static BehaviorStatistic getBehaviorStatistic(String json) {
		BehaviorStatistic statistic = new BehaviorStatistic();
		List<BehaviorSimple> behaviorSimpleList = parseBehaviorList(json);
		if (behaviorSimpleList.isEmpty()) {
			return statistic;
		}
		// 顺序在创建和编辑UserBehavior时保证
		statistic.setBehaviorCount(behaviorSimpleList.size());
		statistic.setStartTime(behaviorSimpleList.get(0).getStartTime());
		statistic.setEndTime(behaviorSimpleList.get(behaviorSimpleList.size() - 1).getStartTime());
		return statistic;
	}

	/**
	 * 解析存储的用户行为列表，获取起止时间和行为个数
	 * @param json 行为列表json
	 * @param <T> 设置的实例
	 */
	public static <T extends UserModelBo> void setBehaviorStatistic(T bo, String json) {
		BehaviorStatistic statistic = getBehaviorStatistic(json);
		bo.setBehaviorCount(statistic.getBehaviorCount());
		bo.setStartTime(statistic.getStartTime());
		bo.setEndTime(statistic.getEndTime());
	}

	/**
	 * 解析访问习惯列表
	 * @param bo 设置的实例
	 * @param json 访问习惯列表json
	 */
	public static <T extends UserModelBase> void setHabits(T bo, String json) {
		if (StringUtils.isBlank(json)) {
			bo.setAccessHabit(Lists.newArrayList());
		}
		if (!json.startsWith(JSON_LIST_INITIAL)) {
			throw new BusinessException(ErrorCode.INVALID_CONFIGURATION);
		}
		bo.setAccessHabit(JsonUtil.toJsonObjectArray(json, String.class));
	}

	/**
	 * 将localTime转为当天的Date
	 * @return 当天的指定时间
	 */
	public static Date getDateToday(LocalTime localTime) {
		LocalDate localDate = LocalDate.now();
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(localDate, localTime));
		return new Date(timestamp.getTime());
	}

	/**
	 * 获取 LocalTime 的下一个 Date
	 * @param localTime 不带日期的时间
	 * @return 下一次该时间的日期+时间
	 */
	public static Date getNextDate(LocalTime localTime) {
		LocalTime now = LocalTime.now();
		LocalDate localDate = LocalDate.now();
		if (localTime.isBefore(now)) {
			localDate = localDate.plusDays(1L);
		}
		Timestamp timestamp = Timestamp.valueOf(localDate.atTime(localTime));
		return new Date(timestamp.getTime());
	}

	/**
	 * 解析出每个行为的行为变量
	 * @param json 行为变量集合json
	 * @return 行为id-行为变量的map
	 */
	public static Map<String, String> getVariableMap(String json) {
		if(StringUtils.isBlank(json) || !json.startsWith(JSON_LIST_INITIAL)){
			return Maps.newHashMap();
		}
		List<BehaviorConfig> configList = JsonUtil.toJsonObjectArray(json, BehaviorConfig.class);
		return StreamApiUtils.toMap(configList, BehaviorConfig::getBehaviorId, config->
				JsonUtil.arrayToString(config.getVariables())
		);
	}

	/**
	 * double四舍五入成整数
	 * @param digit 双精度浮点数，可为负
	 * @return 整型
	 */
	public static Integer round(double digit) {
		boolean negative = false;
		if (digit < 0) {
			digit = -digit;
			negative = true;
		}
		int tmp = (int) (digit * 2);
		int ret = tmp / 2;
		int mod = tmp % 2;
		if (mod == 1) {
			ret++;
		}
		if (negative) {
			ret = -ret;
		}
		return ret;
	}

	/**
	 * 秒转化为分钟，向上取整
	 * @param seconds
	 * @return
	 */
	public static Integer s2Min(Integer seconds){
		if(null == seconds || seconds <= 0){
			return 0;
		}
		int minutes = seconds / SECONDS_PER_MINUTE;
		if(seconds % SECONDS_PER_MINUTE > 0){
			minutes++;
		}
		return minutes;
	}

	/**
	 * 获取ip
	 * @param ipBoList
	 * @return
	 */
	public static String getIp(List<IpBo> ipBoList){
		if(CollectionUtils.isEmpty(ipBoList)){
			return EMPTY_STRING;
		}
		return ipBoList.get(0).getIp();
	}
}
