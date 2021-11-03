import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {
    Faker faker = new Faker(new Locale("ru"));

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    void shouldOk() {
        var user = DataGenerator.Registration.person("ru","active");
        DataGenerator.setUpAll(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2.heading").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldBlocked() {
        var user = DataGenerator.Registration.person("ru","blocked");
        DataGenerator.setUpAll(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldInvalidLogin() {
        var user = DataGenerator.Registration.person("ru","active");
        DataGenerator.setUpAll(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(faker.internet().password());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidPassword() {
        var user = DataGenerator.Registration.person("ru","active");
        DataGenerator.setUpAll(user);
        $("[data-test-id='login'] input").setValue(faker.name().username());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotExist() {
        $("[data-test-id='login'] input").setValue(faker.name().username());
        $("[data-test-id='password'] input").setValue(faker.internet().password());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidLoginAndBlocked() {
        var user = DataGenerator.Registration.person("ru","blocked");
        DataGenerator.setUpAll(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(faker.internet().password());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidPasswordAndBlocked() {
        var user = DataGenerator.Registration.person("ru","blocked");
        DataGenerator.setUpAll(user);
        $("[data-test-id='login'] input").setValue(faker.name().username());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Неверно указан логин или пароль"));
    }
}
