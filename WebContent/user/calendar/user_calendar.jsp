<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Event" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">ユーザー用イベントカレンダー</c:param>
    <c:param name="content">

    <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar/locales/ja.js"></script>

    <h1 class="h3 mb-3 fw-normal text-center bg-secondary bg-opacity-10 py-2 px-4">ユーザー用イベントカレンダー</h1>
    <div id="calendar" class="calendar-container" style="width: 120%; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); aspect-ratio: 16/9;"></div>

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

                <!-- 追加: 通知設定のチェックボックス -->
                <input type="hidden" id="eventID" name="eventID">
                <div class="mb-3">
                    <div id="notification-container">
                        <input type="checkbox" id="notification" name="notification">
                        <label for="notification" class="form-label">イベント前日に通知を受け取る</label>
                    </div>
                </div>
                <input type="hidden" id="eventID" name="eventID">

                <div style="display: flex; gap: 10px;">
                    <button type="submit" class="btn btn-primary">保存</button>
                    <button type="button" id="deleteEventBtn" class="btn btn-danger" style="display: none;">削除</button>
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
        var isLoggedIn = <%= (session.getAttribute("userID") != null) %>; //ログイン状態の確認

        // イベントデータを JavaScript 配列に変換
        var events = [
            <%
                List<Event> events = (List<Event>) request.getAttribute("event");
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

        // カレンダー初期化
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            locale: 'ja',
            timeZone: 'Asia/Tokyo',
            events: events,
            height: 'auto',
            contentHeight: 600,
            aspectRatio: 3,

            // 日付クリック時の処理
            dateClick: function (info) {
                if (!isLoggedIn) {
                    alert("ログインしてください。");
                    return;
                }
                modal.style.display = 'flex';
                modalTitle.textContent = 'イベントを登録';
                document.getElementById('title').value = '';
                document.getElementById('description').value = '';
                document.getElementById('start').value = info.dateStr + 'T00:00';
                document.getElementById('end').value = info.dateStr + 'T00:00';
                document.getElementById('eventID').value = '';

                // 入力可能にする
                document.getElementById('title').disabled = false;
                document.getElementById('description').disabled = false;
                document.getElementById('start').disabled = false;
                document.getElementById('end').disabled = false;

                saveButton.style.display = 'block';
                deleteEventBtn.style.display = 'none';
                eventForm.style.display = 'block';
                document.getElementById('notification-container').style.display = 'block';
            },

            // イベントクリック時の処理
            eventClick: function (info) {
                modal.style.display = 'flex'; // モーダルを表示
                modalTitle.textContent = 'イベント詳細';

                // データを設定
                document.getElementById('title').value = info.event.title;
                document.getElementById('description').value = info.event.extendedProps.description || '';
                document.getElementById('start').value = info.event.start.toISOString().slice(0, 16);
                document.getElementById('end').value = info.event.end
                    ? info.event.end.toISOString().slice(0, 16)
                    : info.event.start.toISOString().slice(0, 16);
                document.getElementById('eventID').value = info.event.id;

                // 通知チェックボックスを設定
                document.getElementById('notification').checked = info.event.extendedProps.notify == 1;

                var isPublic = info.event.extendedProps.isPublic;
                var isEditable = info.event.extendedProps.editable;

                // 全体公開イベントなら編集不可
                if (isPublic) {
                    saveButton.style.display = 'none';
                    deleteEventBtn.style.display = 'none';
                    document.getElementById('notification-container').style.display = 'none';
                    // フィールドを disabled に
                    document.getElementById('title').disabled = true;
                    document.getElementById('description').disabled = true;
                    document.getElementById('start').disabled = true;
                    document.getElementById('end').disabled = true;
                }
                // 自分のイベントでない場合（編集不可）
                else if (!isEditable) {
                    eventForm.style.display = 'none';
                    deleteEventBtn.style.display = 'none';
                    document.getElementById('notification-container').style.display = 'none';
                }
                // 編集可能
                else {
                    eventForm.style.display = 'block';
                    saveButton.style.display = 'block';
                    deleteEventBtn.style.display = 'block';
                    document.getElementById('notification-container').style.display = 'block';
                    // フィールドを有効に
                    document.getElementById('title').disabled = false;
                    document.getElementById('description').disabled = false;
                    document.getElementById('start').disabled = false;
                    document.getElementById('end').disabled = false;
                    document.getElementById('notification').checked = info.event.extendedProps.notify == 1;
                }
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

        // モーダルを閉じる
        closeModal.onclick = function () {
            modal.style.display = 'none';
            deleteEventBtn.style.display = 'none';
        };
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
