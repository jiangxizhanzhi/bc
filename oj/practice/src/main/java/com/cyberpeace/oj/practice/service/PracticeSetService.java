package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;

import java.util.List;
import java.util.Map;

public interface PracticeSetService extends IService<PracticeSet> {

    /**
     * 根据编号查询习题集信息
     *
     * @param resourceId
     * @param authContext
     * @return
     */
    AdminPracticeSetBo selectById(String resourceId, AuthContext authContext);

    /**
     * 习题集是否可访问
     *
     * @param resourceId
     * @param authContext
     * @return
     */
    void enableAccess(String resourceId, AuthContext authContext);

    /**
     * 分页查询习题集列表
     *
     * @param page
     * @param practiceType
     * @param pattern
     * @return
     */
    IPage<AdminPracticeSetBo> selectByTypeAndName(Page page, Integer practiceType, String pattern,
                                                  AuthContext authContext);

    /**
     * 习题集更新
     *
     * @param practiceSet
     * @param authContext
     * @return
     */
    Boolean updateByAuth(PracticeSet practiceSet, AuthContext authContext);

    /**
     * 删除前判断是否可删除
     *
     * @param resourceIds
     * @param authContext
     */
    void enableDelete(List<String> resourceIds, AuthContext authContext);

    /**
     * 资管理论习题集数据导入
     *
     * @param path
     * @return
     */
    boolean importRmTheorySet(String path);

    /**
     * 资管实操习题集导入
     *
     * @param path
     * @param envReqs
     * @return
     */
    boolean importRmCtfSet(String path, Map<String, String> envReqs);
}
