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
            <h2 id="modalTitle">イベントを登録</h2>
            <form id="eventForm" method="post">
                <label for="title">タイトル:</label>
                <input type="text" id="title" name="title" required><br><br>
                <label for="description">説明:</label>
                <input type="text" id="description" name="description"><br><br>
                <label for="start">開始日時:</label>
                <input type="datetime-local" id="start" name="start" required><br><br>
                <label for="end">終了日時:</label>
                <input type="datetime-local" id="end" name="end" required><br><br>
                <input type="hidden" id="eventId" name="eventId">
                <button type="button" id="saveEventBtn">保存</button>
                <button type="button" id="deleteEventBtn" style="display:none;">削除</button>
            </form>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var calendarEl = document.getElementById('calendar');
            var modal = document.getElementById('eventModal');
            var closeModal = document.getElementsByClassName('close')[0];
            var deleteEventBtn = document.getElementById('deleteEventBtn');
            var saveEventBtn = document.getElementById('saveEventBtn');
            var modalTitle = document.getElementById('modalTitle');

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
                            String description = event.getDescription(); // 説明を取得
                            int id = event.getEventId();
                %>
                {
                    id: <%= id %>,
                    title: "<%= title %>",
                    start: "<%= start %>",
                    end: "<%= end %>",
                    extendedProps: { // extendedProps 内に説明を含める
                        description: "<%= description %>"
                    }
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
                timeZone: 'Asia/Tokyo', // タイムゾーンを指定
                events: events,              // イベントデータを設定
                dateClick: function (info) { // 日付クリック時
                    modal.style.display = 'block'; // モーダルを表示
                    modalTitle.textContent = 'イベントを登録';
                    document.getElementById('title').value = '';
                    document.getElementById('description').value = '';
                    document.getElementById('start').value = info.dateStr + 'T00:00';
                    document.getElementById('end').value = info.dateStr + 'T00:00';
                    document.getElementById('eventId').value = '';
                    deleteEventBtn.style.display = 'none'; // 削除ボタンを非表示
                },
                eventClick: function (info) { // イベントクリック時
                    modal.style.display = 'block';
                    modalTitle.textContent = 'イベントを編集';
                    document.getElementById('title').value = info.event.title;
                    document.getElementById('description').value = info.event.extendedProps.description || '';
                    document.getElementById('start').value = info.event.start.toISOString().slice(0, 16);
                    document.getElementById('end').value = info.event.end
                        ? info.event.end.toISOString().slice(0, 16)
                        : info.event.start.toISOString().slice(0, 16); // 終了時間がnullの場合、開始時間を使用
                    document.getElementById('eventId').value = info.event.id;
                    deleteEventBtn.style.display = 'inline-block'; // 削除ボタンを表示
                }
            });

            calendar.render();

            // モーダルを閉じる
            closeModal.onclick = function () {
                modal.style.display = 'none';
                deleteEventBtn.style.display = 'none'; // 削除ボタンを非表示に戻す
            };
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = 'none';
                    deleteEventBtn.style.display = 'none'; // 削除ボタンを非表示に戻す
                }
            };

            // 保存処理
            saveEventBtn.addEventListener('click', function () {
                var form = new FormData(document.getElementById('eventForm'));
                var actionUrl = document.getElementById('eventId').value ? 'EventUpdate.action' : 'EventCreate.action';

                fetch(actionUrl, {
                    method: 'POST',
                    body: new URLSearchParams(form)
                })
                .then(function (response) {
                    if (response.ok) {
                        alert('イベントが正常に保存されました！');
                        modal.style.display = 'none';
                        calendar.refetchEvents(); // カレンダーを再描画
                    } else {
                        alert('保存中にエラーが発生しました。');
                    }
                })
                .catch(function (error) {
                    console.error('通信エラー:', error);
                    alert('通信エラーが発生しました。');
                });
            });

            // 削除処理
            deleteEventBtn.addEventListener('click', function () {
                var eventId = document.getElementById('eventId').value;
                if (!eventId) {
                    alert('イベントIDが取得できません。削除を中止します。');
                    return;
                }

                if (confirm('このイベントを削除しますか？')) {
                    // フォームを作成し、POSTリクエストで送信
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = 'EventDelete.action'; // 削除用アクションURLを設定

                    // イベントIDをフォームに追加
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'eventId';
                    input.value = eventId;
                    form.appendChild(input);

                    // フォームをドキュメントに追加して送信
                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });
    </script>
</body>
</html>