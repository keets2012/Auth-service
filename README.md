## 项目使用方法

### 2017.12.11 更新

**单独的整合项目地址为：   
GitHub：https://github.com/keets2012/microservice-integration   
或者 码云：https://gitee.com/keets/microservice-integration**


本次更新主要是增加了user,role,permission的相应接口。整合了微服务架构下，网关与auth权限服务、后端backend服务。

具体见博客[微服务架构中整合网关、权限服务](http://blueskykong.com/2017/12/10/integration/) 。感谢黄同学@CANGWU 对Spring EL部分做的修改。

项目整合如果遇到问题，可以加入qq群649932629，最好大家都能自行搞定。。

### 2017.11.3 更新

**去掉了Parent依赖，安装后，直接`mvn clean spring-boot:run` (maven 如果没装我也不好说啥)**

--- 
1. ~~首先需要clone 一下Cloud-Parent ，在我的git项目里面可以找到~~
2. ~~安装一下，`mvn clean install`~~
3. 修改Auth项目中的配置文件，写了`XXXX`的地方，替换成自己的实际地址
4. Auth服务有调用到user服务，所以如果你只是想简单跑这个项目，可以不调用client包下的UserClient，
自己在`CustomAuthenticationProvider`中写一个私有方法，返回一个map，结果包括一个userId。另外，`bootstrap.yml`中的关于Spring Cloud的都可以注掉。
只留下：
    
    ```yaml
    server:
      port: 9000
    ```
5. 创建auth数据库，运行auth.sql
6. API级别权限验证修改的源码部分，可以参见我fork 的`spring-security-oauth`和`spring-cloud-security`。
7. 其他细节参考博客
8. **你的star是对我最好的鼓励^_^**


笔者自己运行了可行：

```yaml
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDkwNzMzMjcsIlgtQU9ITy1Vc2VySWQiOiIxNGY1MmE0OS0yYTgxLTRhMmYtOGI5Mi01ZmU0NzUzZGRmZGEiLCJ1c2VyX25hbWUiOiIxODM2MjkxNjcyNiIsImp0aSI6IjM5NDEzN2I5LTNjZGItNGUyNy04NGRjLWM5YjEyYzk3ZTA4YyIsImNsaWVudF9pZCI6ImZyb250ZW5kIiwic2NvcGUiOlsiYWxsIl19.pGZhGNVECg0b4LB_pYXTTVKjNn8FA5biM04Bhcd-MEE",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxODM2MjkxNjcyNiIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiIzOTQxMzdiOS0zY2RiLTRlMjctODRkYy1jOWIxMmM5N2UwOGMiLCJleHAiOjE1MTE2MjIxMjcsIlgtQU9ITy1Vc2VySWQiOiIxNGY1MmE0OS0yYTgxLTRhMmYtOGI5Mi01ZmU0NzUzZGRmZGEiLCJqdGkiOiJkYTBmOTMxMS1lZjc0LTRiMjQtODViZi04ZTNjNDVhNGEyNzkiLCJjbGllbnRfaWQiOiJmcm9udGVuZCJ9.2MRdqEogAwbesRfj2TKoWhMazItBlpjbQx7dlgfFpHE",
    "expires_in": 43199,
    "scope": "all",
    "X-AOHO-UserId": "14f52a49-2a81-4a2f-8b92-5fe4753ddfda",
    "jti": "394137b9-3cdb-4e27-84dc-c9b12c97e08c",
    "X-AOHO-ClientId": "frontend"
}
```

ps: 登录的用户名密码要在表单里面写，内容随意，因为在代码中已经去掉了对user服务的校验。

*有问题联系 aoho002#gmail.com*

### 相关文章
[认证鉴权与API权限控制在微服务架构中的设计与实现（一）](http://blueskykong.com/2017/10/19/security1/)   
[认证鉴权与API权限控制在微服务架构中的设计与实现（二）](http://blueskykong.com/2017/10/22/security2/)   
[认证鉴权与API权限控制在微服务架构中的设计与实现（三）](http://blueskykong.com/2017/10/24/security3/)   
[认证鉴权与API权限控制在微服务架构中的设计与实现（四）](http://blueskykong.com/2017/10/26/security4/)

### 订阅最新文章，欢迎关注我的公众号

![微信公众号](http://ovci9bs39.bkt.clouddn.com/qrcode_for_gh_ca56415d4966_430.jpg)


