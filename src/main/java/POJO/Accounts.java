package POJO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Accounts {

    public String payerAcct;
    public String payeeAcct;
    public String amount;
    public String phone;
    public String name;

    public static class Address {

        public static String city;
        public static String state;
        public static String street;
        public static String zipCode;

    }

}
