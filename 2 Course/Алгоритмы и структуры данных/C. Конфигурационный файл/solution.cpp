#include <iostream>
#include <stack>
#include <unordered_map>
#include <vector>

using namespace std;

unordered_map< string, vector <string> > allValues;
stack < vector <string> > currentLevelValues;
string param;
string value;
unordered_map < string, vector <string> > :: iterator it;

void determineNum(string par, string val){
    it = allValues.find(par);
    if (it == allValues.end()) {
        allValues[par] = {val};
    } else {
        allValues[par].push_back(val);
    }
    currentLevelValues.top().push_back(par);
}

void determineVar(string par, string val){
    it = allValues.find(val);
    if (it == allValues.end()) {
        allValues[val] = {"0"};
        currentLevelValues.top().push_back(val);
    }
    else if (allValues[val].empty()) {
        allValues[val].push_back("0");
        currentLevelValues.top().push_back(val);
    }
    it = allValues.find(par);
    if (it == allValues.end()) {
        allValues[par] = {allValues[val].back()};
    }
    else {
        allValues[par].push_back(allValues[val].back());
    }
    currentLevelValues.top().push_back(par);
}


int main(){
    currentLevelValues.push({});
    string input;
    while(cin>>input){
        if(input == "{"){
            currentLevelValues.push({});
            continue;
        }
        else if(input == "}"){
            for (int i = 0; i < currentLevelValues.top().size(); i++)
                allValues[currentLevelValues.top()[i]].pop_back();
            currentLevelValues.pop();
            continue;
        }
        else {
            param = input.substr(0, input.find("="));
            value = input.substr(input.find("=")+1, input.size());
            if(isdigit(value[0])||isdigit(value[1])){
                determineNum(param, value);
            } else {
                determineVar(param, value);
                cout << allValues[param].back() << endl;
            }
        }
    }
    return 0;
}