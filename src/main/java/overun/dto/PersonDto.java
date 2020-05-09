package overun.dto;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/9
 */
public class PersonDto {

    /** 主键 **/
    private Long id;

    /** 性别 **/
    private Byte sex;

    /** 部门 **/
    private Long dId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }
}
