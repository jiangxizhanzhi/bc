package com.cyberpeace.cr.evaluation.dao.mode.topo;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Element {
    private String id;

    private String name;

    @Expose
    private String image;
}
