package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.EventType;

import lombok.Data;

@Data
public class EventTypeContext extends EventType implements Serializable {

	private String parentType;

}
