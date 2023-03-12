create index if not exists baa_name_index
    on baa (name);

create index if not exists training_name_index
    on training (name);

create index if not exists sportsman_name_index
    on sportsman (full_name);

create index if not exists sport_team_name_index
    on sport_team (name);
