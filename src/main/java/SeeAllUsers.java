import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/SeeAllUsers")
public class SeeAllUsers extends HttpServlet {

        protected void doPost (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {

        }

        protected void doGet (HttpServletRequest request, HttpServletResponse response) throws
         IOException {
            response.setContentType("text/html;charset = UTF-8");
            PrintWriter printWriter = response.getWriter();
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" +
                    "5432/java_ee_db", "postgres", "sasha1818");
                 Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery("SELECT users.id, users.name, users.login, users.password," +
                        " users.birthday_date, users.work_date, users.inn, users.salary, contacts.mobile_number, contacts.e_mail," +
                        " (current_date-birthday_date)/365 AS age" +
                        " FROM users INNER JOIN contacts ON users.id = contacts.id ORDER BY id ASC;");

                printWriter.println("<style>" +
                        " .b1 {border: 2px solid #00b259; background: #aae6b3; width: 80px; color: black; font-size: 9pt;}" +
                        " .b1:hover{background-color: #3c6d24; color: #fff;}" +

                        " .b2 {border: 2px solid #e94239; background: #ffbdbb; width: 80px; color: black; font-size: 9pt;}" +
                        " .b2:hover{background-color: #c43838; color: #fff; }" +

                        " .b5 {border: 2px solid #979797; background: #d6d2d1; color: black ;border-radius: 5px; height: 30px;" +
                        "       width: 200px; font-size: 10pt;}" +
                        " .b5:hover{background-color: #979797; color: #fff;}" +

                        " .b6 {border: 0px; background: #f0efee; width: 100%; font-size: 9pt;}" +

                        " #footer1 {\n" +
                        "    position: fixed;/* Фиксированное положение */\n" +
                        "    left: 0; top: 0; /* Левый нижний угол */\n" +
                        "    padding: 0; /* Поля вокруг текста */\n" +
                        "    background: #f0efee; /* Цвет фона */\n" +
                        "    color: #fff; /* Цвет текста */\n" +
                        "    height: 3.2em; width: 100%; /* Ширина слоя */\n" +
                        "   }" +

                        " #footer2 {\n" +
                        "   left: 0; bottom: 0; /* Левый нижний угол */\n" +
                        "   padding: 1px; /* Поля вокруг текста */\n" +
                        "   background: #cecac8; /* Цвет фона */\n" +
                        "   color: #000000; /* Цвет текста */\n" +
                        "   width: 100%; /* Ширина слоя */\n" +
                        "   }" +

                        " .leftstr, .rightstr {" +
                        "   float: left; /* Обтекание справа */" +
                        "   width: 50%; /* Ширина текстового блока */" +
                        "    }" +
                        " .rightstr {" +
                        "    text-align: right; /* Выравнивание по правому краю */" +
                        "   }" +
                        " .raz {\n" +
                        "  border: 0px; solid #e2e0df;}\n" +
                        " .raz th {\n" +
                        "  position: sticky;\n" +
                        "  top: 4.3em;\n" +
                        "  background: #d6d2d1; border: 1px; solid #ffffff;\n" +
                        "   }" +

                        " .b3 {background: #d6d2d1; height: 35px; font-family: 'Arial'; font-size: 11pt;}" +

                        " .b4 { background: #ffffff; font-family: 'Arial'; font-size: 9pt;}" +
                        " .b4 tr:hover{background: #f7f6f5; /* Цвет фона при наведении */\n" +
                        "    border: 1px solid #ebe9e9; color: #050000; /* Цвет текста при наведении */\n" +
                        "    border: 1px solid #ffffff;" +
                        "    }" +

                        "button {\n" +
                        "    width: 105px; /* Ширина кнопки */\n" +
                        "    height: 22px; /* Высота */\n" +
                        "    border-radius: 5px;\n" +
                        "    }" +
                        "table {\n" +
                        "    width: 95%;/* Ширина таблицы */\n" +
                        "    border-collapse: collapse; /* Убираем двойные линии между ячейками */\n" +
                        "   }\n" +
                        "   td, th {\n" +
                        "    padding: 3px; /* Поля вокруг содержимого таблицы */\n" +
                        "    border: 1px solid #ebe9e9; /* Параметры рамки */\n" +
                        "   }\n" +
                        "</style>");
                printWriter.println("<body bgcolor=\"#f7f6f5\">");
                printWriter.println("<title>SeeAllUsers</title>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<br>");
                printWriter.println("<div class=raz>");
                printWriter.println("<table class=b4 align='center'>");
                printWriter.println("<tr class=b3 align='center'>" + "<th align='center'>" + "id" + "</th>" + "<th>" + "ФИО" + "</th>"
                        + "<th>" + "Логин" + "</th>" + "<th>" + "Пароль" + "</th>"
                        + "<th>" + "Дата рождения" + "</th>" + "<th>" + "Дата приема на работу" + "</th>"
                        + "<th>" + "ИНН" + "</th >" + "<th>" + "Заработная плата" + "</td>"
                        + "<th>" + "Номер телефона" + "</th>" + "<th>" + "e_mail" + "</th>"
                        + "<th>" + "Возраст" + "<br>" + "(полных&nbspлет)" + "</th>"
                        + "<th>" + "Обновить данные" + "</th>" + "<th>" + "Удалить данные" + "</th>" + "</tr>");
                printWriter.println("</div>");
                while (resultSet.next()) {
                    printWriter.println("<tr>"
                            + "<td>" + resultSet.getString("id") + "</td>"
                            + "<td>" + resultSet.getString("name") + "</td>"
                            + "<td>" + resultSet.getString("login") + "</td>"
                            + "<td>" + resultSet.getString("password") + "</td>"
                            + "<td align='center'>" + resultSet.getString("birthday_date") + "</td>"
                            + "<td align='center'>" + resultSet.getString("work_date") + "</td>"
                            + "<td>" + resultSet.getString("inn") + "</td>"
                            + "<td align='center'>" + resultSet.getString("salary") + "</td>"
                            + "<td>" + resultSet.getString("mobile_number") + "</td>"
                            + "<td>" + resultSet.getString("e_mail") + "</td>"
                            + "<td align='center'>" + resultSet.getString("age") + "</td>"
                            + "<td align='center'>" + "<form action=\"PreparingForUpdate\" method=\"get\">" +
                            "<button type= \"submit\"class=\"b1\" name= \"id\"" +
                            " style=\"margin-top: 10px\" value=" + resultSet.getString("id") + "> Обновить </button></form>" + "</td>"
                            + "<td align= 'center'>" + "<form action=\"PreparingForDelete.html\" method=\"get\">" +
                            "<button type= \"submit\"class=\"b2\" name= \"id\"" +
                            " style=\"margin-top: 10px\" value=" + resultSet.getString("id") + "> Удалить </button></form>" + "</td>"
                            + "</tr>");
                }
                printWriter.println("</table>");
                printWriter.println("<div id = footer1>");
                printWriter.println("<table class=b6>");
                printWriter.println("<tr>" + "<td align= 'left'>" + "<br><form action=\"WriteSheetExcel\" method=\"get\">"
                        + "<input type=\"submit\" class=\"b5\" style=\"margin-right: 5px\" value=\"Экспорт в Excel\"></form>" + "</td>"
                        + "<td align= 'center'><a href = 'http://localhost:8081/RootPage.html'>На главную</a></td>"
                        + "<td align= 'right'>" + "<br><form action=\"AddUser.html\" method=\"get\">" +
                        "<input type=\"submit\" class=\"b5\" style=\"margin-right: 5px\" value=\"Добавить нового сотрудника\"></form>" + "</td>" + "</tr>");
                printWriter.println("</table>");
                printWriter.println("</div>");
                printWriter.println("</div>");
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
