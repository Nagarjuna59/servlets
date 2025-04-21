import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Student Information</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Age</th><th>Grade</th></tr>");

        Connection conn = null;
        Statement stmt = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(sql);

            // Display result
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String grade = rs.getString("grade");

                out.println("<tr><td>" + id + "</td><td>" + name + "</td><td>" + age + "</td><td>" + grade + "</td></tr>");
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace(out);
            }
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}