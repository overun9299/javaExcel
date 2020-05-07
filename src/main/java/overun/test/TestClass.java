package overun.test;

import overun.common.PriorityEnum;
import overun.queue.entity.PrintBasicTaskPlus;
import overun.queue.entity.PrintBasicTaskPlus;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZhangPY on 2020/5/7
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain:
 */
public class TestClass {

    public static void main(String[] args) {

        /** 线程池 */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                1, Long.MAX_VALUE,
                TimeUnit.NANOSECONDS, new PriorityBlockingQueue<Runnable>());

        PrintBasicTaskPlus p1 = new PrintBasicTaskPlus(1);

        PrintBasicTaskPlus p2 = new PrintBasicTaskPlus(2);
        p2.setPriority(PriorityEnum.LOW);

        PrintBasicTaskPlus p3 = new PrintBasicTaskPlus(3);

        PrintBasicTaskPlus p4 = new PrintBasicTaskPlus(4);

        PrintBasicTaskPlus p5 = new PrintBasicTaskPlus(5);

        PrintBasicTaskPlus p6 = new PrintBasicTaskPlus(6);
        p6.setPriority(PriorityEnum.HIGH);

        PrintBasicTaskPlus p7 = new PrintBasicTaskPlus(7);

        executor.execute(p1);
        executor.execute(p2);
        executor.execute(p3);
        executor.execute(p4);
        executor.execute(p5);
        executor.execute(p6);
        executor.execute(p7);
    }
}
