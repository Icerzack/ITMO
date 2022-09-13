#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int n;
int m;
int sx;
int sy;
int fx;
int fy;
vector<vector<int> > mapField;
vector<vector<int> > paths;
queue<pair<int, int> > queueForCell;

void findWay() {
    queueForCell.push({sx, sy});
    paths[sx][sy] = 0;
    while (!queueForCell.empty()) {
        int cx = queueForCell.front().first;
        int cy = queueForCell.front().second;
        queueForCell.pop();
        if ((cx < n - 1) && (paths[cx][cy] + mapField[cx + 1][cy] < paths[cx + 1][cy]) && (mapField[cx + 1][cy] != 0)) {
            queueForCell.push({cx + 1, cy});
            paths[cx + 1][cy] = paths[cx][cy] + mapField[cx + 1][cy];
        }
        if ((cy < m - 1) && (paths[cx][cy] + mapField[cx][cy + 1] < paths[cx][cy + 1]) && (mapField[cx][cy + 1] != 0)) {
            queueForCell.push({cx, cy + 1});
            paths[cx][cy + 1] = paths[cx][cy] + mapField[cx][cy + 1];
        }
        if ((cx >= 1) && (paths[cx][cy] + mapField[cx - 1][cy] < paths[cx - 1][cy]) && (mapField[cx - 1][cy] != 0)) {
            queueForCell.push({cx - 1, cy});
            paths[cx - 1][cy] = paths[cx][cy] + mapField[cx - 1][cy];
        }
        if ((cy >= 1) && (paths[cx][cy] + mapField[cx][cy - 1] < paths[cx][cy - 1]) && (mapField[cx][cy - 1] != 0)) {
            queueForCell.push({cx, cy - 1});
            paths[cx][cy - 1] = paths[cx][cy] + mapField[cx][cy - 1];
        }
    }
}

string resultat;
void writePath() {
    if (paths[fx][fy] != INT32_MAX) {
        cout << paths[fx][fy] << endl;
        int tempX = fx;
        int tempY = fy;
        while (paths[tempX][tempY] != 0) {
            if ((tempX < n - 1) && (paths[tempX][tempY] == paths[tempX + 1][tempY] + mapField[tempX][tempY])) {
                tempX = tempX + 1;
                resultat += "N";
            } else if ((tempY < m - 1) && (paths[tempX][tempY] == paths[tempX][tempY + 1] + mapField[tempX][tempY])) {
                tempY = tempY + 1;
                resultat += "W";
            } else if ((tempX > 0) && (paths[tempX][tempY] == paths[tempX - 1][tempY] + mapField[tempX][tempY])) {
                tempX = tempX - 1;
                resultat += "S";
            } else if ((tempY > 0) && (paths[tempX][tempY] == paths[tempX][tempY - 1] + mapField[tempX][tempY])) {
                tempY = tempY - 1;
                resultat += "E";
            }
        }
        reverse(resultat.begin(), resultat.end());
        cout << resultat;
        return;
    }
    cout << "-1";
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cin >> n;
    cin >> m;
    cin >> sx;
    sx--;
    cin >> sy;
    sy--;
    cin >> fx;
    fx--;
    cin >> fy;
    fy--;
    mapField.resize(n, vector<int>(m, 0));
    paths.resize(n, vector<int>(m, INT32_MAX));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            char cell;
            cin >> cell;
            if (cell == '.') {
                mapField[i][j] = 1;
            } else if (cell == 'W') {
                mapField[i][j] = 2;
            }
        }
    }
    findWay();
    writePath();
    return 0;
}