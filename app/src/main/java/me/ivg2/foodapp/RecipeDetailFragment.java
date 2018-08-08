package me.ivg2.foodapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.ivg2.foodapp.Model.Food;
import me.ivg2.foodapp.Model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {
    public static final int GET_FROM_GALLERY = 3;
    @BindView(R.id.recipeImageDetail)
    ImageView ivRecipeImage;
    @BindView(R.id.nameRecipeDetail)
    TextView tvRecipeName;
    @BindView(R.id.timeRecipeDV)
    TextView tvRecipeTime;
    @BindView(R.id.ingredientsDV)
    TextView tvRecipeIngredients;
    @BindView(R.id.RecipeSourceDV)
    TextView tvRecipeSource;
    @BindView(R.id.options)
    TextView tvOptions;
    @BindView(R.id.cookedBtn)
    Button cookedBtn;
    private int hours;
    private int minutes;
    private String cookTime = "";
    private Bitmap bitmap;
    private Callback callback;
    private int index;
    private Unbinder unbinder;
    RecipeItemRepository recipeItemRepository = RecipeItemRepository.getInstance();
    Recipe recipe;
    private final int RECIPES_TO_COOK_POSITION = 0;
    private final int RECIPES_CLOSE_TO_COOKING = 1;
    //List view for ingredients missing
    ArrayList<String> items;
    private MissingIngredientsAdapter itemsAdapter;
    @BindView(R.id.ingredientsMissing)
    RecyclerView rvMissingIngredients;

    private InstructionsAdapter instructionsAdapter;
    @BindView(R.id.instructions)
    RecyclerView rvInstructions;

    interface Callback {
        void goToRecipes();

        void goToRecipesList(int index, int tab);

        void goToEditRecipe(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeDetailFragment.Callback) {
            callback = (RecipeDetailFragment.Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        tvRecipeName.setText(arguments.getString("name"));
        tvRecipeSource.setText(arguments.getString("source"));
        hours = arguments.getInt("hours");
        minutes = arguments.getInt("min");
        if (hours > 0) {
            cookTime += hours + "h";
        }
        if (minutes > 0) {
            cookTime += minutes + "m";
        }
        tvRecipeTime.setText(cookTime);
        setInstructionsInView(arguments.getStringArrayList("instructions"));
        String url = arguments.getString("image_url");
        index = arguments.getInt("index");
        recipe = recipeItemRepository.get(index);
        setIngredientsInView(recipe.getIngredients());
        if (recipe.getImageBitmap() != null) {
            ivRecipeImage.setImageBitmap(recipe.getImageBitmap());
        } else {
            if (url != null) {
                Glide.with(this)
                        .load(url)
                        .into(ivRecipeImage);
            } else {
                tvRecipeName.setTextColor(Color.parseColor("#000000"));
            }
        }
        ImageButton backBtn = view.findViewById(R.id.imageButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToRecipeList(v);
            }
        });
        if (recipe.getIngredientsMissing().size() > 0) {
            ArrayList<Food> ingMissing = recipe.getIngredientsMissing();
            ingMissing.add(0, new Food("You are missing the following ingredients: "));
            itemsAdapter = new MissingIngredientsAdapter(ingMissing);
            rvMissingIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMissingIngredients.setAdapter(itemsAdapter);
        }
    }

    public void backToRecipeList(View v) {
        //toggle back to browse view
        if (recipe.getIngredientsMissing().size() > 0) {
            callback.goToRecipesList(index, RECIPES_CLOSE_TO_COOKING);
        } else {
            callback.goToRecipesList(index, RECIPES_TO_COOK_POSITION);
        }
    }

    public String formatQuantity(double quantity) {
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

    public void setIngredientsInView(ArrayList<Food> ingredients) {
        String ingDisplay = "";
        for (Food ingredient : ingredients) {
            ingDisplay += '\u2022' + " " + formatQuantity(ingredient.getQuantity()) + " " + ingredient.getUnit() + " " + ingredient.getName() + "\n\n";
        }
        if (ingDisplay.length() > 0) {
            tvRecipeIngredients.setText(ingDisplay.substring(0, ingDisplay.length() - 1));
        }
    }

    public void setInstructionsInView(ArrayList<String> instructions) {
        instructionsAdapter = new InstructionsAdapter(instructions);
        rvInstructions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvInstructions.setAdapter(instructionsAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        Objects.requireNonNull(getContext()).getContentResolver(),
                        selectedImage);
                ivRecipeImage.setImageBitmap(bitmap);
                Bundle arguments = getArguments();
                RecipeItemRepository recipeItemRepository = RecipeItemRepository.getInstance();
                Recipe recipe = recipeItemRepository.get(arguments.getInt("index"));
                recipe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Method for return file path of Gallery image
     *
     * @param context
     * @param uri
     * @return path of the selected image file from gallery
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.options)
    public void setMenuClicks() {
        PopupMenu menu = new PopupMenu(getContext(), tvOptions);
        menu.inflate(R.menu.recipe_detail_menu);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        recipeItemRepository.delete(index);
                        callback.goToRecipes();
                        return true;
                    case R.id.edit:
                        callback.goToEditRecipe(index);
                        return true;
                    case R.id.changePicture:
                        startActivityForResult(new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                        return true;
                    default:
                        return false;
                }
            }
        });
        menu.show();
    }

    @OnClick(R.id.cookedBtn)
    public void tryToCook() {
        FoodItemRepository foodItemRepository = FoodItemRepository.getInstance();
        for (Food ingredient : recipe.getIngredients()) {
            for (Food f : foodItemRepository.getAll()) {
                if (f.getName().toLowerCase().equals(ingredient.getName())) {
                    //remove
                    f.setQuantity(f.getQuantity() - ingredient.getQuantity());
                    if (f.getQuantity() <= 0) {
                        foodItemRepository.delete(foodItemRepository.getIndex(f));
                    }
                    break;
                }
            }
        }
        Toast.makeText(getActivity(), "Ingredients removed from your fridge", Toast.LENGTH_LONG).show();
    }
}