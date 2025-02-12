<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/common/base.jsp">
    <jsp:param name="title" value="推奨情報登録"/>
</jsp:include>

<div id="wrap_box">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">推奨情報を登録</h2>

    <form action="UserSuggestInfoExecute.action" method="post">
        <div class="form-floating">
            <input class="form-control" name="annualIncome" type="number" />
            <label>年間収入</label>
        </div>

        <div class="form-floating">
            <input class="form-control" name="childrenCount" type="number" />
            <label>子供の人数</label>
        </div>

  		<div class="form-floating">
    		<select class="form-select" name="employmentStatus">
        		<option value="" selected>選択してください</option>
        		<option value="就業中">就業中</option>
        		<option value="休職中">休職中</option>
        		<option value="求職中">求職中</option>
    		</select>
    		<label>雇用状況</label>
		</div>


        <div class="form-floating">
            <input class="form-control" name="singleParentReason" type="text" />
            <label>ひとり親の理由</label>
        </div>

        <div class="form-floating">
            <input class="form-control" name="childSchoolStatus" type="text" />
            <label>子供の学習状況</label>
        </div>

        <input type="submit" value="保存" class="btn btn-primary mt-3" />
    </form>
</div>

