package com.sqlinjectiondemo.rabbitMq.producer;

import com.sqlinjectiondemo.rabbitMq.model.LogModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogProducer {

    private String directExchange = "exchange";
    private String logBindingKey = "bindingKey";
    private final RabbitTemplate rabbitTemplate;

    public void sendLog(LogModel model){
        rabbitTemplate.convertAndSend(directExchange,logBindingKey,model);
    }
}
