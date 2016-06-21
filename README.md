# Java EE Bootstrap tutorial and examples

This project contains examples and tutorials for Java EE 7 edition watching Adam Bien videos from: 

[https://vimeo.com/ondemand/javaeebootstrap]()

-----

## Project Folder `MyOwnAnnotation`

### video 03.Annotations in Java EE

This little project shows a basic Annotation example

-----

## Project Folder `DependencyInjection`

### videos 06-07 Interface Injection, Dependency Injecion and Inversion of Control

to simulate the behaviour of an application server, we have to inject Interfaces and not Classes, so to demonstrate this we change the Service class to an Interface and create an implementation called ServiceImpl (this is not a nice name because it doesn't say anything about the responsibility of the class, a better name would be ServiceConfiguration or something like that). Then in the main we add lines of code to retrieve the implementation (there is only one here called ServiceImpl) and inject to the Facade.

In real application servers there is a **Scanner** that reads all the packages and sends the metadatas to an object called **Assembler** (the generic _Factory_ of th application server) for injection purpose.

### video 08 Proxies and Decorators

What the application server does is to add value when injecting classes. It creates Proxies to add functionalities, for example when the Service is an EJB it adds:

-	Transaction
-	Security
-	Threading
-	State Management / Pooling
-	Monitoring

`Facade --> $Proxy300 --> Service`

### 09 Aspects with Dynamic Proxy

We show here how to decorate the service variable in the Facade class.

1. Create the Decorator class
2. Create the Aspect (in this case a class LoggingAspect)
3. Inject the Decorator (proxy) rather than the Service itself

in the output you will see 2 calls to the LoggingAspect

the first one is the call to the invokeService() of the Facade that invokes the serve() method of ServiceImpl:

```
Before method public abstract void io.github.dinolupo.javaee.Service.serve()
Served from Impl!
Method invoked in 427737 ns
```

the second one is the call of the toString() method of the Facade object that originates from the main at line 45, (printf of facade variable):

```
Before method public java.lang.String java.lang.Object.toString()
Method invoked in 145755 ns
Facade{service=io.github.dinolupo.javaee.ServiceImpl@54bedef2, somethingElse='null'}
```

-----

## Project Folder `dependency-injection-with-server`

### video 12. Dependency Injection with server

This project shows how Dependency Injection works on Java EE server.

Video Title on Vimeo: __12.Dependency Injection (DI) on Java EE Server__

The best way to work with Java EE project is to start a Maven project based on an Archetype created by Adam Bien:

```sh
mvn archetype:generate -Dfilter=com.airhacks:javaee7-essentials-archetype
```

[https://github.com/AdamBien/javaee7-essentials-archetype]()

We choose here version 1.2 of the template.

To show the example with Java Server Faces, we create a web.xml file under the WEB-INF directory with the following content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.1" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd">
<context-param>
    <param-name>javax.faces.PROJECT_STAGE</param-name>
    <param-value>Development</param-value>
</context-param>
<servlet>
    <servlet-name>Faces Servlet</servlet-name>
    <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>Faces Servlet</servlet-name>
    <url-pattern>/faces/*</url-pattern>
</servlet-mapping>
<session-config>
    <session-timeout>
        30
    </session-timeout>
</session-config>
<welcome-file-list>
    <welcome-file>faces/index.xhtml</welcome-file>
</welcome-file-list>
</web-app>
```

You can import the Maven project into Idea, Netbeans or Eclipse and deploy on server.

You can see the Injected property in the Index class:

```java
@Inject
HelloWorldService helloWorldService;
```

### video 13. Introducing Aspects

We want to intercept the call to the EJB in the method `Index.getMessage()` and even if this seems difficult because Index uses the direct references to `HelloWorldService` we do this with the following code:

- Implement the aspect `MethodCallLogger` with only one method that return Object in the following way:

```java
public class MethodCallLogger {
    @AroundInvoke
    public Object log(InvocationContext invocationContext) throws Exception {
        long start = System.nanoTime();
        Method method = invocationContext.getMethod();
        try {
            invocationContext.proceed();
        } finally {
            System.out.printf("Method %s invoked in %d ns\n", method, System.nanoTime() - start);
        }
    }
}
```

- Annotate the `HelloWorldService` EJB with 

```java
@Interceptors(MethodCallLogger.class)
```

### video 14. Aspects in detail

Showing that Aspects are applied behind the Interceptor using: 

```java
return helloWorldService.getClass().getName();
```

in `HelloWorldService.getMessage()`

Also, deactivating the Interceptor does not change the Proxy generated by the ApplicationServer.

Output is something like:

```java
io.github.dinolupo.di.presentation.HelloWorldService$Proxy$_$$_Weld$EnterpriseProxy$
```

### video 15.Stereotypes -- The Better Macros

Stereotypes are like macros, here we create a new Stereotype names `Presenter` that behaves like the `@Model` annotation.

Substituting the `@Model` annotation with the new one `@Presenter` all works as before, because the `@Presenter` stereotype is expanded in `@Named` and `@RequestScope`

### video 16.CDI vs. EJB Lifecycle

We show here that `@RequestScope` annotation make the EJB to survive only one request. We show this introducing lifecyle methods like `@PostConstruct onInit()` and `@PreDestroy onDestroy()` into the Index and HelloWorldService class.

The output is similar to the following:

```
15:43:55,962 INFO  [stdout] (default task-51) Creating Index
15:43:55,964 INFO  [stdout] (default task-51) Creating EJB HelloWorldService
15:43:55,965 INFO  [stdout] (default task-51) Method public java.lang.String io.github.dinolupo.di.presentation.HelloWorldService.serve() invoked in 257268 ns
15:43:55,965 INFO  [stdout] (default task-51) Destroying Index
15:44:01,795 INFO  [stdout] (default task-52) Creating Index
15:44:01,796 INFO  [stdout] (default task-52) Method public java.lang.String io.github.dinolupo.di.presentation.HelloWorldService.serve() invoked in 58229 ns
15:44:01,796 INFO  [stdout] (default task-52) Destroying Index
```

showing that __Index__ is created and destroyed while the __EJB__ is created and never destroyed.


### video 17.CDI Lifecycles

Here we show how `@ApplicationScoped` and `@SessionScoped` work.

We create a Counter class and annotate it with the scope that we are going to test, and then create two classes to show the two different annotations. 

Running the project and opening two different browsers shows what happens:

- `@ApplicationScoped` annotated class `GlobalCounter`is never destroyed and so the counter is always incremented

- `@SessionScoped` annotated class `UserCounter` is created twice, once per browser, and so is incremented in every browser separately. Here the results of Safari and Firefox after a few refreshes in every browser:

> Safari

```
Hi there! today is Tue Jun 21 16:27:05 CEST 2016 
Global Counter: 81 
User Counter: 31
```

> Firefox

```
Hi there! today is Tue Jun 21 16:27:07 CEST 2016
Global Counter: 80
User Counter: 50
```

### video 18.Mixing CDI Scopes

Here we show that introducing a new `EmptyDelegate` class  as `@ApplicationScope` and referencing the counter beans from it with `@Inject`, the server is capable to inject the same counter instances in both `EmptyDelegate` and `Index` classes.

See the `Index` class where we increase the counter with references to User and Global counters and we retrieve the counter value using the `EmptyDelegate` class.



