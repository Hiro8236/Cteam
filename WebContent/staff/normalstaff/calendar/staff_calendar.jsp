<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Event" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>パブリックカレンダー</title>

    <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>



    <style>
        body {
            font-family: Arial, sans-serif;
        }
        #calendar {
            max-width: 900px;
            margin: 50px auto;
        }
    </style>
</head>

<body>
    <h1 style="text-align: center;">スタッフカレンダー</h1>
    <div id="calendar"></div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var calendarEl = document.getElementById('calendar');

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

            console.log("イベントデータ:", events);

            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth', // カレンダーの表示形式 (月間)
                locale: 'ja',                // 日本語対応
                events: events               // イベントデータを設定
            });

            calendar.render();
        });
    </script>
</body>
</html>
