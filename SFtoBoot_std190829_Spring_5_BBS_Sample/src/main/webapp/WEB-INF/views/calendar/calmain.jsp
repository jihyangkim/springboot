<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href='fullcalendar/core/main.css' rel='stylesheet' />
<link href='fullcalendar/daygrid/main.css' rel='stylesheet' />
<link href='fullcalendar/timegrid/main.min.css' rel='stylesheet' />
<script src='fullcalendar/core/main.js'></script>
<script src='fullcalendar/daygrid/main.js'></script>
<script src="fullcalendar/interaction/main.min.js"></script>
<script src="fullcalendar/timegrid/main.min.js"></script>
<script src='fullcalendar/core/locales/ko.js'></script>

<!-- 캘린더 초기 설정 -->
<script>

document.addEventListener('DOMContentLoaded', function() {
  var Calendar = FullCalendar.Calendar;
  var Draggable = FullCalendarInteraction.Draggable;

  var containerEl = document.getElementById('external-events');
  var calendarEl = document.getElementById('calendar');
  var checkbox = document.getElementById('drop-remove');

  // initialize the external events
  // -----------------------------------------------------------------

  new Draggable(containerEl, {
    itemSelector: '.fc-event',
    eventData: function(eventEl) {
      return {
        title: eventEl.innerText
      };
    }
  });

  // initialize the calendar
  // -----------------------------------------------------------------

  var calendar = new Calendar(calendarEl, {
    plugins: [ 'interaction', 'dayGrid', 'timeGrid' ],
    header: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
    },
    editable: true,
    droppable: true, // this allows things to be dropped onto the calendar
    drop: function(info) {
      // is the "remove after drop" checkbox checked?
      if (checkbox.checked) {
        // if so, remove the element from the "Draggable Events" list
        info.draggedEl.parentNode.removeChild(info.draggedEl);
      }
    },
    locale: 'ko',
    events: [
  	  {
  		  title : 'evt1',
  		  start : '2019-09-03'
  	  },
  	  {
  		  title	:	'evt2',
  		  start	:	'2019-09-10',
  		  end	:	'2019-09-20'
  	  },
  	  {
  		  title	:	'evt3',
  		  start	:	'2019-09-25T12:30:00',
  		  allDay	:	false
  	  }
    ]
  });

  calendar.render();
  
  var arrTest = getCalendarDataInDB();
  $.each(arrTest, function(index, item){
		alert(index + ' : ' + item);
  });
  //alert( '캘린더에서 알린다!!! 잘 받았다! ' + (arrTest.0.id) );
});

function getCalendarDataInDB(){
	var arr = [{title: 'evt1', start:'ssssss'}, {title: 'evt2', start:'123123123'}];
	
	//배열 초기화
	var viewData = {};
	//data[키] = 밸류
	viewData["id"] = $("#currId").text().trim();
	viewData["title"] = $("#title").val();
	viewData["content"] = $("#content").val();
	
	$.ajax({
		contentType:'application/json',
		dataType:'json',
		url:'calendar/getall',
		type:'post',
		data:JSON.stringify(viewData),
		success:function(resp){
			//alert(resp.f.id + ' ggg'); 	
			$.each(resp, function(index, item){
				alert(index + ' : ' + item);
			});
		},
		error:function(){
			alert('저장 중 에러가 발생했습니다. 다시 시도해 주세요.');
		}
	});
	
	return arr;
}

</script>
<!-- 캘린더 한글 설정 -->
<script src="js/calendar/calmain.js"></script>
<style>

  html, body {
    margin: 0;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #external-events {
    position: fixed;
    z-index: 2;
    top: 300px;
    left: 140px;
    width: 150px;
    padding: 0 10px;
    border: 1px solid #ccc;
    background: #eee;
  }

  .demo-topbar + #external-events { /* will get stripped out */
    top: 60px;
  }

  #external-events .fc-event {
    margin: 1em 0;
    cursor: move;
  }

  #calendar-container {
    position: relative;
    z-index: 1;
    margin-left: 200px;
  }

  #calendar {
    max-width: 900px;
    margin: 20px auto;
  }

</style>

</head>
<body>

<div id="external-events">
    <p>
      <strong>Draggable Events</strong>
    </p>
    <div class="fc-event">My Event 1</div>
    <div class="fc-event">My Event 2</div>
    <div class="fc-event">My Event 3</div>
    <div class="fc-event">My Event 4</div>
    <div class="fc-event">My Event 5</div>
    <p>
      <input type="checkbox" id="drop-remove">
      <label for="drop-remove">remove after drop</label>
    </p>
  </div>
<div id='calendar'></div>

</body>
</html>