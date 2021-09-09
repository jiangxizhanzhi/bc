package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PracticeCategoryImpl extends ServiceImpl<PracticeCategoryMapper, PracticeCategory> implements PracticeCategoryService {

    @Autowired
    PracticeCategoryMapper categoryMapper;

    @Override
    public ListRsp<PracticeCategory> search(PracticeListReq queryParams) {
        IPage<PracticeCategory> result = categoryMapper.getList(
                queryParams.toPageParam(),
                queryParams.getPattern(),
                queryParams.getPracticeType()
        );
        return PageUtil.getListResult(result);
    }

    @Override
    public Integer batchDelete(List<String> resourceIds) {
        return categoryMapper.batchDelete(resourceIds);
    }

    /**
     * 查询节点下所有子节点并进行关联
     *
     * @param root 分类根节点
     * @return 收集完成后的root.children数据
     */
    @Override
    public List<PracticeCategory> getChildren(PracticeCategory root) {
        List<PracticeCategory> childrenByRoot = categoryMapper.containsByCode(root.getCode());
        return getNextLevel(root, childrenByRoot);
    }

    /**
     * 迭代计算每个节点的子节点
     *
     * @param parentNode 待关联父节点
     * @param fullNode   当前`code`全量节点
     * @return parentNode所有子节点
     */
    private List<PracticeCategory> getNextLevel(PracticeCategory parentNode, List<PracticeCategory> fullNode) {
        List<PracticeCategory> children = Lists.newArrayList();

        List<PracticeCategory> quoteByFull = Lists.newArrayList();
        quoteByFull.addAll(fullNode);

        for (PracticeCategory item : quoteByFull) {
            // TODO 临时处理, 反转字符串后进行分割. 16表示当前`code`节点长度, 1表示分割符`|`
            String reverseParentCode = StringUtils.reverse(item.getCode()).substring(16 + 1);
            if (parentNode.getCode().equals(StringUtils.reverse(reverseParentCode))) {
                children.add(item);
                fullNode.remove(item);
            }
        }

        // 迭代关联子节点
        if (children.size() >= 1) {
            for (PracticeCategory item : children) {
                item.setChildren(getNextLevel(item, fullNode));
            }
        }

        return children;
    }
}
