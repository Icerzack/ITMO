--create types
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'sex') THEN
        CREATE TYPE sex AS ENUM ('мужчина', 'женщина', 'другое');
    END IF;
    --more types here...
END$$;

CREATE TABLE IF NOT EXISTS sport(
    sport_id serial PRIMARY KEY,
    name varchar(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS competition(
    competition_id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    fk_sport_id int REFERENCES sport ON DELETE SET NULL,
    date_of_event date NOT NULL,
    place varchar(100) NOT NULL,
    prestige smallint CHECK (prestige >= 0 and prestige <= 1000),
    description text
);

CREATE TABLE IF NOT EXISTS doctor(
    doctor_id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    rate int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS coach(
    coach_id serial PRIMARY KEY,
    name varchar(50) NOT NULL,
    rate int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS sport_team(
    sport_team_id serial PRIMARY KEY,
    name varchar(50) NOT NULL,
    average_rate double precision
);

CREATE TABLE IF NOT EXISTS baa(
    baa_id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    manufacturer varchar(100),
    description text,
    UNIQUE(name, manufacturer, description)
);

CREATE TABLE IF NOT EXISTS exercise(
    exercise_id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    description text,
    UNIQUE(name, description)
);

CREATE TABLE IF NOT EXISTS training(
    training_id serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    description text,
    UNIQUE(name, description)
);

CREATE TABLE IF NOT EXISTS training_exercise(
    fk_training_id int REFERENCES training ON DELETE CASCADE,
    fk_exercise_id int REFERENCES exercise ON DELETE CASCADE,
    PRIMARY KEY(fk_training_id, fk_exercise_id)
);

CREATE TABLE IF NOT EXISTS preparation(
    preparation_id serial PRIMARY KEY,
    complex_name varchar(100),
    description text,
    UNIQUE(complex_name)
);

CREATE TABLE IF NOT EXISTS login_password(
    login varchar(50) PRIMARY KEY,
    password varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS sportsman_login(
    sportsman_id serial PRIMARY KEY,
    login varchar(50) NOT NULL REFERENCES login_password
);

CREATE TABLE IF NOT EXISTS sportsman(
    sportsman_id int PRIMARY KEY REFERENCES sportsman_login,
    full_name varchar(100) NOT NULL,
    sex sex NOT NULL,
    date_of_birth date NOT NULL,
    fk_sport_team_id int REFERENCES sport_team ON DELETE SET NULL
);

-- CREATE TABLE IF NOT EXISTS login_password(
--     login varchar(50) PRIMARY KEY,
--     password varchar(50) NOT NULL
-- );

-- CREATE TABLE IF NOT EXISTS sportsman_login(
--     sportsman_id int PRIMARY KEY REFERENCES sportsman,
--     login varchar(50) NOT NULL REFERENCES login_password
-- );

CREATE TABLE IF NOT EXISTS sport_team_sport(
    fk_sport_team_id int REFERENCES sport_team ON DELETE CASCADE,
    fk_sport_id int REFERENCES sport ON DELETE CASCADE,
    fk_sportsman_id int REFERENCES sportsman ON DELETE CASCADE,
    PRIMARY KEY(fk_sport_team_id, fk_sport_id, fk_sportsman_id)
);

CREATE TABLE IF NOT EXISTS personnel(
    fk_sportsman_id int REFERENCES sportsman ON DELETE CASCADE,
    fk_doctor_id int REFERENCES doctor ON DELETE CASCADE,
    fk_coach_id int REFERENCES coach ON DELETE CASCADE,
    PRIMARY KEY(fk_sportsman_id,fk_doctor_id,fk_coach_id)
);

CREATE TABLE IF NOT EXISTS sportsman_sport(
    fk_sportsman_id int REFERENCES sportsman ON DELETE CASCADE,
    fk_sport_id int REFERENCES sport ON DELETE CASCADE,
    rate int DEFAULT 0,
    fk_preparation_id int REFERENCES preparation ON DELETE SET NULL,
    PRIMARY KEY(fk_sportsman_id, fk_sport_id)
);

CREATE TABLE IF NOT EXISTS sportsman_competition(
    fk_sportsman_id int REFERENCES sportsman ON DELETE CASCADE,
    fk_competition_id int REFERENCES competition ON DELETE CASCADE,
    fk_preparation_id int REFERENCES preparation ON DELETE SET NULL,
    rating_difference smallint DEFAULT 0,
    PRIMARY KEY(fk_sportsman_id, fk_competition_id)
);

CREATE TABLE IF NOT EXISTS baa_rate(
     fk_baa_id int PRIMARY KEY REFERENCES baa ON DELETE CASCADE,
     number_uses int DEFAULT 0,
     all_time_rate_difference int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS preparation_rate(
    fk_preparation_id int PRIMARY KEY REFERENCES preparation ON DELETE CASCADE,
    effectiveness bigint DEFAULT 0
);

CREATE TABLE IF NOT EXISTS training_rate(
    fk_training_id int PRIMARY KEY REFERENCES training ON DELETE CASCADE,
    number_uses int DEFAULT 0,
    all_time_rate_difference int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS preparation_baa(
    fk_preparation_id int REFERENCES preparation ON DELETE CASCADE,
    fk_baa_id int REFERENCES baa ON DELETE CASCADE,
    PRIMARY KEY(fk_preparation_id, fk_baa_id)
);

CREATE TABLE IF NOT EXISTS preparation_training(
    fk_preparation_id int REFERENCES preparation ON DELETE CASCADE,
    fk_training_id int REFERENCES training ON DELETE CASCADE,
    PRIMARY KEY(fk_preparation_id, fk_training_id)
);