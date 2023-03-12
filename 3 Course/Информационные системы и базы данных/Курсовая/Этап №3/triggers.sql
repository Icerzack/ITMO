DROP FUNCTION IF EXISTS change_rating_after_competition() CASCADE;
CREATE OR REPLACE FUNCTION change_rating_after_competition() RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'INSERT') THEN
--         update sportsman: rate
        UPDATE sportsman_sport SET rate = rate + NEW.rating_difference WHERE fk_sportsman_id = NEW.fk_sportsman_id and
                                                                             fk_sport_id = (SELECT fk_sport_id from competition
                                                                                                               WHERE competition_id = NEW.fk_competition_id);
--         update doctor: rate
        UPDATE doctor SET rate = rate + NEW.rating_difference
                      WHERE doctor_id in (SELECT fk_doctor_id FROM personnel WHERE fk_sportsman_id = NEW.fk_sportsman_id);
--         update coach: rate
        UPDATE coach SET rate = rate + NEW.rating_difference
                      WHERE coach_id in (SELECT fk_coach_id FROM personnel WHERE fk_sportsman_id = NEW.fk_sportsman_id);
--         update baa: all_time_rating_difference
        UPDATE baa_rate SET all_time_rate_difference = all_time_rate_difference + NEW.rating_difference
                        WHERE fk_baa_id in (SELECT fk_baa_id from preparation_baa
                                                             WHERE fk_preparation_id = NEW.fk_preparation_id);
--         update training: all_time_rating_difference
        UPDATE training_rate SET all_time_rate_difference = all_time_rate_difference + NEW.rating_difference
                        WHERE fk_training_id in (SELECT fk_training_id from preparation_training
                                                             WHERE fk_preparation_id = NEW.fk_preparation_id);
    ELSIF (TG_OP = 'UPDATE') THEN
--         update sportsman: rate
        UPDATE sportsman_sport SET rate = rate - OLD.rating_difference WHERE fk_sportsman_id = NEW.fk_sportsman_id;
        UPDATE sportsman_sport SET rate = rate + NEW.rating_difference WHERE fk_sportsman_id = NEW.fk_sportsman_id;
--         update doctor: rate
        UPDATE doctor SET rate = rate - OLD.rating_difference
                      WHERE doctor_id in (SELECT fk_doctor_id FROM personnel WHERE fk_sportsman_id = NEW.fk_sportsman_id);
        UPDATE doctor SET rate = rate + NEW.rating_difference
                      WHERE doctor_id in (SELECT fk_doctor_id FROM personnel WHERE fk_sportsman_id = NEW.fk_sportsman_id);
--         update coach: rate
        UPDATE coach SET rate = rate - OLD.rating_difference
                      WHERE coach_id in (SELECT fk_coach_id FROM personnel WHERE fk_sportsman_id = NEW.fk_sportsman_id);
        UPDATE coach SET rate = rate + NEW.rating_difference
                      WHERE coach_id in (SELECT fk_coach_id FROM personnel WHERE fk_sportsman_id = NEW.fk_sportsman_id);
--         update baa: all_time_rating_difference
        UPDATE baa_rate SET all_time_rate_difference = all_time_rate_difference - OLD.rating_difference
                        WHERE fk_baa_id in (SELECT fk_baa_id from preparation_baa
                                                             WHERE fk_preparation_id = NEW.fk_preparation_id);
        UPDATE baa_rate SET all_time_rate_difference = all_time_rate_difference + NEW.rating_difference
                        WHERE fk_baa_id in (SELECT fk_baa_id from preparation_baa
                                                             WHERE fk_preparation_id = NEW.fk_preparation_id);
--         update training: all_time_rating_difference
        UPDATE training_rate SET all_time_rate_difference = all_time_rate_difference - OLD.rating_difference
                        WHERE fk_training_id in (SELECT fk_training_id from preparation_training
                                                             WHERE fk_preparation_id = NEW.fk_preparation_id);
        UPDATE training_rate SET all_time_rate_difference = all_time_rate_difference + NEW.rating_difference
                        WHERE fk_training_id in (SELECT fk_training_id from preparation_training
                                                             WHERE fk_preparation_id = NEW.fk_preparation_id);
    END IF;
    RETURN NULL; -- возвращаемое значение для триггера AFTER игнорируется
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tg_change_rating_after_competition on sportsman_competition CASCADE;
CREATE TRIGGER tg_change_rating_after_competition
AFTER INSERT OR UPDATE ON sportsman_competition
FOR EACH ROW EXECUTE PROCEDURE change_rating_after_competition();
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
DROP FUNCTION IF EXISTS add_preparation() CASCADE;
CREATE OR REPLACE FUNCTION add_preparation() RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'INSERT') THEN
--         update baa_rate: count_uses
        UPDATE baa_rate SET number_uses = (number_uses + 1)
                      WHERE fk_baa_id in (SELECT fk_baa_id FROM preparation_baa
                                                            WHERE fk_preparation_id = NEW.fk_preparation_id);
--         update training_rate: count_uses
        UPDATE training_rate SET number_uses = (number_uses + 1)
                      WHERE fk_training_id in (SELECT fk_training_id FROM preparation_training
                                                            WHERE fk_preparation_id = NEW.fk_preparation_id);
    END IF;
    RETURN NULL; -- возвращаемое значение для триггера AFTER игнорируется
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tg_add_preparation on sportsman_sport CASCADE;
CREATE TRIGGER tg_add_preparation
AFTER INSERT OR UPDATE ON sportsman_sport
FOR EACH ROW EXECUTE PROCEDURE add_preparation();
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
DROP FUNCTION IF EXISTS change_preparation_effectiveness() CASCADE;
CREATE OR REPLACE FUNCTION change_preparation_effectiveness() RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        IF (TG_NAME = 'tg_change_preparation_effectiveness_by_baa') THEN
--              updated baa: rate
            UPDATE preparation_rate SET effectiveness = effectiveness - OLD.all_time_rate_difference
                                    WHERE fk_preparation_id in (SELECT fk_preparation_id FROM preparation_baa WHERE fk_baa_id = NEW.fk_baa_id);
            UPDATE preparation_rate SET effectiveness = effectiveness + NEW.all_time_rate_difference
                                    WHERE fk_preparation_id in (SELECT fk_preparation_id FROM preparation_baa WHERE fk_baa_id = NEW.fk_baa_id);
--              updated training: rate
        ELSIF (TG_NAME = 'tg_change_preparation_effectiveness_by_training') THEN
            UPDATE preparation_rate SET effectiveness = effectiveness - OLD.all_time_rate_difference
                                    WHERE fk_preparation_id in (SELECT fk_preparation_id FROM preparation_training WHERE fk_training_id = NEW.fk_training_id);
            UPDATE preparation_rate SET effectiveness = effectiveness + NEW.all_time_rate_difference
                                    WHERE fk_preparation_id in (SELECT fk_preparation_id FROM preparation_training WHERE fk_training_id = NEW.fk_training_id);
        END IF;
        RETURN NULL;
    END IF;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tg_change_preparation_effectiveness_by_baa on baa_rate CASCADE;
CREATE TRIGGER tg_change_preparation_effectiveness_by_baa
AFTER UPDATE OF all_time_rate_difference ON baa_rate
FOR EACH ROW EXECUTE PROCEDURE change_preparation_effectiveness();

DROP TRIGGER IF EXISTS tg_change_preparation_effectiveness_by_training on training_rate CASCADE;
CREATE TRIGGER tg_change_preparation_effectiveness_by_training
AFTER UPDATE OF all_time_rate_difference ON training_rate
FOR EACH ROW EXECUTE PROCEDURE change_preparation_effectiveness();