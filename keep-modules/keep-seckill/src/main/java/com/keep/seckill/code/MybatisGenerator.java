package com.keep.seckill.code;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.HashMap;

/**
 */
public class MybatisGenerator {

    public static void main(String[] args) {
        boolean startWithI = true;
        generateByTables(startWithI);

        System.getProperty("user.dir");
        System.out.println(System.getProperty("user.dir"));

    }

    //生成指定表 包含和出去二选一,如果都不填，则全库的表
    private static String[] inculdeTables = new String[]{
        "t_seckill_order","t_order","t_goods","t_seckill_goods"
    };

    private static String[] exculdeTableNames = new String[]{};

    /**
     * 配置数据源
     *
     * @return 数据源配置 DataSourceConfig
     */

    private static DataSourceConfig getDataSourceConfig() {

        return new DataSourceConfig().setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://127.0.0.1:3306/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true")
                //用时再填
                .setUsername("root")
                //用时再填
                .setPassword("root")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
    }

    /**
     * 根据表自动生成
     *
     * @param serviceNameStartWithI 默认为false
     */
    private static void generateByTables(boolean serviceNameStartWithI) {
        //配置数据源
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig();
        //全局变量配置
        GlobalConfig globalConfig = getGlobalConfig(serviceNameStartWithI);
        //包名配置
        PackageConfig packageConfig = getPackageConfig();
        //自动生成
        atuoGenerator(dataSourceConfig, strategyConfig, globalConfig, packageConfig);
    }

    /**
     * 集成
     *
     * @param dataSourceConfig 配置数据源
     * @param strategyConfig   策略配置
     * @param config           全局变量配置
     * @param packageConfig    包名配置
     */

    private static void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, GlobalConfig config, PackageConfig packageConfig) {
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setTemplateEngine(new VelocityTemplateEngine())
                .execute();
    }

    /**
     * 设置包名
     *
     * @return PackageConfig 包名配置
     */

    private static PackageConfig getPackageConfig() {
        String basePath = "keep-modules/keep-seckill/src/main";
        HashMap<String, String> pathInfo = new HashMap<>(10);
        pathInfo.put(ConstVal.XML_PATH, basePath + "/resources/mapper");
        // /java/tech/valuesimplex/srpstock/code/generator
        pathInfo.put(ConstVal.MAPPER_PATH, basePath + "/java/com/keep/seckill/mapper");
        pathInfo.put(ConstVal.SERVICE_PATH, basePath + "/java/com/keep/seckill/service");
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, basePath + "/java/com/keep/seckill/service/impl");
        pathInfo.put(ConstVal.ENTITY_PATH, basePath + "/java/com/keep/seckill/domain/entity");
        pathInfo.put(ConstVal.CONTROLLER_PATH,basePath+"/java/com/keep/seckill/controller");
        return new PackageConfig()
                .setParent("com.keep.seckill")
                .setXml("mapper")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setEntity("domain.entity")
                .setController("controller")
                .setPathInfo(pathInfo);
    }

    /**
     * 全局配置
     *
     * @param serviceNameStartWithI false
     * @return GlobalConfig
     */

    private static GlobalConfig getGlobalConfig(boolean serviceNameStartWithI) {
        GlobalConfig globalConfig = new GlobalConfig();
        //获取当前项目绝对路径
        String absPath = System.getProperty("user.dir");
        globalConfig
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setActiveRecord(false)
                .setAuthor("system")
                //设置输出路径
                .setOutputDir(absPath)
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            //设置service名
            globalConfig.setServiceName("%Service");
        }
        return globalConfig;
    }

    /**
     * 策略配置
     *
     * @return StrategyConfig
     */

    private static StrategyConfig getStrategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig()
                // 全局大写命名 ORACLE 注意
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                //从数据库表到文件的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel);
        //需要生成的的表名，多个表名传数组
        if (inculdeTables.length > 0) {
            strategyConfig.setInclude(inculdeTables);
        } else {
            //strategyConfig.setExclude(exculdeTableNames);
        }
        return strategyConfig;
    }




}