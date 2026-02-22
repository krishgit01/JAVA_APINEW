package designpatterns.singleton.singletonv3;

public class Singletonv3 {
    private static volatile Singletonv3 instance;

    private Singletonv3() {
        // private constructor to prevent instantiation
        System.out.println("Singletonv3 instance created");
    }

    public static Singletonv3 getInstance() {
        if (instance == null) {            // 1st check
            synchronized (Singletonv3.class) {
                if (instance == null) {     // 2nd check
                    instance = new Singletonv3(); //create object
                }
            }
        }
        return instance;
    }
}


/*
🔥 The Real Problem: Object Creation Is NOT One Step
instance = new Singletonv3();
1. Memory allocation for the object

You think it does this:
Create object
Assign to instance
❌ Wrong.

The JVM actually does 3 steps internally:
1️⃣ Allocate memory
2️⃣ Assign memory address to instance
3️⃣ Run constructor


⚠ And the JVM is allowed to reorder steps 2 and 3!
It may do:
1️⃣ Allocate memory
2️⃣ Assign reference to instance   <-- visible to other threads
3️⃣ Run constructor
This is called instruction reordering.

🚨 What Goes Wrong?
Let’s imagine 2 threads:
Thread A
Thread B

🧵 Thread A runs first
It enters synchronized block and executes:
1️⃣ Allocate memory
2️⃣ Assign reference to instance
⚠ But constructor NOT finished yet.

🧵 Thread B runs now
It checks:
if (instance == null)

Since reference was already assigned:
👉 instance != null
So it skips synchronized block.
It returns the instance.
But...
💥 The object is NOT fully constructed yet.

🎯 Result?
Thread B is using:
👉 A HALF-CONSTRUCTED OBJECT
That means:
Some fields may be null
Some values may be default
Object state is inconsistent
You get random bugs

These are the worst bugs:
Rare
Hard to reproduce
Only in production
Only under heavy load

🔒 Why volatile Fixes This
When you write:
private static volatile  Singletonv3 instance;

It guarantees:
1️⃣ No instruction reordering
2️⃣ Fully constructed object before visible
3️⃣ All threads see latest value

So steps must happen like:
1️⃣ Allocate memory
2️⃣ Run constructor
3️⃣ Assign reference to instance


 synchronized (Singleton.class) this should prevent entering the thread?
 Excellent 🔥 this is exactly where deep understanding starts.

 You’re thinking:
 If we use synchronized (Singleton.class), shouldn’t that prevent other threads from entering and make it safe?

 Short answer:
👉 It prevents multiple threads from entering the synchronized block at the same time.
👉 But it does NOT prevent a thread from reading instance outside the block.
That’s the key.


💡 Very Important Concept
There are TWO different problems in multithreading:
1️⃣ Mutual exclusion → solved by synchronized
2️⃣ Visibility + ordering → solved by volatile
Double Checked Locking needs BOTH.

🧠 One-Line Deep Answer
synchronized prevents multiple threads from executing the block at the same time,
but it does NOT prevent another thread from seeing a partially constructed object published outside the block.

 */