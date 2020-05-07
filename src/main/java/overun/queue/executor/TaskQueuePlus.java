package overun.queue.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import overun.queue.entity.ITask;
import overun.queue.thread.TaskExecutor;
import overun.queue.thread.TaskExecutorPlus;

import java.util.concurrent.*;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/7
 */
@Component
public class TaskQueuePlus {

    @Value("${threadSize}")
    private int threadSize;

    private static Logger log = LoggerFactory.getLogger(TaskQueue.class);

    /** 创建线程池 **/
    private ExecutorService executorService;

    /** 任务队列 */
    private BlockingQueue<ITask> mTaskQueue;


    /**
     * 构造方法
     */
    public TaskQueuePlus() {
        mTaskQueue = new LinkedBlockingQueue<>();

    }

    /**
     *   执行队列
     * 　queue.peek()：检索但不删除此队列的头，如果此队列为空，则返回 null
     *   queue.poll()：检索但不删除此队列的头，如果此队列为空，则返回 null
     *
     */
    public void start() {
        /** 创建线程池 **/
        executorService = Executors.newFixedThreadPool(threadSize);

        log.info("队列已启动");

        while (true) {

            /** 检索并删除此队列的头，如果此队列为空，则返回 null **/
            ITask peek = mTaskQueue.poll();

            /** 执行任务 **/
            if (peek != null) {
                log.info("开始执行Task:");
                TaskExecutorPlus taskExecutorPlus = new TaskExecutorPlus(peek);
                executorService.submit(taskExecutorPlus);
            }

        }
    }


    /**
     * 添加任务
     * @param task
     * @param <T>
     * @return
     */
    public <T extends ITask> int add(T task) {
        if (!mTaskQueue.contains(task)) {
            mTaskQueue.add(task);
        }
        /** 返回排的队的人数，公开透明，让外面的人看的有多少人在等着办事。 */
        return mTaskQueue.size();
    }


    /**
     * 获取当前队列的大小
     * @return
     */
    public int getSize() {
        return mTaskQueue.size();
    }

}
