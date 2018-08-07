package me.ivg2.foodapp;

public class quantityManupilation {
    public static String formatQuantity(double quantity) {
        double rounded = Math.round(quantity * 100) / 100.0;
        if (rounded % 1.0 == 0) {
            return Integer.toString((int) Math.round(rounded));
        }
        String stringified = Double.toString(rounded);
        if(stringified.equals("0.33")) {
            return "\u2153";
        }else if(stringified.equals("0.5")){
            return "\u00BD";
        }else if(stringified.equals("0.66")){
            return "\u2154";
        }else if(stringified.equals("0.13")){
            return "\u215B";
        }else if(stringified.equals("0.25")){
            return "\u00BC";
        }else if(stringified.equals("0.75")){
            return "\u00BE";
        }else if(stringified.equals("0.20")){
            return "\u2155";
        }else if(stringified.equals("0.40")){
            return "\u2156";
        }else if(stringified.equals("0.60")){
            return "\u2157";
        }else if(stringified.equals("0.80")){
            return "\u2158";
        }else if(stringified.equals("0.16")){
            return "\u2159";
        }else if(stringified.equals("0.83")){
            return "\u215A";
        }else if(stringified.equals("0.14")){
            return "\u2150";
        }else if(stringified.equals("0.13")){
            return "\u215B";
        }else if(stringified.equals("0.38")){
            return "\u215C";
        }else if(stringified.equals("0.63")){
            return "\u215D";
        }else if(stringified.equals("0.88")){
            return "\u215E";
        }else if(stringified.equals("0.11")){
            return "\u2151";
        }else if(stringified.equals("0.10")){
            return "\u2152";
        }else
            return stringified;
    }

}
