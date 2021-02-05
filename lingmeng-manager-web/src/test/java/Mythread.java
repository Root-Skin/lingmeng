import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author skin
 * @createTime 2021年01月17日
 * @Description
 */
public class Mythread  extends  Thread{
    static  Object ob =  "aa" ; //值是任意的
//    private static volatile   int lingmeng = 0;
//    public void run(){    //run()是线程类的核心方法
//          synchronized (ob){
//              System.out.println(Thread.currentThread().getName()+" : "+ lingmeng++);
//          }
//
//    }

    private static volatile  AtomicInteger lingmeng = new AtomicInteger(0);
    public void run(){           //run()是线程类的核心方法
           System.out.println(Thread.currentThread().getName()+" : "+lingmeng.getAndIncrement());
    }

    public static void main(String[] args) {
        Mythread t1=new Mythread();
        Mythread t2=new Mythread();
        Mythread t3=new Mythread();
        t1.start();
        t2.start();
        t3.start();

    }
}
