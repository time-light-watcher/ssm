package com.qf.bank.mapper;

import com.qf.bank.pojo.Log;
import com.qf.bank.util.QueryVo;

import java.util.List;

public interface LogMapper {
    //Boolean insertLog(Log log);

    List<Log> selectLog(QueryVo queryVo);

    Integer selectTotalCount(QueryVo queryVo);
}
