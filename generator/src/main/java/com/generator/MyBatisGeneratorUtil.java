package com.generator;

import com.generator.plugin.MyCommentGenerator;
import com.generator.plugin.MyJavaTypeResolverDefaultImpl;
import com.generator.plugin.NoneGetterSetterPlugin;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis自动生成工具
 *
 * @author dupenghui
 * @date 2018-06-14 15:38
 */
public class MyBatisGeneratorUtil {

    public static void main(String[] args) throws Exception {
        fromJavaConfiguration();
    }

    /**
     * 以java code方式自动生成代码
     *
     * @author dupenghui
     * @date 2018-06-14 17:45
     */
    private static void fromJavaConfiguration() throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;

        Configuration config = new Configuration();

        // 声明mysql连接驱动
        config.addClasspathEntry("F:/mysql/mysql-connector-java-5.1.46.jar");

        // 创建上下文
        Context context = new Context(ModelType.FLAT);
        context.setId("myGenerator");
        context.setTargetRuntime("MyBatis3Simple");

        context.addProperty("javaFileEncoding", "UTF-8");

        PluginConfiguration serializablePluginConfiguration = new PluginConfiguration();
        serializablePluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        serializablePluginConfiguration.addProperty("suppressJavaInterface", "false");
        context.addPluginConfiguration(serializablePluginConfiguration);

        PluginConfiguration noneGetterSetterPluginConfiguration = new PluginConfiguration();
        noneGetterSetterPluginConfiguration.setConfigurationType(NoneGetterSetterPlugin.class.getTypeName());
        context.addPluginConfiguration(noneGetterSetterPluginConfiguration);

        // 配置注释
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType(MyCommentGenerator.class.getTypeName());
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        // 配置数据库连接信息
        JDBCConnectionConfiguration jdbcConnectionConfiguration = getEduConnection();
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // 配置Java类型转换
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty("forceBigDecimals", "false");
        javaTypeResolverConfiguration.setConfigurationType(MyJavaTypeResolverDefaultImpl.class.getTypeName());
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

        // 配置实体生成信息
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage("test.model");
        javaModelGeneratorConfiguration.setTargetProject("F:/java");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        javaModelGeneratorConfiguration.addProperty("rootClass", "com.common.BaseEntity");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // 配置mapper xml文件信息
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage("test.mapper");
        sqlMapGeneratorConfiguration.setTargetProject("F:/java");
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // 配置dao接口生成信息
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage("test.dao");
        javaClientGeneratorConfiguration.setTargetProject("F:/java");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages", "true");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        // 配置目标表信息
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName("t_user");
        tableConfiguration.setDomainObjectName("TestEntity");
        tableConfiguration.addProperty("useActualColumnNames", "false");
        GeneratedKey generatedKey = new GeneratedKey("id", "MySql", true, "post");
        tableConfiguration.setGeneratedKey(generatedKey);
        context.addTableConfiguration(tableConfiguration);

        config.addContext(context);

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        System.out.println("生成完毕......");
    }

    private static JDBCConnectionConfiguration getEduConnection() {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass("com.mysql.jdbc.Driver");
        jdbcConnectionConfiguration.setConnectionURL("jdbc:mysql://127.0.0.1:3306/test?autoReconnect=true&autoReconnectForPools=true&useUnicode=true&characterEncoding=utf8");
        jdbcConnectionConfiguration.setUserId("root");
        jdbcConnectionConfiguration.setPassword("123456");
        return jdbcConnectionConfiguration;
    }

}
