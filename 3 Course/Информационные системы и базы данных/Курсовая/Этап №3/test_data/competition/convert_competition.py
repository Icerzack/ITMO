used = set()

with open('competition/names.txt', 'r', encoding='utf-8') as fin:
    with open('competition/insert_names.sql', 'w', encoding='utf-8') as fout:
        fout.write('INSERT INTO competition(name, fk_sport_id, date_of_event, place, prestige, description) VALUES\n')
        for line in fin:
            if(line[:len(line)-1] in used):
                continue
            if(len(used) == 30000):
                fout.write(f"('{line[:len(line)-1]}');")
                break
            used.add(line[:len(line)-1])
            fout.write(f"('{line[:len(line)-1]}'),\n")
            
print(len(used))