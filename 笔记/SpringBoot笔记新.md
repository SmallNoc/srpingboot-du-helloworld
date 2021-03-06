# SpringBoot原理初探

## 启动类

```
@SpringBootApplication 该注解表示这个类是一个SpringBoot应用 :启动类下所有资源被导入
```

```java
@SpringBootConfiguration :SpringBoot的配置
    @Configuration：	Spring的配置
    	@component:	说明这是一个Spring的组件

        
@EnableAutoConfiguration ： 自动配置
    @AutoConfigurationPackage: 自动配置包
        @Import({AutoConfigurationPackage.Registrar.class}):自动配置 ‘包注册’
    @Import({AutoConfigurationImportSelector.class})： 自动配置导入选择
        
//获取所有的配置
protected AutoConfigurationImportSelector类AutoConfigurationEntry getAutoConfigurationEntry方法中
List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);取得所有配置
```

```java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    //getSpringFactoriesLoaderFactoryClass 方法加载选择器获取所有配置内容
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
        return configurations;
    }

//META-INF/spring.factories 自动配置的核心文件

// 返回配置
 protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return EnableAutoConfiguration.class;
    }
```

META-INF/spring.factories 自动配置的核心文件

![image-20210608145714831](SpringBoot笔记新.assets/image-20210608145714831.png)

![image-20210608151800932](SpringBoot笔记新.assets/image-20210608151800932.png)

## 思维导图

![image-20210608160458182](SpringBoot笔记新.assets/image-20210608160458182.png)

## 结论：

SpringBoot所有自动配置都是在启动时扫描并加载：

所有的自动配置类都在spring.factories文件中，但是不一定全部加载生效，需要通过@ConditionalOnXXX注解判断是否为true

只有为true时，才会导入对应的Start,对应的启动器才会启动，自动装配才会生效，才能配置成功。

![image-20210608162033945](SpringBoot笔记新.assets/image-20210608162033945.png)

## Run方法

<img src="SpringBoot笔记新.assets/1112095-20181115161924186-928667393.png" alt="img"  />

https://www.cnblogs.com/shamo89/p/8184960.html

https://www.jianshu.com/p/ef6f0c0de38f

https://blog.csdn.net/weixin_39546747/article/details/111169632







# Ymal

相对于properties的键值对写法ymal写法更简洁，容易理解，并且对于数据的封装以及数据类型更有优势

```yaml
#对空格要求十分严格，尤其注意空格缩进
server:
  port: 8088

#Ymal 可以写对象
cat:
  name: qiutian
  age: 3
#行内写法
cat2: {name: qiutian,age: 3}

#数组
pets:
  - cat
  - dog
  - pig

pets2: [cat,dog,pig]

# 可把值注入到配置文件中
person:
  name: dyd
  age: 26
  happy: true
  birth: 4/7
  maps: {k1: v1,k2: v2}
  lists:
    - code
    - music
    - nagirl
  dog:
    name: 夏天
    age:  1

```

![image-20210610155636490](SpringBoot笔记新.assets/image-20210610155636490.png)

通过@ConfigurationProperties(prefix = "XXX")可以将Ymal中的对象的值映射到绑定的类中





**Ymal同时也支持松散绑定 例如类中属性为dydName  Ymal中可以写为dyd-name**

**Ymal支持jsr303校验**

## jsr303注解

![image-20210610180254354](SpringBoot笔记新.assets/image-20210610180254354.png)

![image-20210610180528060](SpringBoot笔记新.assets/image-20210610180528060.png)

## yaml配置文件可以写再已下几个项目路径中

​		

​	1	./config/application.yaml

​	2	./application.yaml

​	3	./classpath:/config

​	4	./classpath:/

加载顺序优先等级为1234



## 选择启动模块

```yaml
server:
  port: 8080
#选择启动模块
spring:
  profiles:
    active: test

---
server:
  port: 8081
spring:
  profiles: dev

---
server:
  port: 8082
spring:
  profiles: test
```

另外：关于yaml的配置，每一个配置实际都对应于一个实体类，实体类中都有@ConfigurationProperties进行绑定

## 自动装配原理

![image-20210615161957583](SpringBoot笔记新.assets/image-20210615161957583.png)



# SpringBoot web开发

![image-20210615164820891](SpringBoot笔记新.assets/image-20210615164820891.png)

## 静态资源

WebMvcAutoConfiguration.class 中

```java
//加载静态资源配置
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    		//如果配置文件中有，直接返回
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
            } else {
                this.addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
                this.addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
                    registration.addResourceLocations(this.resourceProperties.getStaticLocations());
                    if (this.servletContext != null) {
                        ServletContextResource resource = new ServletContextResource(this.servletContext, "/");
                        registration.addResourceLocations(new Resource[]{resource});
                    }

                });
            }
        }
```

![image-20210615172057888](SpringBoot笔记新.assets/image-20210615172057888.png)

webjars方式导入静态资源基本不再使用

![image-20210617162921872](SpringBoot笔记新.assets/image-20210617162921872.png)



Springboot  静态资源的优先级是 resource  > satatic(默认) > public > /**

另外templates下放的静态资源只能通过Controller进行访问

```java
//在templates目录下所有页面资源，只有通Controller来进行访问
//需要模板引擎的支持 thymeleaf
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
```

## 模板引擎  thymeleaf

```xml
		<!--pom依赖-->
		<dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-java8time</artifactId>
        </dependency>
```

![image-20210617165546678](SpringBoot笔记新.assets/image-20210617165546678.png)

导入对应的pom依赖，将html文件放入templates目录下通过Controller就可以访问到资源

html使用thymeleaf需要引入表头

```html
<!--xmlns:th="http://www.thymeleaf.org"-->
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>我的抬头纹</title>
</head>
<body>
<h1>首页</h1>
<!--所有html元素都可被thymeleaf替换接管： th：元素名-->
<h2 th:text="${msg}"></h2>
<div th:text="${msg}"></div>
</body>
</html>
```

![image-20210617165546678](SpringBoot笔记新.assets/image-20210617165546678.png)



## 扩展MVC  自定义组件 并且交给SpingBoot

主要是实现webMvcConfigurer类 并通过@Bean注册，实现自动装配自己的组件

![image-20210615161805333](SpringBoot笔记新.assets/image-20210615161805333.png)

![image-20210615161836151](SpringBoot笔记新.assets/image-20210615161836151.png

![image-20210617180711147](SpringBoot笔记新.assets/image-20210615161957583.png)

# SpringBoot项目学习代码

## 代码学习详见工程

### 首页配置

1.注意点：所有页面的静态资源都需要使用thymeleaf接管 	@{}

```html
<!DOCTYPE html>
<!-- 引用 xmlns:th="http://www.thymeleaf.org  -->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Signin Template for Bootstrap</title>
		<!-- 使用thymeleaf  th:href="@{/}" 接管静态资源  -->
		<link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
		<!-- Custom styles for this template -->
		<link th:href="@{/css/signin.css}" rel="stylesheet">
	</head>

	<body class="text-center">
		<form class="form-signin" action="dashboard.html">
			<img class="mb-4" th:src="@{/img/bootstrap-solid.svg}" alt="" width="72" height="72">
			<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
			<label class="sr-only">Username</label>
			<input type="text" class="form-control" placeholder="Username" required="" autofocus="">
			<label class="sr-only">Password</label>
			<input type="password" class="form-control" placeholder="Password" required="">
			<div class="checkbox mb-3">
				<label>
          <input type="checkbox" value="remember-me"> Remember me
        </label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			<p class="mt-5 mb-3 text-muted">© 2017-2018</p>
			<a class="btn btn-sm">中文</a>
			<a class="btn btn-sm">English</a>
		</form>

	</body>
```

## 2.页面国际化

首先需要配置中增加语言包配置  例如：i18n

![image-20210707153046024](SpringBoot笔记新.assets/image-20210707153046024.png)

通过login.properties增加配置内容，**login_en_US.properties**与**login_zh_CN.properties**进行相同的配置，但使用不同的语言作为value

![image-20210707153343669](SpringBoot笔记新.assets/image-20210707153343669.png)

随即在**application.yaml或application.properties**中进行**spring.messages.basename**配置

![image-20210707153433520](SpringBoot笔记新.assets/image-20210707153433520.png)

此时国际化的配置就算配置完成，使用时需要在页面 通过前端的引入对应配置的Key进行语言的转换 

例如：#{login.tip}

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Signin Template for Bootstrap</title>
		<!-- Bootstrap core CSS -->
		<link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
		<!-- Custom styles for this template -->
		<link th:href="@{/css/signin.css}" rel="stylesheet">
	</head>

	<body class="text-center">
		<form class="form-signin" action="dashboard.html">
			<img class="mb-4" th:src="@{/img/bootstrap-solid.svg}" alt="" width="72" height="72">
			<h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
			<input type="text" class="form-control" th:placeholder="#{login.username}" required="" autofocus="">
			<input type="password" class="form-control" th:placeholder="#{login.password}" required="">
			<div class="checkbox mb-3">
				<label>
          <input type="checkbox" value="remember-me" >[[#{login.remember}]]
        </label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit" >[[#{login.btn}]]</button>
			<p class="mt-5 mb-3 text-muted">© 2017-2018</p>
			<a class="btn btn-sm">中文</a>
			<a class="btn btn-sm">English</a>
		</form>

	</body>

</html>
```

