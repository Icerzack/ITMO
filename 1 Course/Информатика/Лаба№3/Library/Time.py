import codecs
import timeit
def solution1():

    r = codecs.open(r'WednesdayXML.txt', 'r',
                    "utf_8_sig")  # открываем файл
    w = codecs.open(r'WednesdayYAML.txt',
                    'w')  # открываем файл на запись

    k = 1
    r.readline()
    x = r.read().split('\n')
    for i in range(len(x)):
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

import xmlplain
import codecs

def solution2():
    # Read to plain object
    with codecs.open(r'WednesdayXML.txt', "r", "utf_8_sig") as inf:
        root = xmlplain.xml_to_obj(inf, strip_space=True, fold_dict=True)

    # Output plain YAML
    with codecs.open(r'WednesdayYAML.txt', "w") as outf:
        xmlplain.obj_to_yaml(root, outf)


print("%.9f" % timeit.timeit("solution1()", setup="from __main__ import solution1", number=10))
print("%.9f" % timeit.timeit("solution2()", setup="from __main__ import solution2", number=10))