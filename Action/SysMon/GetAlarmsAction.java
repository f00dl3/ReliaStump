package action.SysMon;

import java.util.List;

import dao.SysMonDAO;
import model.SysMon.Alarm;

public class GetAlarmsAction {
	
	private SysMonDAO sysMonDAO;
	public GetAlarmsAction(SysMonDAO sysMonDAO) { this.sysMonDAO = sysMonDAO; }
	public List<Alarm> getAlarms(String status) { return sysMonDAO.getAlarms(status); }
	
}
