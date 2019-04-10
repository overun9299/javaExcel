package overun.pojo;

/**
 * @ClassName: ImportExaminationVo
 * @Description:
 * @author: zhangPY
 * @version: V1.0
 * @date: 2019/4/10 16:37
 */
public class ExcelVo {

    /**
     * 文件路径
     */
    private String path;

    private String tempName;

    /**
     * 需要导入的起始行数
     */
    private Integer startLine;

    /**
     * 导入最大行数
     */
    private Integer maxCount;

    private String depId;

    private String create;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    @Override
    public String toString() {
        return "ExcelVo [path=" + path + ", tempName=" + tempName + ", startLine=" + startLine
                + ", maxCount=" + maxCount + ", depId=" + depId + ", create=" + create + "]";
    }

}
