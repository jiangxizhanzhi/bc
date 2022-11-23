package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.service.bo.lc.LcVariableRspBo;
import com.cyberpeace.bs.utils.model.LcListBase;

import lombok.Data;

@Data
public class LcVariableListRes extends LcListBase implements Serializable {

	private List<LcVariableRspBo> records;
}
