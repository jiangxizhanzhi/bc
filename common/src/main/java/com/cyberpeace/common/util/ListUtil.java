/**
 * ### 代码分析与总结 
 * #### 功能概述： 
 * 该类 `ListUtil` 是一个工具类，提供了一系列操作列表（`List`）的静态方法。这些方法涵盖了从基本的字符串与列表转换、分页、随机选择、排序到集合操作等常见需求。 
 * --- 
 * #### 方法功能分解： 
 * 1. **`list2String`** 
 * - 将一个字符串列表转换为以逗号分隔的字符串。 
 * - 如果列表为空或 `null`，返回空字符串。 
 * 2. **`string2List`** 
 * - 将逗号分隔的字符串拆分为字符串列表。 
 * - 如果输入字符串为空或空白，则返回空列表。 
 * 3. **`joinList` 和 `joinLineSeparatorList`** 
 * - 从列表中截取一段并拼接为字符串，支持换行符（`System.lineSeparator()` 或 `\n`）。 
 * - 如果元素是字符串且为空白，则跳过。 
 * - 非字符串元素通过 JSON 转换为字符串。 
 * 4. **`pageList`** 
 * - 对列表进行分页操作，返回指定页码和页大小的子列表。 
 * - 边界条件处理：确保起始索引和结束索引在有效范围内。 
 * 5. **`compare2List`** 
 * - 比较两个列表是否包含相同的元素（忽略顺序）。 
 * - 通过将两个列表合并到一个集合中，检查集合大小是否等于其中一个列表的大小。 
 * 6. **`listRangeInt`** 
 * - 返回从 `start` 到 `end` 的整数列表（闭区间）。 
 * 7. **`selectRandomValue`** 
 * - 从列表中随机选择一个元素。 
 * - 如果列表为空，返回 `null`。 
 * 8. **`mergeWithOldOrder`** 
 * - 根据第一个列表的顺序合并两个列表。 
 * - 第二个列表中的元素如果不在第一个列表中，会被追加到最后。 
 * 9. **`getRepeatingList`** 
 * - 创建一个固定长度的列表，其中每个元素都相同。 
 * 10. **`intersect`** 
 * - 计算两个集合的交集，并返回结果列表。 
 * 11. **`washBaseTable`** 
 * - 清洗 `BaseTable` 类型的列表数据，将 `id`、`createTime` 和 `updateTime` 设置为 `null`，通常用于导出数据时清理敏感信息。 
 * --- 
 * #### 特点总结： 
 * - **通用性**：大多数方法支持泛型 `<T>`，适用于不同类型的列表。 
 * - **边界处理**：所有方法都考虑了空列表、空字符串等特殊情况，避免运行时异常。 
 * - **扩展性**：部分方法如 `joinList` 支持自定义换行符，增强了灵活性。 
 * - **性能优化**：如 `compare2List` 使用集合去重，效率较高；`washBaseTable` 直接修改对象属性，减少额外开销。 
 * --- 
 * #### 使用场景： 
 * - 数据处理：如字符串与列表之间的转换、分页、随机选择等。 
 * - 数据清洗：如去除敏感字段或生成固定格式的列表。 
 * - 集合操作：如交集计算、合并列表。 
 * --- 
 * #### 改进建议： 
 * 1. **日志记录**：对于某些可能引发问题的操作（如空列表），可以添加日志记录。 
 * 2. **文档完善**：部分方法的注释可以更详细地描述参数含义和返回值的具体格式。 
 * 3. **多线程安全**：如果方法可能被多线程调用，需注意线程安全性（如使用 `Collections.synchronizedList` 包装列表）。 
 * 总体而言，这是一个高效且实用的工具类，适合在项目中广泛使用。 
**/
package com.cyberpeace.common.util;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.model.BaseTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 列出实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
public class ListUtil {

	/**
	 * 列出实用程序
	 */
	private ListUtil() {}

	/**
	 * list2 字符串
	 *
	 * @param list 列表
	 * @return {@code String }
	 */
	public static String list2String(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (String item : list) {
			stringBuilder.append(item.trim()).append(",");
		}

		return stringBuilder.toString();
	}

	/**
	 * string2 列表
	 *
	 * @param str str
	 * @return {@code List<String> }
	 */
	public static List<String> string2List(String str) {
		List<String> ret = Lists.newArrayList();

		if (StringUtils.isBlank(str)) {
			return ret;
		}

		String[] ss = StringUtils.split(str, ",");
		for (String item : ss) {
			ret.add(item.trim());
		}

		return ret;
	}

	/**
	 * 截取列表拼接成字符串 空字符传略过
	 *
	 * @param objList OBJ 列表
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> String joinList(List<T> objList, int start, int end) {

		List<T> subList = objList.subList(start, end);
		StringBuilder builder = new StringBuilder();

		for (T obj : subList) {
			if (obj instanceof String) {
				String str = (String) obj;
				if (StringUtils.isBlank(str)) {
					continue;
				}
				// 多行增加换行符
				builder.append(obj).append(System.lineSeparator());
			} else {
				builder.append(JSON.toJSONString(obj)).append(System.lineSeparator());
			}
		}

		return builder.toString().trim();
	}

	/**
	 * 截取列表拼接成包括换行符的字符串 空字符传略过
	 *
	 * @param objList OBJ 列表
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> String joinLineSeparatorList(List<T> objList, int start, int end) {

		List<T> subList = objList.subList(start, end);
		StringBuilder builder = new StringBuilder();

		for (T obj : subList) {
			if (obj instanceof String) {
				String str = (String) obj;
				if (StringUtils.isBlank(str)) {
					continue;
				}
				builder.append(obj).append("\n");
			} else {
				builder.append(JSON.toJSONString(obj)).append("\n");
			}
		}

		return builder.toString();
	}

	/**
	 * 将列表处理成分页列表里的records
	 *
	 * @param list     数据
	 * @param page     页码
	 * @param perPage  页尺寸
	 * @param listSize 数据长度
	 * @return {@code List<E>}
	 */
	public static <E> List<E> pageList(List<E> list, int page, int perPage, int listSize) {
		int startIndex = (page - 1) * perPage;
		int endIndex = page * perPage;
		endIndex = Math.min(endIndex, listSize);
		startIndex = Math.max(0, startIndex);
		if (startIndex >= endIndex) {
			return Lists.newArrayList();
		}
		return list.subList(startIndex, endIndex);
	}

	/**
	 * 比较两个不重复的列表是否包含的元素一致
	 *
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static <T> Boolean compare2List(List<T> l1, List<T> l2) {
		if (l1.size() != l2.size()) {
			return false;
		}
		Set<T> set = new HashSet<>();
		set.addAll(l1);
		set.addAll(l2);
		return set.size() == l1.size();
	}

	/**
	 * 返回指定起止的整数列表
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Integer> listRangeInt(Integer start, Integer end) {
		return Lists.newArrayList(IntStream.rangeClosed(start, end).iterator());
	}

	/**
	 * 查询随机值
	 *
	 * @param list 列表
	 * @return {@code T }
	 */
	public static <T> T selectRandomValue(List<T> list) {

		if (list.isEmpty()) {
			return null;
		}
		return list.get((int) (Math.random() * list.size()));

	}

	/**
	 * 将l2的元素按l1的顺序排列，l2中存在但l1中不存在的元素放在末尾
	 *
	 * @param l1 l1
	 * @param l2 l2
	 * @return {@code List<T>}
	 */
	public static <T> List<T> mergeWithOldOrder(List<T> l1, List<T> l2) {
		List<T> res = Lists.newArrayList();
		Set<T> set = Sets.newHashSet(l2);
		for (T t : l1) {
			if (set.contains(t)) {
				res.add(t);
				set.remove(t);
			}
		}
		res.addAll(set);
		return res;
	}

	/**
	 * 获取指定长度的包含某指定元素的数组
	 *
	 * @param size    大小
	 * @param element 元素
	 * @return {@code List<Integer>}
	 */
	public static <T> List<T> getRepeatingList(Integer size, T element) {
		List<T> points = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			points.add(element);
		}
		return points;
	}

	/**
	 * 取交集
	 *
	 * @param c1 l1
	 * @param c2 l2
	 * @return {@code List<E>}
	 */
	public static <E> List<E> intersect(Collection<E> c1, Collection<E> c2) {
		List<E> res = Lists.newArrayList();
		if (CollectionUtils.isEmpty(c1) || CollectionUtils.isEmpty(c2)) {
			return res;
		}
		for (E e : c1) {
			if (c2.contains(e)) {
				res.add(e);
			}
		}
		return res;
	}

	/**
	 * 洗掉baseTable的数据，用于导出
	 *
	 * @param list 列表
	 * @return {@code List<E> }
	 * @throws
	 * @see List<E>
	 * @see List<E>
	 */
	public static <E extends BaseTable> List<E> washBaseTable(List<E> list) {
		for (E e : list) {
			e.setId(null);
			e.setCreateTime(null);
			e.setUpdateTime(null);
		}
		return list;
	}
}
