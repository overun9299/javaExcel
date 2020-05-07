package overun.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import overun.queue.executor.TaskQueue;
import overun.queue.executor.TaskQueuePlus;

import java.io.File;


/**
 * 楠宝起的名字，问雀雀
 * Queue队列执行器，用于springboot加载的时候初始化容器
 * @author ZhangPY
 */
@Component
public class QueueExportRunner implements ApplicationRunner {


    @Autowired
    private TaskQueue taskQueue;

    @Autowired
    private TaskQueuePlus taskQueuePlus;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        /** 启动队列 **/
//        taskQueue.start();

        taskQueuePlus.start();

        /** 创建导出文件夹 **/
        File file = new File("D:\\file");
        if (!file.exists()) {
            file.mkdirs();
        }

    }
}
