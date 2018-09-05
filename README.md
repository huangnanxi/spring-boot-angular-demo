# SpringBoot教程梳理


大家都是老手了。 Spring的东西就那么多，不详细的一一讲了。这里以demo为例。通过demo取了解springboot。


## 跟着demo学习springboot

### 工程结构。如图：
 

5个module:
. demo-portal：controller层的东西
. demo-service：
. demo-service-client：对外暴露服务的接口定义
. demo-service-dmo：数据库实体定义
. demo-service-service：服务实现
. demo-common：常量、工具类、全局配置
. demo-main：可执行程序
. demo-test：



父pom：
 
需要注明parent。这里用的是springboot 1.5.9.RELEASE 的版本。

然后定义了下其它pom依赖以及内部module依赖的版本管理
 



几个核心的pom依赖介绍
加载自定义配置类所需：
 

SpringBoot，Web工程必需
 

SpringBoot，单元测试必需
 

SpringBoot，开发热部署必需
 

springboot应用入口类：
 

class上加注解：@SpringBootApplication，标明这是一个springboot的应用。
	注解：@SpringBootApplication是多个springboot注解的聚合。
	其中三个比较重要
	@Configuration（@SpringBootConfiguration点开查看发现里面还是应用了@Configuration）
	@EnableAutoConfiguration
	@ComponentScan

@ Configuration是标识一个class是IoC容器的配置类（SpringBoot鼓励基于JavaConfig的配置形式）

@ComponentScan 动扫描并加载符合条件的组件（比如@Component和@Repository等）或者bean定义，最终将这些bean定义加载到IoC容器中。默认是从声明@ComponentScan所在类的package进行扫描。（也可通过参数自定义）

@EnableAutoConfiguration 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器


PS：Demo中的配置如下。把数据库配置的自动装载给去掉了。原因是：demo中用到了多数据源，用的是自定义的数据库配置
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })

装载自定义的xml配置
如下，加上这个注解即可（按照springbot的理念（去xml化），此种方式不推荐）
@ImportResource(locations = "classpath:spring/application-aop.xml")


SpringBoot应用配置文件
如下图，application.yml是SpringBoot应用启动加载的配置文件
 


有两种properties的配置文件格式*.properties;	.yml。这里采用的是*.yml的格式。


在demo中，利用profile做了不同环境propertis文件配置的分离
 
如图。选用的是dev环境的配置文件。Springboot应用启用时会去加载application-dev.yml的配置文件

接下来我们看看application-dev.yml配置文件的内容。和*.properties配置文件相似，是key values结构的。不同的是该种配置方式支持树状结构，结构更清晰
 



另外配了logback，和普通的spring应用配起来没啥区别



demo-portal

没什么好讲的。和spring MVC差不多

如果是restful风格的接口，在controller class上加注解：@RestController。普通的接口，那还是@Controller



demo-service
Dao层和普通的spring应用有些区别
	可以选用mapper的方式使用mybatis
	 
	 
	也可以使用注解的方式，在Java class中用接口的方式定义服务	
	 

PS：使用spring-data-jpa，只需要定义接口，都不需要写SQL（通过接口名适配），是很方便的。

Demo-common
这里着重将一些config
 


前面提到了。Springboot推荐使用JavaConfig的配置形式。Demo中配置了：
	数据库配置（DataSourceConfig、MybatisPrimaryConfig、MybatisSecondaryConfig）
	Redis的配置：RedisConfig
	自定义属性类：DemoInfo

关于多数据源的配置
该类配置了两个数据库的DataSource的bean。通过@ConfigurationProperties注解注明加载的是application.yml中的哪个配置项
 


该类配置了主库SessioFactory、SessionTemplate、事务的配置。@MapperScan注解标明了包路径‘com.transwarp.demo.service.dao.primary’下的mapper走的是primary库。
同理另外一个配置类MybatisSecondaryConfig 是包路径‘com.transwarp.demo.service.dao.secondary’下的mapper走的是secondary 库 

关于自定义配置类
定义一个class与application.yml中的属性对应。然后class上加上如下注解。就可以通过@Autowired的方式使用它了
 
 

Demo-test
使用起来和junitTest区别不大

还可以test url
 


 
SpringBoot应用监控
Actuator监控
加上pom依赖
 

加上监控的配置



可以通过下述restful 接口查看监控信息
Actuator 提供了 13 个接口，具体如下表所示。
HTTP 方法	路径	描述
GET	/autoconfig	提供了一份自动配置报告，记录哪些自动配置条件通过了，哪些没通过
GET	/configprops	描述配置属性(包含默认值)如何注入Bean
GET	/beans	描述应用程序上下文里全部的Bean，以及它们的关系
GET	/dump	获取线程活动的快照
GET	/env	获取全部环境属性
GET	/env/{name}	根据名称获取特定的环境属性值
GET	/health	报告应用程序的健康指标，这些值由HealthIndicator的实现类提供
GET	/info	获取应用程序的定制信息，这些信息由info打头的属性提供
GET	/mappings	描述全部的URI路径，以及它们和控制器(包含Actuator端点)的映射关系
GET	/metrics	报告各种应用程序度量信息，比如内存用量和HTTP请求计数
GET	/metrics/{name}	报告指定名称的应用程序度量值
POST	/shutdown	关闭应用程序，要求endpoints.shutdown.enabled设置为true
GET	/trace	提供基本的HTTP请求跟踪信息(时间戳、HTTP头等)

spring-boot-admin监控
参考：
https://github.com/codecentric/spring-boot-admin
https://www.jianshu.com/p/e20a5f42a395
 
深入探索SpringApplication执行流程
SpringApplication的run方法的实现是我们本次旅程的主要线路，该方法的主要流程大体可以归纳如下：

1.	如果我们使用的是SpringApplication的静态run方法，那么，这个方法里面首先要创建一个SpringApplication对象实例，然后调用这个创建好的SpringApplication的实例方法。在SpringApplication实例初始化的时候，它会提前做几件事情：
a)	根据classpath里面是否存在某个特征类（org.springframework.web.context.ConfigurableWebApplicationContext）来决定是否应该创建一个为Web应用使用的ApplicationContext类型。
b)	使用SpringFactoriesLoader在应用的classpath中查找并加载所有可用的ApplicationContextInitializer。
c)	使用SpringFactoriesLoader在应用的classpath中查找并加载所有可用的ApplicationListener。
d)	推断并设置main方法的定义类。
2.	SpringApplication实例初始化完成并且完成设置后，就开始执行run方法的逻辑了，方法执行伊始，首先遍历执行所有通过SpringFactoriesLoader可以查找到并加载的SpringApplicationRunListener。调用它们的started()方法，告诉这些SpringApplicationRunListener，“嘿，SpringBoot应用要开始执行咯！”。
3.	创建并配置当前Spring Boot应用将要使用的Environment（包括配置要使用的PropertySource以及Profile）。
4.	遍历调用所有SpringApplicationRunListener的environmentPrepared()的方法，告诉他们：“当前SpringBoot应用使用的Environment准备好了咯！”。
5.	如果SpringApplication的showBanner属性被设置为true，则打印banner。
6.	根据用户是否明确设置了applicationContextClass类型以及初始化阶段的推断结果，决定该为当前SpringBoot应用创建什么类型的ApplicationContext并创建完成，然后根据条件决定是否添加ShutdownHook，决定是否使用自定义的BeanNameGenerator，决定是否使用自定义的ResourceLoader，当然，最重要的，将之前准备好的Environment设置给创建好的ApplicationContext使用。
7.	ApplicationContext创建好之后，SpringApplication会再次借助Spring-FactoriesLoader，查找并加载classpath中所有可用的ApplicationContext-Initializer，然后遍历调用这些ApplicationContextInitializer的initialize（applicationContext）方法来对已经创建好的ApplicationContext进行进一步的处理。
8.	遍历调用所有SpringApplicationRunListener的contextPrepared()方法。
9.	最核心的一步，将之前通过@EnableAutoConfiguration获取的所有配置以及其他形式的IoC容器配置加载到已经准备完毕的ApplicationContext。
10.	遍历调用所有SpringApplicationRunListener的contextLoaded()方法。
11.	调用ApplicationContext的refresh()方法，完成IoC容器可用的最后一道工序。
12.	查找当前ApplicationContext中是否注册有CommandLineRunner，如果有，则遍历执行它们。
13.	正常情况下，遍历执行SpringApplicationRunListener的finished()方法、（如果整个过程出现异常，则依然调用所有SpringApplicationRunListener的finished()方法，只不过这种情况下会将异常信息一并传入处理）
去除事件通知点后，整个流程如下：
 

附学习资源文档：
➢	SpringBoot各版本官方文档：
	https://docs.spring.io/spring-boot/docs/

➢	SpringBoot各种学习demo源码
	https://github.com/JeffLi1993/springboot-learning-example
