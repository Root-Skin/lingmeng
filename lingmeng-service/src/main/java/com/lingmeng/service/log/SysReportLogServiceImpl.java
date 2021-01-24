package com.lingmeng.service.log;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingmeng.api.log.ISysReportLogService;
import com.lingmeng.dao.log.SysReportLogMapper;
import com.lingmeng.log.model.SysReportLog;
import org.springframework.stereotype.Service;

@Service
public class SysReportLogServiceImpl extends ServiceImpl<SysReportLogMapper, SysReportLog> implements ISysReportLogService {
}
