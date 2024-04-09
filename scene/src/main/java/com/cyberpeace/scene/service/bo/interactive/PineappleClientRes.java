package com.cyberpeace.scene.service.bo.interactive;

import java.util.List;

import lombok.Data;

@Data
public class PineappleClientRes {

	private List<Client> clients;

	public void reformat() {
		for (Client client : this.clients) {
			if ("*".equals(client.getHost())) {
				client.setHost("iPhone");
			}
		}
	}
}

@Data
class Client {

	private String ip;

	private String host;

	private String ssid;

	private String mac;
}
