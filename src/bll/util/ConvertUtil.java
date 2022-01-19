package bll.util;

public class ConvertUtil {

    public static String combinedToPersonal(String combined)
    {
        String personalRating;
        String publicRating;

        String[] split = combined.split(";");

        personalRating = split[1];
        publicRating = split[0];

        return personalRating;
    }

    public static String combinedToPublic(String combined)
    {
        String personalRating;
        String publicRating;

        String[] split = combined.split(";");

        personalRating = split[1];
        publicRating = split[0];

        return publicRating;
    }

    public static String toCombined(String publicRating, String personalRating)
    {
        String combined = publicRating + ";" + personalRating;

        return combined;
    }
}


