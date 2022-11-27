package com.zzq.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * mp代码生成器
 * by
 * @since 2022-01-26
 */
public class CodeGenerator {
    private static final String DATASOURCE = "rsa";
    private static final String basePath = "C:/APITest/spring-boot-rsa/spring-boot-rsa";

    private static final String tableName = "notice";
    private static final String modelName = "报单通知表";

    public static void main(String[] args) throws Exception {

        // 生成SpringBoot
        generate(tableName);
        // 生成vue
//        createVue(tableName);
        // 生成菜单
//        createMenu(tableName, modelName);
    }


    // -----------------------------------------------------------------------------------------

    private static final String url = "jdbc:mysql://localhost:3306/" + DATASOURCE+"?serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "123456";

    /**
     * 生成SpringBoot文件
     */
    private static void generate(String tableName) {

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("") // 设置作者
                            .enableSwagger()
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()
                            .outputDir(basePath + "/src/main/java/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zzq") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, basePath + "src/main/resources/mapper/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }


    /**
     * 获取数据库表字段
     * @param tableName
     * @return
     * @throws SQLException
     */
    private static List<TableColumn> getTableColumns(String tableName) throws SQLException {
        DataSource ds = getDatasource();
        String sql = "SELECT table_name,column_name,data_type, column_comment FROM information_schema.COLUMNS WHERE table_schema = ? and table_name = ?";
        String schema = url.substring(url.lastIndexOf("/") + 1);
        List<Entity> list = Db.use(ds).query(sql, schema, tableName);
        List<TableColumn> columns = CollUtil.newArrayList();
        for (Entity entity : list) {
            TableColumn tableColumn = new TableColumn();
            String columnName = entity.getStr("column_name");
            tableColumn.setName(columnName);
            String comment = entity.getStr("column_comment");
            tableColumn.setComment(comment);
            columns.add(tableColumn);
        }
        return columns;
    }

    private static DataSource getDatasource() {
        return new SimpleDataSource(url, username, password);
    }
}


