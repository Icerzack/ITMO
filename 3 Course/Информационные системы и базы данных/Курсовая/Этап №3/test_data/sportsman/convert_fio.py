used = set()

with open('sportsman/names_w.txt', 'r', encoding='utf-8') as fin:
    with open('sportsman/insert_names_w.sql', 'w', encoding='utf-8') as fout:
        fout.write('INSERT INTO sportsman (name) VALUES\n')
        for line in fin:
            if(line[:len(line)-1] in used):
                continue
            if(len(used) == 5000):
                fout.write(f"('{line[:len(line)-1]}');")
                break
            used.add(line[:len(line)-1])
            fout.write(f"('{line[:len(line)-1]}'),\n")
            
print(len(used))