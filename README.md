## 项目使用方法


1. 首先需要clone 一下Cloud-Parent ，在我的git项目里面可以找到
2. 安装一下，`mvn clean install`
3. 修改Auth项目中的配置文件，写了`XXXX`的地方，替换成自己的实际地址
4. Auth服务有调用到user服务，所以如果你只是想简单跑这个项目，可以不调用client包下的UserClient，
自己在`CustomAuthenticationProvider`中写一个私有方法，返回一个map，结果包括一个userId。另外，`bootstrap.yml`中的关于Spring Cloud的都可以注掉。
只留下：
    
    ```yaml
    server:
      port: 9000
    ```
5. 创建auth数据库，运行auth.sql
6. 其他细节参考博客

[认证鉴权与API权限控制在微服务架构中的设计与实现（一）](http://blueskykong.com/2017/10/19/security1/)   
[认证鉴权与API权限控制在微服务架构中的设计与实现（二）](http://blueskykong.com/2017/10/22/security2/)   
[认证鉴权与API权限控制在微服务架构中的设计与实现（三）](http://blueskykong.com/2017/10/24/security3/)