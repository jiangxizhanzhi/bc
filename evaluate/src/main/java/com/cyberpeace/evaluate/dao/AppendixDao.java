package com.cyberpeace.evaluate.dao;

import com.cyberpeace.evaluate.dao.entity.Appendix;

public interface AppendixDao {

    void insert(Appendix appendix);

    Appendix selectById(String logo);
}
