package com.cyberpeace.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.ListRsp;
import com.google.common.collect.Lists;

public class PageUtil {

	private PageUtil() {}

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

	public static ListRsp getNullResult() {
		return ListRsp.builder().list(Lists.newArrayList()).lastPage(CommonConstant.ZERO)
				.currentPage(CommonConstant.ZERO).total(CommonConstant.ZERO).build();
	}

}
