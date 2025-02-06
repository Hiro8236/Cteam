<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">スタッフ用イベント管理カレンダー</c:param>
    <c:param name="content">
        <link href="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar/locales/ja.js"></script>

        <h1 class="h3 mb-3 fw-normal text-center bg-secondary bg-opacity-10 py-2 px-4">スタッフ用イベント管理カレンダー</h1>
        <div id="calendar" class="calendar-container" style="width: 120%; margin: 0 auto; margin-left: -10%; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); aspect-ratio: 16/9;"></div>

        <!-- モーダル -->
        <div id="eventModal" class="modal">
            <div class="modal-content institution-details">
                <span class="close">&times;</span>
                <h2 id="modalTitle" class="institution-name">イベントを登録</h2>
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

                    <!-- 公開設定ラジオボタン -->
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

                    dateClick: function (info) {
                        modal.style.display = 'flex';
                        document.getElementById('modalTitle').textContent = 'イベントを登録';
                        document.getElementById('title').value = '';
                        document.getElementById('description').value = '';
                        document.getElementById('start').value = info.dateStr + 'T00:00';
                        document.getElementById('end').value = info.dateStr + 'T00:00';
                        document.getElementById('eventID').value = '';
                    }
                });

                calendar.render();

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
</c:import>
