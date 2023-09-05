package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleAssetNodeCopy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrRoleAssetNodeCopyMapper extends BaseMapper<CrRoleAssetNodeCopy> {

    int batchInsert(@Param("list") List<CrRoleAssetNodeCopy> list);
}
