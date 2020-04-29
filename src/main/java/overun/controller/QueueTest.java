package overun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import overun.queue.entity.PrintTask;
import overun.queue.executor.TaskQueue;


@RestController
public class QueueTest {

    @Autowired
    private TaskQueue taskQueue;


    @RequestMapping(value = "/queueTest")
    public void queueTest(Integer id) {
        taskQueue.start();
        PrintTask task = new PrintTask(id);
        taskQueue.add(task);
    }
}