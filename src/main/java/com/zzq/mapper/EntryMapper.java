package com.zzq.mapper;

import com.zzq.entity.Entry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2022-09-19
 */
@Mapper
public interface EntryMapper extends BaseMapper<Entry> {
    int insert(@Param(value="entry")Entry entry);
}
