package overun.controller;

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
public class ExeclController {


    /**
     * 导入demo  起始行从第0行开始  isExcel2003 如果为false则文件后缀为xlsx 如果为true文件后缀为xls
     * @param excelVo
     * @throws Exception
     */
    @RequestMapping(value = "import")
    public void importExcel(ExcelVo excelVo) throws Exception{

        /** 获取上传文件的流文件 */
        InputStream is = new FileInputStream(excelVo.getPath());

        /** 执行方法获取到list */
        List<User> userList = ImportExcelTools.convertSheetToList(is, User.class, true, excelVo.getStartLine(), excelVo.getMaxCount());

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


    public static void main(String[] args) {

        ExcelVo excelVo = new ExcelVo();

        /**
         * 文件路径
         */
        excelVo.setPath("C:\\Users\\admin\\Desktop\\ee.xls");

        /**
         * 起始行数从0开始
         */
        excelVo.setStartLine(0);

        excelVo.setMaxCount(100);

        ExeclController exc = new ExeclController();

        try {
            exc.importExcel(excelVo);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }





}
