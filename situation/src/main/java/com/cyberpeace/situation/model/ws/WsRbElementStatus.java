package com.cyberpeace.situation.model.ws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsRbElementStatus implements Serializable {

	private String elementId;

	private Integer status;
}
