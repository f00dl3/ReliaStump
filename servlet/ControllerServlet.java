package servlet;

import java.io.IOException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import action.SysMon.GetAlarmsAction;
import dao.SysMonDAO;
import model.SysMon.Alarm;

@WebServlet(
	urlPatterns = {
		"/Tomcat/Alarms",
	}
)

public class ControllerServlet extends HttpServlet {
	
	private DataSource dataSourceCore;
	private DataSource dataSourceSNMP;
	
	@Override
	public void init() {
		try {
			Context context = new InitialContext();
			dataSourceSNMP = (DataSource) context.lookup("java:comp/env/jdbc/SNMP");
		} catch (NamingException e) { e.printStackTrace(); }
	}

	@Override
	public void doGet(
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		process(request, response);
	}
	
	@Override
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(
		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex + 1);
		String dispatchUrl = null;
		boolean redirect = false;

		switch (action) {

			case "Alarms":
				dispatchUrl = "/Tomcat/Alarms.jsp";
				String status = "All";
				try {
					status = request.getParameter("AlarmFilter");
					switch (status) {
						case "New": status = "NEW"; break;
						case "Active": status = "ACK"; break;
						case "All": status = "CLR"; break;
					}
				} catch (Exception e) { e.printStackTrace(); }
				GetAlarmsAction getAlarmsAction = new GetAlarmsAction(new SysMonDAO(dataSourceSNMP));
				List<Alarm> alarms = getAlarmsAction.getAlarms(status);
				request.setAttribute("alarms", alarms);
				break;

		}

		if (dispatchUrl != null) {
			RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
			rd.forward(request, response);
		}

	}
	
}
