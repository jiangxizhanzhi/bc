package com.cyberpeace.oj.course.controller.res;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.service.bo.classroom.PlanProgressBo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRspWithS extends ListRsp<PlanProgressBo> {
    private Integer finishedStudentCount;
    private Integer unfinishedStudentCount;

    public ListRspWithS(ListRsp<PlanProgressBo> rsp) {
        this.setList(rsp.getList());
        this.setTotal(rsp.getTotal());
        this.setLastPage(rsp.getLastPage());
        this.setCurrentPage(rsp.getCurrentPage());
    }
}
