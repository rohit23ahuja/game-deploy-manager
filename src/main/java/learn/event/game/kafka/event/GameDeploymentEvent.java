package learn.event.game.kafka.event;

public record GameDeploymentEvent(Long deploymentId, String casinoId, String gameId, String version, String status) {
}
