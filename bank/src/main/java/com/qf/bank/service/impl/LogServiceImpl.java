package com.qf.bank.service.impl;

import com.qf.bank.mapper.LogMapper;
import com.qf.bank.pojo.Log;
import com.qf.bank.service.LogService;
import com.qf.bank.util.Page;
import com.qf.bank.util.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Page<Log> selectLogPage(Integer userId, String dateFrom, String dateTo, Integer page) {

        QueryVo queryVo = new QueryVo();
        queryVo.setUserId(userId);
        queryVo.setDateFrom(dateFrom);
        queryVo.setDateTo(dateTo);
        if (null != page) {
            queryVo.setPage(page);
        }
        queryVo.setStart((queryVo.getPage() - 1) * queryVo.getSize());

        List<Log> logList = logMapper.selectLog(queryVo);
        Integer totalCount = logMapper.selectTotalCount(queryVo);

        Page<Log> logPage = new Page<>();
        logPage.setPage(queryVo.getPage());
        logPage.setRows(logList);
        logPage.setSize(queryVo.getSize());
        logPage.setTotalCount(totalCount);
        if (logPage.getTotalCount() % logPage.getSize() == 0) {
            logPage.setTotalPage(logPage.getTotalCount() / logPage.getSize());
        } else {
            logPage.setTotalPage(logPage.getTotalCount() / logPage.getSize() + 1);
        }
        if (logPage.getTotalPage() == 0) {
            logPage.setTotalPage(1);
        }
        return logPage;
    }
}
