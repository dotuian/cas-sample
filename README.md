# cas-sample
单点登录认证的例子

======================================================
cas server 证书的配置
======================================================

+ 生成证书
```
keytool -genkey -alias cas -keyalg RSA -keystore cas.keystore
keystore的密码： test1234
<cas>的键密码： test1234
```

+ 导出公钥信息
```
keytool -export -file cas.crt -alias cas -keystore cas.keystore
キーストアのパスワードを入力してください:
証明書がファイル<cas.crt>に保存されました
```

+ 导入公钥到JRE
```
keytool -import -keystore "${jdk_path}\jre\lib\security\cacerts" --file cas.crt -alias cas
```
> 执行需要管理者权限，Windows下需要以 管理者 运行cmd。

+ tomcat的 server.xml 的配置
```
<!-- Define a SSL Coyote HTTP/1.1 Connector on port 8443 -->
<Connector protocol="org.apache.coyote.http11.Http11NioProtocol"
           port="8443" maxThreads="200"
           scheme="https" secure="true" SSLEnabled="true"
           clientAuth="false" sslProtocol="TLS"
           keystoreFile="${user.home}/cas.keystore" keystorePass="test1234"
           />
```

[Tomcat下SSL配置](https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html#Prepare_the_Certificate_Keystore)  
[证书制作过程](http://steven-wiki.readthedocs.io/en/latest/security/cas-tomcat/)




Yii1的web工程创建
1. 下载Yii1框架源代码 http://www.yiiframework.com/download/ 。
2. 解压之后，通过 `yii/framework/yiic.bat webapp 项目名`来自动生成源代码。
3. 下载phpCas，解压到yii项目中的 protected/extentions 目录中。





======================================================
认证相关的概念及流程
======================================================
+ Credentials 
用户提供的用于登录用的凭据信息，如用户名/ 密码、证书、IP 地址、Cookie 值等。比如 UsernamePasswordCredentials ，封装的是用户名和密码。CAS 进行认证的第一步，就是把从UI 或request 对象里取到的用户凭据封装成Credentials 对象，然后交给认证管理器去认证。  
+ AuthenticationHandler 
认证Handler, 每种AuthenticationHandler 只能处理一种Credentials ，如AbstractUsernamePasswordAuthenticationHandler 只负责处理 U sernamePasswordCredentials 。  
+ Principal 
封装用户标识，比如 SimplePrincipal, 只是封装了用户名。认证成功后，credentialsToPrincipalResolvers 负责由 Credentials 生成 Principal 对象。  
+ CredentialsToPrincipalResolvers 
负责由 Credentials 生成 Principal 对象，每种CredentialsToPrincipalResolvers 只处理 一种Credentials ，比如UsernamePasswordCredentialsToPrincipalResolver 负责从 U sernamePasswordCredentials 中取出用户名，然后将其赋给生成的 SimplePrincipal 的 ID 属性。  
+ AuthenticationMetaDataPopulators 
负责将 Credentials 的一些属性赋值给 Authentication 的 attributes属性。  
+ Authentication   
Authentication是认证管理器的最终处理结果， Authentication 封装了 Principal ，认证时间，及其他一些属性（可能来自 Credentials ）。  
+ AuthenticationManager 
认证管理器得到 Credentials 对象后，负责调度AuthenticationHandler 去完成认证工作，最后返回的结果是 Authentication 对象。  
+ CentralAuthenticationService  
CAS 的服务类，对 Web 层提供了一些方法。该类还负责调用AuthenticationManager 完成认证逻辑。  





