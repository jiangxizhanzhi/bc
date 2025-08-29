package com.cyberpeace.oj.practice.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.FingerPrintDto;
import com.cyberpeace.oj.practice.controller.req.FingerPrintReq;
import com.cyberpeace.oj.practice.dao.entity.FingerPrint;
import com.cyberpeace.oj.practice.dao.mapper.FingerPrintMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "指纹信息库")
@RestController
@RequestMapping("oj/admin/finger_print")
@Slf4j
public class FingerPrintController {

    private final FingerPrintMapper fingerPrintMapper;

    public FingerPrintController(FingerPrintMapper fingerPrintMapper) {
        this.fingerPrintMapper = fingerPrintMapper;
    }

    @PostConstruct
    public void init() {
        Integer count = this.fingerPrintMapper.selectCount(new LambdaQueryWrapper<>());
        if (count < 100) {
            ClassPathResource resource = new ClassPathResource("fofa.json");
            if (resource.exists()) {
                try {
                    saveFingerPrint(resource.getInputStream());
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    log.error("指纹数据文件读取失败: {}", e.getMessage());
                }
            } else {
                log.error("指纹数据文件fofa.json不存在");
            }
        } else {
            log.info("指纹数据已正常导入，无需重复导入");
        }

    }



    @ApiOperation("指纹信息批量导入")
    @PostMapping("/import")
    public ResponseEntity<String> importFile(@RequestParam("file") MultipartFile file) {
        // 1. 校验文件非空
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件不能为空");
        }
        try {
            return saveFingerPrint(file.getInputStream());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("文件读取失败");
        }
    }

    private ResponseEntity<String> saveFingerPrint(InputStream fingerPrintResourceInputStream) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(fingerPrintResourceInputStream, StandardCharsets.UTF_8))) {

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                // 可在此逐行处理业务逻辑
            }

            List<FingerPrintDto> fingerPrintDtoList = JsonUtil.toJsonObjectArray(content.toString(), FingerPrintDto.class);
            List<FingerPrint> fingerPrintList = fingerPrintDtoList.stream().map(
                    fingerPrintDto -> {
                        FingerPrint fingerPrint = new FingerPrint();
                        fingerPrint.setCreateTime(LocalDateTime.now());
                        fingerPrint.setUpdateTime(LocalDateTime.now());
                        fingerPrint.setDeletedFlag(false);
                        fingerPrint.setCompany(fingerPrintDto.getCompany());
                        fingerPrint.setCategory(fingerPrintDto.getCategory());
                        fingerPrint.setParentCategory(fingerPrintDto.getParentCategory());
                        fingerPrint.setProduct(fingerPrintDto.getProduct());
                        fingerPrint.setRules(JsonUtil.toJsonString(fingerPrintDto.getRules()));
                        return fingerPrint;
                    }
            ).collect(Collectors.toList());
            for (FingerPrint fingerPrint : fingerPrintList) {
                fingerPrintMapper.insert(fingerPrint);
            }
            return ResponseEntity.ok("文件导入成功");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("流读取失败");
        }
    }



    @ApiOperation("指纹信息新增")
    @PutMapping("")
    public BaseRsp<Boolean> add(@RequestBody FingerPrint fingerPrint) {
        fingerPrintMapper.insert(fingerPrint);
        return new BaseRsp<Boolean>(true);
    }


    @ApiOperation("指纹信息删除")
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestParam("resource_id") String resource_id) {
        FingerPrint fingerPrint = fingerPrintMapper.selectById(resource_id);
        fingerPrint.setDeletedFlag(true);
        fingerPrintMapper.updateById(fingerPrint);
        return new BaseRsp<Boolean>(true);
    }

    @ApiOperation("指纹信息修改")
    @PostMapping("")
    public BaseRsp<Boolean> update(@RequestBody FingerPrint fingerPrint) {
        fingerPrint.setUpdateTime(LocalDateTime.now());
        fingerPrintMapper.updateById(fingerPrint);

        return new BaseRsp<Boolean>(true);
    }

    @ApiOperation("指纹信息查询")
    @GetMapping("")
    public BaseRsp query(FingerPrintReq fingerPrintReq) {
        IPage<FingerPrint> page = new Page<>();
        page.setCurrent(fingerPrintReq.getPage());
        page.setSize(fingerPrintReq.getPerPage());
        LambdaQueryWrapper<FingerPrint> fingerPrintLambdaQueryWrapper = new LambdaQueryWrapper<FingerPrint>()
                .eq(FingerPrint::getDeletedFlag, false).likeLeft(StringUtils.isNotEmpty(fingerPrintReq.getParentCategory()),
                        FingerPrint::getParentCategory, fingerPrintReq.getParentCategory())
                        .likeLeft(StringUtils.isNotEmpty(fingerPrintReq.getCategory()), FingerPrint::getCategory,
                                fingerPrintReq.getCategory())
                                .likeLeft(StringUtils.isNotEmpty(fingerPrintReq.getProduct()), FingerPrint::getProduct,
                                        fingerPrintReq.getProduct())
                                        .likeLeft(StringUtils.isNotEmpty(fingerPrintReq.getCompany()), FingerPrint::getCompany,
                                                fingerPrintReq.getCompany());
        page = fingerPrintMapper.selectPage(page, fingerPrintLambdaQueryWrapper);
        ListRsp res = PageUtil.getListResult(page);
        // 替换返回数据
        res.setList(page.getRecords());
        return new BaseRsp(res);
    }
}
