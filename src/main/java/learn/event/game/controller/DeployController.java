package learn.event.game.controller;

import learn.event.game.api.DeployRequest;
import learn.event.game.api.DeployResponse;
import learn.event.game.kafka.GameDeploymentProducer;
import learn.event.game.kafka.event.GameDeploymentEvent;
import learn.event.game.repository.DeployRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

    private final DeployRepository deployRepository;
    private final GameDeploymentProducer gameDeploymentProducer;

    public DeployController(DeployRepository deployRepository,
                            GameDeploymentProducer gameDeploymentProducer) {
        this.deployRepository = deployRepository;
        this.gameDeploymentProducer = gameDeploymentProducer;
    }


    @PostMapping("/game/deploy")
    public ResponseEntity<DeployResponse> deploy(@RequestBody DeployRequest deployRequest) {
        Long deploymentId = deployRepository.save(deployRequest);
        gameDeploymentProducer.publishDeploymentRequest(deployRequest.gameId(),
                new GameDeploymentEvent(deploymentId,
                        deployRequest.casinoId(),
                        deployRequest.gameId(),
                        deployRequest.version(),
                        "REQUESTED"));
        return ResponseEntity.accepted()
                .body(new DeployResponse(deploymentId));
    }
}

