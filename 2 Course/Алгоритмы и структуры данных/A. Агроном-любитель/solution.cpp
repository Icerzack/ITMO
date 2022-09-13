#include <iostream>
#include <string>
using namespace std;

int main()
{
    int num;
    int i;
    cin>>num;
    int firstIndex = 1;
    int lastIndex = 1;
    int repeated = 0;
    int previous = -1;
    int current;
    int maxLength = -1;
    int finalFirst = 1;
    int finalLast = 1;
    int firstElement;
    for(i = 1; i <= num; i++){
        cin>>current;
        if(i==1){
            previous = current;
            firstElement = current;
            continue;
        }
        else if(previous == current){
            repeated+=1;
            lastIndex+=1;
            previous = current;
            if(repeated == 2){
                if(lastIndex-firstIndex == 1){
                    firstIndex = lastIndex-1;
                    repeated = 1;
                    continue;
                }
                int currLength = lastIndex-firstIndex;
                if(currLength>maxLength){
                    maxLength = currLength;
                    finalFirst = firstIndex;
                    finalLast = lastIndex-1;
                }
                firstIndex=lastIndex-1;
                repeated = 1;
                firstElement = previous;
                previous = current;
                continue;
            }
            continue;
        }
        else {
            lastIndex+=1;
            previous = current;
            repeated = 0;
        }
    }
    int currLength = (lastIndex)-firstIndex+1;
    if(currLength>maxLength){
        maxLength = currLength;
        finalFirst = firstIndex;
        finalLast = lastIndex;
    }
    if((finalFirst == finalLast)&&(num>1)){
        finalLast = 2;
    }
    cout<<finalFirst;
    cout<<" ";
    cout<<finalLast;
    return 0;
}