import matplotlib
import matplotlib.pyplot as plt
import numpy as np
from collections import defaultdict
import re

from matplotlib import transforms

matplotlib.use('TkAgg')

#добавьте сюда файл, сгенерированный командой top в течении минуты
fname = "./top.txt"

#измените на свое имя пользователя
username = "max"

dictionary = defaultdict(list)
with open(fname) as p:
    lines = p.readlines()
    for line in lines:
        line_splitted = line.split()
        if (username in line) and ("R" in line):
            dictionary[line_splitted[0]] += "R"
        if (username in line) and ("D" in line):
            dictionary[line_splitted[0]] += "D"
        if (username in line) and ("S" in line):
            dictionary[line_splitted[0]] += "S"
        if (username in line) and ("Z" in line):
            dictionary[line_splitted[0]] += "Z"
counter = 1
ord = 1
fig, ax = plt.subplots()
trans = transforms.blended_transform_factory(
ax.get_yticklabels()[0].get_transform(), ax.transData)
for key, value in dictionary.items():
    for val in value:
        if val == "R":
            plt.plot(counter, ord, 'ro')
        if val == "D":
            plt.plot(counter, ord, 'o', color="black")
        if val == "S":
            plt.plot(counter, ord, 'o', color="cyan")
        if val == "Z":
            plt.plot(counter, ord, 'yo')
        counter = counter + 1
    ax.text(0, ord, key, color="red", transform=trans,
            ha="right", va="center")
    ord = ord + 1
    counter = 1
plt.axis('off')
plt.title("Threads")
plt.show()
