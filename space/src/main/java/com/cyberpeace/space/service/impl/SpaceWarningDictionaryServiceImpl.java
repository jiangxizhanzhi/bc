
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.dao.mapper.SpaceWarningDictionaryMapper;
import com.cyberpeace.space.service.SpaceWarningDictionaryService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间警告字典Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceWarningDictionaryService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceWarningDictionaryServiceImpl extends ServiceImpl<SpaceWarningDictionaryMapper, SpaceWarningDictionary> implements SpaceWarningDictionaryService

{}