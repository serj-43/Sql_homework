package data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthorizationInfo {
        String login;
        String password;
    }

    public static AuthorizationInfo getAuthorizationInfo() {
        return new AuthorizationInfo("vasya", "qwerty123");
    }

    public static AuthorizationInfo getNewUser() {
        return new AuthorizationInfo(DataSql.getNewUser(), "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String verificationCode;
    }


    public static AuthorizationInfo getUserWithWrongPassword() {
        val faker = new Faker();
        return new AuthorizationInfo("vasya", faker.internet().password());
    }
}