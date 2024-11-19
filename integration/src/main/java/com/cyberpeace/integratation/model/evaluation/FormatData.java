package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 设置数据格式
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FormatData implements Serializable {

	/**
	 * 数据
	 *
	 * @see List<String>
	 */
	List<String> data;
}
