package overun.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import overun.instrument.ClientAssembly;
import overun.instrument.QueueExportRunner;
import overun.pojo.Client;
import overun.utils.QueueExportUtils;


/**
 * 队列导出
 */
@RestController
public class QueueExportController {

    @Autowired
    private QueueExportUtils queueExportUtils;



    @RequestMapping(value = "/queueExport")
    public String queueExport() {
        ClientAssembly clientAssembly = new ClientAssembly();
        clientAssembly.setCount(12);
        queueExportUtils.QueueExport(clientAssembly);
        return "请求提交成功";
    }

}
