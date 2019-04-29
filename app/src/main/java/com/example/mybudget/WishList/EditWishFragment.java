package com.example.mybudget.WishList;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mybudget.BuildConfig;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


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
    private int drawable;
    private FloatingActionButton completed_wishes;
    private static final int REQUEST_CHOOSE_IMAGE_FROM_GALLERY = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Hello camera";
    private static final String AUTHORITY=
            BuildConfig.APPLICATION_ID+".provider";
    private static final String PHOTOS="photos";
    private File output=null;
    private String mCurrentPhotoPath;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;

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
        editWishPicture.setImageResource(wish2Edit.getImage());
        meditTitle.setText(wish2Edit.getTitle());
        meditCost.setText(String.valueOf(wish2Edit.getCost()));
        drawable = wish2Edit.getImage();

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
                drawable = R.drawable.button_wish_bike;
            }
        });

        ImageView clothesPic = view.findViewById(R.id.clothes_edit);
        clothesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_clothes);
                drawable = R.drawable.button_wish_clothes;
            }
        });

        ImageView gadgetsPic = view.findViewById(R.id.gadgets_edit);
        gadgetsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_gadgets);
                drawable = R.drawable.button_wish_gadgets;
            }
        });

        ImageView gamesPic = view.findViewById(R.id.games_edit);
        gamesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_games);
                drawable = R.drawable.button_wish_games;
            }
        });

        ImageView giftPic = view.findViewById(R.id.gift_edit);
        giftPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_gift);
                drawable = R.drawable.button_wish_gift;
            }
        });

        ImageView holidayPic = view.findViewById(R.id.holiday_edit);
        holidayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_holiday);
                drawable = R.drawable.button_wish_holiday;
            }
        });

        ImageView iceSkatePic = view.findViewById(R.id.iceskate_edit);
        iceSkatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_iceskate);
                drawable = R.drawable.button_wish_iceskate;
            }
        });


        ImageView petsPic = view.findViewById(R.id.pets_edit);
        petsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_pets);
                drawable = R.drawable.button_wish_pets;
            }
        });

        ImageView scooterPic = view.findViewById(R.id.scooter_edit);
        scooterPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_scooter);
                drawable = R.drawable.button_wish_scooter;
            }
        });

        ImageView shoesPic = view.findViewById(R.id.shoes_edit);
        shoesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_shoes);
                drawable = R.drawable.button_wish_shoes;
            }
        });

        ImageView otherPic = view.findViewById(R.id.dream_edit);
        otherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_dream);
                drawable = R.drawable.button_wish_dream;
            }
        });


        activateOnExitEditWish();
        activateOnSaveEditWish();
        activateDeleteWish();
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
        int newDrawable = drawable;

        if (meditTitle.getText().toString() == wish2Edit.getTitle())
            newTitle = wish2Edit.getTitle();
        else newTitle = meditTitle.getText().toString();

        if (meditCost.getText().toString() == String.valueOf(wish2Edit.getCost()))
            newCost = wish2Edit.getCost();
        else newCost = Integer.parseInt(meditCost.getText().toString());

        if (drawable == wish2Edit.getImage())
            newDrawable = wish2Edit.getImage();
        else newDrawable = drawable;

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
                    imageOptionSelection();
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);*/
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


    private void imageOptionSelection()
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        //fileUri= FileProvider.getUriForFile(getActivity(), AUTHORITY, output);

        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                // Error occurred while creating the File
                ex.getStackTrace();
                Log.i(TAG, "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri outPutFileUri = null;
                File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                outPutFileUri = Uri.fromFile(new File(storageDir.getPath(),"profile.png"));
              //  cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outPutFileUri);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);


            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = "22-02-2020";
        String imageFileName = "JPEG_" + timeStamp + "_";
        /*File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);*/
        //File storageDir = new File("storage/emulated/0/DCIM/Camera");
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                //getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println(storageDir);
        if (storageDir.exists())
            System.out.println("directory exist");
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        System.out.println("done creting file");
        return image;
    }

    private Bitmap mImageBitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        System.out.println("inside activity result method");
        System.out.println("ok= " + getActivity().RESULT_OK);
        System.out.println("request code: " + requestCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
                editWishPicture.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == REQUEST_CHOOSE_IMAGE_FROM_GALLERY && resultCode == getActivity().RESULT_OK)
        {
            try{
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                editWishPicture.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //create file Uri to store image
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    //returning image
    private File getOutputMediaFile(int type) {

        //External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        //create the storage directory if it does not exist
        if(!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                //Log.d(IMAGE_DIRECTORY_NAME, "Failed create" + UserFunctions.IMAGE_DIRECTORY_NAME + "directory");
                return null;
            }
        }

        //Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    editWishPicture.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");
                editWishPicture.setImageBitmap(thumbnail);
            }
        }
    }

*/
    }
