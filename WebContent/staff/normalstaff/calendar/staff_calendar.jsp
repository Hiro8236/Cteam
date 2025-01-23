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

        <h1 style="text-align: center;">スタッフ用イベント管理カレンダー</h1>
        <div id="calendar"></div>

        <!-- モーダル -->
        <div id="eventModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2 id="modalTitle">イベントを登録</h2>
                <form id="eventForm" action="EventCreate.action" method="post">
                    <label for="title">タイトル:</label>
                    <input type="text" id="title" name="title" required><br><br>

                    <label for="description">説明:</label>
                    <input type="text" id="description" name="description"><br><br>

                    <label for="start">開始日時:</label>
                    <input type="datetime-local" id="start" name="start" required><br><br>

                    <label for="end">終了日時:</label>
                    <input type="datetime-local" id="end" name="end" required><br><br>


                    <!-- 公開設定ラジオボタンの追加 -->

                    <label>公開設定:</label><br>
                    <label>
                        <input type="radio" id="public" name="visibility" value="public"> 全体公開
                    </label><br>
                    <label>
                        <input type="radio" id="staffOnly" name="visibility" value="staffOnly" checked> スタッフ限定公開
                    </label><br><br>

                    <input type="hidden" id="eventID" name="eventID">
                        <div style="display: flex; gap: 10px;">
                            <button type="submit">保存</button>
                            <button type="button" id="deleteEventBtn" style="display: none;">削除</button>
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
                    events: events,
                    dateClick: function (info) {
                        modal.style.display = 'block';
                        modalTitle.textContent = 'イベントを登録';
                        document.getElementById('title').value = '';
                        document.getElementById('description').value = '';
                        document.getElementById('start').value = info.dateStr + 'T00:00';
                        document.getElementById('end').value = info.dateStr + 'T00:00';
                        document.getElementById('eventID').value = '';
                        document.getElementById('isPublic').checked = false;
                        document.getElementById('isStaffOnly').checked = true;
                        deleteEventBtn.style.display = 'none';
                    },
                    eventClick: function (info) {
                        modal.style.display = 'block'; // モーダルを表示
                        modalTitle.textContent = 'イベントを編集';

                        // データを設定
                        document.getElementById('title').value = info.event.title;
                        document.getElementById('description').value = info.event.extendedProps.description || '';
                        document.getElementById('start').value = info.event.start.toISOString().slice(0, 16);
                        document.getElementById('end').value = info.event.end
                            ? info.event.end.toISOString().slice(0, 16)
                            : info.event.start.toISOString().slice(0, 16);
                        document.getElementById('eventID').value = info.event.id;

                        // デバッグログ
                        console.log("extendedProps:", info.event.extendedProps);

                        // ラジオボタンの状態を設定
                        var isPublicRadio = document.getElementById('public');
                        var isStaffOnlyRadio = document.getElementById('staffOnly');

                        if (isPublicRadio) {
                            isPublicRadio.checked = info.event.extendedProps.isPublic === true;
                        } else {
                            console.error("要素 'public' が見つかりませんでした");
                        }

                        if (isStaffOnlyRadio) {
                            isStaffOnlyRadio.checked = info.event.extendedProps.isStaffOnly === true;
                        } else {
                            console.error("要素 'staffOnly' が見つかりませんでした");
                        }

                        // 削除ボタンの表示
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
