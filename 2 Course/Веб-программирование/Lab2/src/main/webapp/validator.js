"use strict";

let x, y, r;

function onlyOne(checkbox) {
    const checkboxes = document.getElementsByName('check');
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}

document.getElementById("checkButton").onclick = function () {
    if (validateX() && validateY() && validateR()){
        sendRequest("button");
    }
};

//Параметр key установливает, тип запроса обработки точки на сервере: "button" - для клика по кнопке, "svg" - для клика по канвасу.
function sendRequest(key) {
    const keys = ["button", "svg"];
    if (keys.includes(key)) {
        fetch(createRequest(key)).then(response => response.text()).then(serverAnswer => {
            document.getElementById("outputTable").innerHTML = document.getElementById("outputTable").innerHTML + serverAnswer;
        }).catch(err => alert("Ошибка HTTP. Повторите попытку позже. " + err));
    } else throw new Error("Не указан способ отправки");
}

function createRequest(key) {
        let path = 'controller?x='
            + encodeURIComponent(x) + '&y='
            + encodeURIComponent(y.substring(0,6)) + '&r='
            + encodeURIComponent(r.substring(0,6)) + '&key='
            + encodeURIComponent(key);
        let header = new Headers();
        header.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
        let init = {method: 'GET', headers: header};
        return new Request(path, init);
}

function validateX() {
    try {
        x = document.querySelector("input[type=checkbox]:checked").value;
        return true;
    } catch (err) {
        alert("Значение X не выбрано");
        return false;
    }
}

function validateY() {
    y = document.querySelector("input[name=Y-input]").value.replace(",", "."); //замена разделителя дробной части числа
    y = y.substring(0,6);
    if (y === undefined) {
        alert("Y не введён");
        return false;
    } else if (!isNumeric(y)) {
        alert("Y не число");
        return false;
    } else if (!((y > -5) && (y < 5))) {
        alert("Y не входит в область допустимых значений");
        return false;
    } else return true;
}

function validateR() {
    r = document.querySelector("input[name=R-input]").value.replace(",", "."); //замена разделителя дробной части числа
    r = r.substring(0,6)
    if (r === undefined) {
        alert("R не введён");
        return false;
    } else if (!isNumeric(r)) {
        alert("R не число");
        return false;
    } else if (!((r > 2) && (r < 5))) {
        alert("R не входит в r допустимых значений");
        return false;
    } else return true;
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}