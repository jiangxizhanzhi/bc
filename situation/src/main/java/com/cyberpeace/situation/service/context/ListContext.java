package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ListContext implements Serializable {

	private List<String> context;

}
