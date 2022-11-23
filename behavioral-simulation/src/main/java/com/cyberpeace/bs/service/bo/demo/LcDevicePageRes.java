package com.cyberpeace.bs.service.bo.demo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.utils.model.LcListBase;

import lombok.Data;

@Data
public class LcDevicePageRes extends LcListBase implements Serializable {

	private List<LcDevice> records;
}
