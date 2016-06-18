### Java EE Bootstrap

#### 06-07 Interface Injection, Dependency Injecion and Inversion of Control

to simulate the behaviour of an application server, we have to inject Interfaces and not Classes, so to demonstrate this we change the Service class to an Interface and create an implementation called ServiceImpl (this is not a nice name because it doesn't say anything about the responsibility of the class, a better name would be ServiceConfiguration or something like that). Then in the main we add lines of code to retrieve the implementation (there is only one here called ServiceImpl) and inject to the Facade.

In real application servers there is a **Scanner** that reads all the packages and sends the metadatas to an object called **Assembler** (the generic _Factory_ of th application server) for injection purpose.

#### 08 Proxies and Decorators

What the application server does is to add value when injecting classes. It creates Proxies to add functionalities, for example when the Service is an EJB it adds:

-	Transaction
-	Security
-	Threading
-	State Management / Pooling
-	Monitoring

Facade --> $Proxy300 --> Service

#### 09 Aspects with Dynamic Proxy

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


