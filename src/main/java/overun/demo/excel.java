package overun.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import overun.pojo.ExcelVo;
import overun.pojo.User;
import overun.utils.ExportExcelTools;
import overun.utils.ImportExcelTools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: excel
 * @Description:
 * @author: zhangPY
 * @version: V1.0
 * @date: 2019/4/10 17:15
 */
@Controller
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



    @RequestMapping(value = "export")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{

        /** 导出的数据 */
        List list = new ArrayList<User>();
        for (int i = 0 ; i< 12 ; i++) {
            User u = new User();
            u.setPswd("e"+i);
            u.setCreateTime(new Date());
            list.add(u);
        }

        /** 导出文件名 */
        String fileName = "测试报表.xls";

        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1"));

        new ExportExcelTools(response.getOutputStream()).writeHead(User.class).writeList(list).exportData();

    }


}
