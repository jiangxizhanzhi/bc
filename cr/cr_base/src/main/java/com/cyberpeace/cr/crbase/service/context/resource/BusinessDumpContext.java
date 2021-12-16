package com.cyberpeace.cr.crbase.service.context.resource;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessDumpContext implements Serializable {

    private Integer type;

    private TemplateDumoContext template_data;
}
