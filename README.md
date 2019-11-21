# 微信点餐系统项目源码阅读指引

**该项目后端部分使用到的技术与工具有：SpringBoot，Spring，SpringMVC，Mybatis，MySql，Redis，WebSocket，微信公众平台sdk，Nginx，Maven等**

![markdown](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy1ibG9nLmNzZG5pbWcuY24vMjAxOTA1MjExNDIzMjQxNzMuanBlZw?x-oss-process=image/format,png "markdown")

**简介：该项目为基于微信公众号平台的企业点餐系统，实现了商家与客户在点餐以及订单管理部分的便利化
并且基于微信平台，可免除商家与用户在登录权限方面的一些繁琐操作，可直接利用微信公众平台sdk来进行一系列授权
系统功能从用户方面分为两个部分：卖家端与买家端。  
1.展示给买家端的是一个基于微信公众号的类似“饿了么”app的点餐平台，可以进行商品的挑选，付款，退款等常规功能**

**2.展示给卖家端的是一个网页版的后台管理系统，可进行微信扫码登陆获取访问授权，订单管理，商品上下架和添加修改，类目添加修改等功能
（在卖家端与买家端之间使用WebSocket进行实时通信，实现“客户下单，在卖家端发出提醒如播放音乐”的功能）**

## 1.项目运行环境安装预备
1.前端代码以及nginx和redis等的部署已经配置到了已有的Linux虚拟机中，如果需要可以进行联系获取。
另外jdk8.0以上就可以，其他的springboot会自动配置好

2.修改虚拟机中的配置：
```javascript
cd /opt/code/sell_fe_buyer/config
vim index.js
```
然后把对应的ip配置为自己本地ip:端口号
执行
```javascript
ifconfig
```
查看虚拟机ip，然后浏览器访问ip，看是否会跳转“请在客户端打开该界面”的界面，如果有，则成功啦！

## 2.源码中各文件作用的简单解释
#### src/main/java/com/jay/wechat/下的各个包：
	Test：书写dao层或者service层所进行的各个测试
	aspect：面向切面编程，用来进行后台管理系统的访问权限校验
	bean：实类对象，对应MySql数据库中的各个字段
	config：配置类，用来提前配置好某些类，使用时可以直接被依赖注入
	constant：储存某些常量
	controller：controller层，对外接口
	convertor：类转化，比如说把从前端接收到的对象，转化为与数据库相匹配的bean类
	dao：dao层，连接service层与数据库
	enums：枚举层，主要用来储存异常情况所对应的枚举
	exception：自定义异常
	form：书写实类，这里的类全部用来接受前端提交的数据
	handler：书写对特定异常的处理方法，这里主要与aspect层抛出的异常配合使用
	service：服务层，书写主要业务逻辑
	utils：用来初始化某些值，比如获得一串随机的字符串作为订单id，或者把json字符串转为实例对象
	viewobject：前端所需的格式对应的实类
#### /src/main/resources/下的各个包：
	mapper:mapper文件，书写sql语句，与dao层对应
	templates：后台管理系统的ftl文件
	static：需要引入的文件，入mp3文件，错误界面等

## 3.微信授权部分
![markdown](https://imgconvert.csdnimg.cn/aHR0cHM6Ly90aW1nc2EuYmFpZHUuY29tL3RpbWc_aW1hZ2UmcXVhbGl0eT04MCZzaXplPWI5OTk5XzEwMDAwJnNlYz0xNTc0MzQyNDk2ODMzJmRpPTJmNDQyNWFlMmM1MzFkOTYxNmUwZTJiYzg1YTExNWE5JmltZ3R5cGU9MCZzcmM9aHR0cCUzQSUyRiUyRmltYWdlLmJpYW9iYWlqdS5jb20lMkZ1cGxvYWRzJTJGMjAxODA4MDIlMkYwMyUyRjE1MzMxNTI5MTUtc2hwYmFja2VYRS5qcGc?x-oss-process=image/format,png)
这里需要使用服务号，才能有支付功能，服务号的申请需要企业认证，所以==你懂的，如果实在没有的话，用个订阅号测试号也可以（主要是拿到appId，appSecret等公众号的标识性资料，后面授权要用），但是就是每次进行支付和后台扫码登录的时候就会报错了。

**这里再讲解下微信授权部分的一些逻辑,以客户进入客户端，服务端通过微信平台获得用户openid（微信用户的唯一标识）找个过程为例：**
1.对应的接口在controller包下的WechatController类中，对应为authorize和userinfo这两个方法
2.
```javascript
@Autowired
private WxMpService wxMpService;
```
WxMpService是第三方sdk提供的类，需要通过config配置好公众号appId等属性，然后写好returnUrl，然后进行跳转。会跳转到这里
```javascript
https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
```
然后获得code之后，再以code和returnUrl为参数访问userInfo接口，userInfo通过code去获得access_token和openid等信息，再把openid拼接到returnUrl上（returnUrl一般为点餐界面，没有openid传来的话，无法进入该界面），访问returnUrl，可以进入点餐界面

**再讲解下微信付款：**
1.对应的接口在controller下的PayController中，先看create接口。   用户下单成功，后端给订单分配好订单id，并转化成OrderMaster相关信息存在数据库中，向前端返回订单id，前端再用该订单id访问支付的create接口，同时还传来一个returnUrl（为支付成功后跳转的界面，这里设置为了订单详情界面，这是合理的）。
2.后端根据订单id查询到对应的订单信息，填入PayRequest中（如订单号，openid，订单金额），再用PayService（同样在config中配置好了appId，商家id等之后自动注入）对PayRequest使用pay方法，获得PayResponse对象，这里就储存了预付款信息，然后跳转对应的界面.ftl，把PayResponse对象中的某些属性动态填写进ftl文件中，用户访问该界面，就是我们熟悉的付款界面了


## 4.存在的问题
在使用WebSocket进行前后端实时通信时，经过某段时间之后，连接会自动断开，然后报错
No error handling configured for [com.jay.wechat.service.WebSocket] and the following error occurred
java.io.EOFException: null

查找后可能是因为springboot内置tomcat和WebSocket有冲突的原因
目前该问题尚未解决（但是迟早是会解决滴）

## 5.版本控制
1.0.1：<https://github.com/1185430411/springboot-wechat-order/tree/master/STEP1/wechat>

1.0.2（新增微信支付功能）：<https://github.com/1185430411/springboot-wechat-order/tree/master/STEP2/wechat>

1.0.3（新增后台管理扫码登录，AOP进行权限校验等功能）：<https://github.com/1185430411/springboot-wechat-order/tree/master/STEP3/wechat>

2.0.1（新增前后端实时通信功能:买家下单，卖家端音乐提醒）：<https://github.com/1185430411/springboot-wechat-order/tree/master/STEP4/wechat>

3.0.1（增用redis缓存，redis分布式锁。完善高并发场景下可能会出现的BUG，并提高响应性能。虽然这小破玩意基本不会有高并发场景（逃））:开发中.......

## 6.作者
JAYICE 华南理工大学
