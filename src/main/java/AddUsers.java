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
import java.time.LocalDateTime;

@WebServlet("/AddUsers")
public class AddUsers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();

        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String birthday_date = request.getParameter("birthday_date");
        String work_date = request.getParameter("work_date");
        String inn = request.getParameter("inn");
        String salary = request.getParameter("salary");
        String mobile_number = request.getParameter("mobile_number");
        String e_mail = request.getParameter("e_mail");

        printWriter.println("<style>" +
                " .color_red {color: red; font-size: 12pt;}" +
                " .center {\n" +
                "       font-family: Arial;" +
                "       text-align:  center;" +
                "       padding: 30px; /* Поля вокруг текста */" +
                "       margin: auto; /* Выравниваем по центру */" +
                "       background: #f7f6f5; /* Цвет фона */" +
                "       border-style: solid;" +
                "       border-color: #cecac8;" +
                "       border-radius: 10px;" +
                "       height: 280px; width: 415px;" +
                "        }" +
                "body {margin: 60px; font-size: 12pt;}" +

                ".b1 {border: 2px solid #00b259; border-radius: 4px; background: #78c583; color: black; font-size: 9pt;" +
                "     height: 27px; width: 180px; font-size: 10pt;}" +
                ".b1:hover{background-color: #3c6d24; color: #fff;}" +
                "</style>");

        String current_date = LocalDateTime.now().toString();
        int int_birthday_date_year = Integer.parseInt(birthday_date.substring(0, 4));
        int int_work_date_year = Integer.parseInt(work_date.substring(0, 4));
        int int_current_date_year = Integer.parseInt(current_date.substring(0, 4));

        int int_work_date_month = Integer.parseInt(work_date.substring(5, 7));
        int current_date_month = Integer.parseInt(current_date.substring(5, 7));

        int int_work_date_day = Integer.parseInt(work_date.substring(8, 10));
        int current_date_day = Integer.parseInt(current_date.substring(8, 10));

        if (int_birthday_date_year < 1950 || int_current_date_year < (int_birthday_date_year + 18) || int_work_date_year > int_current_date_year ||
                ((int_work_date_year == int_current_date_year) & (int_work_date_month == current_date_month) & (int_work_date_day > current_date_day)) ||
                int_work_date_year < (int_birthday_date_year + 18)) {
            printWriter.println("<div class='center'>");
            printWriter.println("<div class='color_red'>");
            printWriter.println("<h2>Данные не добавлены</h2>");
            printWriter.println("</div>");
            printWriter.println("<h3>Не корректно заполнены поля 'Дата рождения' и(или) 'Дата приема на работу'</h3>");
            printWriter.println("<br>");
            printWriter.println("<p><a href = 'http://localhost:8081/AddUser.html'>Попробовать еще раз</a></p>");
            printWriter.println("<br>");
            printWriter.println("<p><a href = 'http://localhost:8081/RootPage.html'>На главную</a></p>");
            printWriter.println("</div>");
        } else {

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" +
                    "5432/java_ee_db", "postgres", "sasha1818");
                 Statement statement = connection.createStatement()) {

                int resultSetTableUsers = statement.executeUpdate("INSERT INTO users (name, login, " +
                        "password, birthday_date, work_date, inn, salary) VALUES ('" + name + "','" + login + "'," +
                        "'" + password + "','" + birthday_date + "','" + work_date + "','" + inn + "','" + salary + "')");

                int resultSetTableContacts = statement.executeUpdate("INSERT INTO contacts (id, mobile_number, e_mail) " +
                        "VALUES ((SELECT  MAX(id) FROM users),'" + mobile_number + "','" + e_mail + "')");

                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<div class='center'>");

                if (resultSetTableUsers == 1 && resultSetTableContacts == 1) {
                    printWriter.println("<br>");
                    printWriter.println("<h2>Данные добавлены</h2>");
                    printWriter.println("<br>");
                } else {
                    printWriter.println("<br>");
                    printWriter.println("<br>");
                    printWriter.println("<div class='color_red'>");
                    printWriter.println("<h2>Данные не добавлены</h2>");
                    printWriter.println("</div>");
                    printWriter.println("<br>");
                }

                printWriter.println("<style>" +
                        "  body {margin: 30px; font-size: 12pt;}" +
                        " .b1 {border: 2px solid #00b259; background: #78c583; color: black; font-size: 9pt; " +
                        "       height: 27px; width: 180px; font-size: 10pt;}" +
                        " .b1:hover{background-color: #3c6d24; color: #fff;}" +
                        " </style>");
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
}
