package com.cyberpeace.common.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.ListRsp;
import com.google.common.collect.Lists;

/**
 * 页面工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class PageUtil {

	/**
	 * 页面工具
	 */
	private PageUtil() {}

	/**
	 * 变换结果列表
	 *
	 * @param page 页面
	 * @param func 函数
	 * @return {@link ListRsp}
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
	 * 得到列表结果
	 *
	 * @param data 数据
	 * @return {@link ListRsp}<{@link K}>
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
	 * 得到列表结果
	 *
	 * @param data  数据
	 * @param page  页面
	 * @param total 总
	 * @return {@link ListRsp}<{@link K}>
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
	 * 得到零结果
	 *
	 * @return {@link ListRsp}
	 */
	public static ListRsp getNullResult() {
		return ListRsp.builder().list(Lists.newArrayList()).lastPage(CommonConstant.ZERO)
				.currentPage(CommonConstant.ONE).total(CommonConstant.ZERO).build();
	}

	/**
	 * 设置页面顺序
	 *
	 * @param page      页面
	 * @param orderName 订单名称
	 * @param asc       asc
	 */
	public static void setPageOrder(Page page, String orderName, boolean asc) {
		OrderItem orderItem = new OrderItem(orderName, asc);
		if (CollectionUtils.isEmpty(page.getOrders())) {
			page.setOrders(Lists.newArrayList(orderItem));
		} else {
			page.getOrders().add(orderItem);
		}

	}

}
