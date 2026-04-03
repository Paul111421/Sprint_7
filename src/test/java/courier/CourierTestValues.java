package courier;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;

public class CourierTestValues {

    static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());

    static String login = fakeValuesService.bothify("????_##");
    static String password = fakeValuesService.bothify("###");
    static String firstName = fakeValuesService.bothify("??????");

    public static CourierCard courierValid = new CourierCard(login,password,firstName);

    public static CourierCard courierNotValidDifferentLogin = new CourierCard("Hank_J_Wimbleton",password,firstName);
    public static CourierCard courierNotValidDifferentPass = new CourierCard(login,"tri_semerki", firstName);

    public static CourierCard courierNotValidEmptyLogin = new CourierCard("",password,firstName);
    public static CourierCard courierNotValidEmptyPassword = new CourierCard(login,"",firstName);
    public static CourierCard courierNotValidNoLoginOrPas = new CourierCard(password);

}
