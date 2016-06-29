# Java EE Bootstrap tutorial and examples

This project contains examples and tutorials for Java EE 7 edition watching Adam Bien videos from: 

[https://vimeo.com/ondemand/javaeebootstrap]()

-----

## Project Folder `MyOwnAnnotation`

### video 03.Annotations in Java EE

This little project shows a basic Annotation example

-----

## Project Folder `DependencyInjection`

### videos 06-07 Interface Injection, Dependency Injection and Inversion of Control

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

### video 21.@Inject or @EJB or @Resource?

- `@Inject` is a simplified version of `@EJB`

- `@Resource` can be used to inject everything that is installed in the Application Server, it means everything that is in the JNDI tree of the application server, that belongs to the environment of the component. Example uses of injection via the `@Resource` tag are the following:

	- Topics / JMS
	- Queues / JMS
	- Data Sources
	- Entity Manager Factories
	- etc. 	 

Let's see an example in the `HelloWorldService` class injecting the `SessionContext` with `@Resource` that shows the `CallerPrincipal()` of the `SessionContext`.

### video 22.Different EJB Flavors

- `@Stateless` there is no association between the EJB instance and the invoker (the caller)

- `@Stateful` the invoker would get a dedicated instance of the EJB. In our case there would be one EJB for every Index class, see the console output below where the EJB is created and destroyed on each request:

```
INFO  [stdout] (default task-4) Creating EJB HelloWorldService
INFO  [stdout] (default task-4) Creating Index
INFO  [stdout] (default task-4) Method public java.lang.String io.github.dinolupo.di.presentation.HelloWorldService.serve() invoked in 104421 ns
INFO  [stdout] (default task-4) Destroying Index
INFO  [stdout] (default task-4) Destroying EJB HelloWorldService
```

To retain the EJB until the Session ends, we must add `@SessionScoped` annotations together with `@Stateful`:

```java
@SessionScoped
@Stateful
@Interceptors(MethodCallLogger.class)
public class HelloWorldService implements Serializable {
. . .
}
```

- `@Singleton` Introduce and application wide cache, and together with `@Startup` it starts at deploy time or in startup time of application server, example: 

```java
@Startup
@Singleton
@Interceptors(MethodCallLogger.class)
public class HelloWorldService {

    @Resource
    SessionContext sessionContext;

    @PostConstruct
    public void onInit() {
        System.out.println("Starting... EJB HelloWorldService");
    }

    public String serve() {
        return "Hi there! today is " + new Date() + " Caller Principal is " + sessionContext.getCallerPrincipal();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroying EJB HelloWorldService");
    }

}
```

Generates the following output, see the "Starting... EJB HelloWorldService" line, showing that the newly deployed EJB is instanciated before other components, and this happens on every change of the EJB:

```
11:35:34,166 INFO  [org.jboss.weld.deployer] (MSC service thread 1-2) WFLYWELD0006: Starting Services for CDI deployment: dependency-injection-with-server.war
11:35:34,170 INFO  [org.jboss.weld.deployer] (MSC service thread 1-8) WFLYWELD0009: Starting weld service for deployment dependency-injection-with-server.war
11:35:34,392 INFO  [stdout] (ServerService Thread Pool -- 64) Starting... EJB HelloWorldService
11:35:34,419 INFO  [javax.enterprise.resource.webcontainer.jsf.config] (ServerService Thread Pool -- 64) Initializing Mojarra 2.2.12-jbossorg-2 20150729-1131 for context '/dependency-injection-with-server'
11:35:34,558 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 64) WFLYUT0021: Registered web context: /dependency-injection-with-server
11:35:34,569 INFO  [org.jboss.as.server] (management-handler-thread - 7) WFLYSRV0010: Deployed "dependency-injection-with-server" (runtime-name : "dependency-injection-with-server.war")
```

This can be useful because in `@PostConstruct` you can:

- Prefill the cache
- Parse configuration
- Prepare some stuff...

and it is guaranteed by the application server that after completion of the `@PostConstruct` all other components are going to be started.

- `@Singleton` Dependencies:

Now we want to create two Singletons and assure that one of them starts before the other. Let's create another class `FireStarter`, to ensure that `@HelloWorldService` starts after that, we add the `@DependsOn` annotation like in the following code:

```java
@DependsOn("FireStarter")
@Startup
@Singleton
@Interceptors(MethodCallLogger.class)
public class HelloWorldService {
. . .
}
```

- Singleton bottleneck

The `@Singleton` introduces a bottleneck because the application server locks the instance such as only one thread at a time can access to it. To solve this, add the the annotation `@ConcurrencyManagement(ConcurrencyManagementType.BEAN` 

__pay attention__ that with `@ConcurrencyManagement` annotation, many threads can access the singleton simultaneously and you have to care by yourself about the consistency, using lock free data structures or lock resources by yourself.


### video 23.Fire and Forget with CDI

In Java EE 6 there is a nice way to do Aynchronous programming, we show how to do that with an example. Let's suppose we want to gather data in all classes all around our application server, let's create a BigBrother class with a method that is rather slow, for example it writes information on a database. To simlulate the slowness here, we stop the thread for 1 second (__only for demo purpose__)

```java
@Stateless
public class BigBrother {

    @Asynchronous
    public void gatherEverything(String message){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Save it for later: %s\n", message);
    }

}
```

We can use the `@Asynchronous` annotation on a method, but this annotation works only on EJBs, so we have to put also `@Stateless` or other EJB flavors.

To work with no-EJB classes, we use the following approach:

```java
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;

public class BigBrotherNotEJB {

    @Resource
    ManagedExecutorService managedExecutorService;

    public void gatherEverything(String message){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Not EJB long operation --> Save it for later: %s\n", message);
            }
        };

        managedExecutorService.execute(runnable);

    }
} 
```

### 24.Hardcoded Timers

With the class `BigBrotherNoEJB` we are gathering now all the information asynchronously using the application server interal Thread Pool. We used Thread sleep as a place holder for the expensive operation. We are going to show how to cache the messages and process them in batch.

We create a new class `BigBrotherWithQueue` that is a `@Singleton` and store all the messages in a queue. Then we are going to process the message every 5 seconds in a method like the following:

```java
    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void batchAnalyze(){
        System.out.printf("Analyzing at %s\n", new Date());
    }
```

The output will be like:

```
08:03:40,015 INFO  [stdout] (EJB default - 3) Analyzing at Thu Jun 23 08:03:40 CEST 2016
08:03:45,006 INFO  [stdout] (EJB default - 4) Analyzing at Thu Jun 23 08:03:45 CEST 2016
08:03:50,006 INFO  [stdout] (EJB default - 5) Analyzing at Thu Jun 23 08:03:50 CEST 2016
```

Then we use a `CopyOnWriteArrayList` concurrent data structure as a queue to store messages, and in batch method we work every item in the structure.

### 25.Configurable Timers (@Schedule) 

You can use Configurable Timers using the `TimerService` of the JEE Application Server. In the following class we create a `ScheduleExpression` that could take the configuration parameters from an external configuration file (properties):

```java
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
public class BigBrotherWithQueueUsingTimer {

    CopyOnWriteArrayList<String> messageQueue;

    @Resource
    TimerService timerService;
	 Timer timer;

    @PostConstruct
    public void initialize(){
        this.messageQueue = new CopyOnWriteArrayList<>();
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.second("*/3").minute("*").hour("*");
        this.timer = timerService.createCalendarTimer(scheduleExpression);
    }

    public void gatherEverything(String message){
        messageQueue.add(message);
    }

    @Timeout
    public void batchAnalyze(){
        System.out.printf("**************** Analyzing at %s ***************\n", new Date());
        for (String message: messageQueue) {
            System.out.printf("---- Working on message: %s\n", message);
            messageQueue.remove(message);
        }
    }

}
```

### 26.Asynchronous Behavior and Futures

The BigBrother now processes the messages every n seconds, but what could happen is that the `batchAnalyze()` method could take a long time to process a long list of messages, because it works serially on every message. We can parallelize it using an Asynchronous EJB like the following:

```java 
package io.github.dinolupo.di.presentation;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dinolupo.github.io on 23/06/16.
 */
@Stateless
public class MessageAnalyzer {

    @Asynchronous
    public Future<Boolean> analyze(String message) {
        Boolean retValue = message.hashCode() % 2 == 0;
        // with sleep() here we are going to simulate a long process required to analyze the message
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(retValue);
    }

    @Asynchronous
    public void showResults(List<Future<Boolean>> results){
        for (Future<Boolean> result: results) {
            try {
                System.out.printf("### Result is %s\n", result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    
}

```

It has two methods:

- `analyze` that analyze the message data (in our example it stops the thread for 10 seconds to simulate an heavy processing and then it returns if the hashCode is multiple of 2)

- `showResults` that shows the Future results in Asynchronous way

they are executed asynchronously when they are called by the `batchAnalyze` method:
 

```java
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
public class BigBrotherWithQueueUsingTimerAndMessageAnalyzer {

    CopyOnWriteArrayList<String> messageQueue;

    @Inject
    MessageAnalyzer messageAnalyzer;

    @Resource
    TimerService timerService;
    Timer timer;

    @PostConstruct
    public void initialize(){
        this.messageQueue = new CopyOnWriteArrayList<>();
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.second("*/1").minute("*").hour("*");
        for (Timer timer1 : timerService.getAllTimers()) {
            System.out.println(">>>>>> Timer: " + timer1);
        }
        this.timer = timerService.createCalendarTimer(scheduleExpression);
    }


    public void gatherEverything(String message){
        messageQueue.add(message);
    }

    @Timeout
    public void batchAnalyze(){
        System.out.printf("**************** Analyzing at %s ***************\n", new Date());
        // collector of results from the analyzer
        List<Future<Boolean>> results = new ArrayList<>();
        for (String message: messageQueue) {

            // Asynchronous call to analyze the message
            results.add(messageAnalyzer.analyze(message));

            messageQueue.remove(message);
        }

        // show the results asynchronously
        messageAnalyzer.showResults(results);
    }

    // --------------------------------------------------------
    //  the following method is very important because without
    //  it the Timer will never be destroyed and on following
    //  redeployments you will obtain multiple timers
    // --------------------------------------------------------

    @PreDestroy
    void preDestroy() {
        System.err.println("****** DESTROYING TIMER ******");
        timer.cancel();
        timer = null;
    }

}
```

### 27.Persistence with JPA

In this step we are going to store the messages with JPA.

#### Add Postgresql drivers to Wildfly 10

1) Get the JDBC driver from [http://jdbc.postgresql.org]():

```sh
wget –tries=0 –continue https://jdbc.postgresql.org/download/postgresql-9.4.1207.jar
```

1.a) If the driver does not contain the file `META-INF/services/java.sql.Driver`, Deploy the Jar using procedure at [https://docs.jboss.org/author/display/WFLY10/DataSource+configuration]()

```sh
mkdir -p META-INF/services
echo "org.postgresql.Driver" > META-INF/services/java.sql.Driver
jar \-uf jdbc-driver.jar META-INF/services/java.sql.Driver
```

2) Load the jboss cli and deploy the jar from the command line:

```sh
<WILDFLY_DIR>/bin/jboss-cli.sh
[standalone@localhost:9990 /] deploy path/to/driver.jar
```

3) Configure the connection with jboss console

-	load the console at [http://localhost:9990/console]()
- 	Go to Configuration->Subsystems->Datasources->Non-XA->Add
-  choose a JNDI name, example: `java:jboss/datasources/PostgresDataSource`
-  select the jdbc string, example: `jdbc:postgresql://localhost:15432/database?ApplicationName=JavaEE_Bootstrap`

4) create a `META-INF/persistence.xml` file into the project, referencing the JNDI connection name created before:

> `META-INF/persistence.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.1">
    <persistence-unit name="production" transaction-type="JTA">
        <jta-data-source>java:jboss/datasources/PostgresDataSource</jta-data-source>
        <properties>
            <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
        </properties>
    </persistence-unit>
</persistence>
```

5) create a `Message` entity bean to store the message:

> Message.java

```java
@Entity
public class Message {

    // id
    @Id
    @GeneratedValue
    long id;

    // payload
    String message;

    // needed for our purpose
    public Message(String message) {
        this.message = message;
    }

    // needed by JPA
    public Message() {
    }

}
```

6) create a `MessageArchive` class that is needed to store our messages

```java
public class MessageArchive {

    @PersistenceContext(unitName = "production")
    EntityManager entityManager;

    public void saveMessage(String message){
        entityManager.merge(new Message(message));
    }
}
```

7) Inject the `MessageArchive` into the `BigBrotherJPA` class (we copied and commented out the previous for history purpose) and add the call to save the message:

```java
    public class BigBrotherWithQueueUsingTimerAndMessageAnalyzer {
    	
    	...
    	
    	@Inject
    	MessageArchive messageArchiver;
    
    	public void gatherEverything(String message){
      	  messageArchiver.saveMessage(message);
      	  messageQueue.add(message);
    	}
    	
    	...
    	
    }
```

### 29.JTA Transactions From The Code Perspective

Every EJB method starts a transaction that ends at the end of the method call.

Every request on the EJB applies JTA, it means that the Thread is marked as transactional, and this is forwarded on every other injected EJB, Beans or class used by that method. What happens behind the scenes is that when the Transaction hits the EntityManager (or other transactional resource as JMS, JCA, CDI Events, etc.) then it adds a little and necessary overhead to manage the transaction.

In our example project, the first method that begin a transaction is the `BigBrotherJPA.gatherEverything()` because it is an EJB (Singleton) and transaction are applied only on EJBs. 

As we seen before, the application server applies a Transaction Proxy before calling that method; it acts like the following:

> transactionProxy acts like a decorator on EJB methods, starting and ending the transaction

```java
	// -------------
	// txProxy.begin
	// -------------
    public void gatherEverything(String message){
        messageArchiver.saveMessage(message);
        messageQueue.add(message);
    }
    // --------------
    // txProxy.commit
    // --------------
```

If there is a runtime exception the transaction will be rolled back.

- The messageQueue in the method is just a collection and it is not transactional.
- The saveMessage call is interesting because the class `MessageArchive` is a POJO that does not have transactions (only EJBs have) but it has a `TransactionManager` injected and transaction will be used because it is associated with the Thread that originated the call to that method (in the previous Singleton `BigBrotherJPA`).

To show what happens, let's send a CDI Event (it's like JMS messages are in memory, it's the Observer pattern in JavaEE 6):

- in The `BigBrotherJPA` classe declare the following:

> String is the payload of the Event in our case, but it could be any type 

```java
    @Inject
    Event<String> event;
```

- In the `gatherEverything()` method fire the Event:

> event.fire(message)

```java
    public void gatherEverything(String message){
        messageArchiver.saveMessage(message);
        messageQueue.add(message);
        event.fire(message);
    }
```

- to observe events we declare another class `MessageListener`:

> using the `@Observe` annotation we declare the type of Event to receive, so the Event is received because String is the same type of the Event declared before. We ca also declare when we want to receive it (`during`). 

```java
public class MessageListener {
    
    public void onSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) String message){
        System.out.println("+++++ Transaction commited");
    }

    public void onFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) String message){
        System.out.println("----- Transaction Failure");
    }
    
}
```

- deploy to test for Commit (see the `+++++` in the log)

- To test for rollback we can inject the `SessionContext` and rollback the transaction, we will see the `-----` in the log:

```java
    
    @Resource
    SessionContext sessionContext;
    
    public void gatherEverything(String message){
        messageArchiver.saveMessage(message);
        messageQueue.add(message);
        event.fire(message);

        sessionContext.setRollbackOnly();

    }
```

### 30.JSF Intro

In this section we are going to enhance the JSF page with some components.

- We add a `text` field with setter and getter into the `Index` managed bean:

> `textField` declaration and setter/getter for JSF

```java
    private String textField;
    
        public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }
``` 

- add a `save` method that will be triggered by a button:

> `save()` returns null because we want to stay in the same page, otherwise if you want to go so some other pages, you return a String with the name of the JSF page.

```java
    public Object save() {
        this.bigBrother.gatherEverything(textField);
        return null;
    }
```

- add the following section in the `index.xhtml` JSF page:

```xml
    <h:form>
        <h:inputText value="#{index.textField}"/><br/>
        <h:commandButton value="Save" action="#{index.save}"/>
    </h:form>
```
### 31.Field Validation with Bean Validation

We show how to validate a field:

- In the `Index` Managed Bean, set the field validation with `@Size` annotation:

> @Size annotation with min/max properties

```java
    @Size(min = 3, max = 8)
    private String textField;
``` 

Bean Validation in Java EE 7 can be used also on EJBs, JAX-RS, JPA Entities (JEE 6/7). 
It's a nice way also to document the fields, better than a javadoc comment.

You can set the message with the `message` property:

```java
    @Size(min = 3, max = 8, message="Field is invalid, please retry.")
    private String textField;
``` 

In case the Bean Validation is not satisfied, the Trasaction will be rolled back.

### 32.JAX-RS (REST) Intro

We show how to create web services with JEE.

1) First of all we have to initialize and start the JAX-RS runtime:

> JAX-RS Runtime start

```java
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
}
```

2) Create a JAX-RS resource, to access the class via HTTP

> JAX-RS class resource

```java
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("message")
public class MessagesResource {

	@GET
	public String message() {
 	   return "hello, world!";
	}

}
``` 

3) test the service with:

```sh
% curl http://localhost:8080/web_module_war_exploded/resources/messages
```

URL explanation: 

- `web_module_war_exploded` is the Context URI of the URL
- `resources` is the name of the JAX-RS application
- `messages` is the name of the resource

4) Of course we can return any type of bean, not only String, so let's change the `Message` bean definition adding support for serialization in both XML and JSON:

> add JAXB annotation to `Message` class:

```java
@Entity
@XmlRootElement
@XmlAccessorType
public class Message {...}
``` 

> change the type of the Rest Service to return a Message object:

```java
@Path("messages")
public class MessagesResource {

    @GET
    public Message message() {
        return new Message("Hello from a Message object");
    }

}
```

5) Test both XML and JSON return types:

> XML

```sh
% curl -H "Accept: application/xml" http://localhost:8080/web_module_war_exploded/resources/messages
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<message><id>0</id><message>hello from a Message object</message></message>
```

> JSON

```sh
% curl -H "Accept: application/json" http://localhost:8080/web_module_war_exploded/resources/messages
{"id":0,"message":"hello from a Message object"}
```
