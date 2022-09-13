import numpy as np
import sys
import copy

INPUT = "input2.txt"
A = []
residuals = []


def isint(s):
    try:
        int(s)
        return True
    except ValueError:
        return False


def print_data(mat):
    for i in range(number):
        print('X', end='')
        print(i + 1, end='')
        print('=', end=' ')
        s = '{0:.100f}'.format(mat[i]).rstrip('0').rstrip('.')
        conter = 0
        if (isint(s)):
            print(mat[i], end='\t')
        else:
            first = True
            print(s.split('.')[0], end='')
            print('.', end='')
            for c in s.split('.')[1]:
                if c != '0':
                    first = False
                    conter += 1
                    print(c, end='')
                    if conter == 3:
                        break
                else:
                    if first:
                        print('0', end='')
                    else:
                        conter += 1
                        print(c, end='')
                        if conter == 3:
                            break
            print(' ', end='')
    print()


def print_matrix(mat):
    for row in mat:
        for value in row:
            print('{}'.format(round(value, 3)), end='\t')
        print()


def check_matrix(det):
    if det == 0:
        print("\nСистема не имеет решение или имеет бесконечное множество решений!")
        sys.exit()


def read_from_file():
    with open(INPUT, 'r', encoding='utf-8') as input:
        try:
            temp = []
            global number
            number = int(input.readline())
            lines = input.readlines()
            for r in lines:
                row = []
                coefs = r.strip().split()
                counter = -1
                for coef in coefs:
                    counter += 1
                    row.append(float(coef))
                    if counter == (number - 1):
                        A.append(copy.copy(row))
                if len(row) != (number + 1):
                    raise ValueError
                temp.append(row)
            if len(temp) != number:
                raise ValueError
        except ValueError:
            print("\nНеверно составлен файл.")
            return None
    return temp


def read_from_console():
    while True:
        n = int(input("\nРазмерность матрицы: "))
        if n <= 0:
            print("\nПорядок > 0!")
        elif n > 20:
            print("\nПорядок <= 20!")
        else:
            break
    temp = []
    print("\nКоэффициенты матрицы (через пробел): ")
    try:
        for i in range(n):
            row = []
            coefs = input().strip().split()
            for coef in coefs:
                row.append(float(coef))
            if len(row) != (n + 1):
                raise ValueError
            temp.append(row)
    except ValueError:
        print("\nНеверно введены данные.")
        return None
    return temp


def swap_rows(m, col):
    max_element = m[col][col]
    max_row = col
    for i in range(col + 1, len(m)):
        if abs(m[i][col]) > abs(max_element):
            max_element = m[i][col]
            max_row = i
    if max_row != col:
        m[col], m[max_row] = m[max_row], m[col]


def gauss_method():
    for i in range(number):
        if matrix[i][i] == 0.0:
            swap_rows(matrix, i)
        for j in range(i + 1, number):
            ratio = matrix[j][i] / matrix[i][i]
            for k in range(number + 1):
                matrix[j][k] = matrix[j][k] - ratio * matrix[i][k]


def back_substitution():
    roots[number - 1] = matrix[number - 1][number] / matrix[number - 1][number - 1]
    for i in range(number - 2, -1, -1):
        roots[i] = matrix[i][number]
        for j in range(i + 1, number):
            roots[i] = roots[i] - matrix[i][j] * roots[j]
        roots[i] = roots[i] / matrix[i][i]


def count_residuals():
    global residuals
    residuals = [0] * number
    for i in range(number):
        calculated_part = 0

        for j in range(number):
            calculated_part += matrix[i][j] * roots[j]

        residuals[i] = calculated_part - matrix[i][number]


while True:
    method = input("\nУкажите способ (файл - 0, консоль - 1):")
    global matrix
    if method == "0":
        matrix = read_from_file()
        break
    elif method == "1":
        matrix = read_from_console()
        break
    else:
        continue

original_matrix = copy.copy(matrix)
print("\nИсходная матрица:")
print_matrix(matrix)
roots = np.zeros(number)
print("\nОпределитель:")
det = np.linalg.det(A)
check_matrix(det)
print(det)
print("\nПриведенная матрица:")
gauss_method()
print_matrix(matrix)
print("\nКорни:")
back_substitution()
print_data(roots)
print("\nВектор невязок:")
count_residuals()
print(residuals)
