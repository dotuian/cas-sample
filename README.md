# cas-sample
单点登录认证的例子






Tomcat 8 
Java 8 



keytoolエラー: java.lang.Exception: 鍵ペアは生成されませんでした。別名<tomcat>はすでに存在します

解决方法：　执行　keytool -delete -alias　<别名>

keystore的密码
<别名>的键密码： 

1. 生成 Certificate Keystore
D:\coding\CAS>keytool -genkey -alias tomcat -keyalg RSA -keystore keystore
keystore的密码： test1234
<Tomcat>的键密码： test1234

2. tomcat的 server.xml 的配置
<!-- Define a SSL Coyote HTTP/1.1 Connector on port 8443 -->
<Connector
           protocol="org.apache.coyote.http11.Http11NioProtocol"
           port="8443" maxThreads="200"
           scheme="https" secure="true" SSLEnabled="true"
           keystoreFile="${user.home}/.keystore" keystorePass="changeit"
           clientAuth="false" sslProtocol="TLS"/>

		   
> (参考：)[https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html#Prepare_the_Certificate_Keystore]

3. 在eclipse中通过Maven插件创建webapp的项目，项目名为：cas-overlay-sample。 pom.xml 文件内容如下：
```

```








Yii1的web工程创建
1. 下载Yii1框架源代码 http://www.yiiframework.com/download/ 。
2. 解压之后，通过 `yii/framework/yiic.bat webapp 项目名`来自动生成源代码。
3. 下载phpCas，解压到yii项目中的 protected/extentions 目录中。











