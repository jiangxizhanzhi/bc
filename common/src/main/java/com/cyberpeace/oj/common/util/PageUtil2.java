package com.cyberpeace.oj.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;

public class PageUtil2 {

	/**
	 * @param <T> 数据库模型
	 * @param <U> 输出模型 
	 * @param page 数据库查询结果的IPage对象
	 * @param func 数据库模型转输出模型方法
	 * @return 输出列表查询响应
	 * */
	public static <T, U> BaseRsp<ListRsp<U>> getListResult(IPage<T> page, Function<T, U> func) {
		List<U> outputs = new ArrayList<>();
		for (T record : page.getRecords()) {
			outputs.add(func.apply(record));
		}

		ListRsp<U> listRsp = new ListRsp<>();
		listRsp.setList(outputs);
		listRsp.setTotal(page.getTotal());

		return new BaseRsp<>(listRsp);
	}
}
