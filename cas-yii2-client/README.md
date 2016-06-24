
### 安装composer
> 参考： http://docs.phpcomposer.com/01-basic-usage.html

### 添加cas所需要的包
修改 composer.json
```php 
    "require": {
        ***
        "jasig/phpCAS": "*",
        "alcad/yii2-cas": "dev-master"
    },
```

### 执行 php composer.phar install 安装下载cas包

### 访问使用cas模块进用户登录注销。
> http://localhost/cas-yii2-client/web/index.php?r=cas/cas/login
> http://localhost/cas-yii2-client/web/index.php?r=cas/cas/logout


