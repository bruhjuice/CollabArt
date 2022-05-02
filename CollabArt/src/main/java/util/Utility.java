package util;

import java.util.regex.Pattern;

public class Utility {
	static public final String dbURL = "";
    static public final String DBUserName = "root";
    static public final String DBPassword = "root";
    static public final String FileName = "restaurant_data.json";

    static public Pattern namePattern = Pattern.compile("^[ A-Za-z]+$");
    static public Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$");

}
	