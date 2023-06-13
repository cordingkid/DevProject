<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>GridStack Example</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/gridstack@8.2.1/dist/gridstack.min.css" rel="stylesheet"/>
</head>
<style>
.grid-stack {
  background: lightgoldenrodyellow;
}

.grid-stack-item-content {
  color: #2c3e50;
  text-align: center;
  background-color: #18bc9c;
}

</style>
<body>
	<h1>그리드 스택 JS Test</h1>
	
	
	<p>Fork and modify me to demonstrate your issue when creating an issue for gridstack.js</p>
	<div><a class="btn btn-default" onClick="addNewWidget()" href="#">Add Widget</a></div>
	<br/>

<div class="grid-stack"></div>
	
</body>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/gridstack@8.2.1/dist/gridstack-all.js"></script>
<script type="text/javascript">
var options = { // 여기에 그리드 스택 옵션 삽입
	disableOneColumnMode: true, // jfidle 작은 창 크기용
	float: false
};
var grid = GridStack.init(options);

var count = 0;
var items = [
	{x: 0, y: 0, w: 2, h: 2},
	{x: 2, y: 0, w: 2},
	{x: 3, y: 1, h: 2},
	{x: 0, y: 2, w: 2}
];

addNewWidget = function () {
	var node = items[count] || {
		x: Math.round(12 * Math.random()),
		y: Math.round(5 * Math.random()),
		w: Math.round(1 + 3 * Math.random()),
		h: Math.round(1 + 3 * Math.random())
	};
	node.content = String(count++);
	grid.addWidget(node);
	return false;
};

addNewWidget();
addNewWidget();
</script>
</html>
