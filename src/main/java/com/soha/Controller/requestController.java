package com.soha.Controller;

import com.soha.Configuration.MessageConfiguration;
import com.soha.Model.DeveloperInfo;
import com.soha.Model.SohaDeveloper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/soha")
@RestController
public class requestController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(requestController.class);

    @PostMapping("/requestSave")
    public String saveDeveloper(@RequestBody DeveloperInfo developerInfo) {
        logger.debug("Get Request From User {}", developerInfo);
        SohaDeveloper sohaDeveloper = new SohaDeveloper();
        sohaDeveloper.setId(developerInfo.getId());
        sohaDeveloper.setName(developerInfo.getName());
        sohaDeveloper.setFamily(developerInfo.getFamily());
        sohaDeveloper.setExpert(developerInfo.getExpert());
        logger.debug("Put Object in Queue {}", sohaDeveloper);
        rabbitTemplate.convertAndSend(MessageConfiguration.EXCHANGE,MessageConfiguration.ROUTERKEY,sohaDeveloper);
        return String.format("name: " + sohaDeveloper.getName());
    }
}
