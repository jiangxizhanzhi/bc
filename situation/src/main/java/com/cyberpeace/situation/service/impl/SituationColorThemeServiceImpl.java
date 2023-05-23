package com.cyberpeace.situation.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.situation.constant.SituationErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.controller.req.ColorThemeImportReq;
import com.cyberpeace.situation.controller.req.ColorThemePageReq;
import com.cyberpeace.situation.controller.req.ColorThemeReq;
import com.cyberpeace.situation.dao.entity.ColorTheme;
import com.cyberpeace.situation.dao.mapper.SituationColorThemeMapper;
import com.cyberpeace.situation.service.SituationColorThemeService;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午9:03
 */
@Service
public class SituationColorThemeServiceImpl extends ServiceImpl<SituationColorThemeMapper, ColorTheme> implements SituationColorThemeService {

    @Autowired
    private SituationColorThemeMapper situationColorThemeMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public ColorThemeReq createColorTheme(ColorThemeReq colorThemeReq) {

        // 1、校验主题名称是否重复
        if (situationColorThemeMapper.checkName(colorThemeReq.getName(), null)) {
            throw new BusinessException(SituationErrorCode.COLOR_THEME_NAME_EXIST);
        }

        // 2、解析并插入数据
        ColorTheme colorTheme = parseReqToColorTheme(colorThemeReq);
        colorTheme.setResourceId(IDGeneratorUtil.getID());
        situationColorThemeMapper.insert(colorTheme);
        return colorThemeReq;
    }

    @Override
    public String updateColorTheme(ColorThemeReq colorThemeReq) {

        // 1、提取主题id
        String resourceId = colorThemeReq.getResourceId();

        // 2、校验主题是否存在
        getColorThemeInfo(resourceId);

        // 3、校验主题名称是否重复
        if (situationColorThemeMapper.checkName(colorThemeReq.getName(), resourceId)) {
            throw new BusinessException(SituationErrorCode.COLOR_THEME_NAME_EXIST);
        }

        // 4、解析并更新数据
        ColorTheme reqToColorTheme = parseReqToColorTheme(colorThemeReq);
        situationColorThemeMapper.updateById(reqToColorTheme);
        return null;
    }

    @Override
    public ColorTheme getColorThemeInfo(String resourceId) {
        ColorTheme colorTheme = situationColorThemeMapper.selectById(resourceId);
        if (colorTheme == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return colorTheme;
    }

    @Override
    public Integer deleteColorTheme(List<String> idList) {
        return situationColorThemeMapper.deleteBatchIds(idList);
    }

    @Override
    public ListRsp<ColorTheme> getColorThemePage(ColorThemePageReq colorThemePageReq) {
        Page page = colorThemePageReq.toPageParam();
        IPage<ColorTheme> colorThemeIPage = situationColorThemeMapper.selectPage(page, colorThemePageReq);
        List<ColorTheme> ColorThemeList = colorThemeIPage.getRecords();

        //获取列表用户id
        List<String> userIds = new ArrayList<>();
        for (ColorTheme colorTheme : ColorThemeList) {
            String createUserId = colorTheme.getCreateUserId();
            userIds.add(createUserId);
        }
        ColorThemeList.forEach(colorTheme -> {
            String option = colorTheme.getOption();
            JSONObject jsonObject = JSONObject.parseObject(option);
            Object color = jsonObject.get("color");
            JSONArray objects = JsonUtil.stringToArray(color.toString());
            JSONObject object = new JSONObject();
            object.put("color", objects);
            colorTheme.setOption(object.toJSONString());
        });

        colorThemeIPage.setRecords(ColorThemeList);

        return PageUtil.getListResult(colorThemeIPage);
    }

    @Override
    public String importColorTheme(ColorThemeImportReq colorThemeImportReq) {
        MultipartFile file = colorThemeImportReq.getFile();
        String name = colorThemeImportReq.getName();
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPTY_FILE);
        }
        // 文件类型校验
        FileUtil.validateFileSuffix(file.getOriginalFilename(), "JSON");

        // 限制上传附件大小为500M
        if(file.getSize() > CommonConstant.TOOL_ATTACHMENT_FILE_SIZE){
            throw new BusinessException(ErrorCode.FILE_SIZE_EXCEED_UPPER_LIMIT);
        }
        String filePath = "/cpms/" + file.getOriginalFilename();
        File pathFile = new File(filePath);
        JSONObject colorJson = new JSONObject();

        //解析json内容，新增样式数据
        try {
            file.transferTo(pathFile);
            String content = FileUtil.getContent(pathFile);
            JSONObject jsonObject = JSONObject.parseObject(content);
            String color = jsonObject.getString("color");
            colorJson.put("color", color);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pathFile.delete();
        }

        if (StringUtils.isNotBlank(colorJson.getString("color"))) {
            ColorTheme colorTheme = new ColorTheme();
            colorTheme.setName(name);
            colorTheme.setResourceId(IDGeneratorUtil.getID());
            colorTheme.setCreateUserId(UserInfoContext.getUserContext().getUserId());
            colorTheme.setOption(colorJson.toJSONString());
            situationColorThemeMapper.insert(colorTheme);
        } else {
            throw new BusinessException(SituationErrorCode.IMPORT_FORMAT_FAILED);
        }

        return null;
    }

    private ColorTheme parseReqToColorTheme(ColorThemeReq colorThemeReq) {
        return BeanUtils.copy(colorThemeReq, ColorTheme.class);
    }
}
