package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.utils.model.LcListBase;

import lombok.Data;

/**
 * 远程设备分页结果
 *
 * @author SN
 * @date 2023/06/19
 */
@Data
public class LcDevicePageRes extends LcListBase implements Serializable {

	/**
	 * 记录
	 */
	private List<LcDevice> records;
}
