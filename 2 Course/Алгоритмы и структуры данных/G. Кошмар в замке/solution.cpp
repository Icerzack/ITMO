#include <bits/stdc++.h>
using namespace std;

bool usedLetters[26];
int lettersInData[26];
long weights[26];

int main() {
    
    string output = "";
    string data; 
    cin >> data;
    for (int i = 0; i < 26; i++) {
        cin >> weights[i];
    }
    for (char c : data){
        lettersInData[c-97] += 1;
    }
    for (int i = 0; i < 26; i++) {
        char maxSymbol = '0';
        for (char j = 'a'; j <= 'z'; j++) {
            if ((!usedLetters[j-97]) && (lettersInData[j-97] > 1) && (weights[j-97] > weights[maxSymbol-97])){
                maxSymbol = j;
            } 
        }
        if (maxSymbol != '0') {
            output = output.append(1, maxSymbol);
            lettersInData[maxSymbol-97] = lettersInData[maxSymbol-97] - 2;
            usedLetters[maxSymbol-97] = true;
        }
    }
    cout << output;
    for (auto &item : lettersInData) {
        int index = distance(lettersInData, find(lettersInData, lettersInData + 26, item));
        while(item>0){
            cout << char(index+97);
            item -= 1;
        }
        
    }
    reverse(output.begin(), output.end());
    cout << output;
    return 0;
}