package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import lombok.Data;

@Data
public class ExportTemplateContext implements Serializable {

    private DashboardTemplate dashboardTemplate;

    private List<DashboardComponents> dashboardComponentsList;
}
