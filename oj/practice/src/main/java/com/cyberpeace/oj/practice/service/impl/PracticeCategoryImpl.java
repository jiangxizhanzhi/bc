package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.PracticeCategoryParent;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class PracticeCategoryImpl extends ServiceImpl<PracticeCategoryMapper, PracticeCategory> implements PracticeCategoryService {

    @Autowired
    PracticeCategoryMapper categoryMapper;

    @Override
    public PracticeCategory get(String resourceId) {
        PracticeCategory root = getById(resourceId);
        if (root == null) {
            throw new ResourceNotFoundException(resourceId);
        }
        root.setChildren(getChildren(root));
        return root;
    }

    @Override
    public ListRsp<PracticeCategory> search(PracticeListReq queryParams) {
        IPage<PracticeCategory> result = categoryMapper.getList(
                queryParams.toPageParam(),
                queryParams.getPattern(),
                queryParams.getPracticeType()
        );
        // TODO 列表是否返回子级, 循环查询
        for (PracticeCategory instance : result.getRecords()) {
            instance.setChildren(getChildren(instance));
        }
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

    /**
     * 获取当前节点所有父级节点
     * @param categoryId 练习分类resourceId
     * @return PracticeCategoryParent
     */
    @Override
    public PracticeCategoryParent nodeInfo(String categoryId) {
        PracticeCategory childNode = getById(categoryId);

        String childCode = childNode.getCode();
        String[] codes = childCode.split("\\|");
        List<String> categoryIds = Lists.newArrayList();
        StringBuilder currentCode = new StringBuilder();
        for (int i = 0; i < codes.length; i++) {
            if (i == 0) {
                currentCode.append(codes[i]);
            } else {
                currentCode.append("|").append(codes[i]);
            }
            categoryIds.add(currentCode.toString());
        }
        List<PracticeCategoryParent> categoryLs = categoryMapper.batchGetByCode(categoryIds);
        // 根据`code`长度倒序
        categoryLs.sort(new Comparator<PracticeCategoryParent>() {
            @Override
            public int compare(PracticeCategoryParent o1, PracticeCategoryParent o2) {
                return Integer.compare(o2.getCode().length(), o1.getCode().length());
            }
        });

        return getPrevLevel(categoryLs);
    }

    private PracticeCategoryParent getPrevLevel(List<PracticeCategoryParent> fullNode) {
        PracticeCategoryParent node = fullNode.get(0);
        fullNode.remove(node);
        if (fullNode.size() >= 1) {
            node.setParent(getPrevLevel(fullNode));
        }
        return node;
    }

}
