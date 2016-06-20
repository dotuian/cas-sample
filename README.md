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

+ 导出证书
```
keytool -export -file cas.crt -alias cas -keystore cas.keystore
キーストアのパスワードを入力してください:
証明書がファイル<cas.crt>に保存されました
```

+ 导入证书到JRE
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











