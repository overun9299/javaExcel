package overun.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import overun.annotation.ExcelImport;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ImportExcelTools
 * @Description:导入excel 文件
 * @author: zhangPY
 * @version: V1.0
 * @date: 2019/4/10 16:39
 */
public class ImportExcelTools {

    /**
     * 设置导入数据的最大行数
     */
    private static final int DEFAULT_COUNT=20000;
    /**
     * 设置导入数据的起始行数
     */
    private static final int DEFAULT_START_LINE=0;


    /**
     * 根据参数将导入excel数据转化为list
     * @param in excel 转换成的流
     * @param clazz 行对象
     * @param isExcel2003 是否2003版
     * @param startLine 从哪一行开始
     * @param maxCount 最大行数
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> convertSheetToList(InputStream in, Class<T> clazz, boolean isExcel2003,
                                                 int startLine, int maxCount) throws Exception {
        List<T> list = new ArrayList<T>();
        /** 根据版本选择创建Workbook的方式 */
        Workbook wb;
        if (isExcel2003) {
            wb = new HSSFWorkbook(in);
        } else {
            wb = new XSSFWorkbook(in);
        }
        if(null != wb){
            /**获取第0个工作表格*/
            Sheet sheet = wb.getSheetAt(0);
            int count = sheet.getLastRowNum();
            if(maxCount == 0){
                maxCount = DEFAULT_COUNT;
            }
            if(count > maxCount){
                throw new Exception("导入失败，excel数据控制在"+maxCount+"条之内！");
            }
            /**遍历excel表格并将每一行中的数据转换成对象*/
            if(startLine < 0){
                startLine = DEFAULT_START_LINE;
            }
            for(int i=startLine;i<=count;i++){
                Row row = sheet.getRow(i);
                if(row==null){
                    continue;
                }
                T obj = convertLineToObj(clazz,row);
                if(obj==null){
                    continue;
                }
                list.add(obj);
            }
        }
        return list;
    }


    /**
     * 将行数据转换成class（按注解转换）
     * @param clazz
     * @param row
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> T convertLineToObj(Class<T> clazz, Row row) throws Exception {
        T obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            ExcelImport annotation = field.getAnnotation(ExcelImport.class);
            if(annotation!=null && row.getLastCellNum() >= annotation.columnIndex()){
                /**每行对应的单元格遍历*/
                Cell cell = row.getCell(annotation.columnIndex());
                if(cell==null)
                    throw new Exception("请使用正确的excel模板");
                field.setAccessible(true);
                field.set(obj,getCellValue(cell));
            }
        }
        return obj;
    }


    /**
     * 将cell 的数据格式化
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
            Integer strs = (int) cell.getNumericCellValue();
            return strs.toString();
        }
        return String.valueOf(cell.getStringCellValue());
    }
}
