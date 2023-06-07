<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>PDF Viewer</title>
<script src="https://mozilla.github.io/pdf.js/build/pdf.js"></script>
<script>
var myPath = "${pageContext.request.contextPath}";

// PDF 문서를 로드하고 표시하는 함수
function loadPdf() {
	var url = myPath + '/resources/pdf/certificate.pdf'; // 표시할 PDF 문서의 경로
	pdfjsLib.getDocument(url).promise.then(function(pdf) {
		// 첫 번째 페이지를 가져와서 표시
		pdf.getPage(1).then(function(page) {
			var scale = 1.5;
			var viewport = page.getViewport({
				scale : scale
			});
			var canvas = document.getElementById('pdfCanvas');
			var context = canvas.getContext('2d');
			canvas.height = viewport.height;
			canvas.width = viewport.width;
			var renderContext = {
				canvasContext : context,
				viewport : viewport
			};
			page.render(renderContext);
		});
	});
}
</script>
</head>
<body onload="loadPdf()">
	<canvas id="pdfCanvas"></canvas>
</body>
</html>