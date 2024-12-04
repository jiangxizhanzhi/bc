package com.cyberpeace.oj.course.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页报告博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class PageReportBo implements Serializable {

    /**
     * 数据
     *
     * @see List<ClassroomReportBo>
     */
    private List<ClassroomReportBo> data;

    /**
     * 总
     *
     * @see Long
     */
    private Long total;

}
