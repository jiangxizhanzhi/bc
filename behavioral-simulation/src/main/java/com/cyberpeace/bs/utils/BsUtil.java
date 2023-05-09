package com.cyberpeace.bs.utils;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.service.bo.BehaviorStatistic;
import com.cyberpeace.bs.service.bo.behavior.BehaviorSimple;
import com.cyberpeace.bs.service.bo.model.UserModelBase;
import com.cyberpeace.bs.service.bo.model.UserModelBo;
import com.cyberpeace.bs.service.bo.simulator.BehaviorConfigRaw;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 行为模拟辅助通用函数
 *
 * @author SN
 * @date 2023/05/08
 */
public class BsUtil {

    /**
     * 列表的json字符串的开头
     */
    private static final String JSON_LIST_INITIAL = "[";

    /**
     * 解析存储的行为列表json
     *
     * @param json 行为列表json
     * @return 行为列表
     */
    public static List<BehaviorSimple> parseBehaviorList(String json) {
        if (StringUtils.isBlank(json)) {
            return Lists.newArrayList();
        }
        // 不是合法的列表json
        if (!json.startsWith(JSON_LIST_INITIAL)) {
            throw new BusinessException(BsErrorCode.INVALID_BEHAVIOR_CONF);
        }
        // 解析
        return JsonUtil.toJsonObjectArray(json, BehaviorSimple.class);
    }

    /**
     * 按 start time 给行为列表排序
     *
     * @param behaviorSimpleList 待排序的行为列表
     */
    public static <T extends BehaviorSimple> void sortBehaviorList(List<T> behaviorSimpleList) {
        // 按开始时间排序
        behaviorSimpleList.sort(Comparator.comparing(T::getStartTime));
    }

    /**
     * 解析存储的行为列表json
     *
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
     *
     * @param json 行为列表json
     * @param <T>  设置的实例
     */
    public static <T extends UserModelBo> void setBehaviorStatistic(T bo, String json) {
        BehaviorStatistic statistic = getBehaviorStatistic(json);
        // 行为个数
        bo.setBehaviorCount(statistic.getBehaviorCount());
        // 第一个行为的执行时间
        bo.setStartTime(statistic.getStartTime());
        // 最后一个行为的执行时间
        bo.setEndTime(statistic.getEndTime());
    }

    /**
     * 解析访问习惯列表
     *
     * @param bo   设置的实例
     * @param json 访问习惯列表json
     */
    public static <T extends UserModelBase> void setHabits(T bo, String json) {
        // json为空，返回空列表
        if (StringUtils.isBlank(json)) {
            bo.setAccessHabit(Lists.newArrayList());
        }
        // 非法的json
        if (!json.startsWith(JSON_LIST_INITIAL)) {
            throw new BusinessException(ErrorCode.INVALID_CONFIGURATION);
        }
        bo.setAccessHabit(JsonUtil.toJsonObjectArray(json, String.class));
    }

    /**
     * 将localTime转为当天的Date
     *
     * @return 当天的指定时间
     */
    public static Date getDateToday(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(localDate, localTime));
        return new Date(timestamp.getTime());
    }

    /**
     * 获取 LocalTime 的下一个 Date
     *
     * @param localTime 不带日期的时间
     * @return 下一次该时间的日期+时间
     */
    public static Date getNextDate(LocalTime localTime) {
        LocalTime now = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        // 当前时间已经晚于传入的时间
        if (localTime.isBefore(now)) {
            // 获取第二天的该时间点
            localDate = localDate.plusDays(1L);
        }
        Timestamp timestamp = Timestamp.valueOf(localDate.atTime(localTime));
        return new Date(timestamp.getTime());
    }

    /**
     * 解析出每个行为的行为变量
     *
     * @param json 行为变量集合json
     * @return 行为id-行为变量的map
     */
    public static Map<String, String> getVariableMap(String json) {
        // json为空或无效，返回空字典
        if (StringUtils.isBlank(json) || !json.startsWith(JSON_LIST_INITIAL)) {
            return Maps.newHashMap();
        }
        List<BehaviorConfigRaw> configList = JsonUtil.toJsonObjectArray(json,
                BehaviorConfigRaw.class);
        for (BehaviorConfigRaw raw : configList) {
            // 未指定变量
            if (raw.getVariables() == null) {
                raw.setVariables(BsConstants.EMPTY_LIST_JSON);
            }
        }
        return StreamApiUtils.toMap(configList, BehaviorConfigRaw::getBehaviorId,
                BehaviorConfigRaw::getVariables);
    }

    /**
     * double四舍五入成整数
     *
     * @param digit 双精度浮点数，可为负
     * @return 整型
     */
    public static Integer round(double digit) {
        boolean negative = false;
        // 负数处理为正数
        if (digit < 0) {
            digit = -digit;
            negative = true;
        }
        // 乘以2之后再除以2
        int tmp = (int) (digit * 2);
        int ret = tmp / 2;
        int mod = tmp % 2;
        // 余数为1，则进1
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
     *
     * @param seconds 秒数
     * @return
     */
    public static Integer s2Min(Integer seconds) {
        if (null == seconds || seconds <= 0) {
            return 0;
        }
        int minutes = seconds / BsConstants.SECONDS_PER_MINUTE;
        // 向上取整
        if (seconds % BsConstants.SECONDS_PER_MINUTE > 0) {
            minutes++;
        }
        return minutes;
    }

    /**
     * 将行为设备id转为int
     *
     * @param deviceId  设备id
     * @param isUsingIp 是否使用ip识别设备
     * @return {@code Integer}
     */
    public static Integer getLcDeviceId(String deviceId, Boolean isUsingIp) {
        if (isUsingIp) {
            return new Integer(BsConstants.DEFAULT_DEVICE_ID);
        } else {
            return new Integer(deviceId);
        }
    }

    /**
     * 将列表从前方填充到指定长度
     *
     * @param raw    原始列表
     * @param length 指定长度
     * @param e      指定填0元素
     * @param <E>    元素类型
     * @return
     */
    public static <E> List<E> fill(List<E> raw, Integer length, E e) {
        if (raw == null) {
            raw = Lists.newArrayList();
        }
        // 原始列表元素个数不足
        if (raw.size() < length) {
            List<E> out = Lists.newArrayList();
            for (int i = 0; i < length - raw.size(); i++) {
                // 填充指定元素
                out.add(e);
            }
            // 填充完之后，将原始列表整体加入
            out.addAll(raw);
            return out;
        }
        // 原始列表长度超过指定长度
        if (raw.size() > length) {
            return raw.subList(raw.size() - length, raw.size());
        }
        return raw;
    }

    /**
     * 判断ip是否在指定的网段
     * 运用位运算
     *
     * @param ip
     * @param cidr 网段 如 10.0.40.0/24
     * @return
     */
    public static boolean isInRange(String ip, String cidr) {
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24) | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        // 掩码长度
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24) | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8) | Integer.parseInt(cidrIps[3]);

        return (ipAddr & mask) == (cidrIpAddr & mask);
    }

    public static List<Long> getLcBehaviorIds(List<String> behaviorIds) {
        if (CollectionUtils.isEmpty(behaviorIds)) {
            return Lists.newArrayList();
        }
        return behaviorIds.stream().map(Long::new).collect(Collectors.toList());
    }

    /**
     * 解析json，失败返回空列表
     *
     * @param json
     * @return
     */
    public static List<String> parseListOrEmpty(String json) {
        List<String> habits = Lists.newArrayList();
        try {
            habits = JsonUtil.toJsonObjectArray(json, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habits;
    }

    /**
     * 计算带宽数值，向上取整除法
     *
     * @param dividend 分子
     * @param divisor  分母
     * @return
     */
    public static Integer divide(Integer dividend, Integer divisor) {

        int ret = dividend / divisor;
        int mod = dividend % divisor;
        // 向上取整
        if (mod != 0) {
            ret++;
        }

        return ret;
    }

    /**
     * 解析tags
     * 去除null
     *
     * @param tagString tag字符串
     * @return
     */
    public static List<String> parseTags(String tagString) {
        // 没有tag
        if (StringUtils.isBlank(tagString)) {
            return Lists.newArrayList();
        }
        // 按,分割
        String[] tags = tagString.split(",");
        List<String> res = Lists.newArrayList();
        for (String tag : tags) {
            // 去掉无效数据
            if (StringUtils.isNotBlank(tag) && !"null".equals(tag)) {
                res.add(tag);
            }
        }
        return res;
    }

    /**
     * 特殊处理返回体 data 为字符串的情形
     * 当指定返回类型为string，同时返回第一个字符不是object或array的开头
     *
     * @param jsonString json
     * @return
     */
    public static <T> String handleStringData(String jsonString, Class<T> resultType) {
        if (String.class.equals(resultType) && !jsonString.startsWith("{")
                && !jsonString.startsWith("[")) {
            return "\"" + jsonString + "\"";
        }
        return jsonString;
    }

}
