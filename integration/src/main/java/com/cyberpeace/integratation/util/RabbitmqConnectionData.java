package com.cyberpeace.integratation.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RabbitmqConnectionData implements Serializable {

	private String host;

	private int port;

	private String user;

	private String pwd;

	private String exchangeName;
}
