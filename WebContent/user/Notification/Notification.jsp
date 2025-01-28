<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>通知管理</title>
</head>
<body>
    <h1>通知一覧</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>タイトル</th>
            <th>内容</th>
            <th>操作</th>
        </tr>
        <c:forEach var="notification" items="${notifications}">
     * 全ての通知を取得します。
     * @return 通知のリスト
     * @throws Exception
     */
    public List<Notification> getAllNotifications() throws Exception {
        return notificationDAO.getAll();
    }

    /**
     * 通知を新規作成します。
     * @param title 通知のタイトル
     * @param sentence 通知の内容
     * @return 成功時はtrue、失敗時はfalse
     * @throws Exception
     */
    public boolean createNotification(String title, String sentence) throws Exception {
        return notificationDAO.saveNotification(title, sentence);
    }

    /**
     * 指定されたIDの通知を削除します。
     * @param id 通知ID
     * @return 成功時はtrue、失敗時はfalse
     * @throws Exception
     */
    public boolean deleteNotification(int id) throws Exception {
        return notificationDAO.deleteNotification(id);
    }
}