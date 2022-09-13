#include <iostream>
#include <vector>

using namespace std;

vector <int> color;
vector <vector <int>> initial;
bool impossible = false;

void dfs(int v, int c) {
    color[v] = c;
    for (int i : initial[v]) {
        if (color[i] == 0) {
            dfs(i, 3-c);
        } else if (color[i] == c) {
            impossible = true;
        }
    }
}

int main() {
    int n;
    int m;
    int a;
    int b;
    cin >> n;
    cin >> m;
    color.resize(n, 0);
    initial.resize(n);
    for (int i = 0; i < m; i++) {
        cin >> a;
        cin >> b;
        initial[a-1].push_back(b-1);
        initial[b-1].push_back(a-1);
    }
    for (int i = 0; i < n ; i++) {
        if (color[i] == 0)
            dfs(i, 1);
    }
    if (!impossible) {
        cout << "YES";
    }
    else {
        cout << "NO";
    }
    return 0;
}