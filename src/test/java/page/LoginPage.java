package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginInput= $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public void login(DataHelper.AuthorizationInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(info.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthorizationInfo info) {
        login(info);
        return new VerificationPage();
    }

    public void showErrorMessage() {
        $(".notification").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    public void clearFields() {
        loginInput.doubleClick().sendKeys(Keys.BACK_SPACE);
        passwordInput.doubleClick().sendKeys(Keys.BACK_SPACE);
    }
}
