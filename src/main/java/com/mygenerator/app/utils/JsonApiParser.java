package com.mygenerator.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mygenerator.app.models.Person;
import com.mygenerator.app.utils.MySqlInsert;

import org.apache.commons.lang3.text.WordUtils;

public class JsonApiParser {
    private static final String NA = "N/A";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String URL = "https://randomuser.me/api/";

    public static Person fetchRandomUser() throws Exception {
        URL obj = new URL(URL);
        Random rand = new Random();
        StringBuffer response = new StringBuffer();

        try {
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonElement lastName;
        JsonElement firstName;
        JsonElement sex;
        JsonElement date;
        JsonElement zip;
        JsonElement state;
        JsonElement city;
        JsonElement street;

        JsonElement responseAsJson = new JsonParser().parse(response.toString());

        JsonElement results = responseAsJson.getAsJsonObject().get("results");
        sex = getArrayObject(results, "gender");

        JsonElement dob = getArrayObject(results, "dob");
        date = getValue(dob, "date");

        JsonElement name = getArrayObject(results, "name");
        lastName = getValue(name, "last");
        firstName = getValue(name, "first");

        JsonElement location = getArrayObject(results, "location");
        zip = getValue(location, "postcode");
        state = getValue(location, "state");
        city = getValue(location, "city");
        street = getValue(location, "street");

        String shortSex = sex.getAsString().substring(0, 1);
        String rawStreet = street.getAsString().replaceAll("\\d+", "");
        int rawHouse = Integer.parseInt(street.getAsString().replaceAll("\\D+", ""));

        MySqlInsert.sqlConnectInsert();

        return new Person.Builder().setLastName(WordUtils.capitalize(lastName.getAsString()))
                .setFirstName(WordUtils.capitalize(firstName.getAsString())).setPatronymic("")
                .setBirthDate(convertDate(date.getAsString())).setInn(RandomValidInnGenerator.getNew())
                .setIndex(zip.getAsString()).setCountry(NA).setRegion(WordUtils.capitalize(state.getAsString()))
                .setCity(WordUtils.capitalizeFully(city.getAsString())).setStreet(WordUtils.capitalizeFully(rawStreet))
                .setHouse(rawHouse).setFlat(rand.nextInt(499) + 1).setSex(WordUtils.capitalize(shortSex)).build();
    }

    private static JsonElement getArrayObject(JsonElement array, String objectName) {
        return array.getAsJsonArray().get(0).getAsJsonObject().get(objectName);
    }

    private static JsonElement getValue(JsonElement object, String objectName) {
        return object.getAsJsonObject().get(objectName);
    }

    private static Date convertDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        Date parsedDate = new Date();
        try {
            parsedDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
}