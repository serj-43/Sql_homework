package test;

import com.codeborne.selenide.Selenide;
import data.DataHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import sql.DBHelper;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    void shouldLoginUser() {
        var authInfo = DataHelper.getAuthInfo();
        var code = DataHelper.getVerificationCodeFor(authInfo);

        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var dashBoardPage = verificationPage.validCode(code);
    }

    @AfterAll
    static void cleanDatabase() {
        DBHelper.clean();
    }
}
