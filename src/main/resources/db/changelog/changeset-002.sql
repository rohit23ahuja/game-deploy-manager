alter table deploy_request add column if not exists payload jsonb;
alter table deploy_request add column if not exists created_on timestamptz not null default now();
