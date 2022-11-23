package com.cyberpeace.bs.service.bo.behavior;

import java.util.List;

import com.cyberpeace.bs.service.bo.lc.LcBehaviorRspBo;
import com.cyberpeace.bs.utils.model.LcListBase;

import lombok.Data;

@Data
public class LcBehaviorPageRes extends LcListBase {

	private List<LcBehaviorRspBo> records;
}
