package overun.service;

import overun.dto.PersonDto;
import overun.queue.bo.PersonBo;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/9
 */
public interface QueueExportService {

    /**
     * 导出人员
     * @param personDto
     * @return
     */
    String exportPersonData(PersonDto personDto);
}
