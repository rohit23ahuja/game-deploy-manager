package learn.event.game.controller;

import learn.event.game.api.DeployRequest;
import learn.event.game.api.DeployResponse;
import learn.event.game.repository.DeployRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

    private final DeployRepository deployRepository;

    public DeployController(DeployRepository deployRepository) {
        this.deployRepository = deployRepository;
    }


    @PostMapping("/game/deploy")
    public ResponseEntity<DeployResponse> deploy(@RequestBody DeployRequest deployRequest) {
        Long deploymentId = deployRepository.save(deployRequest);
        return ResponseEntity.accepted()
                .body(new DeployResponse(deploymentId));
    }
}

