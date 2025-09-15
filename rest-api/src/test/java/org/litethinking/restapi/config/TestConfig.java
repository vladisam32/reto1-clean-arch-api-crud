package org.litethinking.restapi.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {
    "org.litethinking.restapi.controller.supermercado"
})
public class TestConfig {
}