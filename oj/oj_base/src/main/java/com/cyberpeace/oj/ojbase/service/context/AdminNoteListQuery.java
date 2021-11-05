package com.cyberpeace.oj.ojbase.service.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminNoteListQuery implements Serializable {

    private String userId;

    private String userName;

    private String pattern;

    private Integer noteType;

    private Integer businessType;

}
