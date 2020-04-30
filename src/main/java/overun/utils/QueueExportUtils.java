package overun.utils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import overun.pojo.Client;
import overun.queue.entity.ITask;
import overun.queue.executor.TaskQueue;

/**
 * 队列导出
 */
@Component
public class QueueExportUtils {


    @Autowired
    private TaskQueue taskQueue;


    public void QueueExport(ITask task) {

        taskQueue.add(task);
    }

}
