package overun.discard.threads;


import overun.discard.entity.ITask;

import java.util.concurrent.BlockingQueue;

/**
 * 任务执行器 、 比做窗口
 */
public class TaskExecutor extends Thread {

    /** 在窗口拍的队，这个队里面是办事的人 **/
    private BlockingQueue<ITask> taskQueue;

    /** 这个办事窗口是否在等待着办事。 **/
    private boolean isRunning = true;

    public TaskExecutor(BlockingQueue<ITask> taskQueue) {
        this.taskQueue = taskQueue;
    }

    /** 下班 **/
    public void quit() {
        isRunning = false;
        interrupt();
    }

    @Override
    public void run() {
        /** 如果是上班状态就待着。 */
        while (isRunning) {
            ITask iTask;
            try {
                /** 叫下一个办事的人进来，没有人就等着。 */
                iTask = taskQueue.take();
            } catch (InterruptedException e) {
                if (!isRunning) {
                    /** 发生意外了，是下班状态的话就把窗口关闭。 */
                    interrupt();
                    /** 如果执行到break，后面的代码就无效了。 */
                    break;
                }
                /** 发生意外了，不是下班状态，那么窗口继续等待。 */
                continue;
            }

            /** 为这个办事的人办事。 */
            iTask.run();
        }
    }


}
