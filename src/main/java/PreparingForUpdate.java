import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/PreparingForUpdate")
public class PreparingForUpdate extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset = UTF-8");
        PrintWriter printWriter = response.getWriter();
        String id = request.getParameter("id");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("userId", id);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" +
                "5432/java_ee_db", "postgres", "sasha1818");
             Statement statement = connection.createStatement()) {

            printWriter.println("<a href = http://localhost:8081/RootPage.html>На главную</a>");
            printWriter.println("<br>");
            printWriter.println("<br>");
            printWriter.println("<br>");

            ResultSet resultSetFromUsers = statement.executeQuery("SELECT * FROM users WHERE id='" + id + "'");
            printWriter.println("<title>PreparingForUpdate</title>");

            printWriter.println("<style>" +
                    " body {margin: 30; font-size: 12pt;}" +
                    " #user  {width: 300px;}" +
                    " #user1 {width: 144px; font-family: Arial;}" +
                    " #user2 {width: 144px; font-family: Arial;}" +
                    " .b1 {border: 2px solid #00b259; background: #78c583; color: black; border-radius: 5px;\n" +
                    "            height: 27px; width: 412px;font-size: 10pt;}" +
                    " .b1:hover{background-color: #3c6d24; color: #fff;}" +
                    " .center {\n" +
                    "font-family: Arial;" +
                    "       padding: 30px; /* Поля вокруг текста */" +
                    "       margin: auto; /* Выравниваем по центру */" +
                    "       background: #f7f6f5; /* Цвет фона */" +
                    "       border-style: solid;" +
                    "       border-color: #cecac8;" +
                    "       border-radius: 10px;" +
                    "       height: 458px; width: 415px;" +
                    "        }" +
                    "</style>");
            printWriter.println("<form action = 'UpdateUser' method = 'get'");
            printWriter.println("<div class='center'>");
            while (resultSetFromUsers.next()) {
                printWriter.println("<input type = 'text' id = 'user' name = 'name' value = '" + resultSetFromUsers.getString
                        ("name") + "' pattern = '[А-Яа-яЁё\\s]+$' required> <b>  ФИО</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<input type = 'text' name = 'login' value = '" + resultSetFromUsers.getString
                        ("login") + "' required'> <b>  Логин</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<input type = 'text' name = 'password' value = '" + resultSetFromUsers.getString
                        ("password") + "'required'> <b>  Пароль</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<input type = 'date' id = 'user1' name = 'birthday_date' value = '" + resultSetFromUsers.getString
                        ("birthday_date") + "'required> <b>  Дата рождения </b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<input type= 'date' id = 'user2' name = 'work_date' value = '" + resultSetFromUsers.getString
                        ("work_date") + "'required> <b>  Дата приема на работу</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<input type = 'text' name = 'inn' value = '" + resultSetFromUsers.getString
                        ("inn") + "'pattern = '^[0-9]{8}' 'required'> <b>  ИНН</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<input type = 'text' name = 'salary' value = '" + resultSetFromUsers.getString
                        ("salary") + "'pattern = '^[0-9]{4,6}' 'required'> <b>  Заработная плата</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
            }
            ResultSet resultSetFromContacts = statement.executeQuery("SELECT * FROM contacts WHERE id='" + id + "'");

            while (resultSetFromContacts.next()) {
                printWriter.println("<input type = 'tel' name = 'mobile_number' value = '" + resultSetFromContacts.getString
                        ("mobile_number") + "'pattern = '[+][3][8][0][-][0-9]{2}[-][0-9]{3}[-][0-9]{2}[-][0-9]{2}' " +
                        "required> <b>  Моб. телефон (+380-__-___-__-__)</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<input type = 'email' name = 'e_mail' value = '" + resultSetFromContacts.getString
                        ("e_mail") + "' required> <b>  E-mail</b>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<p><input type = 'submit' class='b1' value = 'Обновить данные сотрудника'><br>");
            }
            printWriter.println("</div>");
            printWriter.println("</form>");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
