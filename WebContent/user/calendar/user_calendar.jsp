<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Event" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ユーザー用イベントカレンダー</title>
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
    <h1 style="text-align: center;">ユーザー用イベントカレンダー</h1>
    <div id="calendar"></div>

    <!-- モーダル -->
    <div id="eventModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2 id="modalTitle">イベント詳細</h2>
            <form id="eventForm" action="EventUpdate.action" method="post" style="display: none;">
                <label for="title">タイトル:</label>
                <input type="text" id="title" name="title" required><br><br>

                <label for="description">説明:</label>
                <input type="text" id="description" name="description"><br><br>

                <label for="start">開始日時:</label>
                <input type="datetime-local" id="start" name="start" required><br><br>

                <label for="end">終了日時:</label>
                <input type="datetime-local" id="end" name="end" required><br><br>

                <input type="hidden" id="eventId" name="eventId">
                <button type="submit">保存</button>
                <button type="button" id="deleteEventBtn" style="display:none;">削除</button>
            </form>
            <div id="eventDetails" style="display: none;">
                <p><strong>タイトル:</strong> <span id="detailTitle"></span></p>
                <p><strong>説明:</strong> <span id="detailDescription"></span></p>
                <p><strong>開始日時:</strong> <span id="detailStart"></span></p>
                <p><strong>終了日時:</strong> <span id="detailEnd"></span></p>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var calendarEl = document.getElementById('calendar');
            var modal = document.getElementById('eventModal');
            var closeModal = document.getElementsByClassName('close')[0];
            var eventForm = document.getElementById('eventForm');
            var eventDetails = document.getElementById('eventDetails');
            var deleteEventBtn = document.getElementById('deleteEventBtn');

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
                            String description = event.getDescription();
                            int id = event.getEventId();
                            int createdBy = event.getCreatedBy();
                            boolean editable = (Integer) session.getAttribute("userId") == createdBy;

                %>
                {
                    id: <%= id %>,
                    title: "<%= title %>",
                    start: "<%= start %>",
                    end: "<%= end %>",
                    extendedProps: {
                        description: "<%= description %>",
                        editable: <%= editable %>
                    }
                }<%= (i < events.size() - 1) ? "," : "" %>
                <%
                        }
                    }
                %>
            ];

            // カレンダー初期化
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ja',
                timeZone: 'Asia/Tokyo',
                events: events,
                eventClick: function (info) {
                    modal.style.display = 'block';
                    document.getElementById('modalTitle').textContent = 'イベント詳細';

                    if (info.event.extendedProps.editable) {
                        eventForm.style.display = 'block';
                        eventDetails.style.display = 'none';

                        document.getElementById('title').value = info.event.title;
                        document.getElementById('description').value = info.event.extendedProps.description || '';
                        document.getElementById('start').value = info.event.start.toISOString().slice(0, 16);
                        document.getElementById('end').value = info.event.end
                            ? info.event.end.toISOString().slice(0, 16)
                            : info.event.start.toISOString().slice(0, 16);
                        document.getElementById('eventId').value = info.event.id;
                        deleteEventBtn.style.display = 'inline-block';
                    } else {
                        eventForm.style.display = 'none';
                        eventDetails.style.display = 'block';

                        document.getElementById('detailTitle').textContent = info.event.title;
                        document.getElementById('detailDescription').textContent = info.event.extendedProps.description || '';
                        document.getElementById('detailStart').textContent = info.event.start.toISOString().slice(0, 16);
                        document.getElementById('detailEnd').textContent = info.event.end
                            ? info.event.end.toISOString().slice(0, 16)
                            : info.event.start.toISOString().slice(0, 16);
                    }
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

            // 削除処理
            deleteEventBtn.addEventListener('click', function () {
                var eventId = document.getElementById('eventId').value;
                if (!eventId) {
                    alert('イベントIDが取得できません。削除を中止します。');
                    return;
                }
                if (confirm('このイベントを削除しますか？')) {
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = 'EventDelete.action';
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'eventId';
                    input.value = eventId;
                    form.appendChild(input);
                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });
    </script>
</body>
</html>
