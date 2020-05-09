package overun.queue.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import overun.dto.PersonDto;
import overun.mapper.ExportRecordMapper;
import overun.mapper.PersonMapper;
import overun.po.ExportRecord;
import overun.po.Person;
import overun.po.PersonExample;
import overun.queue.entity.BasicTaskPlus;
import overun.utils.ExportExcelTools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/6
 */
public class PersonBo extends BasicTaskPlus {


    private PersonDto personDto;
    /** 记录主键 **/
    private String exportRecordId;

    private PersonMapper personMapper;

    private ExportRecordMapper exportRecordMapper;


    private static Logger log = LoggerFactory.getLogger(PersonBo.class);

    public PersonBo(PersonDto personDto, String exportRecordId, PersonMapper personMapper, ExportRecordMapper exportRecordMapper) {
        this.personDto = personDto;
        this.exportRecordId = exportRecordId;
        this.personMapper = personMapper;
        this.exportRecordMapper = exportRecordMapper;
    }

    @Override
    public void run() {

        /** 根据条件查询记录 **/
        PersonExample example = new PersonExample();
        PersonExample.Criteria or = example.or();
        if (personDto.getId() != null) {
            or.andIdEqualTo(personDto.getId());
        }
        if (personDto.getdId() != null) {
            or.andDIdEqualTo(personDto.getdId());
        }
        if (personDto.getSex() != null) {
            or.andSexEqualTo(personDto.getSex());
        }
        example.setOrderByClause("id limit 60000");
        List<Person> people = personMapper.selectByExample(example);

        try {
            /** 生成文件 **/
            String excelFileName = "Person-record-" +  System.currentTimeMillis() + "-" + ".xlsx";
            FileOutputStream fo = new FileOutputStream("D:\\file\\" + excelFileName);
            new ExportExcelTools(fo).writeHead(Person.class).writeList(people).exportData();
            fo.flush();
            fo.close();
            /** 更新记录 **/
            ExportRecord exportRecord = new ExportRecord();
            exportRecord.setId(exportRecordId);
            exportRecord.setFilePath("D:\\file\\" + excelFileName);
            exportRecord.setState((byte) 1);
            exportRecord.setCompletionDate(new Date());
            exportRecordMapper.updateByPrimaryKeySelective(exportRecord);
            log.info("D:\\file\\" + excelFileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }


}
