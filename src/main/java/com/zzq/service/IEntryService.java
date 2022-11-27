package com.zzq.service;

import com.zzq.entity.Entry;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2022-09-19
 */
public interface IEntryService extends IService<Entry> {
    int insert(Entry entry);
}
