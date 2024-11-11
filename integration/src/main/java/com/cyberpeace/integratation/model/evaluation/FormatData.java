package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 设置数据格式
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class FormatData implements Serializable {

	/**
	 * 数据
	 */
	List<String> data;
}
