import re

# Это задание номер 1
expression = r'((?:0[0-9]|1[0-9]|2[0-3]):(?:[0-5]\d):(?:[0-5]\d)|(?:0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])'
text = 'Уважаемые студенты! В эту субботу в 27687876:89 планируется доп. занятие на 2 часа. То есть в 17:00:01 оно уже точно кончится. '

expression = r'\".*\"'
text = 'Слова "ИТМО" и "ВТ" вы запомните на всю жизнь.'

result = re.search(expression, text)
print(result.group(0))

result = re.sub(expression, '(TBD)', text)
print(result)
# Это задание номер 1


# Это задание номер 2
expression = r'[A-Z][a-z].*,.*,.*[\n.!?;]'
temp = open("Macbeth.txt",'r')
data = temp.read()

result = re.findall(expression, data)
for j in result:
    print(j)
temp.close()
# Это задание номер 2


# Это задание номер 3
expression = r'(?:[-+]\d+)|(?:\d+)'
text = "15 + -456 = -1000 abc ылваоо !аы.а"
temp = re.findall(expression, text)

def func(x):
    return 3 * x * x + 5


for iterator in temp:
    text = text.replace(f'{iterator}', f'{func(int(iterator))}')
print(text)
# Это задание номер 3