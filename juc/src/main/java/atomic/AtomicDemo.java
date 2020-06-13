package atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子类的原理是CAS原理，使用的书unsafe类，此类中大多数的native方法。CAS就是比较交换的过程，预期值和修改时查询的值是否相等，等等则交换，不等则自旋
 * CAS会出现ABA问题，即从A修改到B又修改到A，则其他线程不知道中间过程，只比较了第一查询的A，也做出了修改交换，因此中间的过程就会丢失,使用AtomicStampedReference带时间戳原子引用即可以解决ABA问题
 */
class User
{
    String id = "1";
    String name = "zs";

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

public class AtomicDemo {
    public static void main(String[] args) {
        /**
         * atomicreference的基本使用
         * 它和AtomicInteger等类的区别，是reference比较的是对象的地址，整形等是比较的值
         * 所以保证的是修改对象引用时的原子性，修改对象中变量不会对比较产生影响
         */
        System.out.println("*********************ABA问题************************");
        User ls = new User("1","ls");
        User w5 = new User("2","w5");

        AtomicReference<User> atomicReference = new AtomicReference(ls);
        System.out.println("未修改前的`A`\t"+atomicReference.get().toString());
        new Thread(()->{
            System.out.println("Thread1：由`A`修改成`B`～"+atomicReference.compareAndSet(ls,w5)+"\t"+atomicReference.get().toString());
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace();}
            System.out.println("Thread1:又迅速的将`B`又修改成`A`～"+atomicReference.compareAndSet(w5,ls)+"\t"+atomicReference.get().toString());
        },"ThreadA").start();

        new Thread(()->{
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace();}
            System.out.println("Thread2:因为和A比较成功，但不清楚中间的过程经历过一次修改～"+atomicReference.compareAndSet(ls,w5)+"\t"+atomicReference.get().toString());
        },"Thread2").start();

        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println("****************************测试ABA问题******************************");
        /**
         * atomicStampedReference的基本使用,测试ABA问题，加时间戳
         */
        AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference(ls,0);
        new Thread(()->{
            atomicStampedReference.compareAndSet(ls,w5,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println("Thread1：由`A`修改成`B`,此时的时间戳为\t"+atomicStampedReference.getStamp());
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace();}
            atomicStampedReference.compareAndSet(w5,ls,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println("Thread1：由`B`修改成`A`,此时的时间戳为\t"+atomicStampedReference.getStamp());
        },"ThreadA").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("Thread2 获取的时间戳位\t"+stamp);
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace();}
            boolean flag = atomicStampedReference.compareAndSet(ls, w5, stamp, atomicStampedReference.getStamp() + 1);
            System.out.println("Thread2去比较并交换，因获取的时间戳为1，但此时修改过两次时间戳已经变成2，比对不成功修改结果为"+flag);
        },"Thread2").start();

        /**
         * 此为Unsafe类，CAS自旋源码
         * public final int getAndSetInt(Object var1, long var2, int var4) {
         *         int var5;
         *         do {
         *             var5 = this.getIntVolatile(var1, var2); 循环获取当前的比较预期值
         *         } while(!this.compareAndSwapInt(var1, var2, var5, var4)); 比较成功则退出自旋
         *
         *         return var5;
         * }*/
    }
}
