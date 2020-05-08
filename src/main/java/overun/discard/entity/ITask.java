package overun.discard.entity;


import overun.common.PriorityEnum;

/**
 * 任务
 */
public interface ITask extends Comparable<ITask>{

    void run();

    void setPriority(PriorityEnum priority);

    PriorityEnum getPriority();

    void setSequence(int sequence);

    int getSequence();
}
