package overun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import overun.dto.PersonDto;
import overun.mapper.ExportRecordMapper;
import overun.mapper.PersonMapper;
import overun.po.ExportRecord;
import overun.po.Person;
import overun.po.PersonExample;
import overun.queue.bo.PersonBo;
import overun.queue.threadpool.InitThreadPool;
import overun.service.QueueExportService;
import overun.utils.TextUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/9
 */
@Service
public class QueueExportServiceImpl implements QueueExportService {

    @Autowired
    private ExportRecordMapper exportRecordMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private InitThreadPool initThreadPool;

    @Override
    public String exportPersonData(PersonDto personDto) {
        /** 给记录表插入一条记录 **/
        ExportRecord exportRecord = new ExportRecord();
        exportRecord.setId(TextUtils.getUUID());
        exportRecord.setState((byte) 0);
        exportRecord.setRequestDate(new Date());
        exportRecord.setClassName("Person");
        exportRecord.setRequestorId("1");
        exportRecordMapper.insert(exportRecord);



        PersonBo personBo = new PersonBo(personDto , exportRecord.getId() , personMapper , exportRecordMapper);

        ThreadPoolExecutor executor = initThreadPool.getExecutor();

        executor.execute(personBo);
        return "请求已经成功提交 记录id:" + exportRecord.getId();
    }


}
