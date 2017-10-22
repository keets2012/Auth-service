---
title: 认证鉴权与API权限控制在微服务架构中的设计与实现（一） 
date: 2017-10-19
categories: Security
tags:
- Spring Security
- OAuth2
---
引言： 本文系《认证鉴权与API权限控制在微服务架构中的设计与实现》系列的第一篇，本系列预计四篇文章讲解微服务下的认证鉴权与API权限控制的实现。

## 1. 背景
最近在做权限相关服务的开发，在系统微服务化后，原有的单体应用是基于session的安全权限方式，不能满足现有的微服务架构的认证与鉴权需求。微服务架构下，一个应用会被拆分成若干个微应用，每个微应用都需要对访问进行鉴权，每个微应用都需要明确当前访问用户以及其权限。尤其当访问来源不只是浏览器，还包括其他服务的调用时，单体应用架构下的鉴权方式就不是特别合适了。在微服务架构下，要考虑外部应用接入的场景、用户--服务的鉴权、服务--服务的鉴权等多种鉴权场景。   
比如用户A访问User Service，A如果未登录，则首先需要登录，请求获取授权token。获取token之后，A将携带着token去请求访问某个文件，这样就需要对A的身份进行校验，并且A可以访问该文件。
为了适应架构的变化、需求的变化，auth权限模块被单独出来作为一个基础的微服务系统，为其他业务service提供服务。

## 2. 系统架构的变更
单体应用架构到分布式架构，简化的权限部分变化如下面两图所示。   
（1）单体应用简化版架构图：
![single](http://ovcjgn2x0.bkt.clouddn.com/%E5%8D%95%E4%BD%93%E5%BA%94%E7%94%A8.png "单体架构")
（2）分布式应用简化版架构图：
![distrubted](http://ovcjgn2x0.bkt.clouddn.com/%E5%BE%AE%E6%9C%8D%E5%8A%A1%E6%9E%B6%E6%9E%84%E6%9D%83%E9%99%90.png "分布式架构")

分布式架构，特别是微服务架构的优点是可以清晰的划分出业务逻辑来，让每个微服务承担职责单一的功能，毕竟越简单的东西越稳定。   

但是，微服务也带来了很多的问题。比如完成一个业务操作，需要跨很多个微服务的调用，那么如何用权限系统去控制用户对不同微服务的调用，对我们来说是个挑战。当业务微服务的调用接入权限系统后，不能拖累它们的吞吐量，当权限系统出现问题后，不能阻塞它们的业务调用进度，当然更不能改变业务逻辑。新的业务微服务快速接入权限系统相对容易把控，那么对于公司已有的微服务，如何能不改动它们的架构方式的前提下，快速接入，对我们来说，也是一大挑战。

## 3. 技术方案
**这主要包括两方面需求：其一是认证与鉴权，对于请求的用户身份的授权以及合法性鉴权；其二是API级别的操作权限控制，这个在第一点之后，当鉴定完用户身份合法之后，对于该用户的某个具体请求是否具有该操作执行权限进行校验。** 
### 3.1 认证与鉴权
对于第一个需求，笔者调查了一些实现方案：

1. 分布式`Session`方案   
分布式会话方案原理主要是将关于用户认证的信息存储在共享存储中，且通常由用户会话作为 key 来实现的简单分布式哈希映射。当用户访问微服务时，用户数据可以从共享存储中获取。在某些场景下，这种方案很不错，用户登录状态是不透明的。同时也是一个高可用且可扩展的解决方案。这种方案的缺点在于共享存储需要一定保护机制，因此需要通过安全链接来访问，这时解决方案的实现就通常具有相当高的复杂性了。   

2. 基于`OAuth2 Token`方案   
随着 Restful API、微服务的兴起，基于`Token`的认证现在已经越来越普遍。Token和Session ID 不同，并非只是一个 key。Token 一般会包含用户的相关信息，通过验证 Token 就可以完成身份校验。用户输入登录信息，发送到身份认证服务进行认证。AuthorizationServer验证登录信息是否正确，返回用户基础信息、权限范围、有效时间等信息，客户端存储接口。用户将 Token 放在 HTTP 请求头中，发起相关 API 调用。被调用的微服务，验证`Token`。ResourceServer返回相关资源和数据。

这边选用了第二种方案，基于`OAuth2 Token`认证的好处如下：

- 服务端无状态：Token 机制在服务端不需要存储 session 信息，因为 Token 自身包含了所有用户的相关信息。
- 性能较好，因为在验证 Token 时不用再去访问数据库或者远程服务进行权限校验，自然可以提升不少性能。
- 现在很多应用都是同时面向移动端和web端，`OAuth2  Token`机制可以支持移动设备。
- 最后一点，也是挺重要的，OAuth2与Spring Security结合使用，Spring Security OAuth2的文档写得较为详细。

oauth2根据使用场景不同，分成了4种模式：

- 授权码模式（authorization code）
- 简化模式（implicit）
- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）

对于上述oauth2四种模式不熟的同学，可以自行百度oauth2，阮一峰的文章有解释。常使用的是password模式和client模式。

### 3.2 操作权限控制

对于第二个需求，笔者主要看了Spring Security和Shiro。 

1. `Shiro`     
Shiro是一个强大而灵活的开源安全框架，能够非常清晰的处理认证、授权、管理会话以及密码加密。Shiro很容易入手，上手快控制粒度可糙可细。自由度高，Shiro既能配合Spring使用也可以单独使用。

2. `Spring Security`    
Spring社区生态很强大。除了不能脱离Spring，Spring Security具有Shiro所有的功能。而且Spring Security对Oauth、OpenID也有支持,Shiro则需要自己手动实现。Spring Security的权限细粒度更高。但是Spring Security太过复杂。

看了下网上的评论，貌似一边倒向Shiro。大部分人提出的`Spring Security`问题就是比较复杂难懂，文档太长。笔者综合评估了下复杂性与所要实现的权限需求，以及上一个需求调研的结果，最终选择了`Spring Security`。

## 4. 系统架构
### 4.1 组件
Auth系统的最终使用组件如下：

```
OAuth2.0 JWT Token
Spring Security
Spring boot

```
### 4.2 步骤
主要步骤为：

- 配置资源服务器和认证服务器
- 配置Spring Security   

上述步骤比较笼统，对于前面小节提到的需求，属于Auth系统的主要内容，笔者后面会另写文章对应讲解。

### 4.3 endpoint
提供的endpoint：

```yaml
/oauth/token?grant_type=password #请求授权token

/oauth/token?grant_type=refresh_token #刷新token

/oauth/check_token #校验token

/logout #注销token及权限相关信息

```
### 4.4 maven依赖
主要的jar包，pom.xml文件如下：

```pom
		<dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>2.2.0</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
            <version>1.2.1-SNAPSHOT</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
            <version>1.2.1-SNAPSHOT</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jersey</artifactId>
            <version>1.5.3.RELEASE</version>
        </dependency>
```
### 4.5 AuthorizationServer配置文件
AuthorizationServer配置主要是覆写如下的三个方法，分别针对endpoints、clients、security配置。

```java
	@Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
  	  security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    //配置客户端认证
        clients.withClientDetails(clientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { 
    //配置token的数据源、自定义的tokenServices等信息
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore(dataSource))
                .tokenServices(authorizationServerTokenServices())
                .accessTokenConverter(accessTokenConverter())
                .exceptionTranslator(webResponseExceptionTranslator);
    }
```
### 4.6 ResourceServer配置
资源服务器的配置，覆写了默认的配置。为了支持logout，这边自定义了一个`CustomLogoutHandler`并且将`logoutSuccessHandler`指定为返回http状态的`HttpStatusReturningLogoutSuccessHandler`。

```java
	@Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers().antMatchers("/**")
                .and().authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .addLogoutHandler(customLogoutHandler());
```

### 4.7 执行endpoint
1. 首先执行获取授权的endpoint。

```
method: post 
url: http://localhost:12000/oauth/token?grant_type=password
header:
{
	Authorization: Basic ZnJvbnRlbmQ6ZnJvbnRlbmQ=,
	Content-Type: application/x-www-form-urlencoded
}
body:
{
	username: keets,
	password: ***
}
```
上述构造了一个post请求，具体请求写得很详细。username和password是客户端提供给服务器进行校验用户身份信息。header里面的Authorization是存放的clientId和clientSecret经过编码的字符串。   
返回结果如下：

```
{   
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJYLUtFRVRTLVVzZXJJZCI6ImQ2NDQ4YzI0LTNjNGMtNGI4MC04MzcyLWMyZDYxODY4ZjhjNiIsImV4cCI6MTUwODQ0Nzc1NiwidXNlcl9uYW1lIjoia2VldHMiLCJqdGkiOiJiYWQ3MmIxOS1kOWYzLTQ5MDItYWZmYS0wNDMwZTdkYjc5ZWQiLCJjbGllbnRfaWQiOiJmcm9udGVuZCIsInNjb3BlIjpbImFsbCJdfQ.5ZNVN8TLavgpWy8KZQKArcbj7ItJLLaY1zBRaAgMjdo",   
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJYLUtFRVRTLVVzZXJJZCI6ImQ2NDQ4YzI0LTNjNGMtNGI4MC04MzcyLWMyZDYxODY4ZjhjNiIsInVzZXJfbmFtZSI6ImtlZXRzIiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImJhZDcyYjE5LWQ5ZjMtNDkwMi1hZmZhLTA0MzBlN2RiNzllZCIsImV4cCI6MTUxMDk5NjU1NiwianRpIjoiYWE0MWY1MjctODE3YS00N2UyLWFhOTgtZjNlMDZmNmY0NTZlIiwiY2xpZW50X2lkIjoiZnJvbnRlbmQifQ.mICT1-lxOAqOU9M-Ud7wZBb4tTux6OQWouQJ2nn1DeE",
    "expires_in": 43195,
    "scope": "all",
    "X-KEETS-UserId": "d6448c24-3c4c-4b80-8372-c2d61868f8c6",
    "jti": "bad72b19-d9f3-4902-affa-0430e7db79ed",
    "X-KEETS-ClientId": "frontend"
}
```
可以看到在用户名密码通过校验后，客户端收到了授权服务器的response，主要包括access_ token、refresh_ token。并且表明token的类型为bearer，过期时间expires_in。笔者在jwt token中加入了自定义的info为UserId和ClientId。

2.鉴权的endpoint

```
method: post 
url: http://localhost:12000/oauth/check_token
header:
{
	Authorization: Basic ZnJvbnRlbmQ6ZnJvbnRlbmQ=,
	Content-Type: application/x-www-form-urlencoded
}
body:
{
	token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJYLUtFRVRTLVVzZXJJZCI6ImQ2NDQ4YzI0LTNjNGMtNGI4MC04MzcyLWMyZDYxODY4ZjhjNiIsImV4cCI6MTUwODQ0Nzc1NiwidXNlcl9uYW1lIjoia2VldHMiLCJqdGkiOiJiYWQ3MmIxOS1kOWYzLTQ5MDItYWZmYS0wNDMwZTdkYjc5ZWQiLCJjbGllbnRfaWQiOiJmcm9udGVuZCIsInNjb3BlIjpbImFsbCJdfQ.5ZNVN8TLavgpWy8KZQKArcbj7ItJLLaY1zBRaAgMjdo
}
```
上面即为check_token请求的详细信息。需要注意的是，笔者将刚刚授权的token放在了body里面，这边可以有多种方法，此处不扩展。

```
{
    "X-KEETS-UserId": "d6448c24-3c4c-4b80-8372-c2d61868f8c6",
    "user_name": "keets",
    "scope": [
        "all"
    ],
    "active": true,
    "exp": 1508447756,
    "X-KEETS-ClientId": "frontend",
    "jti": "bad72b19-d9f3-4902-affa-0430e7db79ed",
    "client_id": "frontend"
}
```
校验token合法后，返回的response如上所示。在response中也是展示了相应的token中的基本信息。

3.刷新token
由于token的时效一般不会很长，而refresh_ token一般周期会很长，为了不影响用户的体验，可以使用refresh_ token去动态的刷新token。

```
method: post 
url: http://localhost:12000/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJYLUtFRVRTLVVzZXJJZCI6ImQ2NDQ4YzI0LTNjNGMtNGI4MC04MzcyLWMyZDYxODY4ZjhjNiIsInVzZXJfbmFtZSI6ImtlZXRzIiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImJhZDcyYjE5LWQ5ZjMtNDkwMi1hZmZhLTA0MzBlN2RiNzllZCIsImV4cCI6MTUxMDk5NjU1NiwianRpIjoiYWE0MWY1MjctODE3YS00N2UyLWFhOTgtZjNlMDZmNmY0NTZlIiwiY2xpZW50X2lkIjoiZnJvbnRlbmQifQ.mICT1-lxOAqOU9M-Ud7wZBb4tTux6OQWouQJ2nn1DeE
header:
{
	Authorization: Basic ZnJvbnRlbmQ6ZnJvbnRlbmQ=
}
```
其response和/oauth/token得到正常的相应是一样的，此处不再列出。

4.注销token

```
method: get
url: http://localhost:9000/logout
header:
{
	Authorization: Basic ZnJvbnRlbmQ6ZnJvbnRlbmQ=
}
```
注销成功则会返回200，注销端点主要是将token和SecurityContextHolder进行清空。


## 5. 总结
本文是《认证鉴权与API权限控制在微服务架构中的设计与实现》系列文章的总述，从遇到的问题着手，介绍了项目的背景。通过调研现有的技术，并结合当前项目的实际，确定了技术选型。最后对于系统的最终的实现进行展示。后面将从实现的细节，讲解本系统的实现。敬请期待后续文章。

---
### 参考
1. [理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
2. [微服务API级权限的技术架构](http://blog.csdn.net/OmniStack/article/details/77881185?locationNum=10&fps=1)
3. [微服务架构下的安全认证与鉴权](http://www.jianshu.com/p/15d0a1c366b3)
