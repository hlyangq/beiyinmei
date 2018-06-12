package com.ningpai.scheduling;

import com.ningpai.order.service.OrderService;
import com.ningpai.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by youzipi on 16/10/14.
 */
@Component
public class OrderTask {

    @Autowired
    private ReportService reportService;

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 20 14 ? * *")
    public void generateTodayReport() {
        reportService.generateTodayReport();
    }

    @Scheduled(cron = "59 59 23 ? * *")
    public void cancelOrderByTime() {
        orderService.cancelOrderByTime();
    }
}
