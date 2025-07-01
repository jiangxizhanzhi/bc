package com.cyberpeace.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.ListRsp;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 分页util
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个用于处理分页和数据转换的工具类 `PageUtil`。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **分页结果转换**： 
 * - 提供了方法将分页结果（`IPage`）转换为自定义的 `ListRsp` 对象。 
 * - 支持通过函数 `Function<T, U>` 对分页数据进行转换。 
 * 2. **获取分页结果**： 
 * - 提供了多种方式获取分页结果，包括直接从 `IPage` 对象获取、从列表和分页参数中生成分页结果等。 
 * 3. **设置分页顺序**： 
 * - 可以根据指定的字段名和排序方式（升序或降序）设置分页的排序规则。 
 * 4. **分页工具方法**： 
 * - 提供了将普通列表按指定页码和每页大小进行分页的方法。 
 * ### 关键方法详解 
 * - **`transformListResult`**: 
 * - 功能：将分页结果中的记录通过给定的函数转换为目标类型，并返回包含转换后数据和总记录数的 `ListRsp` 对象。 
 * - **`getListResult`**: 
 * - 功能：直接从 `IPage` 对象提取数据并封装成 `ListRsp` 对象。 
 * - 提供了多个重载版本，支持不同的输入参数组合。 
 * - **`setPageOrder`**: 
 * - 功能：设置分页对象的排序规则，支持动态添加排序字段。 
 * - **`pageList`**: 
 * - 功能：将普通列表按照指定的页码和每页大小进行分页，并返回分页后的结果。 
 * ### 注意事项 
 * - 依赖外部库：代码中使用了 Apache Commons 和 Guava 等库，例如 `CollectionUtils`、`StringUtils` 和 `Lists`。 
 * - 错误处理：在某些方法中进行了基本的空值检查和异常捕获，但可能需要进一步增强以提高健壮性。 
 * - 排序逻辑：在 `getListResult` 方法中，排序部分较为复杂，涉及到反射操作，需确保字段存在且可访问。 
 * 总体而言，这个工具类提供了丰富的分页和数据处理功能，适用于需要频繁进行分页操作的场景。 
**/
public class PageUtil {

	/**
	 * 分页util
	 */
	private PageUtil() {}

	/**
	 * 转换列表结果
	 *
	 * @param page 分页
	 * @param func 函数
	 * @return {@code ListRsp }
	 */
	public static <T, U> ListRsp transformListResult(IPage<T> page, Function<T, U> func) {
		List<U> outputs = new ArrayList<>();
		for (T record : page.getRecords()) {
			outputs.add(func.apply(record));
		}

		ListRsp ret = new ListRsp();

		ret.setList(outputs);
		ret.setTotal(page.getTotal());

		return ret;
	}

	/**
	 * 获取列表结果
	 *
	 * @param data 数据
	 * @return {@code ListRsp<K> }
	 */
	public static <K> ListRsp<K> getListResult(IPage data) {

		if (null == data) {
			return getNullResult();
		}

		ListRsp ret = new ListRsp();

		ret.setList(data.getRecords());
		ret.setTotal(data.getTotal());
		ret.setLastPage(data.getPages());
		ret.setCurrentPage(data.getCurrent());

		return ret;
	}

	/**
	 * 获取列表结果
	 *
	 * @param data  数据
	 * @param page  分页
	 * @param total 总
	 * @return {@code ListRsp<K> }
	 */
	public static <K> ListRsp<K> getListResult(List<K> data, Page page, long total) {

		// 空数据直接返回
		if (CollectionUtils.isEmpty(data)) {
			return PageUtil.getNullResult();
		}

		for (Object order : page.getOrders()) {
			// 获取对应字段
			String column = ((OrderItem) order).getColumn();
			if (StringUtils.isBlank(column)) {
				continue;
			}

			// 获取实体数据
			K k = data.get(CommonConstant.ZERO);

			// 判断是否存在该字段
			try {
				ObjUtil.getCertainFieldValue(k, column);
			} catch (RuntimeException e) {
				continue;
			}

			// 对数据排序
			if (((OrderItem) order).isAsc()) {
				data.sort(Comparator.comparing(classroomMonitorBo -> (ObjUtil
						.getFieldValueByName(column, classroomMonitorBo) != null
								? ObjUtil.getFieldValueByName(column, classroomMonitorBo)
								: "").toString()));

			} else {
				data.sort(Comparator.comparing(classroomMonitorBo -> (ObjUtil
						.getFieldValueByName(column, classroomMonitorBo) != null
								? ObjUtil.getFieldValueByName(column, classroomMonitorBo)
								: "").toString())
						.reversed());
			}
		}

		long remainData = total % page.getSize();
		long totalPage = remainData == CommonConstant.ZERO.longValue() ? (total / page.getSize())
				: (total / page.getSize()) + CommonConstant.ONE;
		ListRsp<K> listRsp = new ListRsp<>();

		// 如果只传一页数据 默认则作为数据体直接返回
		if (data.size() > page.getSize()) {
			long start = (page.getCurrent() - CommonConstant.ONE) * page.getSize();
			long end = page.getCurrent() == totalPage ? total : start + page.getSize();
			List<K> subList = data.subList(new Long(start).intValue(), new Long(end).intValue());
			listRsp.setList(subList);
		} else {
			listRsp.setList(data);
		}

		listRsp.setCurrentPage(page.getCurrent());
		listRsp.setLastPage(totalPage);
		listRsp.setTotal(total);

		return listRsp;
	}

	/**
	 * 获取零结果
	 *
	 * @return {@code ListRsp }
	 */
	public static ListRsp getNullResult() {
		return ListRsp.builder().list(Lists.newArrayList()).lastPage(CommonConstant.ZERO)
				.currentPage(CommonConstant.ONE).total(CommonConstant.ZERO).build();
	}

	/**
	 * 设置分页次序
	 *
	 * @param page      分页
	 * @param orderName 次序名称
	 * @param asc       ASC
	 */
	public static void setPageOrder(Page page, String orderName, boolean asc) {
		OrderItem orderItem = new OrderItem(orderName, asc);
		if (CollectionUtils.isEmpty(page.getOrders())) {
			page.setOrders(Lists.newArrayList(orderItem));
		} else {
			page.getOrders().add(orderItem);
		}
		page.getOrders().add(new OrderItem(OrderNameConstant.ID, false));
	}

	/**
	 * 将列表结果分页
	 *
	 * @param list    列表
	 * @param page    页码
	 * @param perPage 页尺寸
	 * @return {@code ListRsp<T>}
	 */
	public static <T> ListRsp<T> pageList(List<T> list, Integer page, Integer perPage) {
		IPage<T> resPage = new Page<>(page, perPage, list.size());
		resPage.setRecords(ListUtil.pageList(list, page, perPage, (int) resPage.getTotal()));
		return PageUtil.getListResult(resPage);
	}
}

