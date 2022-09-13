"use strict";

let x, y, r;
window.onload = function () {

    let buttons = document.querySelectorAll("input[name=X-button]");
    buttons.forEach(click);

    function click(element) {
        element.onclick = function () {
            x = this.value;
            buttons.forEach(function (element) {
                element.style.boxShadow = "";
                element.style.transform = "";
            });
            this.style.boxShadow = "0 0 40px 5px #10ddc2";
        }
    }

};

document.getElementById("checkButton").onclick = function () {
  if (validateX() && validateY() && validateR()) {
      fetch("answer.php", {
          method: "POST",
          headers: {"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"},
          body: "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r) +
              "&timezone=" + encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone)
      }).then(response => response.text()).then(function (serverAnswer) {
          document.getElementById("tablebody").innerHTML = document.getElementById("tablebody").innerHTML + serverAnswer;
      }).catch(err => createNotification("Ошибка HTTP. Повторите попытку позже. " + err));
  }
};

function createNotification(message) {
    let outputContainer = document.getElementById("outputContainer");
    if (outputContainer.contains(document.querySelector(".notification"))) {
        let stub = document.querySelector(".notification");
        stub.textContent = message;
        stub.classList.replace("outputStub", "errorStub");
    } else {
        let notificationTableRow = document.createElement("h4");
        notificationTableRow.innerHTML = "<span class='notification errorStub'></span>";
        outputContainer.prepend(notificationTableRow);
        let span = document.querySelector(".notification");
        span.textContent = message;
    }
}

function validateX() {
    if (isNumeric(x)) return true;
    else {
        createNotification("x не выбран");
        return false;
    }
}

function validateY() {
    y = document.querySelector("input[name=Y-input]").value.replace(",", ".");
    if (y === undefined) {
        createNotification("y не введён");
        return false;
    } else if (!isNumeric(y)) {
        createNotification("y не число");
        return false;
    } else if (!((y >= -3) && (y <= 5))) {
        createNotification("y не входит в область допустимых значений");
        return false;
    } else return true;
}

function onlyOne(checkbox) {
    var checkboxes = document.getElementsByName('check')
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}

function validateR() {
    try {
        r = document.querySelector("input[type=checkbox]:checked").value;
        return true;
    } catch (err) {
        createNotification("Значение R не выбрано");
        return false;
    }
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}
