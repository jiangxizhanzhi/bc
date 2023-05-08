package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 数据源记录删除请求
 *
 * @Author xuxz
 * @Date 3/11/2022 下午5:26
 */
@Data
public class DataSourceRecordDelReq implements Serializable {

	/**
	 * 数据源记录id列表
	 */
	private List<String> dataResourceIdList;
}
