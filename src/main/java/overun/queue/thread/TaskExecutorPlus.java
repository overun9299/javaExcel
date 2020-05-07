package overun.queue.thread;

import overun.queue.entity.ITask;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/7
 */
public class TaskExecutorPlus implements Runnable{

    /** 执行的任务 **/
    private ITask iTask;

    public TaskExecutorPlus(ITask iTask) {
        this.iTask = iTask;
    }


    @Override
    public void run() {
        iTask.run();
        System.out.println(Thread.currentThread().getName());
    }
}
