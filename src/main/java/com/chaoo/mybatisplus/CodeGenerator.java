package com.chaoo.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.33.135:3306/es?useSSl=false&characterEncoding=UTF8";
        String username = "root";
        String password = "root";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("chaoo") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\A_JavaFrame\\A_workspace\\framework\\springboot-mp-rocketmq-ellaticsearch\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.chaoo") // 设置父包名
//                            .moduleName("demo") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\A_JavaFrame\\A_workspace\\framework\\springboot-mp-rocketmq-ellaticsearch\\src\\main\\java")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // 扫描所有表
                    builder.enableCapitalMode()// ==全部策略配置==
                            .addTablePrefix("tb_", "c_")  // 设置过滤表前缀
                            .entityBuilder() // ==实体策略==
                            .disableSerialVersionUID() // 禁用生成serid
                            .enableRemoveIsPrefix()  // 开启 Boolean 类型字段移除 is 前缀
                            .logicDeleteColumnName("is_deleted") // 逻辑删除字段名(数据库)
                            .enableLombok() // 开启lombok
                            .enableChainModel() // 开启 链式编程
                            .controllerBuilder() // ==controller策略==
                            .enableRestStyle() // 注解
                            .mapperBuilder() // ==mapper策略==
                            .enableMapperAnnotation() // 注解
                            .serviceBuilder() // ==service策略==
                            .formatServiceFileName("%sService") // service
                            .formatServiceImplFileName("%sServiceImp");

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}