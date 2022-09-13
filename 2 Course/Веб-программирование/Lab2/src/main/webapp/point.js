"use strict";

const svg = document.querySelector("svg");

document.addEventListener("DOMContentLoaded", () => {
    svg.addEventListener("click", (event) => {
        if (validateR()) {
            const position = getPosition(svg, event);
            x = position.x;
            y = position.y;
            setPointer(x, y);
            x = position.x - 150;
            y = 150 - position.y;
            console.log(x + " " + y);
            const temp = 120/r;
            x = (x/temp).toFixed(1);
            y = (y/temp).toFixed(1);
            sendRequest("svg");
        }
    });
});

function getPosition(svg, event) {
    const rect = svg.getBoundingClientRect();
    return {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top
    };
}

function setPointer(x, y) {
    svg.insertAdjacentHTML("beforeend", `<circle r="5" cx="${x}" cy="${y}" fill="green"></circle>`);
}