package me.ivg2.foodapp.server;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlManager {
    private static final String SERVER_HOST_NAME = "10.0.2.2:5000";

    public static URL getPluEndpoint(int PLU) throws MalformedURLException {
        return new URL("http://" + SERVER_HOST_NAME + "/plu/" + PLU);
    }

    public static URL savePluEndpoint(int PLU, String name) throws MalformedURLException {
        return new URL("http://" + SERVER_HOST_NAME + "/plu/" + PLU + "/" + name);
    }

    public static URL getBarcodeEndpoint(int barcode) throws MalformedURLException {
        return new URL("http://" + SERVER_HOST_NAME + "/barcode/" + barcode);
    }

    public static URL saveBarcodeEndpoint(int barcode, String name) throws MalformedURLException {
        return new URL("http://" + SERVER_HOST_NAME + "/plu/" + barcode + "/" + name);
    }

    public static URL getRecipeEndpoint() throws MalformedURLException {
        return new URL("http://" + SERVER_HOST_NAME + "/recipes" );
    }

}
