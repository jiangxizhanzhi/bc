package com.cyberpeace.evaluate.dao.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class FormalVariableItem implements Serializable {

	Integer value;

	Date date;
}
