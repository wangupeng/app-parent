package com.cars.core.log;

import com.cars.model.sys.SysLog;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangyupeng on 2018/6/26 15:25
 */
@Component
public class LogQueue {
    private BlockingQueue<SysLog> blockingQueue = new LinkedBlockingQueue<SysLog>();

    public void add(SysLog auditLog) {
        blockingQueue.offer(auditLog);
    }

    public SysLog poll() throws InterruptedException {
//        从BlockingQueue取出一个队首的对象，如果在指定时间内，队列一旦有数据可取，则立即返回队列中的数据。否则知道时间超时还没有数据可取，返回失败
        return blockingQueue.poll(1, TimeUnit.SECONDS);
    }
}
