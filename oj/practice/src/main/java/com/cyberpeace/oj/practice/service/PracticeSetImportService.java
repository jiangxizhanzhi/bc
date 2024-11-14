package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;

import java.util.List;
import java.util.Map;

/**
 * 练习设置导入Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface PracticeSetImportService extends IService<PracticeSet> {

    /**
     * 资管实操习题集导入
     *
     * @param path    习题集路径
     * @param envReqs key=习题名称 value=拓扑编号
     * @return
     */
    List<String> importRmCtfSet(String path, Map<String, String> envReqs);

    /**
     * 资管理论习题集数据导入
     *
     * @param path
     * @return
     */
    boolean importRmTheorySet(String path);

}
