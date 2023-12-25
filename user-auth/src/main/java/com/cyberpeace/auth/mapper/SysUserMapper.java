package com.cyberpeace.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.auth.domain.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author dell
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-11-29 17:27:18
* @Entity com.cyberpeace.domain.SysUser
*/

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username = #{username}")
    SysUser selectByUsername(@Param("username") String username);

    boolean existByUsername(@Param("username") String username);

    IPage<SysUser> pageUser(@Param("page") Page page, @Param("pattern") String pattern, @Param("enabled") Integer enabled);

    @Delete("<script>delete from sys_user where id in <foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    int deleteByIds(@Param("ids") List<Integer> ids);
}




