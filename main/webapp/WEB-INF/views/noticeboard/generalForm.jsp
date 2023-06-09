<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="content">
	<div class="container-fluid">
		<div class="row" style="justify-content: center;">

			<div class="col-md-6">
				<div class="card card-primary">
					<div class="card-header">
						<h3 class="card-title">공지 사항 등록</h3>
					</div>
					<!-- 
						파일 업로드 꼭 필수 요소
						method post
						enctype multipart/form-data
						input type = file
						
						요청 URI  /notice/generalFormPost
						요청 방식  포스트
						요청 파라미터 제목 , 내용 , 작성자 
					 -->
					<form enctype="multipart/form-data" method="post" action="/notice/generalFormPost" id="frm" name="frm">
						<div class="card-body">
							<div class="form-group">
								<label for="boTitle">제목</label> 
								<input type="text" class="form-control" id="boTitle" name="boTitle"
									placeholder="제목을 입력해주세요." required>
							</div>
							<div class="form-group">
								<label for="boContent">내용</label> 
								<textarea class="form-control" rows="3" cols="5" placeholder="내용을 입력해 주세요" name="boContent"></textarea>
							</div>
							<div class="form-group">
								<label for="boWriter">작성자</label> 
								<input type="text" class="form-control" id="boWriter" name="boWriter"
									placeholder="작성자를 입력해주세요" required>
							</div>
							<div class="form-group">
								<label for="boFile">파일 업로드</label>
								<div class="input-group">
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="boFile" name="boFile" multiple> <label
											class="custom-file-label" for="boFile">
											Choose File</label>
									</div>
								</div>
							</div>
						</div>
						<div class="card-footer">
							<button type="submit" class="btn btn-primary">등록</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
	CKEDITOR.replace("boContent");
$(function() {
	
});
</script>






