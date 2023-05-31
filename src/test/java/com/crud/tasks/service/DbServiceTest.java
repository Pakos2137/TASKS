package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DbServiceTest {

    @Autowired
    private DbService dbService;
    @Autowired
    private AdminConfig adminConfig;

    @Test
    void testGetAllTasks() {
        adminConfig.getAdminMail();
    }
}