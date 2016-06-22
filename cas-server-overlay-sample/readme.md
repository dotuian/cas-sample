## cas server 证书的配置

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



## 用户登录验证表结构 ##    
```
    CREATE TABLE users (
        id BIGINT(20) NOT NULL AUTO_INCREMENT,
        username CHAR(50) NOT NULL,
        password CHAR(50) NOT NULL,
        email CHAR(50) NULL DEFAULT NULL,
        sex ENUM('F','M') NOT NULL,
        birthday DATE NULL DEFAULT NULL,
        role ENUM('TEACHER','STUDENT','ADMIN') NULL DEFAULT NULL,
        version INT(11) NOT NULL DEFAULT '1',
        create_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
        update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (id),
        UNIQUE INDEX username (username)
    )
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB;
```

### 通过指定数据库表的验证用户 ###
+ 在 deployerConfigContext.xml 文件中配置信息

```
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property name="driverClassName">
            <value>com.mysql.jdbc.Driver</value>
        </property>
        <property name="url">
            <value>jdbc:mysql://127.0.0.1:3306/authdb</value>
        </property>
        <property name="username">
            <value>root</value>
        </property>
        <property name="password">
            <value>rootadmin</value>
        </property>
    </bean>

    <property name="authenticationHandlers">
        <list>
            <bean class="org.jasig.cas.adaptors.jdbc.QueryDatabaseAuthenticationHandler">
                <property name="dataSource" ref="dataSource" />
                <property name="sql" value="select password from users where lower(username) = lower(?)" />
            </bean>
        </list>
    </property>
```

### 通过代码验证登录用户 ###
+ 集成抽象类 AbstractUsernamePasswordAuthenticationHandler，实现抽象方法 authenticateUsernamePasswordInternal 。  
+ 在 deployerConfigContext.xml 文件中配置信息。
```
    <property name="authenticationHandlers">
        <list>
            <bean class="com.dotuian.cas.UsernamePasswordAuthenticationHandler" />
        </list>
    </property>
```

###　服务器返回多个数据　###
+ 配置 deployerConfigContext.xml 中的 attributeRepository

```
    <bean id="attributeRepository"
        class="org.jasig.services.persondir.support.jdbc.SingleRowJdbcPersonAttributeDao">
        <constructor-arg index="0" ref="dataSource"/>
        <constructor-arg index="1" value="select * from users where {0}"/>
        
        <property name="queryAttributeMapping">
            <map>
                <!-- 这里的key需写username和登录页面一致，value对应数据库用户名字段 -->  
                <entry key="username" value="username"/>  
            </map>
        </property>

        <property name="resultAttributeMapping">
            <map>
                <!-- key为对应的数据库字段名称，value为提供给客户端获取的属性名字，系统会自动填充值 -->
                <entry key="id" value="id"/>
                <entry key="email" value="email"/>
                <entry key="birthday" value="birthday"/>
                <entry key="sex" value="sex"/>
                <entry key="role" value="role"/>
            </map>
        </property>
    </bean>
```
    
+ 配置 deployerConfigContext.xml 中的 InMemoryServiceRegistryDaoImpl 中的 allowedAttributes。
```
    <bean
        id="serviceRegistryDao"
        class="org.jasig.cas.services.InMemoryServiceRegistryDaoImpl">
            <property name="registeredServices">
                <list>
                    <bean class="org.jasig.cas.services.RegexRegisteredService">
                        <property name="id" value="0" />
                        <property name="name" value="HTTP and IMAP" />
                        <property name="description" value="Allows HTTP(S) and IMAP(S) protocols" />
                        <property name="serviceId" value="^(https?|imaps?)://.*" />
                        <property name="evaluationOrder" value="10000001" />
                        <property name="allowedAttributes">
                            <!-- 客户端需要使用的对象的属性名称 -->
                            <list>
                                <value>id</value>
                                <value>email</value>
                                <value>sex</value>
                                <value>birthday</value>
                                <value>role</value>
                            </list>
                        </property>
                    </bean>
                </list>
            </property>
    </bean>
```

+ 修改文件 WEB-INF/view/jsp/protocol/2.0/casServiceValidationSuccess.jsp，[详细参照](https://github.com/dotuian/cas-sample/blob/master/cas-server-overlay-sample/src/main/webapp/WEB-INF/view/jsp/protocol/2.0/casServiceValidationSuccess.jsp)
 
