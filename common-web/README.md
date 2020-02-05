web 公共组件
----
#### 说明

该组件主要为简化 spring-boot 的 web 项目开发

该组件使用spring-boot框架，默认2.1.7版本,为避免出现其他问题，请确保目标项目spring-boot版本2.0以上，而且只兼容spring-boot的项目使用

#### 支持功能
- 统一请求体，响应体封装
- 统一国际化支持
- 统一异常处理
- swagger2文档API

#### 引入方式

1. 拷贝组件源码作为底层模块,通过依赖模块引入

2. 将组件打成jar包，放入本地的lib文件夹下,建立 lib 库。注意使用这种方式时，
spring 的 maven-plugin 插件会将其打包成两个jar包,一个以`.jar`结尾，一个以`.jar.original`
结尾。我们需要的是第二种,导入时前重命名将`.original`去掉。

3. 从仓库引入。这里以这种方式介绍使用

    3.1 自定义远程仓库。在目标工程pom文件添加
    ~~~
   <repositories>
       <repository>
           <id>itsherman</id>
           <name>Itsherman</name>
           <url>http://134.175.164.205:8081/repository/maven-public/</url>
           <snapshots>
               <enabled>false</enabled>
           </snapshots>
       </repository>
   </repositories>
   ~~~
   3.2 pom文件引入组件依赖
   ~~~
   <dependency>
       <groupId>com.itsherman</groupId>
       <artifactId>common-web</artifactId>
       <version>0.0.1</version>
   </dependency>
   ~~~

#### 使用指导


