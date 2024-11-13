package com.cyberpeace.cr.crbase.model.situation;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 行业态势
 *
 * @author SN
 * @date 2023/09/16
 */
@Data
public class IndustrySituationWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 业务id
	 */
	private String business_id;
}
