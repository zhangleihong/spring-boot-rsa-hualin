package com.zzq.service.impl;

import com.zzq.entity.Notice;
import com.zzq.mapper.NoticeMapper;
import com.zzq.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2022-11-07
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Resource
    private NoticeMapper centerMapper;

    @Override
    public List<String> selectNoticeId() {
        return centerMapper.selectNoticeId();
    }

    @Override
    public Notice selectNoticeById(String id) {
        return centerMapper.selectNoticeById(id);
    }

}
