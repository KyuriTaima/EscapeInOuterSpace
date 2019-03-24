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

import spaceescape.model.boardmap.BoardMap;
import spaceescape.model.boardmap.Coordinate;
import spaceescape.model.character.Engineer;
import spaceescape.model.character.Soldier;

/**
 * Servlet implementation class InteractionServlet
 */
@WebServlet("/InteractionServlet")
public class InteractionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardMap map = WebGame.getInstance().getMap();
		String[] moveAndAttack = {"Move", "Move and Attack"};
		String[] move = {"Move"};
		String action = request.getServletPath();
		switch(action) {
		case "/game/interaction":
			String choice = request.getParameter("playerChoice");
			String tempString = choice.substring(1,choice.length()-1);
			String[] onlyCoords = tempString.split(",");
			Coordinate coord = new Coordinate(Integer.parseInt(onlyCoords[0]), Integer.parseInt(onlyCoords[1]));
			WebGame.getInstance().moveCharacterTo(coord, WebGame.getActivePlayer());
	    	if(!(WebGame.getActivePlayer().getCharacter() instanceof Engineer)) {
	    		if(WebGame.getActivePlayer().getCharacter() instanceof Soldier) {	//On teste si c'est un Soldier
	    			if(((Soldier) WebGame.getActivePlayer().getCharacter()).getCanAttack() == true) {	//On teste s'il a déjà attaqué
	    				request.setAttribute("actions", moveAndAttack);
	    			}
	    			else {
	    				request.setAttribute("actions", move);
	    			}
	    		}else {
	    			request.setAttribute("actions", moveAndAttack);
	    		}
	    	}else {
	    		request.setAttribute("actions", move);
	    	}
	    	
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
	        request.setAttribute("playerName", WebGame.getActivePlayer().toString());
	        request.setAttribute("playerType", WebGame.getActivePlayer().getCharacter().toString());
	        request.setAttribute("historic", WebGame.getInstance().getMessages());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/View/map.jsp");
			dispatcher.forward(request, response);
			break;
		case "/game/interaction/action":
			
			line1 = new ArrayList<>();
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
			
			List<String> messages = new ArrayList<>();
			if(request.getParameter("playerChoice").equals("Move")) {
				messages.add(WebGame.getInstance().moveAction(WebGame.getActivePlayer()));
			}else {
				messages.addAll(WebGame.getInstance().attackAction(WebGame.getActivePlayer()));
			}
			request.setAttribute("messages", messages);
	        request.setAttribute("finDuTour", true);
	        request.setAttribute("playerName", WebGame.getActivePlayer().toString());
	        request.setAttribute("playerType", WebGame.getActivePlayer().getCharacter().toString());
	        request.setAttribute("historic", WebGame.getInstance().getMessages());
	        
	        boolean verif = true;
	        do {
	        	WebGame.setNextPlayer();
	        	if(WebGame.getActivePlayer().getCharacter().getPlaying() == true) {
	        		verif = false;
	        	}
	        }while(verif);
	        
			RequestDispatcher dispatcher2 = request.getRequestDispatcher("/View/map.jsp");
			dispatcher2.forward(request, response);
			break;
		}
		
	}

}
