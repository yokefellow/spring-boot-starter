# HttpClient Spring Boot Starter

## 如何使用
1. 将`jar`包放到本地私服
2. 在Spring Boot 项目加入 `HttpClient Spring Boot Starter` 依赖

    ```xml
    <dependency>
       <groupId>com.yokefellow</groupId>
       <artifactId>httpclient-spring-boot-starter</artifactId>
       <version>1.0.0.RELEASE</version>
    </dependency>
    ```
    
3. 配置文件

    - `Yaml` 
    ```yaml
    httpclient:
      builder:
        max-conn-per-route:
        max-conn-total: 
      request-config:
        connect-timeout:
        connection-request-timeout:
        socket-timeout: 

    ```
    - `Properties`
    ```properties
     
    ```
    
4. 配置属性

|属性|描述|
|---|---|
|maxConnTotal|最大连接数|
|maxConnPerRoute|设置每个路由的并发数|
|connectionRequestTimeout|从HttpClient连接池获取连接超时限制|
|connectTimeout|建立连接超时时间，三次握手完成时间|
|socketTimeout|建立链接成功数据传输导致的超时时间|
