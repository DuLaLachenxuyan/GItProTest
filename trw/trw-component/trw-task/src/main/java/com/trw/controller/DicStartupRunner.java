package com.trw.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.trw.factory.DictTaskFactory;
import com.trw.factory.FaciTaskFactory;
import com.trw.manage.MuiThreadManager;


@Component
@Order(value=1)
public class DicStartupRunner implements CommandLineRunner {
	
    @Override
    public void run(String... args) throws Exception {
    	MuiThreadManager.me().executeLog(DictTaskFactory.load());
    	
    	MuiThreadManager.me().executeLog(FaciTaskFactory.load());
    	
    }

}