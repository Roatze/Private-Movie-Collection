package bll.util;

public class ConvertUtil {

    //I denne klasse har vi et par statiske metoder der bruges til at konvertere en string til to eller baglens.
    //Det er fordi at i programmet så består en films rating af to forskellige String's, en personlig og en public rating.
    //Derimod så inde i databasen gemmer vi det dog kun som en enkel kombineret String.

    //De næste to metoder tager den kombineret rating String fra vores database
    // og sepererer den til enten filmens public eller personlige rating.
    public static String combinedToPersonal(String combined)
    {
        String personalRating;
        String publicRating;

        String[] split = combined.split(";");
        //public rating er den første værdi mens personlig er den anden
        personalRating = split[1];
        publicRating = split[0];

        //Her retuneres en af værdierne
        return personalRating;
    }

    public static String combinedToPublic(String combined)
    {
        String personalRating;
        String publicRating;

        String[] split = combined.split(";");

        personalRating = split[1];
        publicRating = split[0];

        //Og her retuneres den anden af værdierne
        return publicRating;
    }

    //Denne metode tager to strings og kombinerer dem til en enkel String med det format som der gemmes i databasen
    public static String toCombined(String publicRating, String personalRating)
    {
        String combined = publicRating + ";" + personalRating;

        return combined;
    }
}


