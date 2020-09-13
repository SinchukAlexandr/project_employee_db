import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/FilterUsers")
public class FilterUsers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset = UTF-8");
        String total_work_days = request.getParameter("total_work_days");
        PrintWriter printWriter = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" +
                "5432/java_ee_db", "postgres", "sasha1818");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM (SELECT users.id, users.name, users.login," +
                    " users.password, users.birthday_date, users.work_date, users.inn, users.salary, contacts.mobile_number," +
                    " contacts.e_mail, (current_date-birthday_date)/365 AS age, current_date - work_date AS total FROM users" +
                    " INNER JOIN contacts ON users.id = contacts.id)" +
                    " AS TWD WHERE total<'" + total_work_days + "'");

            printWriter.println("<style>" +
                    " .b3 {background: #d6d2d1; height: 35px; font-family: 'Arial'; font-size: 11pt;}" +
                    " .b4 {background: #ffffff; font-family: 'Arial'; font-size: 10pt;}" +

                    ".raz {\n" +
                    "  height: 22px;\n" +
                    "}\n" +
                    ".raz th {\n" +
                    "  position: sticky;\n" +
                    "  top: 0;\n" +
                    "  background: #d6d2d1;\n" +
                    "}" +

                    "table {\n" +
                    "    width: 95%; /* Ширина таблицы */\n" +
                    "    border-collapse: collapse; /* Убираем двойные линии между ячейками */\n" +
                    "   }\n" +
                    "   td, th {\n" +
                    "    padding: 6px; /* Поля вокруг содержимого таблицы */\n" +
                    "    border: 1px solid #ebe9e9; /* Параметры рамки */\n" +
                    "   }\n" +

                    "   tbody tr:hover {\n" +
                    "    background: #f7f6f5; /* Цвет фона при наведении */\n" +
                    "    color: #050000; /* Цвет текста при наведении */\n" +
                    "    border: 1px solid #ffffff;" +
                    "    }" +

                    " .leftstr, .rightstr {" +
                    "   float: left; /* Обтекание справа */" +
                    "   width: 50%; /* Ширина текстового блока */" +
                    "    }" +
                    " .rightstr {" +
                    "    text-align: right; /* Выравнивание по правому краю */" +
                    "   }" +

                    ".text {" +
                    "text-align:  center;}" +
                    " #footer2 {\n" +
                    "   left: 0; bottom: 0; /* Левый нижний угол */\n" +
                    "   padding: 1px; /* Поля вокруг текста */\n" +
                    "   background: #cecac8; /* Цвет фона */\n" +
                    "   color: #000000; /* Цвет текста */\n" +
                    "   width: 100%; /* Ширина слоя */\n" +
                    "   }" +
                    "</style>");
            printWriter.println("<body bgcolor=\"#f7f6f5\">");
            printWriter.println("<title>FilterUsers</title>");
            printWriter.println("<br>");
            printWriter.println("<div class=text>");
            printWriter.println("<a href = 'http://localhost:8081/RootPage.html'>На главную</a>");
            printWriter.println("</div>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<div class=raz>");
            printWriter.println("<table align='center' class= 'b4'>");
            printWriter.println("<thead>");
            printWriter.println("<tr align='center' class= b3>" + "<th align='center'>" + "<b>" + "id" + "</th>"
                    + "<th>" + "<b>" + "name" + "</th>" + "<th align='center'>" + "<b>" + "login" + "</th>"
                    + "<th>" + "<b>" + "password" + "</th>" + "<th>" + "<b>" + "birthday_date" + "</th>"
                    + "<th>" + "<b>" + "work_date" + "</th>" + "<th>" + "<b>" + "inn" + "</th>" + "<th>" + "<b>" + "salary" + "</th>"
                    + "<th>" + "<b>" + "mobile_number" + "</th>" + "<th>" + "<b>" + "e_mail" + "</th>"
                    + "<th>" + "<b>" + "age_of_employee" + "</th>"
                    + "<th>" + "<b>" + "total_work_days" + "</th>" + "</tr>");
            printWriter.println("<tbody>");
            while (resultSet.next()) {
                printWriter.println("<tr>"
                        + "<td align='center'>" + resultSet.getString("id") + "</td>"
                        + "<td>" + resultSet.getString("name") + "</td>"
                        + "<td>" + resultSet.getString("login") + "</td>"
                        + "<td>" + resultSet.getString("password") + "</td>"
                        + "<td>" + resultSet.getString("birthday_date") + "</td>"
                        + "<td>" + resultSet.getString("work_date") + "</td>"
                        + "<td>" + resultSet.getString("inn") + "</td>"
                        + "<td>" + resultSet.getString("salary") + "</td>"
                        + "<td>" + resultSet.getString("mobile_number") + "</td>"
                        + "<td>" + resultSet.getString("e_mail") + "</td>"
                        + "<td align='center'>" + resultSet.getString("age") + "</td>"
                        + "<td align='center'>" + resultSet.getString("total") + "</td>"
                        + "</tr>");
            }
            printWriter.println("</div>");
            printWriter.println("</table>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<div id = footer2>");
            printWriter.println("<p class=\"leftstr\"><b>Copyright© 2020</b> by Sinchuk </p>\n" + "<p class=\"rightstr\"><b>Employee database</b>, version 1.2</p>");
            printWriter.println("</div>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
