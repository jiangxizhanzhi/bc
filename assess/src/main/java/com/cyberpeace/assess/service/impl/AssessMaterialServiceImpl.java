package com.cyberpeace.assess.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.assess.controller.res.AssessMaterialExcelDatalRes;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.dao.mapper.AssessCaseMapper;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.MdUtil;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.cs.service.UtilService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import com.cyberpeace.assess.dao.mapper.AssessMaterialMapper;
import com.cyberpeace.assess.service.AssessMaterialService;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;

import java.io.File;
import java.util.List;

/**
* @author dell
* @description 针对表【assess_material】的数据库操作Service实现
* @createDate 2022-11-21 13:55:57
*/
@Service
public class AssessMaterialServiceImpl extends ServiceImpl<AssessMaterialMapper, AssessMaterial>
implements AssessMaterialService{

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AssessCaseService assessCaseService;

    @Autowired
    private AssessMaterialMapper assessMaterialMapper;

    @Autowired
    private UtilService utilService;


    @Override
    public AssessMaterial checkAssessMaterial(MultipartFile attachment, AssessMaterial assessMaterial) {
        Integer materialType = assessMaterial.getMaterialType();
        String originalFilename = attachment.getOriginalFilename();

        String caseId = assessMaterial.getCaseId();

        checkAttachment(materialType, originalFilename);
        assessCaseService.checkCaseId(caseId);

//        String attachmentPath = String.format(AttachmentPath.VIDEO.getDesc(),
//                assessMaterialAddReq.getCaseId());
        AttachmentBo attachmentBo = attachmentService.
                uploadDest(AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), false, attachment);
        assessMaterial.setAttachment(attachmentBo.getFilePath());
        return assessMaterial;
    }

    @Override
    public void checkMaterialIds(List<String> materialIds) {
        LambdaQueryWrapper<AssessMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AssessMaterial::getResourceId, materialIds);
        List<AssessMaterial> assessMaterials = assessMaterialMapper.selectList(queryWrapper);
        if(assessMaterials.isEmpty()){
            throw new BusinessException(AssessErrorCode.MATERIAL_IDS_ERROR);
        }

    }

    @Override
    public void checkParams(String name, String attachment) {
        if(StringUtils.isBlank(name)){
            throw new BusinessException(AssessErrorCode.NAME_MISSING);
        }
        if(StringUtils.isBlank(attachment)){
            throw new BusinessException(AssessErrorCode.MATERIAL_ATTACHMENT_MISSING);
        }
    }

    @Override
    public List<AssessMaterialExcelDatalRes> getExcelData(String attachment) {
        // excel数据读取

        ExcelDataHandler dataHandler = new ExcelDataHandler(attachment, CommonConstant.ZERO);
        List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);
        for (int i = CommonConstant.ONE; i <= sheetData.size(); i++) {
            // 空数据不管
            if (!validateEmptyData(sheetData.get(i - CommonConstant.ONE), i)) {
                continue;
            }

        }
        return null;
    }

    @Override
    public void checkNameRepeat(String name, String id, String caseId) {
        LambdaQueryWrapper<AssessMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssessMaterial::getName, name);
        queryWrapper.eq(AssessMaterial::getCaseId, caseId);

        if (StringUtils.isNotBlank(id)){
            queryWrapper.ne(AssessMaterial::getResourceId, id);
        }
        AssessMaterial assessMaterial = assessMaterialMapper.selectOne(queryWrapper);
        if(null != assessMaterial){
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
    }

    @Override
    public void uploadHandout(AssessMaterial assessMaterial, String attachment) {
        String[] split = attachment.split("\\.");
        if(split.length < 1){
            throw new BusinessException("Incorrect attachment path format");
        }
        String suffix = split[split.length-1];
        suffix = suffix.toLowerCase();

        // zip可能为md 或者 html
        if (suffix.equals(FileSunffixConstant.ZIP.getDesc()) && assessMaterial.getMaterialType().equals(AssessMaterial.MaterialType.TEXT.getCode())) {
            // 文件上传至服务器 原始文件地址存储
            assessMaterial.setAttachment(attachment);

            // 获取讲义文件存储地址
            String realPath = utilService.getRealPath(attachment);
            File file = new File(attachment);
            String parent = file.getParent();

            // 计算要解压的文件地址
            String unzipPath = parent + File.separator + FileUtil.getFileName(realPath, false);
            ZipUtil.unzipToDest(realPath, unzipPath);

            // 获取解压的讲义文件
            String mdFilePath = FileUtil.getOneFile(unzipPath, FileSunffixConstant.MD.getDesc());
            if (StringUtils.isBlank(mdFilePath)) {
                throw new BusinessException(AssessErrorCode.MATERIAL_TYPE_ERROR);
            }
            assessMaterial.setHandoutType(AssessMaterial.HandoutType.MD.getCode());

            // md文件内容作为url
            String content = MdUtil.parseMarkdown(mdFilePath);
            assessMaterial.setAttachmentContent(utilService.replaceMarkdownFile(content));
        }
    }

    /**
     * 校验空数据
     * @param rowData
     * @return
     */
    private boolean validateEmptyData(List<String> rowData, int rowIndex) {
        if (CollectionUtils.isEmpty(rowData)
                || StringUtils.isBlank(String.join(CommonConstant.EMPTY_STRING, rowData))) {
            throw new BusinessException("rowindex error");
        }

        return true;
    }



    private void checkAttachment(Integer materialType, String originalFilename) {
        if(materialType.equals(AssessMaterial.MaterialType.TEXT.getCode())){
            FileUtil.validateFileSuffix(originalFilename, FileTypeContant.RESOURCE_DATA_IMPORT_TYPE);
        } else if(materialType.equals(AssessMaterial.MaterialType.VIDEO.getCode())){
            FileUtil.validateFileSuffix(originalFilename, FileTypeContant.VIDEO_TYPE);
        } else if(materialType.equals(AssessMaterial.MaterialType.PIC.getCode())){
            FileUtil.validateFileSuffix(originalFilename, FileTypeContant.PICTURE_TYPE);
        } else if(materialType.equals(AssessMaterial.MaterialType.EXCEL.getCode())){
            FileUtil.validateFileSuffix(originalFilename, FileTypeContant.EXCEL_TYPE);
        } else {
            throw new BusinessException(AssessErrorCode.MATERIAL_TYPE_ERROR);
        }
    }
}
