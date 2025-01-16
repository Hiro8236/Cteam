<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>イベント登録</title>
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
</head>
<body>
    <h1 style="text-align: center;">イベント管理カレンダー</h1>
    <div id="calendar"></div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            console.log('カレンダー初期化開始');
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ja',
                dateClick: function (info) {
                    console.log('クリックされた日付:', info.dateStr);
                    alert('日付がクリックされました: ' + info.dateStr);
                }
            });

            calendar.render();
            console.log('カレンダー初期化完了');
        });
    </script>
</body>
</html>
