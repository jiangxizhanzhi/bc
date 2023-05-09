package com.cyberpeace.bs.service.bo.monitor;

import com.cyberpeace.common.util.MathUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 带宽和报文数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class BandwidthAndPacket implements Serializable {

	/**
	 * 实时带宽
	 */
	@ApiModelProperty(value = "实时带宽")
    private List<Integer> bandwidth = Lists.newArrayList();

	/**
	 * 报文数
	 */
	@ApiModelProperty(value = "报文数")
    private List<Integer> packet = Lists.newArrayList();

	/**
	 * 将 byte 转为 kbps
	 * @param byteList
	 */
	public void setBandwidth(List<Integer> byteList) {
		for (Integer integer : byteList) {
			if (integer == 0) {
				this.bandwidth.add(0);
			} else {
				int kbps = MathUtils.roundDivide(integer, 128);
				// 不足 1k 的算 1k
				if (kbps == 0) {
					kbps = 1;
				}
				this.bandwidth.add(kbps);
			}
		}
	}
}
