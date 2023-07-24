import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    public static String dateDelivery(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldOrderCardDelivery() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldCityWithDash() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEnglishCity() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Moscow");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub")
                .shouldHave(text("Доставка в выбранный город недоступна"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyCity() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldNameWithDash() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Анна-Мария Петрова");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEnglishName() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Ivan Petrov");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyName() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldPhoneWithoutPlus() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldPhoneWithoutOneNumber() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+7999888775");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldPhonePlusNotInFirstPlace() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("7999+8887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyPhone() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyCheckbox() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $(".button").click();
        $("[data-test-id='agreement']").isSelected();
    }

    @Test
    public void shouldChoiceCityByTwoLetters() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Мо").click();
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldChoiceDateWeekAhead() {
        String date = dateDelivery(7);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79998887755");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }
}