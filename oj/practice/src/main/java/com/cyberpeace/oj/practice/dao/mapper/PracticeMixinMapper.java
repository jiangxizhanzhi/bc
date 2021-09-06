package com.cyberpeace.oj.practice.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeMixinMapper {
    Integer batchPublic(
            @Param("table") String tableName,
            @Param("resource_ids") List<String> resourceIds,
            @Param("public") Boolean isPublic
    );

    Integer batchAuth(
            @Param("table") String tableName,
            @Param("resource_ids") List<String> resourceIds,
            @Param("auth_organizations") String authOrganizations,
            @Param("mode") Integer authMode
    );

    Integer batchShare(
            @Param("table") String tableName,
            @Param("resource_ids") List<String> resourceIds,
            @Param("share_teachers") String shareTeachers,
            @Param("mode") Integer shareMode
    );
}
