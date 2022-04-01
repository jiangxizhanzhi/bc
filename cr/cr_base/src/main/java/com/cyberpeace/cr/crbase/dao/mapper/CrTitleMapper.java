package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CrTitleMapper extends BaseMapper<CrTitle> {

    IPage<CrTitleBo> list(@Param("page") Page page, @Param("name") String name);
}
