package spaceescape.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameInitServlet extends HttpServlet{
	
	private List<String> playersName = new ArrayList<>();

	private static final long serialVersionUID = 1L;
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			playersName.add((String) request.getParameter("playerName"));
			doGet(request, response);
			//this.getServletContext().getRequestDispatcher("/View/initGame.jsp").forward(request, response);
	}
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException {
		request.setAttribute("players", playersName);
		if(playersName.size() == 4) {
			request.setAttribute("joueurMax", false);
		}
		else {
			request.setAttribute("joueurMax", true);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/View/initGame.jsp");
		
		try {
			dispatcher.forward(request, response);
		}catch(IOException e) {
			System.out.println("Error when accessing the jsp file");
		}
	}

}
