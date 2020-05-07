package overun.pojo;

import overun.annotation.ExcelExport;

/**
 * 客户表
 */
public class Client {

    /** 客户主键id **/
    @ExcelExport(columnName = "客户id")
    private Integer cId;

    /** 客户名称 **/
    private String cName;

    /** 对应用户id **/
    private String cUserId;

    /** 客户电话 **/
    @ExcelExport(columnName = "客户电话")
    private String cPhone;


    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcUserId() {
        return cUserId;
    }

    public void setcUserId(String cUserId) {
        this.cUserId = cUserId;
    }

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

}
