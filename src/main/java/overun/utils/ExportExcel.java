//package overun.utils;
//
//import com.google.common.collect.Lists;
//import com.core.common.utils.Reflections;
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.common.usermodel.HyperlinkType;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Comment;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.Hyperlink;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.VerticalAlignment;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
// */
//public class ExportExcel {
//
//   /**
//    * 单页最大的行数
//    */
//   private static final int MAX_ROW_NUM = 11000;
//
//   /**
//    * 工作薄对象
//    */
//   private SXSSFWorkbook wb;
//
//   /**
//    * 工作表对象
//    */
//   private Sheet sheet;
//
//   /**
//    * 样式列表
//    */
//   private Map<String, CellStyle> styles;
//
//   /**
//    * 当前行号
//    */
//   private int rownum;
//
//   /**
//    * 注解列表（Object[]{ ExcelField, Field/Method }）
//    */
//   private List<Object[]> annotationList = Lists.newArrayList();
//
//   /**
//    * 添加数据（通过annotation.ExportField添加数据）
//    *
//    * @return list 数据列表
//    */
//   public <E> ExportExcel setDataList(List<E> list) {
//      if (list == null) {
//         return this;
//      }
//      for (E e : list) {
//         int colunm = 0;
//         Row row = this.addRow();
//         for (Object[] os : annotationList) {
//            ExcelField ef = (ExcelField) os[0];
//            Object val = null;
//            try {
//               if (StringUtils.isNotBlank(ef.value())) {
//                  val = Reflections.invokeGetter(e, ef.value());
//               } else {
//                  if (os[1] instanceof Field) {
//                     val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
//                  } else if (os[1] instanceof Method) {
//                     val =
//                           Reflections
//                                 .invokeMethod(e, ((Method) os[1]).getName(), new Class[]{}, new Object[]{});
//                  }
//               }
//            } catch (Exception ex) {
//               val = "";
//            }
//            this.addCell(row, colunm++, val, ef, ef.fieldType());
//         }
//      }
//      return this;
//   }
//
//   /**
//    * 输出数据流
//    *
//    * @param os 输出数据流
//    */
//   public ExportExcel write(OutputStream os) throws IOException {
//      wb.write(os);
//      os.flush();
//      os.close();
//      return this;
//   }
//
//   /**
//    * 添加一个单元格
//    *
//    * @param row    添加的行
//    * @param column 添加列号
//    * @param val    添加值
//    * @param ef     ef.align对齐方式（1：靠左；2：居中；3：靠右）,ef.link 链接形式展示
//    * @return 单元格对象
//    */
//   private Cell addCell(Row row, int column, Object val, ExcelField ef, Class<?> fieldType) {
//      Cell cell = row.createCell(column);
//      String cellFormatString = "@";
//      try {
//         if (val == null) {
//            cell.setCellValue("");
//         } else if (fieldType != Class.class) {
//            cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
//         } else {
//            if (val instanceof String) {
//               if (ef.link() != HyperlinkType.NONE) {
//                  Hyperlink hyperlink = wb.getCreationHelper().createHyperlink(ef.link());
//                  hyperlink.setAddress((String) val);
//                  cell.setHyperlink(hyperlink);
//               }
//               cell.setCellValue((String) val);
//            } else if (val instanceof Boolean) {
//               cell.setCellValue(Boolean.valueOf(val.toString()));
//            } else if (val instanceof Integer) {
//               cell.setCellValue((Integer) val);
//               cellFormatString = "0";
//            } else if (val instanceof Long) {
//               cell.setCellValue((Long) val);
//               cellFormatString = "0";
//            } else if (val instanceof Double) {
//               cell.setCellValue((Double) val);
//               cellFormatString = "0.00";
//            } else if (val instanceof Float) {
//               cell.setCellValue((Float) val);
//               cellFormatString = "0.00";
//            } else if (val instanceof Date) {
//               cell.setCellValue((Date) val);
//               cellFormatString = "yyyy-MM-dd HH:mm:ss";
//            } else {
//               cell.setCellValue("");
//            }
//         }
//         CellStyle style = styles.get("data_column_" + column);
//         if (style == null) {
//            int align = ef.align();
//            style = wb.createCellStyle();
//            style.cloneStyleFrom(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));
//            style.setDataFormat(wb.createDataFormat().getFormat(cellFormatString));
//
//            styles.put("data_column_" + column, style);
//         }
//         cell.setCellStyle(style);
//      } catch (Exception ex) {
//         ex.printStackTrace();
//         cell.setCellValue(val.toString());
//      }
//      return cell;
//   }
//
//   /**
//    * 添加一行
//    *
//    * @return 行对象
//    */
//   private Row addRow() {
//      return sheet.createRow(rownum++);
//   }
//
//
//   /**
//    * 构造函数
//    *
//    * @param title     表格标题，传“空值”，表示无标题
//    * @param sheetName 页签名称
//    * @param cls       实体对象，通过annotation.ExportField获取标题
//    */
//   public ExportExcel(String title, String sheetName, Class<?> cls) {
//      this(title, sheetName, cls, 1);
//   }
//
//   /**
//    * 构造函数
//    *
//    * @param title     表格标题，传“空值”，表示无标题
//    * @param sheetName 页签名称
//    * @param cls       实体对象，通过annotation.ExportField获取标题
//    * @param type      导出类型（1:导出数据；2：导出模板）
//    * @param groups    导入分组
//    */
//   public ExportExcel(String title, String sheetName, Class<?> cls, int type, int... groups) {
//      // Get annotation field
//      List<Field> allFields = new ArrayList<>();
//      Field[] fs = cls.getDeclaredFields();
//      allFields.addAll(Arrays.asList(fs));
//      Class superClass = cls.getSuperclass();
//      while (superClass != null) {
//         Field[] declaredFields = superClass.getDeclaredFields();
//         allFields.addAll(Arrays.asList(declaredFields));
//         superClass = superClass.getSuperclass();
//      }
//      for (Field f : allFields) {
//         ExcelField ef = f.getAnnotation(ExcelField.class);
//         if (ef != null && (ef.type() == 0 || ef.type() == type)) {
//            if (groups != null && groups.length > 0) {
//               boolean inGroup = false;
//               for (int g : groups) {
//                  if (inGroup) {
//                     break;
//                  }
//                  for (int efg : ef.groups()) {
//                     if (g == efg) {
//                        inGroup = true;
//                        annotationList.add(new Object[]{ef, f});
//                        break;
//                     }
//                  }
//               }
//            } else {
//               annotationList.add(new Object[]{ef, f});
//            }
//         }
//      }
//      // Get annotation method
//      // Field sorting
//      annotationList.sort(Comparator.comparing(o -> (((ExcelField) o[0]).sort())));
//      // Initialize
//      List<String> headerList = Lists.newArrayList();
//      for (Object[] os : annotationList) {
//         String t = ((ExcelField) os[0]).title();
//         // 如果是导出，则去掉注释
//         if (type == 1) {
//            String[] ss = StringUtils.split(t, "**", 2);
//            if (ss.length == 2) {
//               t = ss[0];
//            }
//         }
//         headerList.add(t);
//      }
//      initialize(title, sheetName, headerList);
//   }
//
//   /**
//    * 初始化函数
//    *
//    * @param title      表格标题，传“空值”，表示无标题
//    * @param headerList 表头列表
//    */
//   private void initialize(String title, String sheetName, List<String> headerList) {
//      this.wb = new SXSSFWorkbook(MAX_ROW_NUM);
//      if (StringUtils.isNotEmpty(sheetName)) {
//         this.sheet = wb.createSheet(sheetName);
//      } else {
//         this.sheet = wb.createSheet("Export");
//      }
//      this.styles = createStyles(wb);
//      // Create title
//      if (StringUtils.isNotBlank(title)) {
//         Row titleRow = sheet.createRow(rownum++);
//         titleRow.setHeightInPoints(30);
//         Cell titleCell = titleRow.createCell(0);
//         titleCell.setCellStyle(styles.get("title"));
//         titleCell.setCellValue(title);
//         sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
//                                          titleRow.getRowNum(), titleRow.getRowNum(),
//                                          headerList.size() - 1));
//      }
//      // Create header
//      if (headerList == null) {
//         throw new RuntimeException("headerList not null!");
//      }
//      Row headerRow = sheet.createRow(rownum++);
//      headerRow.setHeightInPoints(16);
//      for (int i = 0; i < headerList.size(); i++) {
//         Cell cell = headerRow.createCell(i);
//         cell.setCellStyle(styles.get("header"));
//         String[] ss = StringUtils.split(headerList.get(i), "**", 2);
//         if (ss.length == 2) {
//            cell.setCellValue(ss[0]);
//            Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
//                  new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
//            comment.setString(new XSSFRichTextString(ss[1]));
//            cell.setCellComment(comment);
//         } else {
//            cell.setCellValue(headerList.get(i));
//         }
//         //  sheet.autoSizeColumn(i);
//      }
//      for (int i = 0; i < headerList.size(); i++) {
//         int colWidth = sheet.getColumnWidth(i) * 2;
//         sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
//      }
//   }
//
//   /**
//    * 创建表格样式
//    *
//    * @param wb 工作薄对象
//    * @return 样式列表
//    */
//   private Map<String, CellStyle> createStyles(Workbook wb) {
//      Map<String, CellStyle> styles = new HashMap<>(16);
//
//      CellStyle style = wb.createCellStyle();
//      style.setAlignment(HorizontalAlignment.CENTER);
//      style.setVerticalAlignment(VerticalAlignment.CENTER);
//      Font titleFont = wb.createFont();
//      titleFont.setFontName("微软雅黑");
//      titleFont.setFontHeightInPoints((short) 16);
//      titleFont.setBold(true);
//      style.setFont(titleFont);
//      styles.put("title", style);
//
//      style = wb.createCellStyle();
//      style.setVerticalAlignment(VerticalAlignment.CENTER);
//      style.setBorderRight(BorderStyle.THIN);
//      style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//      style.setBorderLeft(BorderStyle.THIN);
//      style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//      style.setBorderTop(BorderStyle.THIN);
//      style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//      style.setBorderBottom(BorderStyle.THIN);
//      style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//      Font dataFont = wb.createFont();
//      dataFont.setFontName("微软雅黑");
//      dataFont.setFontHeightInPoints((short) 10);
//      style.setFont(dataFont);
//      styles.put("data", style);
//
//      style = wb.createCellStyle();
//      style.cloneStyleFrom(styles.get("data"));
//      style.setAlignment(HorizontalAlignment.LEFT);
//      styles.put("data1", style);
//
//      style = wb.createCellStyle();
//      style.cloneStyleFrom(styles.get("data"));
//      style.setAlignment(HorizontalAlignment.CENTER);
//      styles.put("data2", style);
//
//      style = wb.createCellStyle();
//      style.cloneStyleFrom(styles.get("data"));
//      style.setAlignment(HorizontalAlignment.RIGHT);
//      styles.put("data3", style);
//
//      style = wb.createCellStyle();
//      style.cloneStyleFrom(styles.get("data"));
//      style.setAlignment(HorizontalAlignment.CENTER);
//      style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//      style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//      Font headerFont = wb.createFont();
//      headerFont.setFontName("微软雅黑");
//      headerFont.setFontHeightInPoints((short) 10);
//      headerFont.setBold(true);
//      headerFont.setColor(IndexedColors.WHITE.getIndex());
//      style.setFont(headerFont);
//      styles.put("header", style);
//
//      return styles;
//   }
//}