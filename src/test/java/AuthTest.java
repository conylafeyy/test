import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    void shouldOk() {
        var user = DataGenerator.Registration.person("ru","active");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2.heading").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldBlocked() {
        var user = DataGenerator.Registration.person("ru","blocked");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldInvalidLogin() {
        var user = DataGenerator.Registration.person("ru","active");
        var fakeUser = DataGenerator.Registration.person("ru","active");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(fakeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidPassword() {
        var user = DataGenerator.Registration.person("ru","active");
        var fakeUser = DataGenerator.Registration.person("ru","active");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(fakeUser.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotExist() {
        var fakeUser = DataGenerator.Registration.person("ru","active");
        $("[data-test-id='login'] input").setValue(fakeUser.getLogin());
        $("[data-test-id='password'] input").setValue(fakeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidLoginAndBlocked() {
        var user = DataGenerator.Registration.person("ru","blocked");
        var fakeUser = DataGenerator.Registration.person("ru","blocked");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(fakeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidPasswordAndBlocked() {
        var user = DataGenerator.Registration.person("ru","blocked");
        var fakeUser = DataGenerator.Registration.person("ru","blocked");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(fakeUser.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }
}
