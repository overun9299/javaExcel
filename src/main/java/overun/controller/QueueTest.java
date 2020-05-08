package overun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import overun.common.PriorityEnum;
import overun.discard.entity.PrintTask;
import overun.discard.executor.TaskQueuePlus;


@RestController
public class QueueTest {

    @Autowired
    private TaskQueuePlus taskQueuePlus;


    @RequestMapping(value = "/queueTest")
    public void queueTest(Integer id) {

        PrintTask task = new PrintTask(id);
        if (id == 10) {
            /** 当id=10 ,优先级最高 **/
            task.setPriority(PriorityEnum.HIGH);
        } else if (id == 3) {
            /** 当id=3 ,优先级最低 **/
            task.setPriority(PriorityEnum.LOW);
        }
        taskQueuePlus.add(task);
    }

    @RequestMapping(value = "/getQueueTestSize")
    public int getQueueTestSize() {
        return taskQueuePlus.getSize();
    }
}