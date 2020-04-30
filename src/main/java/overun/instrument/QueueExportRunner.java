package overun.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import overun.queue.executor.TaskQueue;

import java.io.File;

@Component
public class QueueExportRunner implements ApplicationRunner {


    @Autowired
    private TaskQueue taskQueue;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        /** 启动队列 **/
        taskQueue.start();

        /** 创建导出文件夹 **/
        File file = new File("D:\\file");
        if (!file.exists()) {
            file.mkdirs();
        }

    }
}
