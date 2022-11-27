package com.zzq.service.impl;

import com.zzq.entity.Entry;
import com.zzq.mapper.EntryMapper;
import com.zzq.service.IEntryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2022-09-19
 */
@Service
public class EntryServiceImpl extends ServiceImpl<EntryMapper, Entry> implements IEntryService {

    @Resource
    EntryMapper entryMapper;

    @Override
    public int insert(Entry entry) {

        return entryMapper.insert(entry);
    }
}
