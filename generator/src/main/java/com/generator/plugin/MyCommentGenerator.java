package com.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * @author dupenghui
 * @date 2018-06-14 20:53
 */
public class MyCommentGenerator extends DefaultCommentGenerator {

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //获取实体类名称
        String entityName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        StringBuilder sb = new StringBuilder();

        //添加导入类的信息
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel;");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty;");
        topLevelClass.addImportedType("lombok.Data;");

        //添加类注释
        topLevelClass.addJavaDocLine("/**");
        sb.append(" * "+ entityName);
        sb.append("\n");
        sb.append(" * 实体类对应的数据表为：  ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * @author dupenghui");
        topLevelClass.addJavaDocLine(" */");
        sb.append("\n");

        topLevelClass.addJavaDocLine("@ApiModel(value =\"" + entityName + "\")");
        topLevelClass.addJavaDocLine("@Data");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        //字段备注信息
        String remarks = introspectedColumn.getRemarks();
        field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
    }

}
