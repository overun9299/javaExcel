package overun.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import overun.dto.PersonDto;
import overun.queue.bo.AddressBo;
import overun.queue.bo.PersonBo;
import overun.service.QueueExportService;


/**
 * 队列导出
 */
@RestController
@Api(value = "队列导出中心" , tags = "队列导出中心")
public class QueueExportController {

    @Autowired
    private QueueExportService queueExportService;


    @ResponseBody
    @RequestMapping(value = "/exportAddress")
    @ApiOperation(value = "地址条件导出", notes = "根据条件导出地址" , httpMethod = "POST")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String")
    )
    public String exportAddressData(AddressBo addressBo) {

        return null;

    }


    @ResponseBody
    @RequestMapping(value = "/exportPerson")
    @ApiOperation(value = "人员条件导出", notes = "根据条件导出人员" , httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sex", value = "性别", required = false, dataType = "byte"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "dId", value = "部门id", required = false, dataType = "int")
    })
    public String exportPersonData(PersonDto personDto) {

        return queueExportService.exportPersonData(personDto);

    }

}
