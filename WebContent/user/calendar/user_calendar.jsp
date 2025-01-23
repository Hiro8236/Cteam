<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="bean.Event" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">ユーザー用イベントカレンダー</c:param>
    <c:param name="scripts">
        <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar/locales/ja.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var calendarEl = document.getElementById('calendar');
                var modal = document.getElementById('eventModal');
                var closeModal = document.getElementsByClassName('close')[0];
                var eventForm = document.getElementById('eventForm');
                var eventDetails = document.getElementById('eventDetails');
                var deleteEventBtn = document.getElementById('deleteEventBtn');

                var isLoggedIn = <%= (session.getAttribute("userID") != null) %>;

                // イベントデータを JavaScript 配列に変換
                var events = [
                    <%
                        List<Event> events = (List<Event>) request.getAttribute("events");
                        Integer userID = (Integer) session.getAttribute("userID");
                        if (events != null && !events.isEmpty()) {
                            for (int i = 0; i < events.size(); i++) {
                                Event event = events.get(i);
                                String title = event.getTitle();
                                String start = event.getStartTime().toString();
                                String end = event.getEndTime().toString();
                                String description = event.getDescription();
                                int id = event.getEventID();
                                int createdBy = event.getCreatedBy();
                                boolean editable = (userID != null && userID == createdBy);

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
                    dateClick: function (info) {
                        if (isLoggedIn) {
                            modal.style.display = 'block';
                            document.getElementById('modalTitle').textContent = 'イベントを登録';
                            eventForm.style.display = 'block';
                            eventDetails.style.display = 'none';

                            document.getElementById('title').value = '';
                            document.getElementById('description').value = '';
                            document.getElementById('start').value = info.dateStr + 'T00:00';
                            document.getElementById('end').value = info.dateStr + 'T00:00';
                            document.getElementById('eventID').value = '';
                            eventForm.action = 'EventCreate.action';
                        } else {
                            alert("ログインしてください。");
                        }
                    },
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
                            document.getElementById('eventID').value = info.event.id;
                            eventForm.action = 'EventUpdate.action';
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

             // 削除ボタンのイベントリスナー
                deleteEventBtn.addEventListener('click', function () {
                    var eventID = document.getElementById('eventID').value;
                    if (!eventID) {
                        alert('イベントIDが取得できません。削除を中止します。');
                        return;
                    }

                    if (confirm('このイベントを削除しますか？')) {
                        // フォームを作成してPOSTリクエストを送信
                        var form = document.createElement('form');
                        form.method = 'post';
                        form.action = 'EventDelete.action';

                        var input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = 'eventID';
                        input.value = eventID;
                        form.appendChild(input);

                        document.body.appendChild(form);
                        form.submit();
                    }
                });

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
    </c:param>
    <c:param name="content">
        <h1 style="text-align: center;">イベントカレンダー</h1>
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

                    <input type="hidden" id="eventID" name="eventID">
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
    </c:param>
</c:import>
