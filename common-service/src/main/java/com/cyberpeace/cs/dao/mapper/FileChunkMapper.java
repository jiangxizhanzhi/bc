package com.cyberpeace.cs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.FileChunk;

@Mapper
@Repository
public interface FileChunkMapper extends BaseMapper<FileChunk> {

}
