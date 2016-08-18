<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>

<head>

<title>ReliaStump</title>

</head>

<body>

<form action="Tomcat/Alarms" method="post">

<div class="table">

<div class="tr">
<span class='td'><strong>Time</strong></span>
<span class='td'><strong>Severity</strong></span>
<span class='td'><strong>Action</strong></span>
<span class='td'><strong>Status</strong></span>
<span class='td'><strong>Ticket</strong></span>
<span class='td'><strong>Host</strong></span>
<span class='td'><strong>Alarm Text</strong></span>
</div>

<c:forEach items="${alarms}" var="alarm">

	<c:choose>
		<c:when test="${alarm.severity eq 0}"><c:set var="alarmSevBtn" value="<button class='UButton' style='width: 60px; background-color: grey; color: white;'>Critical</button>"/></c:when>
		<c:when test="${alarm.severity eq 1}"><c:set var="alarmSevBtn" value="<button class='UButton' style='width: 60px; background-color: red; color: white;'>Major</button>"/></c:when>
		<c:when test="${alarm.severity eq 2}"><c:set var="alarmSevBtn" value="<button class='UButton' style='width: 60px; background-color: orange; color: black;'>Major</button>"/></c:when>
		<c:when test="${alarm.severity eq 3}"><c:set var="alarmSevBtn" value="<button class='UButton' style='width: 60px; background-color: yellow; color: black;'>Minor</button>"/></c:when>
		<c:when test="${alarm.severity eq 4}"><c:set var="alarmSevBtn" value="<button class='UButton' style='width: 60px; background-color: lightblue; color: black;'>FYI</button>"/></c:when>
		<c:otherwise><c:set var="alarmSevBtn" value="<button class='UButton' style='width: 60px; background-color: lightblue; color: black;'>UNK</button>"/></c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${not empty alarm.shortAlarmText}"><c:set var="thisAlarmText" value="${alarm.shortAlarmText}"/></c:when>
		<c:otherwise><c:set var="thisAlarmText" value="${alarm.alarmText}"/></c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${alarm.status eq 'NEW'}"><c:set var="alarmActionBtn" value="ACK"/></c:when>
		<c:when test="${alarm.status eq 'ACK'}"><c:set var="alarmActionBtn" value="CLR"/></c:when>
		<c:otherwise><c:set var="alarmActionBtn" value=""/></c:otherwise>
	</c:choose>

	<div class="tr">
	<input name="AlarmID[${alarm.alarmId}]" type="hidden" value="${alarm.alarmId}"/>
	<span class="td"><fmt:formatDate type="both" value="${alarm.alarmTime}"/></span>
	<span class="td">${alarmSevBtn}</span>
	<span class="td"><c:if test="${not empty alarmActionBtn}"><input name="DoUpdate[${alarm.alarmId}]" class="UButton" type="checkbox" value="${alarmActionBtn}"/></c:if></span>
	<span class="td"><div class="UPop">${alarm.status}
	<div class="UPopO">
	<c:if test="${not empty alarm.ackBy}"><strong>Acknowledged</strong> by: ${alarm.ackBy}<br/>at: <fmt:formatDate type="both" value="${alarm.ackTime}"/></c:if>
	<c:if test="${not empty alarm.clrBy}"><br/><strong>Cleared</strong> by: ${alarm.clrBy}<br/>at: <fmt:formatDate type="both" value="${alarm.clrTime}"/></c:if>
	</div></div></span>
	<span class="td">
	<c:choose>
		<c:when test="${not empty alarmActionBtn}"><input name="AlarmTicket[${alarm.alarmId}]" type="text" value="${alarm.ticket}" style="width: 80px;"/></c:when>
		<c:otherwise>${alarm.ticket}</c:otherwise>
	</c:choose>
	</span>
	<span class="td"><div class="UPop">${alarm.hostname}
	<div class="UPopO">
	<strong>IP Address</strong>: ${alarm.sourceIp}<br/>
	<c:if test="${not empty alarm.owner}">
		<br/><strong>Owner</strong>: ${alarm.owner}
		<br/><strong>Contact</strong>: ${alarm.contact}
		<br/><strong>Workgroup</strong>: ${alarm.workgroup}
		<br/><strong>Description</strong>: ${alarm.hostDescription}
	</c:if>
	</div></div></span>
	<span class="td"><div class="UPop">${fn:substring(thisAlarmText, 0, 30)}
	<div class="UPopO">${thisAlarmText}
	<c:if test="${not empty alarm.description}"><br/><em>${alarm.description}</em></c:if>
	</div></div></span>
	</div>
	
</c:forEach>

</div>

</form>

</body>

</html>
