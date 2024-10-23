
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.dao.mapper.SpaceWarningDictionaryMapper;
import com.cyberpeace.space.service.SpaceWarningDictionaryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceWarningDictionaryServiceImpl extends ServiceImpl<SpaceWarningDictionaryMapper, SpaceWarningDictionary> implements SpaceWarningDictionaryService

{}