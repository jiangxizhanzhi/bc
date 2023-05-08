package com.cyberpeace.bs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserType;
import com.cyberpeace.bs.service.bo.user.UserTypeBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户类型Mapper
 *
 * @author SN
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface UserTypeMapper extends BaseMapper<UserType> {

    /**
     * 获取用户类型分页列表
     *
     * @param page   分页参数
     * @param search 模糊搜索参数
     * @return 用户类型分页列表
     */
    IPage<UserTypeBo> page(@Param("page") Page page, @Param("search") String search);

    /**
     * 检查名称是否已存在
     *
     * @param name   名称
     * @param selfId 要编辑的类型id（可选）
     * @return 是否存在
     */
    boolean existByName(@Param("name") String name, @Param("selfId") String selfId);
}
