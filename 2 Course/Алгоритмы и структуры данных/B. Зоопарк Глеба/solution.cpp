#include <iostream>
#include <cstring>
#include <cctype>
#include <stdio.h>
#include <stack>
using namespace std;

int main()
{
    stack <char> steck;
    stack <int> animalStack;
    stack <int> trapStack;
    string data;
    cin>>data;
    int out[data.size()/2];
    int i;
    int animalCounter=0;
    int trapCounter=0;
    for(i = 0; i < data.size(); i ++){
        char current = data[i];
        if(steck.empty()){
            steck.push(current);
            if(isupper(current)){
                trapCounter+=1;
                trapStack.push(trapCounter);
            } else {
                animalCounter+=1;
                animalStack.push(animalCounter);
            }
        } else {
            if(isupper(current)){
                trapCounter+=1;
                trapStack.push(trapCounter);
            } else {
                animalCounter+=1;
                animalStack.push(animalCounter);
            }
            char whatsOnStack = steck.top();
            if((isupper(current) && islower(whatsOnStack)) || (isupper(whatsOnStack) && islower(current))){
                if(tolower(current)==tolower(whatsOnStack)){
                    out[trapStack.top()-1]=animalStack.top();
                    animalStack.pop();
                    trapStack.pop();
                    steck.pop();
                } else {
                    steck.push(current);
                }
            } else {
                steck.push(current);
            }
        }
    }
    if(!steck.empty()||(data.size()==0)){
        cout<<"Impossible";
    } else {
        cout<<"Possible"<<endl;
        for (int i = 0; i < data.size() / 2; i++) {
            cout << out[i] << " ";
        }
    }
    return 0;
}