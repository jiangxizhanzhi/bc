package com.cyberpeace.cr.tool.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.service.ToolFileService;
import org.springframework.stereotype.Service;

@Service
public class ToolFileServiceImpl extends ServiceImpl<ToolFileMapper, ToolFile>
		implements ToolFileService {

}
