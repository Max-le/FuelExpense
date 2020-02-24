package be.max;

public class Calculation {
    /**
     *
     * @param distance in km.
     * @param avConso L / 100km
     * @param fuelPrice  price for one liter of fuel.
     * @return
     */
    public static float calculateCost(double distance, double avConso ,double fuelPrice ){
        double totalConsumption = distance * (avConso/100);
        return (float) (totalConsumption * fuelPrice);
    }
}
