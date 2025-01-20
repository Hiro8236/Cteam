<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Event" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>イベント管理カレンダー</title>
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar/locales/ja.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        #calendar {
            max-width: 900px;
            margin: 50px auto;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            padding-top: 100px;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1 style="text-align: center;">イベント管理カレンダー</h1>
    <div id="calendar"></div>

    <!-- モーダル -->
    <div id="eventModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>イベントを登録</h2>
            <form action="EventCreate.action" id="eventForm" method="post">
                <label for="title">タイトル:</label>
                <input type="text" id="title" name="title" required><br><br>
                <label for="description">説明:</label>
                <input type="text" id="description" name="description"><br><br>
                <label for="start">開始日時:</label>
                <input type="datetime-local" id="start" name="start" required><br><br>
                <label for="end">終了日時:</label>
                <input type="datetime-local" id="end" name="end" required><br><br>
                <button type="submit">登録</button>
            </form>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var calendarEl = document.getElementById('calendar');
            var modal = document.getElementById('eventModal');
            var closeModal = document.getElementsByClassName('close')[0];

            // イベントデータを JavaScript 配列に変換
            var events = [
                <%
                    List<Event> events = (List<Event>) request.getAttribute("events");
                    if (events != null && !events.isEmpty()) {
                        for (int i = 0; i < events.size(); i++) {
                            Event event = events.get(i);
                            String title = event.getTitle();
                            String start = event.getStartTime().toString();
                            String end = event.getEndTime().toString();
                %>
                {
                    title: "<%= title %>",
                    start: "<%= start %>",
                    end: "<%= end %>"
                }<%= (i < events.size() - 1) ? "," : "" %>
                <%
                        }
                    }
                %>
            ];

            // カレンダー初期化
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth', // 月表示
                locale: 'ja',                // 日本語対応
                events: events,              // イベントデータを設定
                dateClick: function (info) { // 日付クリック時
                    modal.style.display = 'block'; // モーダルを表示
                    document.getElementById('start').value = info.dateStr + 'T00:00';
                    document.getElementById('end').value = info.dateStr + 'T00:00';
                }
            });

            calendar.render();

            // モーダルを閉じる
            closeModal.onclick = function () {
                modal.style.display = 'none';
            };
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = 'none';
                }
            };
        });
    </script>
</body>
</html>
