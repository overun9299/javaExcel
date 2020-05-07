package overun.queue.entity;

import java.util.Date;

/**
 * Created by ZhangPY on 2020/5/7
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain:
 */
public class PrintBasicTaskPlus extends BasicTaskPlus {

    private int id;

    public PrintBasicTaskPlus(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }

        System.out.println("我的id是：" + id + "--- Time" + new Date());
    }
}
