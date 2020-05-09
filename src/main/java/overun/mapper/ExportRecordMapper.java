package overun.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import overun.po.ExportRecord;
import overun.po.ExportRecordExample;

import java.util.List;

@Mapper
public interface ExportRecordMapper {
    long countByExample(ExportRecordExample example);

    int deleteByExample(ExportRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(ExportRecord record);

    int insertSelective(ExportRecord record);

    List<ExportRecord> selectByExample(ExportRecordExample example);

    ExportRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ExportRecord record, @Param("example") ExportRecordExample example);

    int updateByExample(@Param("record") ExportRecord record, @Param("example") ExportRecordExample example);

    int updateByPrimaryKeySelective(ExportRecord record);

    int updateByPrimaryKey(ExportRecord record);
}