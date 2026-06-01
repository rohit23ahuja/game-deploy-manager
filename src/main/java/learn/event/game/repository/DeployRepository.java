package learn.event.game.repository;

import learn.event.game.api.DeployRequest;
import learn.event.game.util.CommonUtil;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class DeployRepository {
    private static final String DEPLOY_REQUEST_INSERT = """
            insert into deploy_request (casino_id, game_id, game_version, payload) 
            values (:casinoId, :gameId, :gameVersion, cast(:payload AS JSON)) 
            returning id
            """;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DeployRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long save(DeployRequest deployRequest) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("casinoId", deployRequest.casinoId());
        parameters.put("gameId", deployRequest.gameId());
        parameters.put("gameVersion", deployRequest.version());
        parameters.put("payload", CommonUtil.convertObjectToJsonString(deployRequest));
        return namedParameterJdbcTemplate.queryForObject(DEPLOY_REQUEST_INSERT, parameters, Long.class);
    }
}
