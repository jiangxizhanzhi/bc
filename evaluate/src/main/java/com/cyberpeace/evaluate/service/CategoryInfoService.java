package com.cyberpeace.evaluate.service;

import java.util.List;
import com.cyberpeace.evaluate.service.bo.CategoryInfoBO;

public interface CategoryInfoService {

    List<CategoryInfoBO> selectByType(Integer type);

}
