#include <iostream>
#include <deque>
using namespace std;

int main() {
    int n;
    cin >> n;
    deque<string> queueLeft;
    deque<string> queueRight;
    for(int i = 0; i < n; i++){
        string operation;
        cin>>operation;
        if(operation=="-"){
            cout<<queueLeft.front()<<endl;
            queueLeft.pop_front();
        }
        else if(operation=="*"){
            string goblinNum;
            cin>>goblinNum;
            queueRight.push_front(goblinNum);
        }
        else {
            string goblinNum;
            cin>>goblinNum;
            queueRight.push_back(goblinNum);
        }
        if(queueRight.size()>queueLeft.size()){
            queueLeft.push_back(queueRight.front());
            queueRight.pop_front();
        }
    }
    return 0;
}