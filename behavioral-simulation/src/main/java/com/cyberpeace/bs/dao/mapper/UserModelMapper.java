package com.cyberpeace.bs.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserModel;

/**
 * 用户模型Mapper
 *
 * @author SN
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface UserModelMapper extends BaseMapper<UserModel> {

    /**
     * 用户行为分页查询
     *
     * @param page       分页器
     * @param userTypeId 用户类型id
     * @param pattern    模糊匹配关键字
     * @param accessOs   访问操作系统
     * @return {@code IPage<UserModel>}
     */
    IPage<UserModel> pageUserBehaviors(@Param("pageUserBehaviors") Page page,
                                       @Param("userTypeId") String userTypeId, @Param("pattern") String pattern,
                                       @Param("accessOs") Integer accessOs);

    /**
     * 通过id批量查询用户模型
     * 根据id倒序
     *
     * @param userModelIds 用户模型ids
     * @return {@code List<UserModel>}
     */
    List<UserModel>
    selectBatchIdsOrderByIdDesc(@Param("userModelIds") Collection<String> userModelIds);

	/**
	 * 检查是否重名
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean existByName(@Param("name") String name, @Param("resourceId") String resourceId);
}