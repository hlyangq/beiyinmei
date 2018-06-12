package com.ningpai.scheduling;

import com.ningpai.manager.service.impl.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by youzipi on 16/10/14.
 */
@Component
public class ManagerTask {

    @Autowired
    private ManagerService managerService;

    @Scheduled(cron = "59 59 23 ? * *")
    public void stopCloudAccountByTime() {
        managerService.stopCloudAccountByTime();
    }


}
