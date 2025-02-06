<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    var deleteEventBtn = document.getElementById('deleteEventBtn');

    // モーダルを最初に非表示
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

    // FullCalendar初期化
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ja',
        timeZone: 'Asia/Tokyo',
        height: 'auto',
        contentHeight: 'auto',
        aspectRatio: 1.5,
        selectable: true,
        events: events, // イベントをここで渡す

        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },

        dateClick: function (info) {
            // 新規イベント登録
            modal.style.display = 'flex';
            document.getElementById('modalTitle').textContent = 'イベントを登録';
            document.getElementById('title').value = '';
            document.getElementById('description').value = '';
            document.getElementById('start').value = info.dateStr + 'T00:00';
            document.getElementById('end').value = info.dateStr + 'T00:00';
            document.getElementById('eventID').value = '';
            deleteEventBtn.style.display = 'none';
        },

        eventClick: function (info) {
            // 既存イベントの編集
            modal.style.display = 'flex';
            document.getElementById('modalTitle').textContent = 'イベントを編集';
            document.getElementById('title').value = info.event.title;
            document.getElementById('description').value = info.event.extendedProps.description || '';
            document.getElementById('start').value = info.event.start.toISOString().slice(0, 16);
            document.getElementById('end').value = info.event.end
                ? info.event.end.toISOString().slice(0, 16)
                : info.event.start.toISOString().slice(0, 16);
            document.getElementById('eventID').value = info.event.id;

            deleteEventBtn.style.display = 'block';
        }
    });

    calendar.render();

    // モーダルを閉じる処理
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
        if (confirm('このイベントを削除しますか？')) {
            var eventID = document.getElementById('eventID').value;
            if (!eventID) {
                alert('イベントIDが取得できません。削除を中止します。');
                return;
            }

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
