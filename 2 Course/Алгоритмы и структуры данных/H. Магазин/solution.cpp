#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int n;
    int k;
    cin >> n;
    cin >> k;
    vector <long> elements;
    for (int i = 0; i < n; i ++) {
        long price;
        cin >> price;
        elements.push_back(price);
    }
    sort(elements.rbegin(), elements.rend());
    for (int i = k-1; i <= n-1; i+=k) {
        elements[i] = 0;
    }
    long totalPriceWithDiscounts = 0;
    for(int i = 0; i < elements.size(); i++){
        totalPriceWithDiscounts += elements[i];
    }
    cout<<totalPriceWithDiscounts<<endl;
    return 0;
}