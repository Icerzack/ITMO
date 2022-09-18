# Вариант (28+05)%10+1 = 4
# Рига -> Уфа
import collections

graph = {}
graph["Вильнюс"]["Даугавпилс"] = 2


def bfs(graph, root):
    visited = set()
    queue = collections.deque([root])
    visited.add(root)
    while queue:
        vertex = queue.popleft()
        for neighbour in graph[vertex]:
            if (neighbour == "Уфа"):
                print("found!")
                return
            if neighbour not in visited:
                visited.add(neighbour)
                queue.append(neighbour)


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press ⌘F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print_hi('PyCharm')

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
