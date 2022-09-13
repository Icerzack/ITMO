#include <iostream>
using namespace std;

int main() {
    int n;
    int k;
    cin>>n;
    cin>>k;
    int arr[n];
    for(int i = 0; i < n; i ++){
        cin>>arr[i];
    }
    int middle;
    int leftBorder = 0;
    int rightBorder = arr[n-1]-arr[0]+1;
    for(int i = 0; i < 100; ++i) {
        middle = (leftBorder + rightBorder)/2;
        int previous = arr[0];
        int counter = 1;
        for (int j = 1; j < n; j++) {
            if (arr[j] - previous >= middle) {
                previous = arr[j];
                counter++;
            }
        }
        if(counter<k){
            rightBorder = middle;
        } else {
            leftBorder = middle;
        }
    }
    cout<<leftBorder<<endl;
    return 0;
}