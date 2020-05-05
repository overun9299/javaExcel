package overun.queue.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import overun.queue.entity.ITask;
import overun.queue.thread.TaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 *
 */
@Component
public class TaskQueue {


    private static Logger log = LoggerFactory.getLogger(TaskQueue.class);

    /** 某机构排的队，队里面是办事的人。 */
    private BlockingQueue<ITask> mTaskQueue;
    /** 好多窗口。 */
    private TaskExecutor[] mTaskExecutors;

    /** 在开发者new队列的时候，要指定窗口数量。 */
    public TaskQueue() {
        mTaskQueue = new LinkedBlockingQueue<>();
        mTaskExecutors = new TaskExecutor[1];
    }

    /** 开始上班。 */
    public void start() {
        stop();
        /** 把各个窗口都打开，让窗口开始上班。 */
        for (int i = 0; i < mTaskExecutors.length; i++) {
            mTaskExecutors[i] = new TaskExecutor(mTaskQueue);
            mTaskExecutors[i].start();
        }
        log.info("队列已启动");

    }

    /** 统一各个窗口下班。 */
    public void stop() {
        if (mTaskExecutors != null)
            for (TaskExecutor taskExecutor : mTaskExecutors) {
                if (taskExecutor != null) taskExecutor.quit();
            }
    }

    /** 开一个门，让办事的人能进来。 */
    public <T extends ITask> int add(T task) {
        if (!mTaskQueue.contains(task)) {
            mTaskQueue.add(task);
        }
        /** 返回排的队的人数，公开透明，让外面的人看的有多少人在等着办事。 */
        return mTaskQueue.size();
    }
}
