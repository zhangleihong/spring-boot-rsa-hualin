package com.zzq.mapper;

import com.zzq.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2022-11-07
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    List<String> selectNoticeId();

    Notice selectNoticeById(String id);

}
