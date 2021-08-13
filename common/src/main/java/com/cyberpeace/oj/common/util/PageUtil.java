package com.cyberpeace.oj.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.oj.common.model.ListRsp;

public class PageUtil {

	public static ListRsp getListResult(IPage data) {
		ListRsp ret = new ListRsp();

		ret.setList(data.getRecords());
		ret.setTotal(data.getTotal());

		return ret;
	}
}
