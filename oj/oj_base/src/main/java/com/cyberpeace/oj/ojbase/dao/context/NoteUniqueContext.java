package com.cyberpeace.oj.ojbase.dao.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class NoteUniqueContext implements Serializable {

    private Integer noteType;

    private Integer businessType;

    private String userId;

    private String businessId;

}
