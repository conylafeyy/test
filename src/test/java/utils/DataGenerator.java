import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;


import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Registration {
        public static DataPerson person(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            return new DataPerson(
                    faker.name().username(),
                    faker.internet().password(),
                    status
            );
        }
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(DataPerson user) {
        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user)
        .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
        .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
}