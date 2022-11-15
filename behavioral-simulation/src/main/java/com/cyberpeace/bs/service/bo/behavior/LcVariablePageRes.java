package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.utils.LcListBase;

import lombok.Data;

@Data
public class LcVariablePageRes extends LcListBase implements Serializable {

	private List<LcVariableRspBo> records;
}
