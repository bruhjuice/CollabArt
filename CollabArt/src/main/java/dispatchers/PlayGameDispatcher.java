package dispatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Rooms;

import java.io.*;
/**
 * Servlet implementation class PlayGameDispatcher
 */
@WebServlet("/playgame")
public class PlayGameDispatcher extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Default constructor.
     */
    public PlayGameDispatcher() {
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
    	boolean missingData = false;
    	boolean invalidCode = false;

    	String name = request.getParameter("name");
    	String roomCode = request.getParameter("roomCode");

    	if (name == null || name.contentEquals("") || roomCode == null || roomCode.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (!missingData && !Rooms.roomExists(roomCode))
    	{
    		invalidCode = true;	
    	}


    	if (!missingData && !invalidCode)
    	{
        	response.sendRedirect("gameStart.jsp");
    	}
    	else if (missingData)
    	{
    		request.setAttribute("error", "missingData");
    		request.getRequestDispatcher("playGame.jsp").forward(request, response);
    	}
    	else
    	{
    		request.setAttribute("error", "invalidCode");
    		request.getRequestDispatcher("playGame.jsp").forward(request, response);
    	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}