package com.zzq.service;

import com.zzq.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2022-11-07
 */
public interface INoticeService extends IService<Notice> {
    List<String> selectNoticeId();

    Notice selectNoticeById(String id);
}
