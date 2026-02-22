package designpatterns.singleton.singletonv4;

public class Singletonv4 {

    private Singletonv4() {
        // private constructor to prevent instantiation
        System.out.println("Singletonv4 instance created");
    }

    public static class Holder {
        private static final Singletonv4 instance = new Singletonv4();
    }

    public static Singletonv4 getInstance() {
        return Holder.instance;
    }
}
//This is called Initialization-on-demand holder idiom.
//Created by: Bill Pugh.

/*
Part 1 — Why Static Initialization Is Thread-Safe
In Java, class loading is handled by the JVM, not by you.

When a class is loaded:
JVM initializes static variables
JVM guarantees it happens
Only once
By one thread
Before any thread can use the class
This is defined in the Java Language Specification (JLS).

🔐 What JVM Guarantees
When a class is initialized:
1️⃣ It acquires a lock internally
2️⃣ Only one thread executes static initialization
3️⃣ Other threads wait
4️⃣ After initialization completes → all threads see fully initialized state

So this is automatically thread-safe:
class Singleton {
    static Singleton instance = new Singleton();
}

No synchronized
No volatile
No double-checking
No complexity

Because JVM does:
Acquire Class Initialization Lock
Initialize static variables
Release Lock


🔎 Why This Is Safe
Static initialization happens:
When the class is first actively used
Only once
Fully completed before access

So instruction reordering cannot break it because:
👉 JVM guarantees a happens-before relationship
👉 All writes during static init are visible to all threads

🧠 Think of It Like This
The JVM says:
“Nobody touches this class until I finish building it.”
That’s stronger than synchronized.

🚀 Part 2 — Bill Pugh Singleton (The Cleanest Solution)
public class Singleton {

    private Singleton() {}

    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }
}
This is called Initialization-on-demand holder idiom.
Created by: Bill Pugh.

🧩 Why This Is Genius
Key Idea:
The inner static class Holder:
Is NOT loaded when Singleton loads
Is loaded only when getInstance() is called

🔥 What Happens Internally
When you call:
Acquire class initialization lock
Initialize static final INSTANCE
Release lock

Thread-safe.
Lazy.
No volatile.
No synchronized.
No double-checking.

🧠 Why It Avoids All Problems
| Problem                | Why Bill Pugh Avoids It             |
| ---------------------- | ----------------------------------- |
| Multiple threads       | JVM handles locking                 |
| Instruction reordering | Class initialization prevents it    |
| Visibility issues      | Guaranteed by JVM                   |
| Partial construction   | Impossible                          |
| Performance overhead   | No synchronization after first load |

⚡ Compare Approaches
❌ Double Checked Locking
Complex
Needs volatile
Easy to mess up
Hard to understand

✅ Bill Pugh
Clean
Lazy-loaded
JVM guaranteed thread-safe
No extra keywords

🎯 Final Answer (Simple)

Static initialization is thread-safe because:
JVM guarantees class initialization happens once, under a lock, and is visible to all threads.
Bill Pugh avoids all concurrency problems because:
It relies entirely on JVM class-loading guarantees instead of manual synchronization.


📌 First Important Concept
In Java:
👉 Inner static classes are NOT loaded when outer class loads.
👉 They are loaded only when they are first used.

This is called:
Lazy Class Loading

🔹 Step 1: Singleton Class Loads
Singleton.getInstance();

The JVM loads the Singleton class first.

At this point:
✔ Singleton is loaded
❌ Holder is NOT loaded yet

Why?
Because it hasn’t been referenced yet.

🔹 Step 2: getInstance() Executes
When this line runs:
return Holder.INSTANCE;

Now JVM sees:
👉 You are referencing the Holder class.
Since Holder is not loaded yet:
💥 JVM loads it NOW.

🔥 What Happens During Holder Loading
When Holder class loads:
private static final Singleton INSTANCE = new Singleton();

This static field is initialized.
And remember:
👉 Class initialization is thread-safe
👉 Happens once
👉 Under JVM internal lock

So JVM does:
1️⃣ Lock class initialization
2️⃣ Create new Singleton()
3️⃣ Assign to INSTANCE
4️⃣ Mark Holder as initialized
5️⃣ Release lock

🧠 Why This Is Lazy?
Because until this line runs:
Holder.INSTANCE

The Holder class does not even exist in memory.
It’s just bytecode sitting in .class file.

🔍 Why Only Holder Gets Loaded?

Because of Java rule:

A class is initialized only when it is actively used.
Active use includes:
Accessing static field
Calling static method
Creating instance
Reflection

In our case:
Holder.INSTANCE

That counts as active use.
So only then Holder loads.

🧠 Important Detail

Loading and initialization are different:
Loading = JVM reads class metadata
Initialization = static variables are assigned
Holder is both loaded AND initialized at first access.

🚀 Why This Is Better Than Double Checked Locking

Because:
No manual synchronization
No volatile
No instruction reordering issues
JVM handles locking
This is why it's called:
Initialization-on-demand holder idiom

🎯 Final One-Line Answer
Holder is created only when getInstance() references it because:
JVM loads and initializes a class only when it is first actively used.



 */

