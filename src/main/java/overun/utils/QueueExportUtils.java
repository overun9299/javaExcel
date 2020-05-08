package overun.utils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import overun.discard.entity.ITask;
import overun.discard.executor.TaskQueue;

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
