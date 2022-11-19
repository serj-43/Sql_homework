package test;

import com.codeborne.selenide.Selenide;
import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import sql.DBHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    void shouldLogIn() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val dashboardPage = verificationPage.validCode(DBHelper.getCode());
    }

    @Test
        void shouldGetBlock() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfoWithInvalid();
        loginPage.login(authInfo);
        loginPage.getInvalidLoginOrPassword();
        loginPage.cleaning();
        loginPage.login(authInfo);
        loginPage.getInvalidLoginOrPassword();
        loginPage.cleaning();
        loginPage.login(authInfo);
        val status = DBHelper.getUserStatus(authInfo.getLogin());
        assertEquals("blocked", status);
    }

    @AfterAll
    static void cleanDatabase() {
        DBHelper.clean();
    }
}
