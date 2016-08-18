package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

import model.SysMon.Alarm;

public class SysMonDAO {
	
	private DataSource dataSource;
	public SysMonDAO(DataSource dataSource) { this.dataSource = dataSource; }

	private static final String GET_AlarmsJoined_SQL = "SELECT "
		+ "aT.AlarmID, aT.Status, aT.Ticket, aT.AlarmTime, aT.SourceIP, "
		+ "aT.DestIP, aT.AlarmText, aT.AckBy, aT.AckTime, aT.ClrBy, "
		+ "aT.ClrTime, aT.Host, aH.Hostname, aH.Description as hDescription, aH.Owner, "
		+ "aH.Contact, aH.Workgroup, aD.Severity, aD.Description, aD.ShortAlarmText "
		+ "FROM AlarmTable aT "
		+ "LEFT JOIN Hosts aH ON aT.SourceIP = aH.SourceIP "
		+ "LEFT JOIN AlarmDefinitions aD ON aT.AlarmText LIKE CONCAT('%', aD.Pattern, '%') "
		+ "WHERE aT.Status='NEW' OR aT.Status='ACK' OR aT.Status=? "
		+ "ORDER BY aT.AlarmID DESC "
		+ "LIMIT 25;";

	private static final String UPDATE_Alarms_SQL = "UPDATE AlarmTable SET "
		+ "Status=?, Ticket=?, AckBy=?, AckTime=?, ClrBy=?, ClrTime=? "
		+ "WHERE AlarmID=?;";

	public List<Alarm> getAlarms(String status) {
		List<Alarm> alarms = new ArrayList<>();
		String pattern = "yyyyMMddHHmm";
		String patternSecs = "yyyyMMddHHmmss";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		SimpleDateFormat formatSecs = new SimpleDateFormat(patternSecs);
		try (
			Connection connection = dataSource.getConnection();
			PreparedStatement pStatement = connection.prepareStatement(GET_AlarmsJoined_SQL);
		) {
			pStatement.setString(1, status);
			try (	
				ResultSet resultSet = pStatement.executeQuery()
			) {
				while (resultSet.next()) {
					Alarm alarm = new Alarm();
					try {
						Date dateAckTime = formatSecs.parse(resultSet.getString("aT.AckTime"));
						Date dateAlarmTime = format.parse(resultSet.getString("aT.AlarmTime"));
						Date dateClrTime = formatSecs.parse(resultSet.getString("aT.ClrTime"));
						alarm.setAckTime(dateAckTime);
						alarm.setAlarmTime(dateAlarmTime);
						alarm.setClrTime(dateClrTime);
					} catch (ParseException e) { e.printStackTrace(); }
					alarm.setAckBy(resultSet.getString("aT.AckBy"));
					alarm.setAlarmId(resultSet.getInt("aT.AlarmID"));
					alarm.setAlarmText(resultSet.getString("aT.AlarmText"));
					alarm.setClrBy(resultSet.getString("aT.ClrBy"));
					alarm.setContact(resultSet.getString("aH.Contact"));
					alarm.setDescription(resultSet.getString("aD.Description"));
					alarm.setDestIp(resultSet.getString("aT.DestIP"));
					alarm.setHost(resultSet.getString("aT.Host"));
					alarm.setHostDescription(resultSet.getString("aH.hDescription"));
					alarm.setHostname(resultSet.getString("aH.Hostname"));
					alarm.setOwner(resultSet.getString("aH.Owner"));
					alarm.setSeverity(resultSet.getInt("aD.Severity"));
					alarm.setShortAlarmText(resultSet.getString("aD.ShortAlarmText"));
					alarm.setSourceIp(resultSet.getString("aT.SourceIP"));
					alarm.setStatus(resultSet.getString("aT.Status"));
					alarm.setTicket(resultSet.getString("aT.Ticket"));
					alarm.setWorkgroup(resultSet.getString("aH.Workgroup"));
					alarms.add(alarm);
				}
			} catch (SQLException e) { e.printStackTrace(); }
		} catch (SQLException e) { e.printStackTrace(); }
		return alarms;
	}

}
