#include <iostream>
#include <vector>

using namespace std;

int main() {
    string input;
    vector<string> v;
    bool first = true;
    while(cin>>input){
        if(first){
            v.push_back(input);
            first = false;
            continue;
        }
        string vectorElement;
        bool found = false;
        for(int i = 0; i < v.size(); i ++){
            int totalCounter = 0;
            int k = 0;
            vectorElement = v[i];
            if(input.size() < vectorElement.size()){
                for(int j = 0; j < input.size(); j++){
                    if(input[j]-'0' > vectorElement[k] - '0'){
                        v.insert(v.begin()+i, input);
                        found = true;
                        break;
                    } else if(input[j]-'0' < vectorElement[k] - '0'){
                        break;
                    } else if(totalCounter == 201){
                        break;
                    } else {
                        if(k == vectorElement.size()-1){
                            k = -1;
                        }
                        k++;
                        if(j == input.size()-1){
                            j=-1;
                        }
                        totalCounter++;
                    }
                }
            } else {
                for(int j = 0; j < vectorElement.size(); j++){
                    if(vectorElement[j] - '0' > input[k] - '0'){
                        break;
                    } else if(vectorElement[j] - '0' < input[k] - '0'){
                        v.insert(v.begin()+i, input);
                        found = true;
                        break;
                    } else if(totalCounter == 201){
                        break;
                    } else {
                        if(k == input.size()-1){
                            k = -1;
                        }
                        k++;
                        if(j==vectorElement.size()-1){
                            j=-1;
                        }
                        totalCounter++;
                    }
                }
            }
            if(i == v.size()-1 && !found){
                v.push_back(input);
                break;
            }
            if(found){
                break;
            }
        }
    }
    for(int i = 0; i < v.size(); i++){
        cout<<v[i];
    }
    return 0;
}