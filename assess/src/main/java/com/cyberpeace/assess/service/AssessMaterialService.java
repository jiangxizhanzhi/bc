package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.controller.req.AssessCaseAddReq;
import com.cyberpeace.assess.controller.req.AssessMaterialAddReq;
import com.cyberpeace.assess.controller.res.AssessMaterialExcelDatalRes;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author dell
* @description 针对表【assess_material】的数据库操作Service
* @createDate 2022-11-21 13:55:57
*/
public interface AssessMaterialService extends IService<AssessMaterial> {

    /**
     * 校验材料中附件的格式并上传文件
     * @param attachment
     * @param assessMaterial
     * @return
     */
    AssessMaterial checkAssessMaterial(MultipartFile attachment, AssessMaterial assessMaterial);

    void checkMaterialIds(List<String> materialIds);

    void checkParams(String name, String attachment);

    List<AssessMaterialExcelDatalRes> getExcelData(String attachment);

    void checkNameRepeat(String name, String id);

}
