package be.max;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        String baseURL = "https://maps.googleapis.com/maps/api/distancematrix/json?";
        String origin = askOrigin();
        String dest = askDestination();
        //TODO Get relative path and concatenate
        String key = Files.readString(Paths.get("/Users/max/Developer/FuelExpenseJava/src/main/java/be/max/apikey.txt"));

        String parameters = String.format("origins=%s&destinations=%s&key=%s",origin, dest, key );
        HTTPRequest GMapsRequest = new HTTPRequest(baseURL+parameters);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(GMapsRequest.readResponse());
        JSONObject jsonResult = (JSONObject) obj;
        String originSupposed = (String) ((JSONArray)jsonResult.get("origin_addresses")).get(0);
        String destSupposed   = (String) ((JSONArray)jsonResult.get("destination_addresses")).get(0);
        JSONArray rows = (JSONArray) jsonResult.get("rows");
        JSONArray elements = (JSONArray) ((JSONObject)rows.get(0)).get("elements");
        JSONObject distance = (JSONObject) ((JSONObject)elements.get(0)).get("distance");
        long meters = (long)distance.get("value");
        float avConso = 5.1f;
        float fuelPrice = 1.4f;
        float oneWayTripCost = Calculation.calculateCost(meters/1000.0, avConso,  fuelPrice);
        System.out.printf("Estimated cost for travel for travel :\nFROM %s ----> TO %s :\n%.2f EUR ",
                originSupposed, destSupposed,  oneWayTripCost);
        System.out.printf("(%.2f EUR for round trip).\n", oneWayTripCost*2);
        System.out.printf("Based on those assumptions : %.2f L/100km, %.2f EUR/L\n",avConso, fuelPrice);

    }


    public static String askOrigin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the departure location :");
        return replaceSpaces(sc.nextLine());
    }
    public static String askDestination(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your destination");
        return replaceSpaces(sc.nextLine());
    }

    public static String replaceSpaces(String s){
        return s.replace(" ", "+");
    }
}
