package com.sqlinjectiondemo.rabbitMq.consumer;

import com.sqlinjectiondemo.entity.Log;
import com.sqlinjectiondemo.rabbitMq.model.LogModel;
import com.sqlinjectiondemo.repository.LogRepository;
import com.sqlinjectiondemo.utils.constant.QueueConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {

    private final LogRepository logRepository;

    public LogConsumer(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @RabbitListener(queues = {QueueConstants.LOG_QUEUE_NAME}, containerFactory = "rabbitListenerContainerFactory")
    public void receive(@Payload LogModel logModel) {
        Log log = Log.builder()
                .message(logModel.getQuery())
                .createdDate(logModel.getCreatedDate())
                .build();

        this.logRepository.save(log);
    }

}
