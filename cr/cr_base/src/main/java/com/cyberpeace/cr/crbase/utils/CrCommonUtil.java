package com.cyberpeace.cr.crbase.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.cyberpeace.common.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.MissionBase;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.mission.MissionOption;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cr.crbase.service.context.scene.AssetProperty;
import com.cyberpeace.cs.service.context.IdNameType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 一些靶场工具
 */
public class CrCommonUtil {

	private static final String NULL = "null";

	private CrCommonUtil() {}

	/**
	 * 从 roleConfig 读取 任务模板id 列表
	 */
	public static List<String> getMissionTemplateIds(List<CrRoleConfig> roles) {
		List<String> missionTemplateIds = Lists.newArrayList();
		roles.forEach(
				role -> missionTemplateIds.addAll(getMissionTemplateIds(role.getMissionsScheme())));
		return missionTemplateIds;

	}

	/**
	 * 从 missions scheme 读取 任务模板id 列表
	 * @param missionsScheme
	 * @return
	 */
	public static List<String> getMissionTemplateIds(CrRoleConfigMissionsScheme missionsScheme) {
		List<String> missionTemplateIds = Lists.newArrayList();
		missionsScheme.getSchemeData().forEach(stage -> stage.getMissions()
				.forEach(mission -> missionTemplateIds.add(mission.getResourceId())));
		return missionTemplateIds;
	}

	/**
	 * 校验训练 tag
	 * @param tags
	 * @return
	 */
	public static String handleTrainingTag(List<String> tags) {
		CrCommonValidator.validateSize(tags, CrCommonConstants.MIN_TAG_COUNT,
				CrCommonConstants.MAX_TAG_COUNT, CrBusinessErrorCode.INVALID_TAGS_NUM);

		tags.forEach(
				tag -> CrCommonValidator.validateNameLen(tag, CrBusinessErrorCode.ILLEGAL_TAG_LEN));
		CrCommonValidator.checkStringListDuplicate(tags, CrBusinessErrorCode.TAG_DUPLICATE);

		return JsonUtil.arrayToString(tags);
	}

	/**
	 * 原地操作，去掉两个列表中相交的元素
	 * @param l1
	 * @param l2
	 * @param <T>
	 */
	public static <T> void differ2List(List<T> l1, List<T> l2) {
		if (l1 == null || l1.isEmpty() || l2 == null || l2.isEmpty()) {
			return;
		}
		int i = 0;
		while (i < l1.size()) {
			int j = l2.indexOf(l1.get(i));
			if (j > -1) {
				l1.remove(i);
				l2.remove(j);
			} else {
				i++;
			}
		}
	}

	/**
	 * 获取雷达图初始点数列表
	 *
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> getInitPoints() {
		return getZeroList(CrCommonConstants.SKILL_POINTS_SIZE);
	}

	/**
	 * 获取指定长度的填0数组
	 *
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> getZeroList(Integer size) {
		return ListUtil.getRepeatingList(size, 0);
	}

	/**
	 * 技能点数加和
	 *
	 * @param sum    总和
	 * @param addend 加数
	 */
	public static void integratePoints(List<Integer> sum, List<Integer> addend) {
		if (sum.size() != addend.size()) {
			return;
		}
		for (int i = 0; i < sum.size(); i++) {
			sum.set(i, sum.get(i) + addend.get(i));
		}
	}

	/**
	 * 计算雷达图
	 *
	 * @param all 所有
	 * @param get 获取
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> calculateRadar(List<Integer> all, List<Integer> get) {
		List<Integer> res = Lists.newArrayList();
		for (int i = 0; i < all.size(); i++) {
			res.add(MathUtils.roundDivide(get.get(i) * 100, all.get(i)));
		}
		return res;
	}

	/**
	 * 解析选项
	 *
	 * @param optionString 选项字符串
	 * @return {@code List<MissionOption>}
	 */
	public static List<MissionOption> parseOption(String optionString) {
		if (StringUtils.isBlank(optionString) || optionString.equals(NULL)) {
			return Lists.newArrayList();
		}
		List<MissionOption> res = JsonUtil.toJsonObjectArray(optionString, MissionOption.class);
		return res;
	}

	/**
	 * 给多选题的答案排序
	 *
	 * @param answerString 回答字符串
	 * @return {@code String}
	 */
	public static String handleMultiAnswer(String answerString) {
		List<String> characterList = JsonUtil.toJsonObjectArray(answerString, String.class);
		// natural order
		characterList.sort(Comparator.naturalOrder());
		return String.join(",", characterList);
	}

	/**
	 * 获取处理后的答案
	 * 处理多选题的答案
	 *
	 * @param answerString 回答字符串
	 * @param missionType  任务类型
	 * @return {@code String}
	 */
	public static String getHandledAnswer(String answerString, Integer missionType) {
		if (missionType.equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())) {
			return handleMultiAnswer(answerString);
		}
		return answerString;
	}

	/**
	 * 获取操作系统列表
	 * 去重
	 *
	 * @param assetList 资产列表
	 * @return {@code List<IdNameType>}
	 */
	public static List<IdNameType> getOsListFromAssets(List<JSONObject> assetList) {
		Set<String> osTypeSet = Sets.newHashSet();
		List<IdNameType> res = Lists.newArrayList();
		for (JSONObject jsonObject : assetList) {
			String osType = jsonObject.getString(AssetProperty.SYSTEM_TYPE);
			String osName = jsonObject.getString(AssetProperty.SYSTEM_TYPE_NAME);
			if (osTypeSet.contains(osType)) {
				continue;
			}
			IdNameType idNameType = new IdNameType();
			idNameType.setId(osType);
			idNameType.setName(osName);

			osTypeSet.add(osType);
			res.add(idNameType);
		}
		return res;
	}

	/**
	 * 列表通过角色任务配置获取动态任务id
	 *
	 * @param missionSchemeJson 任务计划json
	 * @return {@code List<String>}
	 */
	public static List<String> getDynamicMissionIdsByRoleMissionScheme(String missionSchemeJson) {
		List<String> res = Lists.newArrayList();
		List<CrRoleMissionSchemeStage> stages = JsonUtil.toJsonObjectArray(missionSchemeJson,
				CrRoleMissionSchemeStage.class);
		if (stages.isEmpty()) {
			return res;
		}
		for (CrRoleMissionSchemeStage stage : stages) {
			List<String> dynamicIds = stage.getDynamic();
			if (CollectionUtils.isNotEmpty(dynamicIds)) {
				res.addAll(dynamicIds);
			}
		}
		return res;
	}

	/**
	 * 获取username列表
	 *
	 * @param userIdList  用户id列表
	 * @param userInfoMap 用户信息
	 * @return {@code String}
	 */
	public static List<String> getUserNamesByUserIds(List<String> userIdList,
			Map<String, UserInfo> userInfoMap) {
		List<String> usernames = Lists.newArrayList();
		for (String userId : userIdList) {
			if (userInfoMap.containsKey(userId)) {
				usernames.add(userInfoMap.get(userId).getUserName());
			}
		}
		return usernames;
	}

	/**
	 * 获取用户数据列表
	 *
	 * @param userIdList  用户id列表
	 * @param userInfoMap 用户信息
	 * @return {@code List<UserData>}
	 */
	public static List<UserData> getUserDataList(List<String> userIdList,
			Map<String, UserInfo> userInfoMap) {
		List<UserData> userDataList = Lists.newArrayList();
		for (String userId : userIdList) {
			if (userInfoMap.containsKey(userId)) {
				UserInfo userInfo = userInfoMap.get(userId);
				UserData userData = new UserData();
				userData.setResourceId(userInfo.getUserId());
				userData.setRepName(userInfo.getName());
				userData.setLogo(userInfo.getAvatarUrl());
				userDataList.add(userData);
			}
		}
		return userDataList;
	}

	/**
	 * 控制最终存入数据库的 role_config 和 missions_scheme json 只包含哪些字段
	 */
	public static <T> String roleConfig2DbJson(T roleConfig) {
		SimplePropertyPreFilter missionFilter = new SimplePropertyPreFilter(
				CrRoleConfigMission.class, "resource_id");
		SimplePropertyPreFilter schemeFilter = new SimplePropertyPreFilter(
				CrRoleConfigSchemeStage.class, "stage", "missions");
		SimplePropertyPreFilter roleFilter = new SimplePropertyPreFilter(CrRoleConfig.class, "name",
				"asset_nodes", "visible_nodes", "missions_scheme");
		SerializeConfig config = new SerializeConfig();
		config.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
		return JSON.toJSONString(roleConfig, config,
				new SimplePropertyPreFilter[] { missionFilter, schemeFilter, roleFilter });
	}

	/**
	 * c1是否包含c2的所有元素
	 *
	 * @param c1 c1
	 * @param c2 c2
	 * @return boolean
	 */
	public static <T> boolean contain(Collection<T> c1, Collection<T> c2) {
		for (T t : c2) {
			if (!c1.contains(t)) {
				return false;
			}
		}
		return true;
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
			raw = org.apache.commons.compress.utils.Lists.newArrayList();
		}
		// 原始列表元素个数不足
		if (raw.size() < length) {
			List<E> out = org.apache.commons.compress.utils.Lists.newArrayList();
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
}
