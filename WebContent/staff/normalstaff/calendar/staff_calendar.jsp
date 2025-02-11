<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Event" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/staffcommon/base.jsp">
    <c:param name="title">スタッフ用イベント管理カレンダー</c:param>
    <c:param name="content">

    <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar/locales/ja.js"></script>

    <h1 class="h3 mb-3 fw-normal text-center bg-secondary bg-opacity-10 py-2 px-4">スタッフ用イベント管理カレンダー</h1>
    <div id="calendar" class="calendar-container" style="width: 100%; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);"></div>

    <!-- モーダル -->
    <div id="eventModal" class="modal">
        <div class="modal-content institution-details">
            <span class="close">&times;</span>
            <h2 id="modalTitle" class="institution-name">イベント詳細</h2>
            <form id="eventForm" action="EventCreate.action" method="post">
                <div class="mb-3">
                    <label for="title" class="form-label">タイトル:</label>
                    <input type="text" id="title" name="title" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">説明:</label>
                    <input type="text" id="description" name="description" class="form-control">
                </div>

                <div class="mb-3">
                    <label for="start" class="form-label">開始日時:</label>
                    <input type="datetime-local" id="start" name="start" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="end" class="form-label">終了日時:</label>
                    <input type="datetime-local" id="end" name="end" class="form-control" required>
                </div>

                <!-- 公開設定 -->
                <fieldset class="mb-3">
                    <legend class="form-label">公開設定:</legend>
                    <div>
                        <label>
                            <input type="radio" id="public" name="visibility" value="public"> 全体公開
                        </label>
                    </div>
                    <div>
                        <label>
                            <input type="radio" id="staffOnly" name="visibility" value="staffOnly" checked> スタッフ限定公開
                        </label>
                    </div>
                </fieldset>

                <input type="hidden" id="eventID" name="eventID">

                <div style="display: flex; gap: 10px;">
                    <button type="submit" class="btn btn-edit">保存</button>
                    <button type="button" id="deleteEventBtn" class="btn btn-delete" style="display: none;">削除</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var calendarEl = document.getElementById('calendar');
            var modal = document.getElementById('eventModal');
            var closeModal = document.getElementsByClassName('close')[0];
            var deleteEventBtn = document.getElementById('deleteEventBtn');
            var modalTitle = document.getElementById('modalTitle');
            var saveButton = document.querySelector('#eventForm button[type="submit"]');
            var eventForm = document.getElementById('eventForm');

            var isLoggedIn = <%= (session.getAttribute("staffID") != null) ? "true" : "false" %>;
            console.log("ログイン状態:", isLoggedIn);
            var isAdmin = <%= (session.getAttribute("isAdmin") != null) ? session.getAttribute("isAdmin") : false %>;

            // イベントデータを JavaScript 配列に変換
            var events = [
                <%
                    List<Event> events = (List<Event>) request.getAttribute("events");
                    Integer staffID = (Integer) session.getAttribute("staffID");
                    if (events != null && !events.isEmpty()) {
                        for (int i = 0; i < events.size(); i++) {
                            Event event = events.get(i);
                            String title = event.getTitle();
                            String start = event.getStartTime().toString();
                            String end = event.getEndTime().toString();
                            String description = event.getDescription();
                            int id = event.getEventID();
                            int createdBy = event.getCreatedBy();
                            boolean editable = (staffID != null && staffID == createdBy);
                            boolean isPublic = event.isPublic();
                %>
                {
                    id: <%= id %>,
                    title: "<%= title %>",
                    start: "<%= start %>",
                    end: "<%= end %>",
                    extendedProps: {
                        description: "<%= description %>",
                        editable: <%= editable %>,
                        isPublic: <%= isPublic %>,
                        notify: <%= event.isNotify() ? 1 : 0 %>
                    }
                }<%= (i < events.size() - 1) ? "," : "" %>
                <%
                        }
                    }
                %>
            ];

            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ja',
                timeZone: 'Asia/Tokyo',
                events: events,
                height: 'auto',
                contentHeight: 600,
                aspectRatio: 3,

                dateClick: function (info) {
                    modal.style.display = 'flex';
                    modalTitle.textContent = 'イベントを登録';
                    document.getElementById('title').value = '';
                    document.getElementById('description').value = '';
                    document.getElementById('start').value = info.dateStr + 'T00:00';
                    document.getElementById('end').value = info.dateStr + 'T00:00';
                    document.getElementById('eventID').value = '';
                    saveButton.style.display = 'block';
                    deleteEventBtn.style.display = 'none';
                },

                eventClick: function (info) {
                    modal.style.display = 'flex';
                    modalTitle.textContent = 'イベント詳細';

                    document.getElementById('title').value = info.event.title;
                    document.getElementById('description').value = info.event.extendedProps.description || '';
                    document.getElementById('start').value = info.event.start.toISOString().slice(0, 16);
                    document.getElementById('end').value = info.event.end
                        ? info.event.end.toISOString().slice(0, 16)
                        : info.event.start.toISOString().slice(0, 16);
                    document.getElementById('eventID').value = info.event.id;

                    saveButton.style.display = 'block';
                    deleteEventBtn.style.display = 'block';
                }
            });

            calendar.render();

            // 保存ボタンのクリック時の処理
            document.getElementById('eventForm').addEventListener('submit', function (e) {
                e.preventDefault();
                var eventID = document.getElementById('eventID').value;
                var formAction = eventID ? 'EventUpdate.action' : 'EventCreate.action';
                var form = e.target;
                form.action = formAction;
                form.submit();
            });

            // モーダルを閉じる処理
            closeModal.onclick = function () {
                modal.style.display = 'none';
                deleteEventBtn.style.display = 'none';
            };

            // モーダルの背景をクリックしたときも閉じる
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = 'none';
                    deleteEventBtn.style.display = 'none';
                }
            };

            // 削除処理
            deleteEventBtn.addEventListener('click', function () {
                var eventID = document.getElementById('eventID').value;
                if (!eventID) {
                    alert('イベントIDが取得できません。削除を中止します。');
                    return;
                }

                if (confirm('このイベントを削除しますか？')) {
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

        });
    </script>

    </c:param>
</c:import>
