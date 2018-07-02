package com.cars.core.log;

import com.cars.model.sys.SysLog;
import com.cars.service.sys.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyupeng on 2018/6/26 15:37
 * 队列消费
 */
@Component
public class LogConsumer implements Runnable {
    @Autowired
    private SysLogService logService;
    private static Logger logger = LoggerFactory.getLogger(LogConsumer.class);
    public static final int DEFAULT_BATCH_SIZE = 64;
    private int batchSize = DEFAULT_BATCH_SIZE;

    @Autowired
    private LogQueue auditLogQueue;

    private boolean active = true;
    private Thread thread;

    @PostConstruct
    public void init() {
        thread = new Thread(this);
        thread.start();
    }

    @PreDestroy
    public void close() {
        active = false;
    }

    public void run() {
        while (active) {
            execute();
        }
    }

    public void execute() {
        List<SysLog> sysLogs = new ArrayList<SysLog>();

        try {
            int size = 0;

            while (size < batchSize) {
                SysLog sysLog = auditLogQueue.poll();

                if (sysLog == null) {
                    break;
                }
                sysLogs.add(sysLog);
                size++;
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }

        if (!sysLogs.isEmpty()) {
            logService.insertBatch(sysLogs);
        }
    }
}
