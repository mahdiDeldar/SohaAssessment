package com.soha.ServiceImpl;

import com.soha.Configuration.MessageConfiguration;
import com.soha.Controller.requestController;
import com.soha.Model.SohaDeveloper;
import com.soha.Repository.SohaDeveloperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SohaDeveloperServiceImpl {
    @Autowired
    private SohaDeveloperRepository sohaDeveloperRepository;
    private static final Logger logger = LoggerFactory.getLogger(SohaDeveloperServiceImpl.class);

    @RabbitListener(queues = MessageConfiguration.QUEU)
    public void Save(SohaDeveloper sohaDeveloper) {
        logger.debug("Read Object from Queue {}", sohaDeveloper);
        try {
            logger.debug("Save Entity to Database {}", sohaDeveloper);
            sohaDeveloperRepository.save(sohaDeveloper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
