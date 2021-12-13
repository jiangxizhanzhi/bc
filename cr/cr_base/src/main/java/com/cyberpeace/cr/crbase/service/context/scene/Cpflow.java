package com.cyberpeace.cr.crbase.service.context.scene;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Cpflow implements Serializable {
    private Map<String, FlowElement> elements;
}
