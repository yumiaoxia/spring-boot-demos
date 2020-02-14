web 公共组件
----
#### 说明

该组件主要为简化 spring-boot 的 web 项目开发

该组件使用spring-boot框架，默认2.2.4版本,为避免出现其他问题，请确保目标项目spring-boot版本2.0以上，而且只兼容spring-boot的项目使用.

该组件采用自动化配置，只要引入该组件依赖，则会扫描到组件所有相关配置

#### 支持功能
- 统一请求体，响应体封装
- 统一国际化支持
- 统一异常处理
- 使用swagger2，自动生成接口文档API
- 日期、时间格式

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
       <artifactId>common-web-spring-boot-starter</artifactId>
       <version>{版本号}</version>
   </dependency>
   ~~~

#### 使用指导

1. 使用 swagger2 步骤
    组件已经引入了swagger2 相关依赖并做了简单配置，默认所有以`/api/`开头的接口将会被识别，当然
    不满意，也可以自定义自己的swagger2。
    1) 在目标项目的配置类上使用注解 `@EnableSwagger2`
    2) 在 controller 层使用 swagger 相关注解标注接口

2. 请求体和响应体封装

    请求和响应已做了统一的封装。对于一个Ajax请求，请求参数可以使用组件的ApiRequest包装，如果是分页查询则可以使用
    ApiPageRequest包装。对于响应体同理，可以使用ApiResponse或APiPageResponse包装。结合swagger2 示例如下：
    
    ~~~java
   @ApiOperation("获取详情")
       @GetMapping("/detail/{id}")
       public ApiResponse<Person> detail(@PathVariable String id) {
           Person person = new Person();
           person.setId(id);
           person.setAge(28);
           person.setBirthday(LocalDateTime.now());
           person.setName("Sherman");
           return ApiResponse.createSuccess(person);
       }
   
   
       @ApiOperation("创建")
       @PostMapping("/exception")
       public ApiResponse<Person> exception(@RequestBody ApiRequest<Person> request) {
           if (request.getCommend() == null) {
               throw new NullPointerException("person must be not null!");
           }
           return ApiResponse.createSuccess(request.getCommend());
       }
   ~~~
   如上代码。我们将想要返回的数据包装在ApiResponse或ApiPageResponse中，请求参数包装在ApiRequest或ApiPageRequest中

3. 统一异常处理和国际化
    开发项目处理异常不可避免，组件根据spring的异常处理机制进一步简化
    
    *检查异常*： 通常指的是业务逻辑错误,组件定义为 ServiceException 的实例, 并指明具体的异常编号, 组件国际化会根据编号
    返回具体异常信息
    
    *非检查异常*： 程序代码异常(空指针，数据类型...),统一界定为系统异常, 没有具体异常信息

    组件提供国际化支持，默认根据浏览器设置提示。
    
    使用步骤：
    
    3.1 在项目配置文件配置 i18n 文件的路径,以`spring.common-web.messages`开头，如在 yaml 文件中：
    ~~~
    spring:
        common-web:
            messages:
                encoding: UTF-8
                basename: classpath:example/i18n/mess
    ~~~~
   
    3.2 创建一个业务异常编号接口文件，继承于组件的 CommonErrorCode,假设一个业务异常
    编号设定为 9999
    
    ~~~java
   public interface ErrorCode extends CommonErrorCode {
       String DEMO_EXCEPTION = "10001";
   }
   ~~~~

    3.3 在发生业务异常时主动抛出
    
   ~~~~java
   throw new ServiceException(ErrorCode.DEMO_EXCEPTION);
   ~~~~
   
   3.4 编辑国际化文件
   
   ~~~properties
   10001=示例业务异常
   ~~~~
   
 
 4. 统一日期,时间序列化格式
 
   引入该组件后，目标项目的请求参数，响应参数中如果有LocalDate,LocalDateTime,ZonedDateTime类型的字段，则会按
   指定的序列化格式序列化，序列化格式在application.yaml或application.properties文件中指定，如：
   
  ~~~
  spring:
    common-web:
       format:
          pattern:
            date: yyyy-MM-dd
            date-time: yyyy-MM-dd HH:mm:ss
            zone: GMT+8
  ~~~
   
