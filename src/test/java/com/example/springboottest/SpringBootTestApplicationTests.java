package com.example.springboottest;

import com.example.springboottest.controller.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@SpringBootTest
class SpringBootTestApplicationTests {

    @Autowired
    MainController mainController;

    @Autowired
    ResourceLoader resourceLoader;
    @Test
    void testValueAnnotation() {
        String value = mainController.getValue();
        String ret = null;
        Resource resource = resourceLoader.getResource("classpath:application.yml");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                ret = reader.readLine();
                if (ret.contains("name")) {
                    ret = ret.split(":")[1].trim();
                    System.out.println("ret:" + ret);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("error");
        }
        Assert.isTrue(value.equals(ret), "test failed");
    }

}
