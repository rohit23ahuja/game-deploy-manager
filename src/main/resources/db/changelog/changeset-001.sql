CREATE TABLE IF NOT EXISTS deploy_request (
id BIGSERIAL PRIMARY KEY,
casino_id varchar(255) not null,
game_id varchar(255) not null,
game_version varchar(255) not null,
created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);