package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProbePage implements Serializable {

    Integer current_page;

    Integer total;

    Integer from;

    Integer to;

    Integer last_page;

    Object next_page_url;

    Object perv_page_url;

}
