package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.service.bo.lc.LcVariableRspBo;
import com.cyberpeace.bs.utils.model.LcListBase;

import lombok.Data;

/**
 * 远程变量列表结果
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class LcVariableListRes extends LcListBase implements Serializable {

	/**
	 * 数据条目
	 */
	private List<LcVariableRspBo> records;
}
