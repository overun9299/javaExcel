package overun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import overun.queue.entity.PrintTask;
import overun.queue.executor.TaskQueue;
import overun.queue.executor.TaskQueuePlus;


@RestController
public class QueueTest {

    @Autowired
    private TaskQueuePlus taskQueuePlus;


    @RequestMapping(value = "/queueTest")
    public void queueTest(Integer id) {
        PrintTask task = new PrintTask(id);
        taskQueuePlus.add(task);
    }

    @RequestMapping(value = "/getQueueTestSize")
    public int getQueueTestSize() {
        return taskQueuePlus.getSize();
    }
}