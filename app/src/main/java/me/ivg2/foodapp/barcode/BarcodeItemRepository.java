package me.ivg2.foodapp.barcode;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

class BarcodeItemRepository {
    private ArrayList<Barcode> barcodes;

    private void load() {
        barcodes = new ArrayList<>();
    }

    private void save() {

    }

    private static final BarcodeItemRepository ourInstance = new BarcodeItemRepository();

    static BarcodeItemRepository getInstance() {
        return ourInstance;
    }

    private BarcodeItemRepository() {
        load();
    }

    public static ArrayList<Barcode> getAll() {
        return getInstance().barcodes;
    }

    public static Barcode get(int i) {
        return getInstance().barcodes.get(i);
    }

    public static void create(Barcode barcode) {
        getInstance().barcodes.add(barcode);
        getInstance().save();
    }

    public static void delete(int i) {
        getInstance().barcodes.remove(i);
        getInstance().save();
    }

    public static int size() {
        return getInstance().barcodes.size();
    }

}
