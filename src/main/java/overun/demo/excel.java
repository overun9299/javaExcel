package overun.demo;

import overun.pojo.ExcelVo;
import overun.pojo.User;
import overun.utils.ImportExcelTools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName: excel
 * @Description:
 * @author: zhangPY
 * @version: V1.0
 * @date: 2019/4/10 17:15
 */
public class excel {


    /**
     * 导入demo
     * @param ExcelVo
     * @throws Exception
     */
    public void importExcel(ExcelVo ExcelVo) throws Exception{

        /** 获取上传文件的流文件 */
        InputStream is = new FileInputStream(ExcelVo.getPath());

        /** 执行方法获取到list */
        List<User> userList = ImportExcelTools.convertSheetToList(is, User.class, false, ExcelVo.getStartLine(), ExcelVo.getMaxCount());

        System.out.println(userList.size());
    }


}
