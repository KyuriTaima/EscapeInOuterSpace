package spaceescape.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spaceescape.model.Player;
import spaceescape.model.boardmap.BoardMap;
import spaceescape.model.boardmap.Coordinate;

/**
 * Servlet implementation class GameServlet
 */
@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
	private List<Player> players = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;
       
    public GameServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch(action) {
		case "/game/start":
			String[] names = request.getParameterValues("players")[0].substring(1).split(", ");
			initPlayers(names);
			WebGame.config("Space Escape", players);
			request.setAttribute("playerName", players.get(0).getName());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/View/Game.jsp");
			dispatcher.forward(request, response);
			break;
		case "/game/map":
			BoardMap map = WebGame.getInstance().getMap();
			List<String> possibleMoves = new ArrayList<>();
			List<Coordinate> coords = WebGame.getInstance().whereCharacterCanGo(WebGame.getActivePlayer());
			for(int j=0; j<coords.size(); j++) {
				possibleMoves.add(coords.get(j).toString());
			}
			request.setAttribute("possibleMoves", possibleMoves);
			
			List<String> line1 = new ArrayList<>();
			line1.add("   ");
			for(int i=0; i<map.getWidth(); i++) {
				if(i<10) {
					line1.add(Integer.toString(i) + "  ");
				}else {
					line1.add(Integer.toString(i) + " ");
				}
			}
			request.setAttribute("line1", line1);
	        for (int y = 0 ; y < map.getHeight() ; y++){
	            List<String> line = new ArrayList<>();
	            if(y<10) {
	            	line.add(Integer.toString(y) + "  ");
	            }else {
	            	line.add(Integer.toString(y) + " ");
	            }
	            for(int x = 0 ; x < map.getWidth() ; x++){
	                if(WebGame.getActivePlayer().getCharacter().getCoordinate().equals(new Coordinate(x,y))){
	                    line.add("X  ");
	                } else {
	                    line.add(map.getMap().get(new Coordinate(x,y)).toString() + "  ");
	                }
	            }
	            request.setAttribute("line" + (y + 2), line);
	        }
			RequestDispatcher mapDispatcher = request.getRequestDispatcher("/View/map.jsp");
			mapDispatcher.forward(request, response);
			break;
		default:
			break;
		}
	}
	
	private void initPlayers(String[] names) {
		for(int i=0; i<names.length;i++) {
			players.add(new Player(names[i]));
		}
	}
	
}
