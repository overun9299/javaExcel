package overun.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/15
 */
@Component
public class ImageUtils {

    /** 定义固定宽度 **/
    private int fixedWidth = 700;

    /** 定义图片之间的间隙 **/
    private int clearance = 5;

    /** 定义图片缩放比例 , 范围0~1  1为原画质 **/
    private float scale = 0.2f;


    @Async
    public String ImageMerge() {

        String imageBase64 = null;

        try {
            /** 6张照片 **/
            File _file1 = new File("D:\\image\\7.jpg");
            File _file2 = new File("D:\\image\\8.jpg");
            File _file3 = new File("D:\\image\\9.jpg");
            File _file4 = new File("D:\\image\\10.jpg");
            File _file5 = new File("D:\\image\\11.jpg");
            File _file6 = new File("D:\\image\\12.jpg");

            /** 放入list,并降低图片质量 **/
            List<Image> imageList = new ArrayList<>();
            imageList.add(Thumbnails.of(_file1).scale(scale).asBufferedImage());
            imageList.add(Thumbnails.of(_file2).scale(scale).asBufferedImage());
            imageList.add(Thumbnails.of(_file3).scale(scale).asBufferedImage());
            imageList.add(Thumbnails.of(_file4).scale(scale).asBufferedImage());
            imageList.add(Thumbnails.of(_file5).scale(scale).asBufferedImage());
            imageList.add(Thumbnails.of(_file6).scale(scale).asBufferedImage());

            /** 计算高度 **/
            int totalHeight = 0;
            for (Image image : imageList) {
                totalHeight = totalHeight + getEquivalency(image);
            }
            /** 加入间隙 **/
            totalHeight = totalHeight + ( imageList.size() -1 ) * clearance;

            /** 构造一个类型为预定义图像类型之一的 BufferedImage。 宽度为固定，高度为各个图片高度之和 **/
            BufferedImage tag = new BufferedImage(fixedWidth, totalHeight, BufferedImage.TYPE_INT_RGB);

            /** 绘制合成图像 **/
            Graphics graphics2D = tag.createGraphics();
            int yCoordinate = 0;
            for (Image image : imageList) {
                graphics2D.drawImage(image, 0, yCoordinate, fixedWidth, getEquivalency(image), null);
                yCoordinate = yCoordinate + getEquivalency(image) + clearance;
            }

            /** 释放此图形的上下文以及它使用的所有系统资源 **/
            graphics2D.dispose();

            /** 转Base64 **/
            imageBase64 = ToBase64(tag);
        } catch (IOException e) {
            e.printStackTrace();

        }

        return imageBase64;
    }


    /**
     * BufferedImage -> Base64
     * @param bufferedImage
     * @return
     */
    public String ToBase64(BufferedImage bufferedImage) {
        String base64 = null;
        try {
            /** io流 **/
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            /** 写入流中 **/
            ImageIO.write(bufferedImage, "png", baos);
            /** 转换成字节 **/
            byte[] bytes = baos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            /** 转换成base64串 **/
            String png_base64 = encoder.encodeBuffer(bytes).trim().replaceAll("\n", "").replaceAll("\r", "");

            /** 拼接 data:image/png;base64 **/
            base64 = "data:image/png;base64,"+png_base64;

            /** 测试将Base64写入文件 , StandardOpenOption.CREATE是处理文件的方式，表示不管路径下有或没有，都创建这个文件，有则覆盖 **/
            Files.write(Paths.get("D:\\image\\god.png"), Base64.getDecoder().decode(png_base64), StandardOpenOption.CREATE);

            System.out.println(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64;
    }


    /**
     * 获取等比缩放后的高度
     * @param image
     * @return
     */
    private int getEquivalency(Image image) {
        double width = image.getWidth(null);
        double height = image.getHeight(null);
        Double equivalency = fixedWidth/width;
        int equHeight = (int) (equivalency * height);
        return equHeight;
    }


    /**
     * 从ftp读取文件
     */
    private void getImageForFTP() {

        try {
            FTPClient ftpClient=new FTPClient();
            /** 连接ftp,端口默认21 **/
            ftpClient.connect("192.168.153.2", 21);
            /** 账号密码登陆ftp **/
            ftpClient.login("admin", "123456");
            /** 是否连接成功 **/
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                /** 获取目录 **/
                ftpClient.changeWorkingDirectory("/pathName");


                /** 获取文件集合 **/
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    InputStream inputStream = ftpClient.retrieveFileStream(ftpFile.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            FTPClient ftpClient=new FTPClient();
            /** 连接ftp,端口默认21 **/
            ftpClient.connect("192.168.3.126", 21);
            /** 账号密码登陆ftp **/
            ftpClient.login("xianze", "0");
            boolean positiveCompletion = FTPReply.isPositiveCompletion(ftpClient.getReplyCode());
            if (positiveCompletion) {
                InputStream inputStream = ftpClient.retrieveFileStream("7.jpg");

                BufferedImage read = ImageIO.read(inputStream);
                read.getHeight();
                read.getWidth();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
