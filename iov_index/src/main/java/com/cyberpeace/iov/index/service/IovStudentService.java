package com.cyberpeace.iov.index.service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.iov.index.service.bo.student.RawPacket;

import java.util.List;

public interface IovStudentService {

    List<String> sniffBluetooth();

    JSONArray sniffWifi();

    RawPacket getLatestPacket();
}
