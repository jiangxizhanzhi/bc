package com.cyberpeace.rtks.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.rtks.service.bo.Difference;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;

public class RtksCommonUtil {

	/**
	 * 比较编辑前后的关联关系
	 * @param oldRel 编辑前原有的relations
	 * @param field 关联字段
	 * @param newList 编辑后前端传入的关联
	 * @param <T> relation
	 * @return Difference
	 */
	public static <T> Difference<T> differ(List<T> oldRel, String field, List<IdWeight> newList) {

		Difference<T> difference = new Difference<>();

		List<IdWeight> added = Lists.newArrayList();
		added.addAll(newList);

		boolean flag;
		int i;
		IdWeight idWeight;
		for (T relation : oldRel) {
			flag = false;
			i = 0;
			// 获取关系表中的关联的id
			String val = ObjUtil.getFieldValueByName(field, relation).toString();
			while (i < added.size()) {
				idWeight = added.get(i);
				if (val.equals(idWeight.getResourceId())) {
					flag = true;
					// 编辑了权重的
					if (!ObjUtil.getFieldValueByName("weight", relation)
							.equals(idWeight.getWeight())) {
						ObjUtil.setCertainFieldValue(relation, "weight", idWeight.getWeight());
						difference.getModified().add(relation);
					}
					// 去掉既存的，剩下的就是新增的
					added.remove(i);
					break;
				} else {
					i++;
				}
			}
			// 没有在新列表中找到，删除
			if (!flag) {
				difference.getDeleted().add(val);
			}
		}

		difference.setAdded(added);

		return difference;
	}

	public static String format6(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.format(formatter);
	}
}
