## Introduction
### Definition (a apprendre ) 
- Une application repartie est une application qui est exectue sur differents systemes autonomes 

### Why do we need distributed systems ? 
- Distributed Systems became necessary as applications needs to scale . 


### QoS Criteria
- Scalability 
- Reliability 
- Security 
- Performance

### Realization Issues 
- Transparency :
	-  The feel that you are working with only one system and not many . 
	It's more like adding a layer of abstraction to hide the complex architecture underneath
	
![[Pasted image 20220301012100.png]]

### **Horizontal** Scalability
- Adding more devices to handle big loads of computation.

### **Vertical** Scalability 
- Adding more hardware power to one device ( Upgrading a device ).

### **Architectural** Scalability
- The Application is designed to be distributed on many systems 


### 8 Common False Assumptions Made When Designing Distributed Systems 
- Good Network 
- 0 Latency  
- Infinite Bandwidth 
- Secure Network 
- Static Network Topology 
- There is a network administrator 
- 0 Transport Costs 
- Homogenic Network (One Protocol Working only )


## Threads and Serialization
### Serialization 
#### Serializable OBJECTS 
- In Java , C++ , C# ... , There is a mechanism that allows to convert objects into bytes that are saved in a file and convert them back to objects later. 

-  To implement that we should extend the class *Serializable*
```java
class SObject extends java.io.Serializable { 
}
```
> All underlying classes ( programmer's defined classes ) used in the *SObject* should also extend Serializable


#### OBJECT OUTPUT STREAM
- **Serializing** an object  into a file.
```java
import java.io.*;
public class SerializerPersonne {

	public static void main(String argv[]) {

		Personne personne = new
		Personne("Dupond","Jean",175);
		try {
			// We open a file output stream where we will write the data
			FileOutputStream fichier = new FileOutputStream("personne.ser");

			// Here we open an object output stream 
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(personne);

			// Cleaning Up
			oos.flush();
			oos.close(); 
		}
		
		catch (java.io.IOException e) {
			e.printStackTrace(); 
		} 
	}
}
```

#### OBJECT INPUT STREAM 
- **DeSerializing** an object from a file.

```java
import java.io.*;
public class DeSerializerPersonne { 

	public static void main(String argv[]) {
		try {
			// We open the file as an input stream
			FileInputStream fichier = new FileInputStream("personne.ser");

			// We open an object input stream
			ObjectInputStream ois = new ObjectInputStream(fichier);

			// We readObject and notice we cast the object because the typing is lost
			Personne personne = (Personne) ois.readObject();

			System.out.println("Personne : ");
			System.out.println("nom : "+personne.getNom());
			System.out.println("prenom : "+personne.getPrenom());
			System.out.println("taille : "+personne.getTaille()); }

		catch (java.io.IOException e) {
			e.printStackTrace(); }
		catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		} 
	}
}
```


#### Manual (DE) serialization 

- To manually defining the **(De) Serialization** behaviour we 
	- Extend the *Externalizable* class.
	- Define *writeExternal* and *readExternal*.
	
```java
public class UserInfo implements Externalizable {
	// Attributs to work on
	private String login,
	private String id;

	public writeExternal(ObjectOutput out) throws IOException {
		// We encrypt data before writing it
		crypter();
		out.writeObject();
	}

	public readExternal (ObjectOutput out)throws IOException, ClassNotFoundException {
		// We decrypt data  and define how to read each attribute
		login=decrypte((String )in.readObject());
		id.in.readInt();
	}
}
```

#### Unserializable Objects 
- Classes or types marked **Transient**
- **Threads**


### Threads 

#### Processor and processes
- How they really work and how OS Handle multiple process. 

#### Creating a thread 
There are two ways to create a thread in java :

1. Create an Instance of class **Thread** 

``` java
public class ThreadTest1 extends Thread {

	// What the thread will do when run
	public void run(){
		for (int i=0; i<=10;i++){
		System.out.println(i );}
	}

	// Main function
	public static void main(String [] args) {
		Thread t1 = new ThreadTest1();
		Thread t2 = new ThreadTest1();
		t1.start();
		T2.stard();
	}
}
```

2. Create an Instance of class **Runnable**

```java
public class ThreadTest2 implements Runnable {

	// Runnable is a derived class  of thread
	// run function 
	Public void run() {
		for (int i=0; i<=10;i++){
		System.out.println(i);}
	}

	public static void main(String [] args){
		ThreadTest2 t1 = new ThreadTest2();
		ThreadTest2 t2 = new ThreadTest2();
		// We should create a thread then start it (same as the method 1 )
		new Thread(t1).start();
		new Thread(t2).start();
	}
}
```

#### Life cycle of a thread
![[Pasted image 20220301022110.png]]

#### Race Conditions
Problems that might rise because of the asynchronous way in which threads work.

#### Locking variables
- We call *Synchronized* with the variable reference so the thread try to take ownership of the variable.

```java
	public void push(char c) {
	// We call synchronized with the variable reference 
	synchronized(this) {
	data[idx] = c;
	idx++;
	}
}

```

##### How it works ? 
- There is a **lock indicator** on each section :
1. When a thread wants to enter in the protected block it tries to take the lock indicator 
	1. Lock Indicator **present** -> The thread **takes it and executes the block**.
	2. Lock Indicator **missing** -> The thread **sleeps till it's available**.

> Because a missing lock indicator block the execution of a thread. Java ensures that lock indicators are reinstituted even if an exception has risen.

> If a thread calls for synchronized  variable twice ( where one is inside of the other ) . The call happen only once and that for the external block call.


##### Private Variables a necessity  
-  When variables are public via accessed in many different ways so protecting them becomes hard.
- That's why **all synchronized variables should be private** and JAVA encourages this pattern by giving **a syntax sugar**.

```java

// Instead of this 
public void push(char c) {
	synchronized(this) { } 
}

// We write this > Java will make all the method synchronized
public synchronized void push(char c) { }

```

> Adding synchronized as a method declarator might increase the waiting time unnecessarily as all the method will become synchronized

> Using synchronized for a method is documented with javadoc but not synchronized(this)


#### WAIT | NOTIFY
- To handle concurrency effectively all **threads operations** should be synchronized. 
- If the **wait** is called the thread gives back the *lock indicator* before sleeping
- **notifyAll** is called to wake all sleeping threads however **notify** only wakes one thread.
- The thread calling **notify** will give back the *lock indicator*.

```java

synchronized(drainingBoard) {
	if (drainingBoard.isEmpty())
	drainingBoard.wait();
}

synchronized(drainingBoard) {
	drainingBoard.addItem(plate);
	drainingBoard.notify();
}
```


#### Interrupted exception 
- Thrown by **Thread** methods.
- When a waiting process is interrupted.

> we use Thread.join() to wait for the execution of a thread.


#### EXECUTOR SERVICE 
```java
Executors.newSingleThreadExecutor.execute(Runnable,time,TimeUnit.MILLISECONDS);
```



### Defining Asynchronous behavior
- The execution of **the function will not block the program execution**. However the program will continue its execution and when the **called function finishes it will notify the main program**.
```java
// Callable is an interface with generic type to define

class Async implements Callable<String>{

	// Interface method implemented
	public String call( ) {
		return calcul();
	}

	public String calcul(){return success;}
}

// How it will be called
String resultat = calculator.call();
```


## RMI - Remote Method Invocation
- A mechanism that allows the call of methods between objects from different machines. 
- Uses **sockets**



#### Linkers
- **Stub** | **Skeleton**
	- Handle Calls through the network 
	- Marshalling and unmarshalling
	
		>Marshalling is a process in which a java object is converted into a XML object. Unmarshalling is the process in which an xml representation is converted into an Java Object.
    - Keep track of distributed Objects references. 
    - Created through **rmic** generator



#### STUB ( Sender )
It's the main responsible for the communication. It's the class that creates the abstraction layer for the RMI Protocol. It handles everything so everything works as intended.

##### What it does ? 
- Represents **Remote Objects** as **Local** ones.
- Establish Remote Connections and convert local calls to remote ones.
- Wait for the remote call return value

#### Skeleton ( Receiver )
It's an old class now deprecated and we use only stubs. It handles remote requests.
- Knows which object and method to invoke for the remote call. 

#### REMOTE REFERENCE LAYER
##### What is it ? 
- A reference table that links each object to its specified URI.
It's a hash table where keys are the object names and values are the object instances.

##### Starting the Service
To launch the service  in windows
```powershell
rmiregister
```

- The **rmiregister** is unique per JVM. The service runs on port **1099** per default.

##### Protocol USED 
**JRMP: Java Remote Method Invocation) basé sur TCP/IP**


#### IMPLEMENTATION 
1. We define an **Interface**  that will be shared between client and server as the form of a contract **DTO**.

	```java
	/* IRemoteClass.java */
	
	import java.rmi.Remote;
	import java.rmi.RemoteException;
	
	public interface IRemoteClass extends Remote {
		ReturnValue remoteMethod(...args) throws RemoteException;
	}
	```
	
	> All methods  of the **distributed object** should throw RemoteException.

2. We implement the class with extending **UnicastRemoteObject** and implementing the previously defined **interface**.

	```java
	/* RemoteClass.java */
	
	import java.rmi.*;
	import java.rmi.server.*;
	
	public class RemoteClass extends UnicastRemoteObject implements IRemoteClass
	{ 
		public Reverse() throws RemoteException {  }
		public ReturnValue remoteMethod (args) throws RemoteException {
			/* Implementation*/ 
			}
	}
	```
	
3.  We define the **Server** and expose our Remote Object. 

	```java
	/*Server.java*/

	import java.rmi.*;
	import java.rmi.server.*;

	public class Server {

	public static void main(String[] args)
		{
			try {
				// Instantiating remote object.
				RemoteClasss rmc= new RemoteClass();
	
				// Binding the object to URI
				Naming.rebind("rmi://sinus.cnam.fr:1099/className", rmc);
	
				}
		
				catch (Exception e) {
					System.out.println("Error Linking Object to URI");
					System.out.println(e.toString());
				}
		}
	} 
	```

4. We define the **Client** which will try to access and use the Remote Object.
	```java
	import java.rmi.*;
	import IRemoteClass;
	public class Client
	{
		public static void main (String [] args) {
			try {
					// We use the casting here (DTO)
				IRemoteClass rmc = (IRemoteClass) Naming.lookup ("rmi://sinus.cnam.fr:1099/ClassName");
				String result = rmc.method(args);
			}
			catch (Exception e)
			{
				System.out.println ("Error Accessing remote object.");
				System.out.println (e.toString());
			}
		}
	}
	```


#### REAL LIFE USE CASES
##### Interests
- To use the RMI Method we need a class that handles the **interface** files sharing. *Because for each update the interface should be updated too*.
- That protects both client and server from going out of sync. 

##### Methods
```java
java.rmi.server.codebase(URI) // to specify where the class files exists
	java.rmi.server.useCodebaseOnly() // to tell the server to only use class file in the codebase
```

- Now if the client connect with naming.lookup. Java will check if the client has the remoteObject class files. If not they will be loaded from the server.

##### Classes that manages the codebase
```java
java.rmi.RMISecurityManager
java.rmi.RMIClassLoader 

```

##### Dynamic Server Code
```java
	/* DynamicServer.java */
	
	import java.rmi.*;
	import java.util.Propreties;
	
	public class DynamicServer { 
	
		public static void main (String args[]){ 
			System.setSecurityManager(new RMISecurityManager());
	
			try { 
					// Server propreties url , port , jvm infos
					Propreties p = new System.getPropreties();
					
					// we get the codebase url
					String url = p.getProprety("java.rmi.server.codebase");
					// We load the Class
					Class ServerClass = RMIClassLoader.loadClass(url,ServerClass_Name);
	
					// We bind the class 
					Naming.rebind(URI,(Remote) ServerClass.newInstance());
			}
		
			catch(Exception e) { 
				System.out.println("Error Occured");
			}
		}
	}
	```

##### Dynamic Client 
```java
	/* DynamicClient.java */
	
	public class DynamicClient {
	
		public DynamicClient(String[] args) throws Exception { 
			// Local execution for both Server and Client
			Propreties p = System.getPropreties();
			String url = p.getProprety("java.rmi.server.codebase");
	
			// Loading the classes
			Class ClientClass = RMIClassLoader.loadClass(url,ClientClass_Name);
	
			// To start the client after loading the classes
			Constructor[] C = ClientClass.getConstructors();
			C[0].newInstance(new Object[]{args})
		}
	}



	/* MainClientClass.java */

	public static void main (String[] args){ 
		System.setSecurity(new RMISecurityManager());
		try { 
			DynamicClient c1 = new DynamicClient();
		}
	}
```
___