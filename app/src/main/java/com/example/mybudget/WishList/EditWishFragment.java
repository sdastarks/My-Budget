package com.example.mybudget.WishList;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditWishFragment extends Fragment {

    private static final String TAG = "editWishFragment";
    private EditText meditTitle;
    private EditText meditCost;
    private ImageView editWishPicture;
    private ImageButton btnchooseCamerOrGallery;
    private Button btn_exitEditWish;
    private Button btn_saveEditWish;
    private FloatingActionButton btn_deletwish;
    private int index;
    private int dbid;
    private WishList wish2Edit;
    View view;
    private String drawable;
    private FloatingActionButton completed_wishes;
    private static final int REQUEST_CHOOSE_IMAGE_FROM_GALLERY = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_PERMISSION = 200;
    private String mCurrentPhotoPath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        completed_wishes = getActivity().findViewById(R.id.completed_wishes);
        completed_wishes.hide();

        view = inflater.inflate(R.layout.fragment_edit_wish, container, false);

        index = getArguments().getInt("indexEdit");

        meditTitle = view.findViewById(R.id.edit_wish_title);
        meditCost = view.findViewById(R.id.edit_wish_cost);
        btn_exitEditWish = view.findViewById(R.id.btn_cancel_edit_wish);
        btn_saveEditWish = view.findViewById(R.id.btn_save_edit_wish);
        btn_deletwish = view.findViewById(R.id.floatingActionButton_delete_wish);
        editWishPicture = view.findViewById(R.id.edit_wish_picture);
        btnchooseCamerOrGallery = view.findViewById(R.id.btn_camera_option);

        dbid = ((WishlistActivity) getActivity()).id;
        wish2Edit = ((WishlistActivity) getActivity()).db.returnWish(dbid);
        editWishPicture.setImageURI(Uri.parse(wish2Edit.getImage()));
        meditTitle.setText(wish2Edit.getTitle());
        meditCost.setText(String.valueOf(wish2Edit.getCost()));
        drawable = Integer.parseInt(wish2Edit.getImage());

        btnchooseCamerOrGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        ImageView theBikePic = view.findViewById(R.id.bike_edit);
        theBikePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_bike);
                drawable = getURLForResource(R.drawable.button_wish_bike);
            }
        });

        ImageView clothesPic = view.findViewById(R.id.clothes_edit);
        clothesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_clothes);
                drawable = getURLForResource(R.drawable.button_wish_clothes);
            }
        });

        ImageView gadgetsPic = view.findViewById(R.id.gadgets_edit);
        gadgetsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_gadgets);
                drawable = getURLForResource(R.drawable.button_wish_gadgets);
            }
        });

        ImageView gamesPic = view.findViewById(R.id.games_edit);
        gamesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_games);
                drawable = getURLForResource(R.drawable.button_wish_games);
            }
        });

        ImageView giftPic = view.findViewById(R.id.gift_edit);
        giftPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_gift);
                drawable = getURLForResource(R.drawable.button_wish_gift);
            }
        });

        ImageView holidayPic = view.findViewById(R.id.holiday_edit);
        holidayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_holiday);
                drawable = getURLForResource(R.drawable.button_wish_holiday);
            }
        });

        ImageView iceSkatePic = view.findViewById(R.id.iceskate_edit);
        iceSkatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_iceskate);
                drawable = getURLForResource(R.drawable.button_wish_iceskate);
            }
        });


        ImageView petsPic = view.findViewById(R.id.pets_edit);
        petsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_pets);
                drawable = getURLForResource(R.drawable.button_wish_pets);
            }
        });

        ImageView scooterPic = view.findViewById(R.id.scooter_edit);
        scooterPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_scooter);
                drawable = getURLForResource(R.drawable.button_wish_scooter);
            }
        });

        ImageView shoesPic = view.findViewById(R.id.shoes_edit);
        shoesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_shoes);
                drawable = getURLForResource(R.drawable.button_wish_shoes);
            }
        });

        ImageView otherPic = view.findViewById(R.id.dream_edit);
        otherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_dream);
                drawable = getURLForResource(R.drawable.button_wish_dream);
            }
        });


        activateOnExitEditWish();
        activateOnSaveEditWish();
        activateDeleteWish();

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION);
        }
        return view;
    }

    /*
     * Method creates a dialog fragment allowing the user
     * to delete a wish or abort the procedure
     */
    public void activateDeleteWish() {
        btn_deletwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteWishDialog deleteDialog = new DeleteWishDialog();
                Bundle args = new Bundle();
                args.putInt("indexEdit", index);
                deleteDialog.setArguments(args);
                deleteDialog.show(getActivity().getSupportFragmentManager(), "delete dialog");
            }
        });
    }

    private void activateOnExitEditWish() {

        btn_exitEditWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });

    }

    private void activateOnSaveEditWish() {
        btn_saveEditWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int dbid = ((WishlistActivity) getActivity()).id;
                    String title = meditTitle.getText().toString().trim();
                    int cost = Integer.parseInt(meditCost.getText().toString());


                    if (wish2Edit.getSaved() >= cost) {
                        meditCost.setError("You've already saved " + wish2Edit.getSaved() + " SEK");
                    } else if (cost > 10000000) {
                        meditCost.setError("Wish must be less than 10M SEK");
                    } else if (cost <= 0) {
                        meditCost.setError("Must be greater than 0 SEK");
                    } else if (title.isEmpty()) {
                        Log.v(TAG, "title: " + title + "cost: " + cost);
                        updateWish();
                    } else if (title.length() > 18) {
                        meditTitle.setError("Wish must be less than 18 characters");
                    } else {
                        updateWish();
                    }

                } catch (Exception e) {
                    if (meditCost.getText().toString().trim().isEmpty()) {
                        Log.v(TAG, "if statment initialsed");
                        Log.v(TAG, "title: " + meditTitle.getText().toString() + " cost: " + wish2Edit.getCost());
                        updateWish();
                    } else {
                        meditCost.setError("Try Again");
                    }

                }

            }
        });
    }

    public void updateWish() {
        String newTitle = meditTitle.getText().toString();
        int newCost = Integer.parseInt(meditCost.getText().toString());
        String newDrawable = drawable;

        if (meditTitle.getText().toString() == wish2Edit.getTitle())
            newTitle = wish2Edit.getTitle();
        else newTitle = meditTitle.getText().toString();

        if (meditCost.getText().toString() == String.valueOf(wish2Edit.getCost()))
            newCost = wish2Edit.getCost();
        else newCost = Integer.parseInt(meditCost.getText().toString());

        if (drawable == Integer.parseInt(wish2Edit.getImage()))
            newDrawable = String.valueOf(wish2Edit.getImage());
        else newDrawable = String.valueOf(drawable);

        ((WishlistActivity) getActivity()).db.updateWish(dbid, newTitle, newCost, wish2Edit.getSaved(), newDrawable);
        Intent intent = new Intent(getActivity(), WishlistActivity.class);
        startActivity(intent);
    }

    /*@author Benish
     * Method for choosing image from gallery
     * or capture image from camera
     */
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    openCameraIntent();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CHOOSE_IMAGE_FROM_GALLERY);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

    private void openCameraIntent(){
        Intent pictureIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null ;
            try{
                photoFile = createImageFile();
            }catch (IOException e){
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getActivity().getBaseContext(), getActivity().getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            pictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        mCurrentPhotoPath =  image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast toast = Toast.makeText(getActivity(),"Permission granted ",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            try {
                drawable = mCurrentPhotoPath;
                editWishPicture.setImageURI(Uri.parse(mCurrentPhotoPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == REQUEST_CHOOSE_IMAGE_FROM_GALLERY && resultCode == getActivity().RESULT_OK)
        {
            try{
                final Uri imageUri = data.getData();
                mCurrentPhotoPath = getPath(getActivity().getApplicationContext(), imageUri);
                drawable = mCurrentPhotoPath;
                editWishPicture.setImageURI(imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  String getPath(Context context, Uri uri){
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null );
        if(cursor != null){
            if(cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {

            result = "Not found";
            Toast toast = Toast.makeText(getActivity(),"Image does not exist ",Toast.LENGTH_LONG);
            toast.show();
        }
        return result;
    }


}
