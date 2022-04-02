package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleBusinessTemplate;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleTrainBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface CrTitleBusinessTemplateMapper extends BaseMapper<CrTitleBusinessTemplate> {

    int saveBatch(@Param("title_id") String titleId, @Param("business_template_ids") Collection<? extends Serializable> idList);

    int deleteBatch(@Param("title_id") String titleId, @Param("business_template_ids") Collection<? extends Serializable> idList);

    List<CrTitleBusinessTemplate> listByTitleId(@Param("title_id") String titleId);

    IPage<CrTitleTrainBo> listTrain(@Param("page") Page page,
                                    @Param("type") Integer businessType,
                                    @Param("search") String search,
                                    @Param("title_id") String titleId);
}
