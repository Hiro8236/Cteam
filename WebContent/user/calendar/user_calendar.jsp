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
        <div id="calendar" class="calendar-container" style="width: 120%; margin: 0 auto; margin-left: -10%; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); aspect-ratio: 16/9;"></div>

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

                    %>
                    {
                        id: <%= id %>,
                        title: "<%= title %>",
                        start: "<%= start %>",
                        end: "<%= end %>",
                        extendedProps: {
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
                    initialView: 'dayGridMonth', // 初期表示のビュー
                    locale: 'ja',               // ロケール設定（日本語）
                    timeZone: 'Asia/Tokyo',     // タイムゾーン
                    events: events,             // イベントデータ
                    height: 'auto',             // 高さを自動調整
                    contentHeight: 600,         // カレンダー部分の高さを固定
                    aspectRatio: 3,             // 幅:高さの比率を指定（デフォルトは1.35）

                    // 日付クリック時の処理
                    dateClick: function (info) {
                        modal.style.display = 'block';
                        modalTitle.textContent = 'イベントを登録';
                        document.getElementById('title').value = '';
                        document.getElementById('description').value = '';
                        document.getElementById('start').value = info.dateStr + 'T00:00';
                        document.getElementById('end').value = info.dateStr + 'T00:00';
                        document.getElementById('eventID').value = '';
                        deleteEventBtn.style.display = 'none';
                    },

                    // イベントクリック時の処理
                    eventClick: function (info) {
                        modal.style.display = 'block'; // モーダルを表示
                        modalTitle.textContent = 'イベント詳細';

                        // データを設定
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

                // 保存ボタンのクリック時の処理
                document.getElementById('eventForm').addEventListener('submit', function (e) {
                    e.preventDefault(); // フォームのデフォルト動作を防止
                    var eventID = document.getElementById('eventID').value;
                    var formAction = eventID ? 'EventUpdate.action' : 'EventCreate.action'; // 更新か新規作成かを判定

                    // フォームの action 属性を設定して送信
                    var form = e.target;
                    form.action = formAction; // 適切なアクションを設定
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
