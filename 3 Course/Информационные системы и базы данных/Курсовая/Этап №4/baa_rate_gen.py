from random import randint

i = 1
while(i < 3010):
    with open(f'baa_rate.sql', 'a') as f:
        f.write(f'insert into baa_rate (fk_baa_id, number_uses, all_time_rate_difference) values ({i}, {randint(1, 10000)},  {randint(1, 1000)});\n')
        i+= 1