package model.SysMon;

import java.io.Serializable;
import java.util.Date;

public class Alarm implements Serializable {

	private String ackBy;
	private Date ackTime;
	private int alarmId;
	private String alarmText;
	private Date alarmTime;
	private String clrBy;
	private Date clrTime;
	private String contact;
	private String description;
	private String destIp;
	private String host;
	private String hostDescription;
	private String hostname;
	private String owner;
	private int severity;
	private String shortAlarmText;
	private String sourceIp;
	private String status;
	private String ticket;
	private String workgroup;

	public String getAckBy() { return ackBy; }
	public Date getAckTime() { return ackTime; }
	public int getAlarmId() { return alarmId; }
	public String getAlarmText() { return alarmText; }
	public Date getAlarmTime() { return alarmTime; }
	public String getClrBy() { return clrBy; }
	public Date getClrTime() { return clrTime; }
	public String getContact() { return contact; }
	public String getDescription() { return description; }
	public String getDestIp() { return destIp; }
	public String getHost() { return host; }
	public String getHostDescription() { return hostDescription; }
	public String getHostname() { return hostname; }
	public String getOwner() { return owner; }
	public int getSeverity() { return severity; }
	public String getShortAlarmText() { return shortAlarmText; }
	public String getSourceIp() { return sourceIp; }
	public String getStatus() { return status; }
	public String getTicket() { return ticket; }
	public String getWorkgroup() { return workgroup; }

	public void setAckBy(String ackBy) { this.ackBy = ackBy; }
	public void setAckTime(Date ackTime) { this.ackTime = ackTime; }
	public void setAlarmId(int alarmId) { this.alarmId = alarmId; }
	public void setAlarmText(String alarmText) { this.alarmText = alarmText; }
	public void setAlarmTime(Date alarmTime) { this.alarmTime = alarmTime; }
	public void setClrBy(String clrBy) { this.clrBy = clrBy; }
	public void setClrTime(Date clrTime) { this.clrTime = clrTime; }
	public void setContact(String contact) { this.contact = contact; }
	public void setDescription(String description) { this.description = description; }
	public void setDestIp(String destIp) { this.destIp = destIp; }
	public void setHost(String host) { this.host = host; }
	public void setHostDescription(String hostDescription) { this.hostDescription = hostDescription; }
	public void setHostname(String hostname) { this.hostname = hostname; }
	public void setOwner(String owner) { this.owner = owner; }
	public void setSeverity(int severity) { this.severity = severity; }
	public void setShortAlarmText(String shortAlarmText) { this.shortAlarmText = shortAlarmText; }
	public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
	public void setStatus(String status) { this.status = status; }
	public void setTicket(String ticket) { this.ticket = ticket; }
	public void setWorkgroup(String workgroup) { this.workgroup = workgroup; }
	
}
