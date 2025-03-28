package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;

import java.util.List;
import java.util.Map;

/**
 * 实践设置导入Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface PracticeSetImportService extends IService<PracticeSet> {

    /**
     * 资管实操习题集导入
     *
     * @param path    习题集路径
     * @param envReqs key=习题名称 value=拓扑编号
     * @param scriptFileMap
     * @return
     * @throws
     * @see String
     * @see Map<String, String>
     * @see List<String>
     */
    List<String> importRmCtfSet(String path, Map<String, String> envReqs, Map<String, String> scriptFileMap);

    /**
     * 资管理论习题集数据导入
     *
     * @param path
     * @return
     * @throws
     * @see String
     * @see boolean
     */
    boolean importRmTheorySet(String path);

}
