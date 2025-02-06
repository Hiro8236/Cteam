<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Event" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">スタッフ用イベント管理カレンダー</c:param>
    <c:param name="content">

        <!-- FullCalendar のスタイルとスクリプト -->
        <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar/locales/ja.js"></script>

        <h1 class="calendar-title">スタッフ用イベント管理カレンダー</h1>

        <!-- カレンダー表示部分 -->
        <div id="calendar" class="calendar-container"></div>

        <!-- モーダル（イベント登録・編集） -->
        <div id="eventModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2 id="modalTitle">イベントを登録</h2>
                <form id="eventForm" action="EventCreate.action" method="post" accept-charset="UTF-8">
                    <div>
                        <label for="title">タイトル:</label>
                        <input type="text" id="title" name="title" required>
                    </div>
                    <div>
                        <label for="description">説明:</label>
                        <input type="text" id="description" name="description">
                    </div>
                    <div>
                        <label for="start">開始日時:</label>
                        <input type="datetime-local" id="start" name="start" required>
                    </div>
                    <div>
                        <label for="end">終了日時:</label>
                        <input type="datetime-local" id="end" name="end" required>
                    </div>
                    <input type="hidden" id="eventID" name="eventID">
                    <button type="submit">保存</button>
                    <button type="button" id="deleteEventBtn" style="display: none;">削除</button>
                </form>
            </div>
        </div>

        <script>
        document.addEventListener('DOMContentLoaded', function () {
            var calendarEl = document.getElementById('calendar');
            var modal = document.getElementById('eventModal');
            var closeModal = document.getElementsByClassName('close')[0];

            // 初期状態でモーダルを非表示にする
            modal.style.display = 'none';

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
                            int id = event.getEventID();
                            boolean isPublic = event.isPublic();
                            boolean isStaffOnly = event.isStaffOnly();
                %>
                {
                    id: <%= id %>,
                    title: "<%= title %>",
                    start: "<%= start %>",
                    end: "<%= end %>",
                    extendedProps: {
                        description: "<%= description %>",
                        isPublic: <%= isPublic %>,
                        isStaffOnly: <%= isStaffOnly %>
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
                height: 'auto',
                contentHeight: 'auto',
                aspectRatio: 1.5,
                selectable: true,

                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay'
                },

                // クリック時にモーダルを表示
                dateClick: function (info) {
                    modal.style.display = 'flex';
                    document.getElementById('modalTitle').textContent = 'イベントを登録';
                    document.getElementById('title').value = '';
                    document.getElementById('description').value = '';
                    document.getElementById('start').value = info.dateStr + 'T00:00';
                    document.getElementById('end').value = info.dateStr + 'T00:00';
                    document.getElementById('eventID').value = '';
                },

                // 既存イベントクリック時にモーダルを表示
                eventClick: function (info) {
                    modal.style.display = 'flex'; // モーダルを表示
                    document.getElementById('modalTitle').textContent = 'イベントを編集';
                    document.getElementById('title').value = info.event.title;
                    document.getElementById('description').value = info.event.extendedProps.description || '';
                    document.getElementById('start').value = info.event.start.toISOString().slice(0, 16);
                    document.getElementById('end').value = info.event.end
                        ? info.event.end.toISOString().slice(0, 16)
                        : info.event.start.toISOString().slice(0, 16);
                    document.getElementById('eventID').value = info.event.id;
                }
            });

            // カレンダーを表示
            calendar.render();

            // モーダルを閉じる
            closeModal.onclick = function () {
                modal.style.display = 'none';
            };

            // モーダル外クリックで閉じる
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = 'none';
                }
            };
        });
        </script>

        <style>
        /* カレンダーのデザイン */
        .calendar-container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .calendar-title {
            text-align: center;
            font-size: 24px;
            margin-bottom: 20px;
        }

        /* モーダルのスタイル */
        .modal {
            display: none;  /* 🔹 初期状態で非表示 */
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.4);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            width: 400px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
            position: relative;
        }

        .close {
            float: right;
            font-size: 24px;
            cursor: pointer;
        }
        </style>

    </c:param>
</c:import>
