package com.cyberpeace.common.model.ws;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yinqingrong
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessageContext<I extends WsMessageIdentifier, M> implements Serializable {

	private I messageIdentifier;

	private M data;

}
