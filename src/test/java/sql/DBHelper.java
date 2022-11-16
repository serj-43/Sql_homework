package sql;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        return connection;
    }

    public static void clean(){
        val runner = new QueryRunner();
        val cleanUsers = "DELETE FROM users;";
        val cleanCards = "DELETE FROM cards;";
        val cleanAuth_Codes = "DELETE FROM auth_codes;";
        val cleanCard_Transactions = "DELETE FROM card_transactions;";
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            runner.execute(conn, cleanCards);
            runner.execute(conn, cleanAuth_Codes);
            runner.execute(conn, cleanUsers);
            runner.execute(conn, cleanCard_Transactions);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getCode() {
        try (val conn = getConnection();
             val countStmt = conn.createStatement()) {
            val sql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
            val resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return null;
    }
}
