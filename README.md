# 03-29 Java Review Lab

For this lab, you'll be reviewing some Java fundamentals in preparation for the programming we'll be doing in this course. There is no Android Framework interaction here, just pure Java. You should **Fork and clone** this repository to complete the lab. You'll need to make sure you have the [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed (it is already on the lab machines).


## 1. Building Apps with Gradle
Consider the included `Dog` class found in the `src/main/java/edu/info498/review/` folder. This is a very basic class representing a Dog. You can instantiate and call methods on this class by building and running the `Tester` class found in the same folder.
- You can just use _Sublime Text_ to view and edit these files.

You've probably run Java programs using an IDE, but let's consider what is involved in building this app "by hand", or just using the JDK tools. There are two main steps to running a Java program:
1. **Compiling** This converts the Java source code (in `.java` files) into JVM bytecode that can be understood by the virtual machine (in `.class`) files.
2. **Running** This actually loads the bytecode into the virtual machine and executes the `main()` method.

Compiling is done with the `javac` ("java compile") command. For example, from inside the repo directory, you can compile both the `.java` files with:
```bash
# compile all .java files
javac src/main/java/edu/info498/review/*.java
```

Running is then done with the `java` command: you specify the full package name of the class you wish to run, as well as the [classpath](https://docs.oracle.com/javase/tutorial/essential/environment/paths.html) so that Java knows where to go find classes it depends on:
```bash
# Runs the Tester main method with the `src/main/java` folder as the classpath
java -classpath ./src/main/java edu.info498.review.Tester
```

___Compile and run this application now.___

___Modify the `Dog` class so that it's `.bark()` method barks twice (`"Bark Bark!"`). What do you have to do to test that your change worked?___

You may notice that this can get pretty tedious: there are two commands we need to execute to run our code, and both are complex enough that they are a pain to retype.

Enter **Gradle**. Gradle is a build automation system: a "script" that you can run that will automatically perform the multiple steps required to build and run an application. This script is defined by the `build.gradle` configuration file: ___open that file and look through its contents___. The `task run()` is where the "run" task is defined: do you see how it defines the same arguments we otherwise passed to the `java` command?

You can run the version of Gradle included in the repo with the `gradlew <task>` command, specifying what task you want to the build system to perform. For example:
```bash
./gradle tasks
```
Will give you a list of available tasks. Use `gradlew classes` to compile the code, and `gradlew run` to compile _and_ run the code. **Helpful hint**: you can specify the "quite" flag with `gradlew -q <task>` to not have Gradle output its build status (handy for the `run` task)

___Use gradle to build and run your Dog program. See how much easier that is?___

We will be using Gradle to build our Android applications (which are much more complex than this simple Java demo)!


## 2. Class Basics
Now consider the `Dog` class in more detail. Like all classes, it has two parts:
1. **Attributes** (a.k.a., instance variables, fields, or member variables). For example, `String name`.
  - Notice that all of these attributes are `private`, meaning they are not accessible to members of another class! This is important for **encapsulation**: it means we can change how the `Dog` class is implemented without changing any other class that depends on it (for example, if we want to store `breed` as a number instead of a `String`).
2. **Methods** (a.k.a., functions). For example `bark()`
  - Note the _method declaration_ `public void wagTail(int)`. This combination of access modifier (`public`), return type (`void`), method name (`wagTail`) and parameters (`int`) is called the **method signature**: it is the "autograph" of that particular method. When we call a method (e.g., `myDog.wagTail(3)`), Java will look for a method definition that _matches_ that signature.
  - Method signatures are very important! They tell us what the inputs and outputs of a method will be. We should be able to understand how the method works _just_ from its signature.

Notice that one of the methods, `.createPuppies()` is a `static` method. This means that the method belongs to the ___class___, not to individual object instances of the class! For example, try running the following code (by placing it in the `main()` method of the `Tester` class):
```java
Dog[] pups = Dog.createPuppies(3);
System.out.println(Arrays.toString(pups));
```

Notice that to call the `createPuppies()` method you didn't need to have a `Dog` object (you didn't need to use the `new` keyword): instead you went to the "template" for a `Dog` and told that template to do some work. _Non-static_ method (ones without the `static` keyword) need to be called on an object.

___Try to run the code `Dog.bark()`. What happens?___ This is because you can't tell the "template" for a `Dog` to bark, only an actual `Dog` object!

In general, in 98% of cases, your methods should **not** be `static`, because you want to call them on a specific object rather than on a general "template" for objects. Variables should **never** be static, unless they are **also** `final` constants (like the `BEST_BREED` variable).
- In Android, `static` variables cause significant memory leaks, as well as just being incredibly poor design.


## 3. Inheritance
___Create a new class `Husky` (in a new file) with the following class declaration:___
```java
public class Husky extends Dog {...}
```
(You'll need to specify the `package` for this class at the top of the file). The `extends` keyword means that `Husky` is a [**subclass**](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html) of `Dog`, inheriting all of its methods and attributes. It also means that that `Husky` **is a** `Dog`.

___In the Tester, instantiate a new `Husky` and call `bark()` on it. What happens?___
- Because we've inherited from `Dog`, the `Husky` class gets all of the methods defined in `Dog` for free!
- Try adding a constructor that takes in a single parameter (`name`) and calls the appropriate `super()` constructor so that the breed is `"Husky"`, which makes this a little more sensible.

We can also add more methods to the **subclass** that the **parent class** doesn't have. For example: ___add a method called `.pullSled()` to the `Husky` class.___
- Try calling `.pullSled()` on your `Husky` _object_. What happens? Then try calling `.pullSled()` on a `Dog` _object_. What happens?


## 4. Interfaces
___Create a new file `Huggable.java` with the following code:___
```java
public interface Huggable {
  public void hug();
}
```
This is an example of an [**interface**](https://docs.oracle.com/javase/tutorial/java/concepts/interface.html). An **interface** is a list of methods that a class _promises_ to provide. By _implementing_ the interface (with the `interface` keyword in the class declaration), the class promises to include any methods listed in the interface.
- This is a lot like hanging a sign outside your business that says _"Accepts Visa"_. It means that if someone comes to you and tries to pay with a Visa card, you'll be able to do that!
- Implementing an interface makes no promise about _what_ those methods do, just that the class will include methods with those signatures. For example, if we change the `Husky` class declaration:
  ```java
  public class Husky extends Dog implements Huggable {...}
  ```
  then the `Husky` class needs to have a `public void hug()` method, but what that method _does_ is up to you!
- A class can still have a `.hug()` method even without implementing the `Huggable` interface (see `TeddyBear`), but we gain more benefits by announcing that we support that method.
  - Just like how hanging an "Accepts Visa" sign will bring in more people who would be willing to pay with a credit card, rather than just having that option available if someone asks about it.

_Why not just make `Huggable` a superclass, and have the `Husky` extend that?_
- Because `Husky` extends `Dog`, and you can only have one parent in Java!
- And because not all dogs are `Huggable`, and not all `Huggable` things are `Dogs`, there isn't a clear hierarchy for where to include the interface.
- In addition, we can implement multiple interfaces (`Husky implements Huggable, Pettable`), but we can't inherit from multiple classes
  - This is great for when we have other classes of different types but similar behavior: e.g., a `TeddyBear` can be `Huggable` but can't `bark()` like a `Dog`!

___Create another interface `Huggable` (with a method `public void hug()`) that `Dog` implements___
- Have the `Dog` class _wag its tail_ (print that out) when it is hugged.
- Have the `Husky` class **override** this method so that it _wags its tail AND barks_ when hugged.
- Make the class `TeddyBear` implement `Huggable`. Do you need to add any new methods?

___What's the difference between inheritance and interfaces?___
The main rule of thumb: use _inheritance_ (`extends`) when you want classes to share **code** (implementation). Use _interfaces_ (`implements`) when you want classes to share **behaviors** (method signatures). In the end, _interfaces_ are more important for doing good Object-Oriented design.


## 5. Polymorphism
Implementing an interface also establishes an **is a** relationship: so a `Husky` **is a** `Huggable`. This allows the greatest benefit of interfaces and inheritance: [**polymorphism**](https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html), or the ability to treat one object as the type of another!

Consider the standard variable declaration:
```java
Dog myDog; //= new Dog();
```
The variable type of `myDog` is `Dog`, which means that variable can refer to any object that **is a** `Dog`.

___Try the following declarations (note that some will not compile!)___
```java
Dog v1 = new Husky();
Husky v2 = new Dog();
Huggable v2 = new Husky();
Huggable v3 = new TeddyBear();
Husky v4 = new TeddyBear();
```
If the **value** (the thing on the right side) _is a_ instance of the **variable type** (the type on the left side), then you have a valid declaration.

Even if you declare a variable `Dog v1 = new Husky()`, then the **value** in that object _is_ a `Husky`. If you call `.bark()` on it, you'll get the `Husky` version of the method (try overriding it to print out `"barks like a Husky"` to see).

You can **cast** between types if you need to convert. As long as the **value** _is a_ instance of the type you're casting to, the operation will work fine.
```java
Dog v1 = new Husky();
Husky v2 = (Husky)v1; //legal casting
```

The biggest benefit from polymorphism is abstraction. Consider:
```java
ArrayList<Huggable> hugList = new ArrayList<Huggable>();
hugList.add(new Husky());
hugList.add(new TeddyBear());

for(Huggabble thing : hugList) { //enhanced for loop ("foreach" loop)
    thing.hug();
}
```
___What happens if you run the above code?___ Because Huskies and Teddy Bears share the same behavior (`interface`), we can treat them as a single "type", and so put them both in a list. And because everything in the list supports the `Huggable` interface, we can call `.hug()` on each item in the list and we know they'll have that method---they promised by `implementing` the interface after all!


## 6. Abstract Methods and Classes
Take another look at the `Huggable` interface you created. It contains a single method declaration... followed by a semicolon. This is an [**abstract method**](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html): in fact, you can add the `abstract` keyword to this method declaration without changing anything (but all methods are interfaces are implicitly `abstract`, so it isn't required):
```java
public abstract void hug();
```
An **abstract method** is one that does not (yet) have a method body: it's just the signature, but no actual implementation (no body). It is "unfinished." In order to instantiate a class (using the `new` keyword), that class needs to be "finished" and provide implementations for _all_ abstract methods---e.g., all the ones you've inherited from an interface. This is exactly how you've used `interfaces` so far: it's just another way of thinking about what those methods are.

If the `abstract` keyword is implied for interfaces, what's the point? Well consider the `Animal` class (which is a parent class for `Dog`). The `.speak()` method is "empty"; in order for it to do anything, the subclass needs to override it. And currently there is nothing to stop someone who is subclassing `Animal` from forgetting to implement that method!

We can _force_ the subclass to override this method by making it `abstract`: effectively, leaving it unfinished so that if the `Dog` class wants to do anything, it must finish up the method. ___Make the `Animal#speak()` method `abstract`. What happens when you try and build the code?___

If the `Animal` class contains an unfinished (`abstract`) method... then that class itself is unfinished, and Java requires us to mark it as such. We do this by declaring the _class_ as `abstract` in the class declaration :
```java
public abstract class MyAbstractClass {...}
```
___Make the `Animal` class `abstract`.___ You will need to provide an implementation of the `.speak()` method in the `Dog` class: try just having it call the `.bark()` method.

Only abstract classes and `interfaces` can contain `abstract` methods. In addition, an `abstract` class is unfinished, meaning it can't be instantiated. ___Try and instantiate a `new Animal()`. What happens?___ Abstract classes are great for containing "most" of a class, but making sure that it isn't used without all the details provided. And if you think about it, we'd never want to ever instantiate a generic `Animal` anyway---we'd instead make a `Dog` or a `Cat` or a `Turtle` or something. All that the `Animal` class is acting as an **abstraction** for these other classes to allow them to share implementations (e.g., of a `walk()` method).
- Abstract classes are like "templates" for classes... which are themselves "templates" for objects.


## 7. Generics
Speaking of templates: think back to the `ArrayList` class you've used, and how you specified the "type" inside that List by using angle brackets (e.g., `ArrayList<Dog>`). Those angle brackets indicate that `ArrayList` is a [generic](https://docs.oracle.com/javase/tutorial/java/generics/) class: a template for a class where a _data type_ for that class is itself a variable.

Consider the `GiftBox` class, representing a box containing a `TeddyBear`. ___What changes would you need to make to this class so that it contains a `Husky` instead of a `TeddyBear`? What about if it contained a `String`?___

You should notice that the only difference between `TeddyGiftBox` and `HuskyGiftBox` and `StringGiftBox` would be the **variable type** of the contents. So rather than needing to duplicate work and write the same code for every different type of gift we might want to give... we can use **generics**.
- Generics let us specify a data type (e.g., what is currently `TeddyBear` or `String`) as a _variable_, which is set when we instantiate the class using the angle brackets (e.g., `GiftBox<TeddyBear>` would set that datatype variable to be `TeddyBear`).

We specify these by declaring the data type variable in the class declaration:
```java
public class GiftBox<T> {...}
```
(`T` is a common variable name, meaning "Type". Other options include `E` for Elements in lists, `K` for Keys and `V` for Values in maps).

And then everywhere you had a datatype (e.g., `TeddyBear`), you can just replace it with the `T` variable.
- Warning: _always_ use single-letter variable names for generic types! If you try to name it something like `String` (e.g., `public class GiftBox<String>`), then Java will interpret the word `String` to be that variable type, rather than refering to the `java.lang.String` class. This a lot like declaring a variable `int Dog = 498`, and then calling `Dog.createPuppies()`.

___Try to make the `GiftBox` class generic and instantiate a new `GiftBox<Husky>`___

## 8. Nested Classes
One last piece: we've been putting _attributes_ and _methods_ into classes... but we can also define additional _classes_ inside a class! These are called [**nested or inner classes**](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html).
- We'll often nest "helper classes" inside a bigger class: for example, you may have put a `Node` class inside a `LinkedList` class:
  ```java
  public class LinkedList {
    //nested class
    public class Node {
      private int data;

      public Node(int data) {
        this.data = data;
      }
    }

    private Node start;

    public LinkedList() {
      this.start = new Node(498);
    }
  }
  ```
- Or maybe we want to define a `Smell` class inside the `Dog` class to represent different smells, allowing us to talk about different `Dog.Smell` objects.
  - And of course, the `Dog.Smell` class would implement the `Sniffable` interface...

Nested classes we define are usually `static`: meaning they belong to the _class_ not to object instances of that class. This means that there is only one copy of that nested blueprint class in memory; it's the equivalent to putting the class in a separate file, but this lets us keep them in the same place and provides a "namespacing" function.

Non-static nested classes (or **inner classes**) on the other hand are defined for each object. This is important only if the behavior of that class is going to depend on the object in which it lives. This is a subtle point that we'll see as we provide inner classes required by the Android framework.

## Finish!
That covers most of the "CS 2"-level Java that we'll be interacting with; at least during the first few weeks. If there are any questions about other topics on the intro survey after your own reviewing, please check in with me!
