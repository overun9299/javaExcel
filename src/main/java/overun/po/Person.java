package overun.po;

import overun.annotation.ExcelExport;

public class Person {

    @ExcelExport(columnName = "id")
    private Long id;

    @ExcelExport(columnName = "first-name")
    private String fname;

    @ExcelExport(columnName = "last-name")
    private String lname;

    @ExcelExport(columnName = "年龄")
    private Byte age;

    @ExcelExport(columnName = "性别")
    private Byte sex;

    @ExcelExport(columnName = "部门id")
    private Long dId;

    @ExcelExport(columnName = "地址")
    private String address;

    @ExcelExport(columnName = "描述")
    private String describes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}