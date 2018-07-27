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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    @BindView(R.id.instructionsDV)
    TextView tvRecipeInstructions;
    @BindView(R.id.RecipeSourceDV)
    TextView tvRecipeSource;
    @BindView(R.id.options)
    TextView tvOptions;
    private int hours;
    private int minutes;
    private String cookTime = "";
    private Bitmap bitmap;
    private Callback callback;
    private int index;
    private Unbinder unbinder;
    RecipeItemRepository recipeItemRepository = RecipeItemRepository.getInstance();

    interface Callback {
        void goToRecipes();

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
        setIngredientsInView(arguments.getStringArrayList("ingredients"));
        setInstructionsInView(arguments.getStringArrayList("instructions"));
        String url = arguments.getString("image_url");
        index = arguments.getInt("index");
        Recipe recipe = recipeItemRepository.get(index);
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
    }

    public void setIngredientsInView(ArrayList<String> ingredients) {
        String ingDisplay = "";
        for (String ingredient : ingredients) {
            ingDisplay += ingredient + "\n";
        }
        if (ingDisplay.length() > 0) {
            tvRecipeIngredients.setText(ingDisplay.substring(0, ingDisplay.length() - 1));
        }
    }

    public void setInstructionsInView(ArrayList<String> instructions) {
        String instDisplay = "";
        for (String instruction : instructions) {
            instDisplay += instruction + "\n";
        }
        if (instDisplay.length() > 0) {
            tvRecipeInstructions.setText(instDisplay.substring(0, instDisplay.length() - 1));
        }
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
}