<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
  href="../../css/frontierCalendar/jquery-frontier-cal-1.3.2.css" />
<link rel="stylesheet" type="text/css"
  href="../../css/colorpicker/colorpicker.css" />
<link rel="stylesheet" type="text/css"
  href="../../css/redmond/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css"
  href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />

<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
  src="../../js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="../../js/colorpicker/colorpicker.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/lib/jshashtable-2.1.js"></script>
<script type="text/javascript"
  src="../../js/frontierCalendar/jquery-frontier-cal-1.3.2.min.js"></script>
<script type="text/javascript"
  src="../../js/jquery-qtip-1.0.0-rc3140944/jquery.qtip-1.0.js"></script>

<script type="text/javascript">
$(document).ready(function(){ 

  var clickDate = "";
  var clickAgendaItem = "";
  
  /**
   * Initializes calendar with current year & month
   * specifies the callbacks for day click & agenda item click events
   * then returns instance of plugin object
   */
  var jfcalplugin = $("#mycal").jFrontierCal({
    date: new Date(),
    dayClickCallback: myDayClickHandler,
    agendaClickCallback: myAgendaClickHandler,
    agendaDropCallback: myAgendaDropHandler,
    agendaMouseoverCallback: myAgendaMouseoverHandler,
    applyAgendaTooltipCallback: myApplyTooltip,
    agendaDragStartCallback : myAgendaDragStart,
    agendaDragStopCallback : myAgendaDragStop,
    dragAndDropEnabled: true
  }).data("plugin");
     
  
  /**
   * Do something when dragging starts on agenda div
   */
  function myAgendaDragStart(eventObj,divElm,agendaItem){
    // destroy our qtip tooltip
    if(divElm.data("qtip")){
      divElm.qtip("destroy");
    } 
  };
  
  /**
   * Do something when dragging stops on agenda div
   */
  function myAgendaDragStop(eventObj,divElm,agendaItem){
    //alert("drag stop");
  };
  
  /**
   * Custom tooltip - use any tooltip library you want to display the agenda data.
   * for this example we use qTip - http://craigsworks.com/projects/qtip/
   *
   * @param divElm - jquery object for agenda div element
   * @param agendaItem - javascript object containing agenda data.
   */
  function myApplyTooltip(divElm,agendaItem){

    // Destroy currrent tooltip if present
    if(divElm.data("qtip")){
      divElm.qtip("destroy");
    }
    
    var displayData = "";
    
    var title = agendaItem.title;
    var startDate = agendaItem.startDate;
    var endDate = agendaItem.endDate;
    var allDay = agendaItem.allDay;
    var data = agendaItem.data;
    displayData += "<br><b>" + title+ "</b><br><br>";
    if(allDay){
      displayData += "(All day event)<br><br>";
    }else{
      displayData += "<b><s:text name='calendar.start' />:</b> " + startDate + "<br>" + "<b><s:text name='calendar.end' />:</b> " + endDate + "<br><br>";
    }
    for (var propertyName in data) {
      displayData += "<b>" + propertyName + ":</b> " + data[propertyName] + "<br>"
    }
    // use the user specified colors from the agenda item.
    var backgroundColor = agendaItem.displayProp.backgroundColor;
    var foregroundColor = agendaItem.displayProp.foregroundColor;
    var myStyle = {
      border: {
        width: 5,
        radius: 10
      },
      padding: 10, 
      textAlign: "left",
      tip: true,
      name: "dark" // other style properties are inherited from dark theme    
    };
    if(backgroundColor != null && backgroundColor != ""){
      myStyle["backgroundColor"] = backgroundColor;
    }
    if(foregroundColor != null && foregroundColor != ""){
      myStyle["color"] = foregroundColor;
    }
    // apply tooltip
    divElm.qtip({
      content: displayData,
      position: {
        corner: {
          tooltip: "bottomMiddle",
          target: "topMiddle"     
        },
        adjust: { 
          mouse: true,
          x: 0,
          y: -15
        },
        target: "mouse"
      },
      show: { 
        when: { 
          event: 'mouseover'
        }
      },
      style: myStyle
    });

  };

  /**
   * Make the day cells roughly 3/4th as tall as they are wide. this makes our calendar wider than it is tall. 
   */
  jfcalplugin.setAspectRatio("#mycal",0.75);

  /**
   * Called when user clicks day cell
   * use reference to plugin object to add agenda item
   */
  function myDayClickHandler(eventObj){
  };
  
  /**
   * Called when user clicks and agenda item
   * use reference to plugin object to edit agenda item
   */
  function myAgendaClickHandler(eventObj){
    // Get ID of the agenda item from the event object
    var agendaId = eventObj.data.agendaId;    
    // pull agenda item from calendar
    var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId);
    clickAgendaItem = agendaItem;
    $("#display-event-form").dialog('open');
  };
  
  /**
   * Called when user drops an agenda item into a day cell.
   */
  function myAgendaDropHandler(eventObj){
    // Get ID of the agenda item from the event object
    var agendaId = eventObj.data.agendaId;
    // date agenda item was dropped onto
    var date = eventObj.data.calDayDate;
    // Pull agenda item from calendar
    var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId);    
    alert("You dropped agenda item " + agendaItem.title + 
      " onto " + date.toString() + ". Here is where you can make an AJAX call to update your database.");
  };
  
  /**
   * Called when a user mouses over an agenda item  
   */
  function myAgendaMouseoverHandler(eventObj){
    var agendaId = eventObj.data.agendaId;
    var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId);
  };
  /**
   * Initialize jquery ui datepicker. set date format to yyyy-mm-dd for easy parsing
   */
  $("#dateSelect").datepicker({
    showOtherMonths: true,
    selectOtherMonths: true,
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    dateFormat: 'yy-mm-dd'
  });
  
  /**
   * Set datepicker to current date
   */
  $("#dateSelect").datepicker('setDate', new Date());
  /**
   * Use reference to plugin object to a specific year/month
   */
  $("#dateSelect").bind('change', function() {
    var selectedDate = $("#dateSelect").val();
    var dtArray = selectedDate.split("-");
    var year = dtArray[0];
    // jquery datepicker months start at 1 (1=January)    
    var month = dtArray[1];
    // strip any preceeding 0's   
    month = month.replace(/^[0]+/g,"")    
    var day = dtArray[2];
    // plugin uses 0-based months so we subtrac 1
    jfcalplugin.showMonth("#mycal",year,parseInt(month-1).toString());
  }); 
  /**
   * Initialize previous month button
   */
  $("#BtnPreviousMonth").button();
  $("#BtnPreviousMonth").click(function() {
    jfcalplugin.showPreviousMonth("#mycal");
    // update the jqeury datepicker value
    var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
    var cyear = calDate.getFullYear();
    // Date month 0-based (0=January)
    var cmonth = calDate.getMonth();
    var cday = calDate.getDate();
    // jquery datepicker month starts at 1 (1=January) so we add 1
    $("#dateSelect").datepicker("setDate",cyear+"-"+(cmonth+1)+"-"+cday);
    jfcalplugin.deleteAllAgendaItems("#mycal");
    addAgendaItem(jfcalplugin);
    return false;
  });
  /**
   * Initialize next month button
   */
  $("#BtnNextMonth").button();
  $("#BtnNextMonth").click(function() {
    jfcalplugin.showNextMonth("#mycal");
    // update the jqeury datepicker value
    var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
    var cyear = calDate.getFullYear();
    // Date month 0-based (0=January)
    var cmonth = calDate.getMonth();
    var cday = calDate.getDate();
    // jquery datepicker month starts at 1 (1=January) so we add 1
    $("#dateSelect").datepicker("setDate",cyear+"-"+(cmonth+1)+"-"+cday);   
    jfcalplugin.deleteAllAgendaItems("#mycal");
    addAgendaItem(jfcalplugin);
    return false;
  });
  
  
  /**
   * Initialize display event form.
   */
  $("#display-event-form").dialog({
    autoOpen: false,
    height: 400,
    width: 400,
    modal: true,
    buttons: {    
    	<s:text name="calendar.close" />: function() {
        $(this).dialog('close');
      }
    },
    open: function(event, ui){
      if(clickAgendaItem != null){
        var title = clickAgendaItem.title;
        var startDate = clickAgendaItem.startDate;
        var endDate = clickAgendaItem.endDate;
        var allDay = clickAgendaItem.allDay;
        var data = clickAgendaItem.data;
        // in our example add agenda modal form we put some fake data in the agenda data. we can retrieve it here.
        $("#display-event-form").append(
          "<br><b>" + title+ "</b><br><br>"   
        );        
        if(allDay){
          $("#display-event-form").append(
            "(All day event)<br><br>"       
          );        
        }else{
          $("#display-event-form").append(
            "<b><s:text name="calendar.start" />:</b> " + startDate + "<br>" +
            "<b><s:text name="calendar.end" />:</b> " + endDate + "<br><br>"        
          );        
        }
        for (var propertyName in data) {
          $("#display-event-form").append("<b>" + propertyName + ":</b> " + data[propertyName] + "<br>");
        }     
      }   
    },
    close: function() {
      // clear agenda data
      $("#display-event-form").html("");
    }
  }); 
   
  addAgendaItem(jfcalplugin);
});

function addAgendaItem(jfcalplugin) {
	var params = {
			  dateSelect : $("#dateSelect").val()
			 };  
	$.ajax({
		    type: "POST",
		    url: "getCalendarInfo.action",
		    data: params,
		    dataType:"text", 
		    success: function(json){  
		      var res = $.parseJSON(json);  
				for(var i=0;i<res.length;i++){
					var startDate = new Date();
					startDate.setTime(res[i].startDate);
					var endDate = new Date();
					endDate.setTime(res[i].endDate);
					jfcalplugin.addAgendaItem(
					        "#mycal",
							res[i].subject,
							startDate,
							endDate,
							false,
							{
					        	<s:text name="calendar.type" />: res[i].type,
					        	<s:text name="entity.description.label" />: res[i].description
							},
							{
								backgroundColor: "#333333",
								foregroundColor: "#FFFFFF"
							}
						);   				
				}
		    },
		    error: function(json){
		      alert("json=" + json);
		      return false;
		    }
		  }); 	
  }

</script>


</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />

      <div id="feature-title">
        <h2>
          <s:text name="menu.calendar.title" />
        </h2>
      </div>

      <div id="feature-content">
        <table style="" cellspacing="10" cellpadding="0" width="100%">
          <font color="red"> <s:actionerror /></font>
          <s:if test="hasFieldErrors()">
            <tr>
              <td align="left" colspan="4"><s:actionerror /> <s:iterator
                  value="fieldErrors" status="st">
                  <s:if test="#st.index  == 0">
                    <s:iterator value="value">
                      <font color="red"> <s:property
                        escape="false" /></font>
                    </s:iterator>
                  </s:if>
                </s:iterator></td>
            </tr>
          </s:if>
        </table>

        <div id="toolbar" class="ui-widget-header ui-corner-all"
          style="padding: 3px; vertical-align: middle; white-space: nowrap; overflow: hidden;">
          <button id="BtnPreviousMonth"><s:text name="calendar.previous.month" /></button>
          <button id="BtnNextMonth"><s:text name="calendar.next.month" /></button>
          &nbsp;&nbsp;&nbsp; <s:text name="calendar.date" />: <input type="text" id="dateSelect"
            size="20" /> &nbsp;&nbsp;&nbsp;
        </div>

        <br>
          <div id="mycal"></div>
        <div id="display-event-form" title="View Agenda Item"></div>
      </div>
    </div>

    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



