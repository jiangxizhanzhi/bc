package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 共性平台
 */
@Mapper
@Repository
public interface GXPTIndexMapper {

    void updateIndexDataByResourceIds(@Param("indexIds") List<String> indexIds, @Param("isSychronized") String isSychronized,
                                      @Param("returnData") String data, @Param("returnInfo") String returnData);

    List<String> selectReturnDataByEvalSystemIds(@Param("evalSystemIds") List<String> evalSystemIds);
}
