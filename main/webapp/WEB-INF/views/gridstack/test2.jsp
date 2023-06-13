<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/gridstack@3.1.2/dist/gridstack.min.css" rel="stylesheet"/>
<script src="https://cdn.jsdelivr.net/npm/gridstack@3.1.2/dist/gridstack-h5.js"></script>
</head>
<body>
<!-- <script src="https://cdn.jsdelivr.net/npm/gridstack@3.1.2//gridstack-static.js"></script> -->
<style>
/* @import "../dist/gridstack.css"; */

/* Optional styles for demos */
.btn-primary {
  color: #fff;
  background-color: #007bff;
}

.btn {
  display: inline-block;
  padding: .375rem .75rem;
  line-height: 1.5;
  border-radius: .25rem;
}

a {
  text-decoration: none;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: .5rem;
}

.grid-stack {
  background: #FAFAD2;
}

.grid-stack-item-content {
  color: #2c3e50;
  text-align: center;
  background-color: #18bc9c;
}
.grid-stack-item-removing {
  opacity: 0.8;
  filter: blur(5px);
}
#trash {
  background: rgba(255, 0, 0, 0.4);
}
.grid-stack {
  background: #fafad2;
}
.grid-stack-item-content {
  background-color: #18bc9c;
}
</style>
<h1>Advanced Demo</h1>
<div class="row">
  <div class="col-md-2 d-none d-md-block">
    <div id="trash" style="padding: 5px; margin-bottom: 15px;" class="text-center">
      <div>
        <ion-icon name="trash" style="font-size: 300%"></ion-icon>
      </div>
      <div>
        <span>Drop here to remove!</span>
      </div>
    </div>
    <div class="newWidget grid-stack-item">
      <div class="grid-stack-item-content" style="padding: 5px;">
        <div>
          <ion-icon name="add-circle" style="font-size: 300%"></ion-icon>
        </div>
        <div>
          <span>Drag me in the dashboard!</span>
        </div>
      </div>
    </div>
  </div>
  <div class="col-sm-12 col-md-10">
    <div class="grid-stack"></div>
  </div>
</div>
</body>
<script type="text/javascript">
let grid = GridStack.init({
	  alwaysShowResizeHandle: /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
	    navigator.userAgent
	  ),
	  resizable: {
	    handles: "e, se, s, sw, w"
	  },
	  acceptWidgets: true,
	  dragIn: ".newWidget", // class that can be dragged from outside
	  dragInOptions: {
	    revert: "invalid",
	    scroll: false,
	    appendTo: "body",
	    helper: "clone"
	  },
	  removable: "#trash", // drag-out delete class
	  removeTimeout: 100
	});

	let items = [
	  { x: 0, y: 0, w: 4, h: 2, content: "1" },
	  {
	    x: 4,
	    y: 0,
	    w: 4,
	    h: 4,
	    noMove: true,
	    noResize: true,
	    locked: true,
	    content:
	      'I can\'t be moved or dragged!<br><ion-icon name="ios-lock" style="font-size:300%"></ion-icon>'
	  },
	  {
	    x: 8,
	    y: 0,
	    w: 2,
	    h: 2,
	    minW: 2,
	    noResize: true,
	    content:
	      '<p class="card-text text-center" style="margin-bottom: 0">Drag me!<p class="card-text text-center"style="margin-bottom: 0"><ion-icon name="hand" style="font-size: 300%"></ion-icon><p class="card-text text-center" style="margin-bottom: 0">...but don\'t resize me!'
	  },
	  { x: 10, y: 0, w: 2, h: 2, content: "4" },
	  { x: 0, y: 2, w: 2, h: 2, content: "5" },
	  { x: 2, y: 2, w: 2, h: 4, content: "6" },
	  { x: 8, y: 2, w: 4, h: 2, content: "7" },
	  { x: 0, y: 4, w: 2, h: 2, content: "8" },
	  { x: 4, y: 4, w: 4, h: 2, content: "9" },
	  { x: 8, y: 4, w: 2, h: 2, content: "10" },
	  { x: 10, y: 4, w: 2, h: 2, content: "11" }
	];
	grid.load(items);

	grid.on("added removed change", function (e, items) {
	  let str = "";
	  items.forEach(function (item) {
	    str += " (x,y)=" + item.x + "," + item.y;
	  });
	  console.log(e.type + " " + items.length + " items:" + str);
	});
</script>
</html>