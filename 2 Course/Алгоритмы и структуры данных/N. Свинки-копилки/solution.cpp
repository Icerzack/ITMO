#include <vector>
#include <iostream>

using namespace std;

vector <vector<int>> initial;
vector <int> used;
int totalResult = 0;

void dfs(int v){
    used[v] = 1;
    for(int i : initial[v]){
        if(used[i] == 0){
            dfs(i);
        }
    }
}
int main(){
    int n;
    cin >> n;
    used.resize(n, 0);
    initial.resize(n);
    for (int i = 0; i < n; i++){
        int kopilka;
        cin >> kopilka;
        initial[kopilka-1].push_back(i);
        initial[i].push_back(kopilka-1);
    }
    for (int i = 0; i < n; i++){
        if(used[i] == 0){
            dfs(i);
            totalResult++;
        }
    }
    cout << totalResult;
    return 0;
}