package com.cyberpeace.oj.course.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页报告博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
public class PageReportBo implements Serializable {

    /**
     * 数据
     */
    private List<ClassroomReportBo> data;

    /**
     * 总
     */
    private Long total;

}
