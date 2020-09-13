import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/DeleteUsers")
public class DeleteUsers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset = UTF-8");
        PrintWriter printWriter = response.getWriter();
        String id = request.getParameter("id");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" +
                "5432/java_ee_db", "postgres", "sasha1818");
             Statement statement = connection.createStatement()) {

            int resultSetTableContacts = statement.executeUpdate("DELETE FROM contacts WHERE id ='" + id + "'");

            int resultSetTableUsers = statement.executeUpdate("DELETE FROM users WHERE id ='" + id + "'");

            printWriter.println("<style>" +
                    " .center {\n" +
                    "       font-family: Arial;" +
                    "       text-align:  center;" +
                    "       padding: 30px; /* Поля вокруг текста */" +
                    "       margin: auto; /* Выравниваем по центру */" +
                    "       background: #f7f6f5; /* Цвет фона */" +
                    "       border-style: solid;" +
                    "       border-color: #cecac8;" +
                    "       border-radius: 10px;" +
                    "       height: 165px; width: 415px;" +
                    "        }" +
                    "body {margin: 30px; font-size: 12pt;}" +

                    ".b1 {border: 2px solid #00b259; border-radius: 4px; background: #78c583; color: black; font-size: 9pt;" +
                    "     height: 27px; width: 180px; font-size: 10pt;}" +
                    ".b1:hover{background-color: #3c6d24; color: #fff;}" +
                    "</style>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<div class='center'>");
            if (resultSetTableUsers == 1 && resultSetTableContacts == 1) {
                printWriter.println("<br>");
                printWriter.println("Данные удалены (" + (resultSetTableUsers & resultSetTableContacts) + ")");
            } else {
                printWriter.println("<br>");
                printWriter.println("Данные не удалены (" + (resultSetTableUsers & resultSetTableContacts) + ")");
            }
            printWriter.println("<p><form action=\"SeeAllUsers\" method=\"get\" >\n" +
                    "    <input type=\"submit\" class=\"b1\" value=\"Просмотреть результат\">\n" +
                    "</form>");
            printWriter.println("<br>");
            printWriter.println("<p><a href = 'http://localhost:8081/RootPage.html'>На главную</a></p>");
            printWriter.println("</div>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
