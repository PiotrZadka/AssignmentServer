import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

//Card Validation servlet 

@WebServlet("/CardValidator")
public class CardValidator extends HttpServlet {
	   private static final long serialVersionUID = 1L;

	   Gson gson = new Gson();
    
	   Connection conn = null;
	   Statement stmt;

	  public void init(ServletConfig config) throws ServletException {

	    super.init(config);
		String user = "zadkap";
	    String password = "herGytal3";
	    // Note none default port used, 6306 not 3306
	    String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/"+user;

		// Load the database driver
		try {  Class.forName("com.mysql.jdbc.Driver").newInstance();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		
	        try {
	            conn = DriverManager.getConnection(url, user, password);
				  System.out.println("Validator is running");	  

	            stmt = conn.createStatement();
	        } catch (SQLException se) {
	            System.out.println(se);
	            System.out.println("\nDid you alter the lines to set user/password in the sensor server code?");
	        }
 
	  }
	  
	// Data received by GET method from "cardReaderController" method "ValidateCard"
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setStatus(HttpServletResponse.SC_OK);
	    
	    String getdata = request.getParameter("getdata");
	    if (getdata == null){
			String sensorJsonString = request.getParameter("validationData");
			if (sensorJsonString != null) {
				cardReaderData reader = gson.fromJson(sensorJsonString, cardReaderData.class);
				// If card if valid send success response
				reader = checkCardinDB(reader);
				String readerJson = gson.toJson(reader);
				response.setContentType("application/json");  
			    PrintWriter out = response.getWriter();
			    out.println(readerJson);
			    out.close();
			}
		}
	}
	
	// Making contact with database to check if data sent from RFID card is existing in DB
	private cardReaderData checkCardinDB(cardReaderData readerJson){
		ResultSet resultSet = null;
		System.out.print("CardName =>"+readerJson.getTagId()+" CardReaderID =>"+readerJson.getReaderId()+" MotorID =>"+readerJson.getMotorId());
		try {
			String updateSQL = "select * from cards where cardId = '"+readerJson.getTagId()+"';";
		    System.out.println(updateSQL);          
		        System.out.println("DEBUG: Statement: " + updateSQL);
		        resultSet = stmt.executeQuery(updateSQL);
		        if(resultSet.next()) {
		        	System.out.println("DEBUG: Select statement successful");
		        	readerJson.setReaderId(resultSet.getString("sensorId"));
		        	readerJson.setMotorId(resultSet.getString("motorId"));
		        	stmt.execute(updateSQL);
		        	
		        	//Check what has been returned from DB
		        	System.out.println("Checking what data is being returned from Database");
		        	System.out.println("CardName =>"+readerJson.getTagId()+" CardReaderID =>"+readerJson.getReaderId()+" MotorID =>"+readerJson.getMotorId());
		        }else {
		        	return readerJson;
		        }
		} catch (SQLException se) {
		    System.out.println(se);
	        System.out.println("\nDEBUG: Update error - see error trace above for help. ");
		}
		return readerJson;
	}	
}
