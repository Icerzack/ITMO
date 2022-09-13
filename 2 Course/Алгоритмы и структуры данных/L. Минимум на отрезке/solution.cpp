#include <iostream>
#include <deque>
#include <vector>

using namespace std;

int main() {
    int n;
    int k;
    cin >> n;
    cin >> k;
    int mass[n];
    for(int i = 0; i < n; i++){
        int num;
        cin >> num;
        mass[i] = num;
    }
    vector <int> minimums;
    deque <int> deque;
    for(int i = 0; i < n; i++){
        if(deque.size()>0 && (deque.front()<=i-k)){
            deque.pop_front();
        }
        while(deque.size()>0 && mass[deque.back()]>=mass[i]){
            deque.pop_back();
        }
        deque.push_back(i);
        if(i>=k-1){
            minimums.push_back(mass[deque.front()]);
        }
    }
    for(int i = 0; i < minimums.size(); i++){
        cout<<minimums[i]<<" ";
    }
    return 0;
}