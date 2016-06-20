### Dependency Injection with server

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


