package com.cyberpeace.oj.ojbase.service.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionContext implements Serializable {

    private String userId;

    private String businessId;

    private Integer businessType;

}
