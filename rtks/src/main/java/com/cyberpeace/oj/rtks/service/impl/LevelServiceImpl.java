package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.InvalidParamException;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.oj.rtks.service.LevelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {

    @Autowired
    private LevelMapper levelMapper;


    @Override
    public Level getFrameById(String resource_id) {
        Level level = getById(resource_id);
        if (level == null) {
            throw new ResourceNotFoundException("Frame");
        }

        if (level.getParent() != null) {
            // level有父级，不是Frame
            throw new InvalidParamException("错误的框架id");
        }

        return level;
    }


    @Override
    public List<Level> getFrames() {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("parent", null);
        return listByMap(columnMap);
    }

    @Override
    public List<Level> getLevelsByFrameId(String frameId) {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("frame", frameId);
        return listByMap(columnMap);
    }

    private void checkLevel(Level level) {
        String parentId = level.getParent();
        Level parentLevel = getById(parentId);
        if (parentLevel == null) {
            throw new ResourceNotFoundException("parent");
        }

        String frameId = level.getFrame();
        Level frame = getFrameById(frameId);
        if (frame == null) {
            throw new ResourceNotFoundException("frame");
        }
    }

    @Override
    public void addLevel(Level level) {
        checkLevel(level);

        save(level);
    }

    @Override
    public void modifyLevel(Level level) {
        checkLevel(level);

        updateById(level);
    }

    @Override
    public Level getLevelTree(String frameId) {
        Map<String, List<Level>> levelMap = new HashMap<>();

        List<Level> levels = getLevelsByFrameId(frameId);
        for (Level level : levels) {
            String parentId = level.getParent();
            if (StringUtils.isBlank(parentId)) {
                continue; // 如果此处parent为空应为脏数据
            }

            if (!levelMap.containsKey(parentId)) {
                levelMap.put(parentId, new ArrayList<>());
            }
            levelMap.get(parentId).add(level);
        }

        Level frame = getFrameById(frameId);
        List<Level> childLevels = levelMap.getOrDefault(frame.getResourceId(), null);
        if (childLevels != null) {
            frame.setChildren(childLevels);
            for (Level level : levels) {
                level.setChildren(levelMap.getOrDefault(level.getResourceId(), null));
            }
        }

        return frame;
    }

}