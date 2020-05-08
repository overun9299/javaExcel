package overun.queue.entity;

import overun.common.PriorityEnum;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/8
 */
public abstract class BasicTaskPlus implements Runnable , Comparable<BasicTaskPlus>{

    private PriorityEnum priority = PriorityEnum.DEFAULT;

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(BasicTaskPlus another) {
        final PriorityEnum me = this.getPriority();
        final PriorityEnum it = another.getPriority();
        if (it.ordinal() < me.ordinal()) {
            return -1;
        } else if (it.ordinal() > me.ordinal()) {
            return 1;
        } else {
            return 0;
        }
    }
}
