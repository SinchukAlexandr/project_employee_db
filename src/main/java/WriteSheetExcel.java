import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.sql.*;


@WebServlet("/WriteSheetExcel")
public class WriteSheetExcel extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            PrintWriter printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(" Employee Info ");
        XSSFRow row;
        Cell cell;

        int rowNumHead = 0;
        row = sheet.createRow(rowNumHead);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("ФИО");
        row.createCell(2).setCellValue("Логин");
        row.createCell(3).setCellValue("Пароль");
        row.createCell(4).setCellValue("Дата рождения");
        row.createCell(5).setCellValue("Дата приема на работу");
        row.createCell(6).setCellValue("ИНН");
        row.createCell(7).setCellValue("Заработная плата");
        row.createCell(8).setCellValue("Номер телефона");
        row.createCell(9).setCellValue("e_mail");
        row.createCell(10).setCellValue("Возраст (полных лет)");

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

            int rowNum = rowNumHead + 1;
            while (resultSet.next()) {
                rowNum++;
                row = sheet.createRow(rowNum);

                cell = row.createCell(0);
                cell.setCellValue(resultSet.getString("id"));

                cell = row.createCell(1);
                cell.setCellValue(resultSet.getString("name"));

                cell = row.createCell(2);
                cell.setCellValue(resultSet.getString("login"));

                cell = row.createCell(3);
                cell.setCellValue(resultSet.getString("password"));

                cell = row.createCell(4);
                cell.setCellValue(resultSet.getString("birthday_date"));

                cell = row.createCell(5);
                cell.setCellValue(resultSet.getString("work_date"));

                cell = row.createCell(6);
                cell.setCellValue(resultSet.getString("inn"));

                cell = row.createCell(7);
                cell.setCellValue(resultSet.getString("salary"));

                cell = row.createCell(8);
                cell.setCellValue(resultSet.getString("mobile_number"));

                cell = row.createCell(9);
                cell.setCellValue(resultSet.getString("e_mail"));

                cell = row.createCell(10);
                cell.setCellValue(resultSet.getString("age"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        JButton jButton = new JButton();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(jButton);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.getSelectedFile() == null) {
            try {
                response.sendRedirect(request.getContextPath() + "/SeeAllUsers");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".xlsx"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                workbook.write(out);
                out.close();
                response.sendRedirect(request.getContextPath() + "/SeeAllUsers");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

