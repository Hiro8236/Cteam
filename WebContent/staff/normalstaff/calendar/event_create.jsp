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

                    // 登録データの作成
                    var eventData = {
                        title: "新しいイベント",
                        description: "自動生成イベント",
                        start: info.dateStr + 'T00:00:00',
                        end: info.dateStr + 'T23:59:59',
                        createdBy: 1
                    };

                    // サーバーへデータ送信
                    fetch('/staff/normalstaff/calendar/create', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: new URLSearchParams(eventData)
                    })
                    .then(response => {
                        if (response.ok) {
                            alert('イベントが登録されました！');
                            location.reload();
                        } else {
                            alert('登録中にエラーが発生しました。');
                        }
                    })
                    .catch(error => {
                        console.error('エラー:', error);
                        alert('通信エラーが発生しました。');
                    });
                }
            });

            calendar.render();
            console.log('カレンダー初期化完了');
        });
    </script>
</body>
</html>
