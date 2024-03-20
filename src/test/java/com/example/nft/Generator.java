package com.example.nft;

/**
 * @Author wangyixiong
 * @Date 2023/8/2 下午8:56
 * @PackageName:com.example.nft
 * @ClassName: Generator
 * @Description: TODO
 * @Version 1.0
 */

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.platform.commons.util.StringUtils;

import java.util.Scanner;

/**
 * @Author james
 * @DATE 2022/3/26 17:10
 */
public class Generator {

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //获取工作目录跟路径，测试下来就是项目父级目录的文件路径，当前香炉的工作路径是：D:/bodyPark/park-user
        String projectPath = System.getProperty("user.dir");
        //gc.setOutputDir 设置生成的文件输出目录,想将生成的文件存在在哪就设置什么路径
        gc.setOutputDir(projectPath + "/src/main/resources");
        //设置生成的类的作者
        gc.setAuthor("xxx");
        gc.setOpen(false);
        //关闭xml文件中配置二级缓存，默认是true 开启的,这个如果没有关闭的话，mapper.xml文件中就会开启二级缓存
        gc.setEnableCache(false);
        gc.setFileOverride(true);//是否覆盖以前文件
        gc.setBaseResultMap(true);//生成基本ResultMap
        gc.setBaseColumnList(true);//生成基本ColumnList
        gc.setServiceName("%sService");//去掉服务默认前缀
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/db_car2?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("0323");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        scanner("数据表");
        //这个不需要设置，不然会影响类的package
        pc.setModuleName("");
        //类的包路径，moduleName如果设置的话，会将moduleName设置的值拼接在类名和parent之间
        pc.setParent("com.example.nft");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix("t_");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}


