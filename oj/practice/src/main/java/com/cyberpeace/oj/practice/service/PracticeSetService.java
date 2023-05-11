package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;

import java.io.File;
import java.util.List;

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
     * 获取可访问习题集
     *
     * @param authContext
     * @param setId
     * @return
     */
    List<String> getSetIds(AuthContext authContext, String setId);

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
     * 导出习题集生成压缩文件
     *
     * @param practiceSetIds 习题集编号
     * @param authContext
     * @return
     */
    File dumpPracticeSet(List<String> practiceSetIds, AuthContext authContext);

    /**
     * 习题集数据加载
     *
     * @param realPath
     * @param userId
     * @return
     */
    Boolean loadPracticeSet(String realPath, String userId);

    /**
     * 检查习题集是否重复
     *
     * @return
     */
    List<String> duplicatePracticeSetInspect(List<String> practiceSets);

    /**
     * 实操习题数据导出
     *
     * @param practiceSetId
     * @param practiceId
     * @return 导出的文件路径
     */
    String exportPracticeSet(String practiceSetId, String practiceId, Boolean newPath, Boolean disableImageExport);

    void clearUserAuth(String user);

    void clearOrgAuth(String org);

    MdTheoryPractice exportMdTheoryPractice(String path, List<PracticeTheory> theories);

    List<AdminPracticeSetBo> getMorePracticeSet(AuthContext authContext, String practiceSetId, Integer setType);

}
