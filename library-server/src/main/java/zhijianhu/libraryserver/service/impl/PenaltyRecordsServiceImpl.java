package zhijianhu.libraryserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zhijianhu.constant.StatusConstant;
import zhijianhu.dto.PenaltyDTO;
import zhijianhu.dto.PenaltyRecordPageDTO;
import zhijianhu.entity.PenaltyRecords;
import zhijianhu.libraryserver.mapper.BooksMapper;
import zhijianhu.libraryserver.mapper.PenaltyRecordsMapper;
import zhijianhu.libraryserver.service.BooksService;
import zhijianhu.libraryserver.service.PenaltyRecordsService;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.PenaltyRecordVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
* @author windows
* @description 针对表【penalty_records(用户违规缴纳罚款记录表)】的数据库操作Service实现
* @createDate 2025-03-03 13:25:42
*/
@Service
public class PenaltyRecordsServiceImpl extends ServiceImpl<PenaltyRecordsMapper, PenaltyRecords>
    implements PenaltyRecordsService {
    @Autowired
    @Lazy
    private UsersService usersService;
    @Autowired
    private BooksMapper booksService;

    @Override
    public PageVO<PenaltyRecordVO> getByPage(PenaltyRecordPageDTO pageDTO) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPage(pageDTO.getPageNum());
        pageQuery.setPageSize(pageDTO.getPageSize());
        Page<PenaltyRecords> mpPage = pageQuery.toMpPage();
        Integer status = pageDTO.getStatus();
        Page<PenaltyRecords> page = lambdaQuery()
                .eq(status != null, PenaltyRecords::getStatus, status)
                .page(mpPage);
        return PageVO.of(page, this::turnToPenaltyRecordVO);
    }

    @Override
    public PenaltyRecordVO getRecordById(Integer id) {
        PenaltyRecords record = getById(id);
        return turnToPenaltyRecordVO(record);
    }

    @Override
    public boolean updateRecordById(PenaltyDTO dto) {
        Integer id = dto.getId();
        PenaltyRecords record = getById(id);
        String note = dto.getNote();
        BigDecimal penaltyAmount = dto.getPenaltyAmount();
        Integer status = dto.getStatus();
        record.setStatus(status);
        record.setPenaltyAmount(penaltyAmount);
        record.setNote(note);
        return updateById(record);
    }

    @Override
    public boolean payPenalty(Integer id) {
        PenaltyRecords penaltyRecord = getById(id);
        penaltyRecord.setStatus(StatusConstant.ENABLE);
        return updateById(penaltyRecord);
    }

    @Override
    public List<PenaltyRecordVO> getRecordByUserId(Integer userId) {
        List<PenaltyRecords> list = lambdaQuery().
                eq(PenaltyRecords::getUserId, userId)
                .list();
        if(list==null||list.isEmpty()){
            return List.of();
        }
        List<PenaltyRecordVO> penaltyRecordVOS=new ArrayList<>();
        list.forEach(p->{
            PenaltyRecordVO penaltyRecordVO = turnToPenaltyRecordVO(p);
            penaltyRecordVOS.add(penaltyRecordVO);
        });

        return penaltyRecordVOS;
    }

    private PenaltyRecordVO turnToPenaltyRecordVO(PenaltyRecords record){
        Integer bookId = record.getBookId();
        Integer userId = record.getUserId();
        Integer status1 = record.getStatus();
        PenaltyRecordVO penaltyRecordPageVO = BeanUtil.copyProperties(record, PenaltyRecordVO.class);
        String name = booksService.selectById(bookId).getName();
        String userName = usersService.getById(userId).getName();
        if(status1 == 0){
            penaltyRecordPageVO.setStatusName("未缴纳");
        }else{
            penaltyRecordPageVO.setStatusName("已缴纳");
        }
        penaltyRecordPageVO.setBookName(name);
        penaltyRecordPageVO.setUserName(userName);
        return penaltyRecordPageVO;
    }
}




