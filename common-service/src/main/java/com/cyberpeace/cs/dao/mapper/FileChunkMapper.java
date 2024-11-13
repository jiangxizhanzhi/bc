package com.cyberpeace.cs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.FileChunk;

/**
 * 文件块Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface FileChunkMapper extends BaseMapper<FileChunk> {

}
