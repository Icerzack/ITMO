import codecs

r = codecs.open(r'C:\Users\Max-PC\Desktop\ИТМО\Информатика\Лаба№3\WednesdayXML.txt', 'r', "utf_8_sig")  # открываем файл
w = codecs.open(r'C:\Users\Max-PC\Desktop\ИТМО\Информатика\Лаба№3\WednesdayYAML.txt', 'w')  # открываем файл на запись

k = 1
r.readline()
x = r.read().split('\n')
for i in range(len(x)):
    print(x[i].strip().replace('<', '>').split('>')[1:-1])
    a = x[i].strip().replace('<', '>').split('>')[1:-1]
    if len(a) > 2: a = a[:-1]
    if len(a) == 1:
        if a[0][0] == '/':
            k -= 1
        else:
            w.write(' ' * k + a[0] + ":" + '\n')
            k += 1
    else:
        w.write(
            ' ' * k + a[0] + ':“' + a[1] + '” ' + '\n')
r.close()
w.close()
