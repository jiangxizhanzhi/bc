package com.cyberpeace.cr.crbase.service;


import java.io.IOException;
import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

public interface ExportTrainMDService {

    /**
     * 选择类型，导出训练资管
     * @param resourceIds 模板id列表
     * @param path 路径
     * @param type 类型
     * @throws IOException
     */
    void chooseTypeAndExportTrain (List<String> resourceIds, String path, Integer type, String sceneNumber, Boolean disableImageExport) throws IOException;

    /**
     * 实际导出资管
     * @param businessTemplateList 业务模板列表
     * @param path 路径
     */
    void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path, String sceneNumber, Boolean disableImageExport);
}
