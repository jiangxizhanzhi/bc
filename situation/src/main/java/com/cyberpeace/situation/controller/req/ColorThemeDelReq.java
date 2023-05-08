package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 删除颜色主题请求
 *
 * @Author xuxz
 * @Date 8/12/2022 上午10:21
 */
@Data
public class ColorThemeDelReq implements Serializable {

	/**
	 * 颜色主题ID列表
	 */
	private List<String> idList;
}
