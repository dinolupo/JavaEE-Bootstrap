### Java EE Bootstrap

#### Interface Injection

to simulate the behaviour of an application server, we have to inject Interfaces and not Classes, so to demonstrate this we change the Service class to an Interface and create an implementation called ServiceImpl (this is not a nice name because it doesn't say anything about the responsibility of the class, a better name would be ServiceConfiguration or something like that). Then in the main we add lines of code to retrieve the implementation (there is only one here called ServiceImpl) and inject to the Facade.

In real application servers there is a **Scanner** that reads all the packages and sends the metadatas to an object called **Assembler** (the generic _Factory_ of th application server) for injection purpose.

