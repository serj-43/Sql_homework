package sql;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        return connection;
    }

    public static void clean() {
        val runner = new QueryRunner();
        val cleanUsers = "DELETE FROM users;";
        val cleanCards = "DELETE FROM cards;";
        val cleanAuth_Codes = "DELETE FROM auth_codes;";
        val cleanCard_Transactions = "DELETE FROM card_transactions;";
        try (
                val conn = getConnection();
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

    public static String getUserStatus(String login) {
        val selectSQL = "SELECT status FROM users WHERE login = ?;";
        val runner = new QueryRunner();
        String status = null;

        try (
                val conn = getConnection();
        ) {
            status = runner.query(conn, selectSQL, new ScalarHandler<>(), login);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }
}
