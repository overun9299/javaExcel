package overun.queue.threadpool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 初始化线程池
 * @Author ZhangPY
 * @Date 2020/5/8
 */
@Component
public class InitThreadPool {

//    /** 线程池的大小 **/
//    @Value("${threadSize}")
//    private int threadSize;
//
//    private ThreadPoolExecutor executor;
//
//    public ThreadPoolExecutor getExecutor() {
//        return executor;
//    }
//
//    public void setExecutor() {
//        this.executor = new ThreadPoolExecutor(
//                threadSize,
//                threadSize,
//                Long.MAX_VALUE,
//                TimeUnit.NANOSECONDS,
//                new PriorityBlockingQueue<Runnable>());
//    }



}
