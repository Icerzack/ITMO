import numpy as np
import matplotlib.pyplot as plt

# Задаем интервал для x
x = np.linspace(0, 2*np.pi, 1000)

# Строим график функции sin(x)
plt.plot(x, np.sin(x))

# Отмечаем точки на окружности с интервалом pi/6
for i in range(12):
    plt.plot(np.pi/6*i, np.sin(np.pi/6*i), 'o', markersize=10, color='black')
    
# Отмечаем пограничные точки квадрантов
plt.plot([0, np.pi/2, np.pi, 3*np.pi/2, 2*np.pi], [0, 1, 0, -1, 0], 'o', markersize=10, color='yellow')

# Закрашиваем квадранты разными цветами
plt.fill_between([0, np.pi/2], 0, 1, facecolor='green', alpha=0.3)
plt.fill_between([np.pi/2, np.pi], 0, 1, facecolor='red', alpha=0.3)
plt.fill_between([np.pi, 3*np.pi/2], 0, -1, facecolor='blue', alpha=0.3)
plt.fill_between([3*np.pi/2, 2*np.pi], 0, -1, facecolor='yellow', alpha=0.3)

# Настраиваем оси и заголовок
plt.xlabel('x')
plt.ylabel('sin(x)')
plt.title('График функции sin(x) с отмеченными точками и квадрантами')

# Отображаем график
plt.show()
