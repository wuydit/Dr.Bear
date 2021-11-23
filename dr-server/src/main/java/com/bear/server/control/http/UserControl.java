package com.bear.server.control.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyd
 * 2021/3/9 14:50
 */
@Slf4j
@RestController
@RequestMapping
public class UserControl {

    @GetMapping("test")
    public ResponseEntity<String> test(){
        log.info("test");
        return ResponseEntity.ok("test");
    }

}
