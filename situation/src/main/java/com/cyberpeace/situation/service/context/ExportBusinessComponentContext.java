package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.dao.entity.BusinessComponent;
import com.cyberpeace.situation.dao.entity.BusinessComponentsSetting;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;

import lombok.Data;

/**
 * 导出业务组件上下文
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
public class ExportBusinessComponentContext implements Serializable {

    private BusinessComponent businessComponent;

    private List<BusinessComponentsSetting> businessComponentsSettingList;
}
