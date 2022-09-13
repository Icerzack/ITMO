<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru-RU">
<head>
  <style>

    * {
      font-family: "Avenir Next Cyr", Arial, sans-serif;
      text-align: center;
      font-weight: normal;
    }

    h1 {
      color: #fafafa;
    }

    h2 {
      color: black;
    }

    h2::after {
      content: ":";
    }

    h3{
      color: #000000;
    }

    body {
      background-color: lightgreen;
    }

    body {
      background-color: #c7eeff;
    }

    .bg {
      background: #0077c0;
    }

    table {
      width: 100%;
      border: 1px solid #000;
    }

    tr > td {
      font-style: normal;
    }

    input, a {cursor: pointer}

    svg {
      width: 300px;
      height: 300px;
      border: 1px solid #000000;
    }

    figure img {
      width: 47px;
      height: 55px;
    }

  </style>
  <meta charset="utf-8">
  <title>Лабораторная №2</title>
</head>
<body>
<header class="bg">
  <h1>Веб-программирование, Лаб. 2, Вариант 12015</h1>
  <div id="credit">
    <h3>Кузнецов Максим Александрович P3211</h3>
  </div>
</header>
<table id="mainTable">
  <thead><td colspan="5"><h3>Ввод значений</h3></td></thead>
  <tbody>
  <tr><td colspan="6"><hr></td></tr>
  <tr>
    <td rowspan="3">Выберите X:</td>
    <td>
      -3
      <input type="checkbox" name="check" class="radio" value="-3" onclick="onlyOne(this)"></td>
    <td>
      -2
      <input type="checkbox" name="check" class="radio" value="-2" onclick="onlyOne(this)"></td>
    <td>
      -1
      <input type="checkbox" name="check" class="radio" value="-1" onclick="onlyOne(this)"></td>
    <td rowspan="5">
      <svg xmlns="http://www.w3.org/2000/svg">
        <line x1="0" y1="150" x2="300" y2="150" stroke="#000720"></line>
        <line x1="150" y1="0" x2="150" y2="300" stroke="#000720"></line>
        <line x1="270" y1="148" x2="270" y2="152" stroke="#000720"></line>
        <text x="265" y="140">R</text>
        <text x="200" y="140">R/2</text>
        <text x="75" y="140">-R/2</text>
        <text x="20" y="140">-R</text>
        <text x="156" y="35">R</text>
        <text x="156" y="95">R/2</text>
        <text x="156" y="215">-R/2</text>
        <text x="156" y="275">-R</text>
        <polygon points="300,150 295,155 295, 145" fill="#000720" stroke="#000720"></polygon>
        <polygon points="150,0 145,5 155,5" fill="#000720" stroke="#000720"></polygon>
        <rect x="150" y="90" width="120" height="60" fill-opacity="0.4" stroke="navy" fill="blue"></rect>
        <polygon points="150,150 270,150 150,210" fill-opacity="0.4" stroke="navy" fill="blue"></polygon>
        <path d="M90 150 A 75 75, 0, 0, 0, 150 210 L 150 150 Z" fill-opacity="0.4" stroke="navy" fill="blue"></path>
      </svg>
    </td>
  </tr>
  <tr>
    <td>
      0
      <input type="checkbox" name="check" class="radio" value="0" onclick="onlyOne(this)"></td>
    <td>
      1
      <input type="checkbox" name="check" class="radio" value="1" onclick="onlyOne(this)"></td>
    <td>
      2
      <input type="checkbox" name="check" class="radio" value="2" onclick="onlyOne(this)"></td>
  </tr>
  <tr>
    <td>
      3
      <input type="checkbox" name="check" class="radio" value="3" onclick="onlyOne(this)"></td>
    <td>
      4
      <input type="checkbox" name="check" class="radio" value="4" onclick="onlyOne(this)"></td>
    <td>
      5
      <input type="checkbox" name="check" class="radio" value="5" onclick="onlyOne(this)"></td>
  </tr>
  <tr>
    <td>Введите значение Y:</td>
    <td colspan="3"><input required name="Y-input" type="text" placeholder="(-5 до 5)" maxlength="6"></td>
  </tr>
  <tr>
    <td>Введите значение R:</td>
    <td colspan="3"><input required name="R-input" type="text" placeholder="(2 до 5)" maxlength="6"></td>
  </tr>
  <tr>
    <td colspan="5" rowspan="6">
      <button id="checkButton">Проверить на попадание в область</button>
      <hr>
    </td>
  </tr>
  </tbody>
  <tfoot>
  <tr>
    <td colspan="6" id="outputContainer"><h4><span class="outputStub notification"></span></h4>
      <table>
        <tbody id="outputTable">
        <tr>
          <th>x</th>
          <th>y</th>
          <th>r</th>
          <th>Точка входит в ОДЗ</th>
          <th>Текущее время</th>
        </tr>
        <%
          List tableRows = (List) config.getServletContext().getAttribute("tableRows");
          if (tableRows == null) {
            tableRows = new ArrayList<>();
            config.getServletContext().setAttribute("tableRows", tableRows);
          } else {
              for (Object tableRow: tableRows) { %>
              <%= tableRow%>
              <%}%>
          <%}%>
        </tbody>
      </table>
    </td>
  </tr>
  </tfoot>
</table>
<script src="validator.js"></script>
<script src="point.js"></script>
</body>
</html>
