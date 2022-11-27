package com.zzq.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzq.common.Result;
import com.zzq.config.AuthAccess;
import com.zzq.entity.Notice;
import com.zzq.service.INoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private INoticeService noticeService;

    @GetMapping("/selectNoticeId")
    public Result selectNoticeId() {
        return Result.success(noticeService.selectNoticeId());
    }


    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Notice notice) {
        noticeService.saveOrUpdate(notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        noticeService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        noticeService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(noticeService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(noticeService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String serial_no) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if(!"".equals(serial_no)){
            queryWrapper.eq("serial_no", serial_no);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(noticeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

