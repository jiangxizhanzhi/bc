package com.cyberpeace.integratation.model.situation.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.NodeWeakContext;

import lombok.Data;

@Data
public class LgNodeWeakRsp extends LgPageResBase implements Serializable {

	private List<NodeWeakContext> list;
}
