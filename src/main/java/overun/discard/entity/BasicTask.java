package overun.discard.entity;

import overun.common.PriorityEnum;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/7
 */
public abstract class BasicTask implements ITask {

    /** 默认优先级 **/
    private PriorityEnum priority = PriorityEnum.DEFAULT;

    private int sequence;

    @Override
    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    @Override
    public PriorityEnum getPriority() {
        return priority;
    }

    @Override
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    @Override
    public int compareTo(ITask another) {
        final PriorityEnum me = this.getPriority();
        final PriorityEnum it = another.getPriority();
        return me == it ? this.getSequence() - another.getSequence() : it.ordinal() - me.ordinal();
    }
}
