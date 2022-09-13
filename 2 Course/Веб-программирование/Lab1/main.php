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
            color: #fafafa;
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
        
        </style>
        <meta charset="utf-8">
        <meta name="description" content="Лабораторная работа №1.">
        <meta name="author" content="Кузнецов Максим Александрович">
        <title>Лабораторная №1</title>
    </head>
    <body>
      <header class="bg">
          <h1>Веб-программирование, Лаб. 1, Вариант 11009</h1>
          <div id="credit">
            <h3>Кузнецов Максим Александрович P3211</h3>
          </div>
      </header>
        <table id="mainTable" class="shaded">
          <thead>colspan="5">Ввод значений</h3></thead>
          <tbody>
          <tr><td colspan="5"><hr></td></tr>
            <tr>
              <td rowspan="3">Выберите значение X:</td>
              <td><input name="X-button" type="button" value="-2.0"></td>
              <td><input name="X-button" type="button" value="-1.5"></td>
              <td><input name="X-button" type="button" value="-1.0"></td>
              <td rowspan="6">
                <img src = "img/areas.png" alt = "">
              </td>
            </tr>
            <tr>
              <td><input name="X-button" type="button" value="-0.5"></td>
              <td><input name="X-button" type="button" value="0"></td>
              <td><input name="X-button" type="button" value="0.5"></td>
            </tr>
            <tr>
              <td><input name="X-button" type="button" value="1"></td>
              <td><input name="X-button" type="button" value="1.5"></td>
              <td><input name="X-button" type="button" value="2"></td>
            </tr>
            <tr>
              <td>Введите значение Y:</td>
              <td colspan="3"><input required name="Y-input" type="text" placeholder="(-3 до 5)" maxlength="6"></td>
            </tr>
            <tr>
              <td rowspan="2">Выберите R:</td>
              <td>
                1 <input type="checkbox" name="check" class="radio" value="1" onclick="onlyOne(this)">
              </td>
              <td>
                2
                <input type="checkbox" name="check" class="radio" value="2" onclick="onlyOne(this)"></td>
              <td>
                3
                <input type="checkbox" name="check" class="radio" value="3" onclick="onlyOne(this)"></td>
            </tr>
            <tr>
              <td>
              4
              <input type="checkbox" name="check" class="radio" value="4" onclick="onlyOne(this)"></td>
              <td>
              5
              <input type="checkbox" name="check" class="radio" value="5" onclick="onlyOne(this)"></td>
            </tr>
          <tr>
            <td colspan="5" rowspan="6">
              <button id="checkButton">Проверить на попадание в область</button>
              <hr>
            </td>
          </tr>
          </tbody>
            <table id="tablebody">
                 <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Точка входит в ОДЗ?</th>
                    <th>Текущее время</th>
                    <th>Время скрипта</th>
                </tr>Результаты
            <?php
              session_start();
              foreach ($_SESSION["tableRows"] as $tableRow) echo $tableRow;
            ?>
            </table>
        </table>
    <script src="validator.js"></script>
    </body>
</html>
