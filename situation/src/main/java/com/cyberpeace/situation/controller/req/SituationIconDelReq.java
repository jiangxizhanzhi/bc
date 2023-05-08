package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 图标删除请求
 *
 * @Author xuxz
 * @Date 15/12/2022 上午11:20
 */
@Data
public class SituationIconDelReq implements Serializable {

	/**
	 * 图标id列表
	 */
	private List<String> idList;
}
