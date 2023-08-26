package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.rsp.TrainModeRsp;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TrainModeMapper {

    IPage<TrainModeRsp> listBusinessTemplate(Page page, @Param("name") String name, @Param("businessType") List<Integer> businessType,
                                             @Param("trainCategory") Integer trainCategory, @Param("ids") List<String> ids);

}
