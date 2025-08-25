package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.PenaltyDTO;
import zhijianhu.dto.PenaltyRecordPageDTO;
import zhijianhu.entity.PenaltyRecords;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.PenaltyRecordVO;

import java.util.List;

/**
* @author windows
* @description 针对表【penalty_records(用户违规缴纳罚款记录表)】的数据库操作Service
* @createDate 2025-03-03 13:25:42
*/
public interface PenaltyRecordsService extends IService<PenaltyRecords> {

    PageVO<PenaltyRecordVO> getByPage(PenaltyRecordPageDTO pageDTO);

    PenaltyRecordVO getRecordById(Integer id);

    boolean updateRecordById(PenaltyDTO dto);

    boolean payPenalty(Integer id);

    List<PenaltyRecordVO> getRecordByUserId(Integer userId);
}
