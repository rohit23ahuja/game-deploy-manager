package learn.event.game.kafka;

import learn.event.game.kafka.event.GameDeploymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameDeploymentProducer {
    private static final String TOPIC = "game-deployment-requested";
    private final KafkaTemplate<String, GameDeploymentEvent> kafkaTemplate;

    public GameDeploymentProducer(KafkaTemplate<String, GameDeploymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishDeploymentRequest(String gameId, GameDeploymentEvent gameDeploymentEvent) {
        kafkaTemplate.send(TOPIC, gameId, gameDeploymentEvent)
                .whenComplete((result, ex) -> {
                    if(ex!=null) {
                        System.out.println("Failed to publish to "+TOPIC+": "+ex.getMessage());
                    } else {
                        System.out.println("Published to "+TOPIC+" [partition="+result.getRecordMetadata().partition()+", " +
                                "offset="+result.getRecordMetadata().offset());
                    }
                });
    }
}
