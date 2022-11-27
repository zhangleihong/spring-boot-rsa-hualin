package com.zzq.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzq.common.Result;
import com.zzq.config.AuthAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/serial")
public class searchController {
    @AuthAccess
    @GetMapping("/page")
    public String findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        System.out.println(name);

//        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("is_delete", false);
//        queryWrapper.eq("userid", userid);
//        //重点订单
//        queryWrapper.last("order by case when is_important = false then true else false end, id desc");
//        if (!"".equals(name)) {
//            queryWrapper.like("name", name);
//        }
//        return Result.success(goodsService.page(new Page<>(pageNum, pageSize), queryWrapper));
        return "asd";
    }

}
