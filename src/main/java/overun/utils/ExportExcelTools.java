package overun.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import overun.annotation.ExcelExport;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ExportExcelTools
 * @Description: 导出
 * @author: zhangPY
 * @version: V1.0
 * @date: 2019/4/10 17:51
 */
public class ExportExcelTools {

    /** 定义输出流对象 */
    private OutputStream outputStream;
    /** 定义Workbook对象 */
    private Workbook workbook;
    /** 定义HSSFSheet */
    private HSSFSheet sheet;
    private int index;


    /**
     * 以流的形式初始化ExportExcelTools
     * @param outputStream
     */
    public ExportExcelTools(OutputStream outputStream) {
        try {
            this.init(outputStream);
        } catch (Exception e) {
            System.out.println("导出excel出错" + e.getMessage());
        }
    }


    /**
     * 以流的形式初始化ExportExcelTools
     * @param fileOutputStream
     */
    private void init(OutputStream fileOutputStream) {
        this.outputStream = fileOutputStream;
        this.workbook = new HSSFWorkbook();
        this.index = 0;
    }


    /**
     * 写文件头
     * @param tClass
     * @return
     */
    public ExportExcelTools writeHead(Class<?> tClass) {
        if (tClass == null) {
            return this;
        }
        List<String> list = new ArrayList<String>();
        for (Field filed : getFields(tClass)) {
            ExcelExport excelExport = filed.getAnnotation(ExcelExport.class);
            list.add(excelExport.columnName());
        }
        createData(list);
        return this;
    }


    /**
     * 获取带有ExcelExport注解的属性
     * @param tClass
     * @return
     */
    private List<Field> getFields(Class<?> tClass) {
        Field[] fields = tClass.getDeclaredFields();
        List<Field> list = new ArrayList<Field>(fields.length);
        /** 遍历tClass中的属性 */
        for (Field f : fields) {
            /** 有ExcelExport注解的信息属性保留 */
            if (f.isAnnotationPresent(ExcelExport.class)) {
                list.add(f);
            }
        }
        return list;
    }


    /**
     * 将数据写入到excel的sheet 中
     * @param list
     */
    private void createData(List<String> list) {
        if (sheet == null) {
            sheet = (HSSFSheet) workbook.createSheet();
        }
        HSSFRow row = sheet.createRow(index++);
        HSSFCell[] cells = new HSSFCell[list.size()];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = row.createCell(i);
            cells[i].setCellValue(list.get(i));
        }
    }


    /**
     * 写入动态表头到指定位置
     * @param tClass
     * @param dynamicList
     * @param index
     * @return
     */
    public ExportExcelTools writeDyanmicHead(Class<?> tClass, List<String> dynamicList, int index) {
        if (tClass == null) {
            return this;
        }
        List<String> list = new ArrayList<String>();
        for (Field filed : getFields(tClass)) {
            ExcelExport excelExport = filed.getAnnotation(ExcelExport.class);
            list.add(excelExport.columnName());
        }
        list.addAll(list.size() - index, dynamicList);
        createData(list);
        return this;
    }


    /**
     * 写入列表数据
     * @param list
     * @param <T>
     * @return
     */
    public <T> ExportExcelTools writeList(List<T> list) {
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                writeObject(obj);
            }
        }
        return this;
    }


    /**
     * 按行写入数据
     * 根据对象的属性类型做格式化
     * 将一行数据写入到excel 表格
     * @param obj
     */
    private void writeObject(Object obj) {
        Class clazz = obj.getClass();
        List<String> list = new ArrayList<String>();
        for (Field f : getFields(clazz)) {
            f.setAccessible(true);
            try {
                Object o = f.get(obj);
                if (o instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    list.add(sdf.format(o));
                } else if (o instanceof BigDecimal) {
                    list.add(String.valueOf(((BigDecimal) o).setScale(2, BigDecimal.ROUND_CEILING)));
                } else {
                    if (null == o) {
                        list.add("");
                    } else {
                        list.add(String.valueOf(o));
                    }
                }
            } catch (IllegalAccessException e) {
                // e.printStackTrace();
                System.out.println("格式化obj失败" + e.getMessage());
            }
        }
        if (!list.isEmpty()) {
            createData(list);
        }
    }


    /**
     * 写入动态数据到excel指定位置
     * @param list
     * @param dynamicList
     * @param flag
     * @param <T>
     * @return
     */
    public <T> ExportExcelTools writeDynamicList(List<T> list, List<List<String>> dynamicList, int flag) {
        int index = 0;
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                writeDynamicObject(obj, dynamicList.get(index), flag);
                index++;
            }
        }
        return this;
    }


    /**
     * 写入动态行数据
     * @param obj
     * @param dynamicList
     * @param flag
     */
    private void writeDynamicObject(Object obj, List<String> dynamicList, int flag) {
        Class clazz = obj.getClass();
        List<String> list = new ArrayList<String>();
        for (Field f : getFields(clazz)) {
            f.setAccessible(true);
            try {
                Object o = f.get(obj);
                if (o instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    list.add(sdf.format(o));
                } else if (o instanceof BigDecimal) {
                    list.add(String.valueOf(((BigDecimal) o).setScale(2, BigDecimal.ROUND_CEILING)));
                } else {
                    if (null == o) {
                        list.add("");
                    } else {
                        list.add(String.valueOf(o));
                    }
                }
            } catch (IllegalAccessException e) {
                // e.printStackTrace();
                System.out.println("格式化obj失败" + e.getMessage());
            }
        }
        if (!list.isEmpty()) {
            list.addAll(list.size() - flag, dynamicList);
            createData(list);
        }
    }


    /**
     * 将内存的中信息输出到文件
     */
    public void exportData() {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("创建excel失败" + e.getMessage());
        }
    }
}
