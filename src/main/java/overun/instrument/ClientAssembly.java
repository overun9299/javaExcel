package overun.instrument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import overun.pojo.Client;
import overun.queue.entity.ITask;
import overun.utils.ExportExcelTools;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Client的组件
 * @author ZhangPY
 */
public class ClientAssembly implements ITask {

    private static Logger log = LoggerFactory.getLogger(ClientAssembly.class);

    private Integer count;

    @Override
    public void run() {


        /** 导出的数据 */
        List list = new ArrayList<Client>();
        for (int i = 0 ; i< count ; i++) {
            Client client = new Client();
            client.setcId("cId"+i);
            client.setcName("客户："+client.getcId());
            list.add(client);
        }

        String excelFileName = null;

        try {

            Thread.sleep(75000);

            excelFileName = "record-" + System.currentTimeMillis() + "-" + (new Date().getTime()) + ".xlsx";
            FileOutputStream fo = new FileOutputStream("D:\\file\\" + excelFileName);
            new ExportExcelTools(fo).writeHead(Client.class).writeList(list).exportData();
            fo.flush();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出client文件失败："+ e.getMessage());
        }

        log.info("导出client文件成功，文件路径：" + excelFileName);

    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
