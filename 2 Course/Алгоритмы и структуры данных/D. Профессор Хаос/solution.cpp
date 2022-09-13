#include <iostream>
#include <stdlib.h>
using namespace std;

int main()
{
    int a;
    int b;
    int c;
    int d;
    int k;
    cin >> a;
    cin >> b;
    cin >> c;
    cin >> d;
    cin >> k;
    int result;
	bool finished=false;
    int oldresult = a*1;
    for(int i = 0; i < k; i++){
        result = oldresult*b-c;
        if(result < 0){
            cout<<0;
            finished = true;
            break;
        }
        if(result == a){
            cout<<a;
            finished = true;
            break;
        }
        if(result>d){
            cout<<d;
            finished = true;
            break;
        }
        oldresult = result;
    }
    if(!finished){
        cout<<result;
    }
}