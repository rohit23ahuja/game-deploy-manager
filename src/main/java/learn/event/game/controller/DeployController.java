package learn.event.game.controller;

import learn.event.game.api.DeployRequest;
import learn.event.game.api.DeployResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

    @PostMapping("/game/deploy")
    public DeployResponse deploy(DeployRequest deployRequest) {
        return null;
    }
}

