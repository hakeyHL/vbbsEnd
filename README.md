微论坛最终完成版本 

项目初始化
使用sql文件数据库建立数据库
1、导入sql
2、修改jdbc.properties中的数据库账号密码


generator——生成mybatis相关代码的工具
使用方法：
1、到generatorConfig.xml配置要生成表的信息
2、如果要生成的文件已经存在，删除 ，删除前确保原文件没被修改过
3、cd到generatorConfig目录
4、运行mvn mybatis-generator:generate
idea配置自动生成参见http://blog.csdn.net/luanlouis/article/details/43192131
