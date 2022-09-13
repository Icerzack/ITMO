#include <iostream>

using namespace std;

int used[1000];
int initial[1000][1000];
int gsm[1000][1000];
int n;

void dfs(int v, int dir) {
    used[v] = 1;
    for (int i = 0; i < n; i++) {
        if(dir == 1){
            if(gsm[i][v] && !used[i]){
                dfs(i, dir);
            }
        } else {
            if(gsm[v][i] && !used[i]){
                dfs(i, dir);
            }
        }
    }
}

int wasOnAll() {
    for (int i = 0; i < n; i++) {
        if (!used[i]) {
            return 0;
        }
    }
    return 1;
}

int main() {
    cin >> n;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int value;
            cin >> value;
            initial[i][j] = value;
        }
    }
    int leftBound = 0;
    int rightBound = 1000000000;
    int middle = -1;
    int direction = -1;
    while (leftBound < rightBound) {
        middle = (leftBound + rightBound) / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (middle >= initial[i][j]) {
                    gsm[i][j] = 1;
                } else {
                    gsm[i][j] = 0;
                }
            }
        }
        for (int & i : used) {
            i = 0;
        }
        dfs(0, 0);
        direction = 0;
        if (!wasOnAll()) {
            direction = 1;
        } else {
            for (int & i : used) {
                i = 0;
            }
            dfs(0, 1);
            if (!wasOnAll()) {
                direction = 1;
            }
        }
        if (direction == 1) {
            leftBound = middle + 1;
        } else {
            rightBound = middle;
        }
    }
    cout << leftBound;
    return 0;
}