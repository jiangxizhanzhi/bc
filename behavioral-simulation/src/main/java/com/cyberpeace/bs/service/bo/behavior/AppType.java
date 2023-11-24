package com.cyberpeace.bs.service.bo.behavior;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.constant.BsClient;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;

import lombok.Data;

@Data
public class AppType {

	private Integer code;

	/**
	 * 客户端类型
	 */
	private String category;

	/**
	 * 具体客户端
	 */
	private List<CodeNameRsp> clients = Lists.newArrayList();

	public AppType(Integer code, String category) {
		this.code = code;
		this.category = category;
	}

	public static List<AppType> getAppTypeList() {
		List<AppType> res = Lists.newArrayList();
		final int[] i = { 1 };
		BsClient.AppMap.forEach((k, v) -> {
			AppType appType = new AppType(i[0], k);
			appType.setClients(v);
			res.add(appType);
			i[0] = i[0] + 1;
		});
		return res;
	}
}
