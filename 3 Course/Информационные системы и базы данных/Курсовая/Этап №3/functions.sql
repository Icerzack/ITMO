CREATE OR REPLACE FUNCTION GetTopBAAsByRating(number integer)
RETURNS TABLE(baa_id integer, baa_name varchar(100), baa_manufacturer varchar(50), baa_description text, baa_uses int, baa_rate int)
AS $$
BEGIN
    IF number = 0 then
    return query (select baa.baa_id, name, manufacturer, description, baa_rate.number_uses, baa_rate.all_time_rate_difference from baa
        join baa_rate on baa.baa_id = baa_rate.fk_baa_id order by baa_rate.all_time_rate_difference desc);
    ELSE
    return query (select baa.baa_id, name, manufacturer, description, baa_rate.number_uses, baa_rate.all_time_rate_difference from baa
        join baa_rate on baa.baa_id = baa_rate.fk_baa_id order by baa_rate.all_time_rate_difference desc limit number);
    END IF;
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetTopBAAsByUses(number integer)
RETURNS TABLE(baa_id integer, baa_name varchar(100), baa_manufacturer varchar(50), baa_description text, baa_uses int, baa_all_time_rate_difference int)
AS $$
BEGIN
    IF number = 0 then
    return query (select baa.baa_id, name, manufacturer, description, baa_rate.number_uses, baa_rate.all_time_rate_difference from baa
        join baa_rate on baa.baa_id = baa_rate.fk_baa_id order by baa_rate.number_uses desc);
    ELSE
    return query (select baa.baa_id, name, manufacturer, description, baa_rate.number_uses, baa_rate.all_time_rate_difference from baa
        join baa_rate on baa.baa_id = baa_rate.fk_baa_id order by baa_rate.number_uses desc limit number);
    END IF;
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetTopTrainingsByRating(number integer)
RETURNS TABLE(training_id integer, training_name varchar(100), training_description text, training_rate int)
AS $$
BEGIN
    IF number = 0 then
    return query (select training.training_id, training.name, training.description, training_rate.all_time_rate_difference from training
        join training_rate on training.training_id = training_rate.fk_training_id order by training_rate.all_time_rate_difference desc);
    ELSE
    return query (select training.training_id, training.name, training.description, training_rate.all_time_rate_difference from training
        join training_rate on training.training_id = training_rate.fk_training_id order by training_rate.all_time_rate_difference desc limit number);
    END IF;
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetTopTrainingsByUses(number integer)
RETURNS TABLE(training_id integer, training_name varchar(100), training_description text, training_rate int)
AS $$
BEGIN
    IF number = 0 then
    return query (select training.training_id, training.name, training.description, training_rate.number_uses from training
        join training_rate on training.training_id = training_rate.fk_training_id order by training_rate.number_uses desc);
    ELSE
    return query (select training.training_id, training.name, training.description, training_rate.number_uses from training
        join training_rate on training.training_id = training_rate.fk_training_id order by training_rate.number_uses desc limit number);
    END IF;
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetExercisesForTraining(train_id integer)
RETURNS TABLE(training_id integer, training_name varchar(100), training_description text, exercise_id integer, exercise_name varchar(100), exercise_description text)
AS $$
BEGIN
    return query (select training.training_id, training.name, training.description, exercise.exercise_id, exercise.name, exercise.description from training
        join training_exercise on training.training_id = training_exercise.fk_training_id
        join exercise on training_exercise.fk_exercise_id = exercise.exercise_id
        WHERE training.training_id = train_id);
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetTopPreparations(number integer)
RETURNS TABLE(preparation_id integer, training_name varchar(100), baa_name varchar(100), preparation_rate double precision)
AS $$
BEGIN
    IF number = 0 then
    return query (select preparation.preparation_id, training.name, preparation_baa.complex_name, preparation_rate.effectiveness from preparation
        join training on preparation.fk_training_id = training.training_id
        join preparation_baa on preparation.preparation_id = preparation_baa.fk_preparation_id
        join baa on preparation_baa.fk_baa_id = baa.baa_id
        join preparation_rate on preparation.preparation_id = preparation_rate.fk_preparation_id
        order by preparation_rate.effectiveness desc);
    ELSE
    return query (select preparation.preparation_id, training.name, preparation_baa.complex_name, preparation_rate.effectiveness from preparation
        join training on preparation.fk_training_id = training.training_id
        join preparation_baa on preparation.preparation_id = preparation_baa.fk_preparation_id
        join baa on preparation_baa.fk_baa_id = baa.baa_id
        join preparation_rate on preparation.preparation_id = preparation_rate.fk_preparation_id
        order by preparation_rate.effectiveness desc limit number);
    END IF;
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetInfoAboutPreparation(prep_id integer)
RETURNS TABLE(preparation_id integer, training_id integer, training_name varchar(100), training_description text, baa_id integer, baa_name varchar(100), baa_desc text, preparation_rating double precision)
AS $$
BEGIN
    return query (select preparation.preparation_id, training.training_id, training.name, training.description, baa.baa_id, baa.name, baa.description, preparation_rate.effectiveness from preparation
        join training on preparation.fk_training_id = training.training_id
        join baa on preparation.fk_baa_id = baa.baa_id
        join preparation_rate on preparation.preparation_id = preparation_rate.fk_preparation_id
        where preparation.preparation_id = prep_id);
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetCompetitionsForSportsman(person_id integer)
RETURNS TABLE(sportsman_id integer, competition_id integer, competition_name varchar(100), competition_date date, competition_place varchar(100), competition_prestige smallint, sport_id integer, sport_name varchar(50), preparation_id int, rating_diff smallint)
AS $$
BEGIN
    return query (select sportsman.sportsman_id, competition.competition_id, competition.name, competition.date_of_event, competition.place, competition.prestige, sport.sport_id, sport.name, sportsman_competition.fk_preparation_id, sportsman_competition.rating_difference from competition
        join sportsman_competition on sportsman_competition.fk_competition_id = competition.competition_id
        join sportsman on sportsman.sportsman_id = sportsman_competition.fk_sportsman_id and sportsman.sportsman_id = person_id
        join sport on competition.fk_sport_id = sport.sport_id );
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;

-- CREATE OR REPLACE FUNCTION GetTopSportsmenFromSportTeam(sportTeam_id integer, number integer)
-- RETURNS TABLE(sport_team_id int, sport_team_name varchar(50), sportsman_id int, sportsman_full_name varchar(100), sportsman_sex sex, sportsman_date_of_birth date, sportsman_rate int)
-- AS $$
-- BEGIN
--     IF number = 0 THEN
--     return query (select sport_team.sport_team_id, sport_team.name, sportsman.sportsman_id, sportsman.full_name, sportsman.sex, sportsman.date_of_birth, sportsman_sport.rate from sportsman
--         join sport_team on sportsman.fk_sport_team_id = sport_team.sport_team_id and sport_team.sport_team_id = sportTeam_id
--         join sportsman_sport on sportsman.sportsman_id = sportsman_sport.fk_sportsman_id and sportsman_sport.fk_sport_id = sport_team.sport_team_id
--         order by sportsman_sport.rate desc);
--     ELSE
--     return query (select sport_team.sport_team_id, sport_team.name, sportsman.sportsman_id, sportsman.full_name, sportsman.sex, sportsman.date_of_birth, sportsman_sport.rate from sportsman
--         join sport_team on sportsman.fk_sport_team_id = sport_team.sport_team_id and sport_team.sport_team_id = sportTeam_id
--         join sportsman_sport on sportsman.sportsman_id = sportsman_sport.fk_sportsman_id and sportsman_sport.fk_sport_id = sport_team.sport_team_id
--         order by sportsman_sport.rate desc limit number);
--     END IF;
--     EXCEPTION
--         WHEN duplicate_function THEN
--         NULL;
-- END;
-- $$ LANGUAGE plpgsql;
-- SELECT * from GetTopSportsmenFromSportTeam(1, 3);

CREATE OR REPLACE FUNCTION GetTopSportTeams(sportId integer, number integer)
RETURNS TABLE(sport_id int, sport_team_id int, sport_team_name varchar(50), sport_team_rate double precision)
AS $$
BEGIN
    IF number = 0 THEN
    return query (select sport_team_sport.fk_sport_id, sport_team.sport_team_id, sport_team.name, sport_team.average_rate from sport_team
        join sport_team_sport on sport_team.sport_team_id = sport_team_sport.fk_sport_team_id and sport_team_sport.fk_sport_id = sportId
        order by sport_team.average_rate desc);
    ELSE
    return query (select sport_team_sport.fk_sport_id, sport_team.sport_team_id, sport_team.name, sport_team.average_rate from sport_team
        join sport_team_sport on sport_team.sport_team_id = sport_team_sport.fk_sport_team_id and sport_team_sport.fk_sport_id = sportId
        order by sport_team.average_rate desc limit number);
    END IF;
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetCurrentPreparationsForSportsman(sportsmanId integer)
RETURNS TABLE(sportsman_id int, sportsman_full_name int, sportsman_sex varchar(50), sportsman_date_of_birth date, sportsman_rate double precision, preparation_id int)
AS $$
BEGIN
    return query (select sportsman.sportsman_id, sportsman.full_name, sportsman.sex, sportsman.date_of_birth, sportsman_sport.rate, preparation.preparation_id from sportsman
        join sportsman_sport on sportsman.sportsman_id = sportsman_sport.fk_sportsman_id and sportsman.sportsman_id = sportsmanId
        join preparation on sportsman_sport.fk_preparation_id = preparation.preparation_id
        order by sportsman_sport.rate desc);
    EXCEPTION
        WHEN duplicate_function THEN
        NULL;
END;
$$ LANGUAGE plpgsql;