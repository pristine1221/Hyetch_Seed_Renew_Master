package com.example.pristineseed.global;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.model.menu.MenuData;
import com.example.pristineseed.ui.bootmMainScreen.ui.menuHandler.MenuMainPageFragment;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class StaticMethods {
    public static String removeDecimal(float number) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(number);
    }
    public static String AddZero(int number) {
        DecimalFormat df = new DecimalFormat("##");
        return df.format(number);
    }
    public static String removeDecimalKG(double number) {
        DecimalFormat df = new DecimalFormat("###.###");
        return df.format(number);
    }
    public static String removeDecimal(String number) {
        float decimalvalue = Float.parseFloat(number);
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(decimalvalue);
    }
    public static void loadFragmentsWithBackStack(FragmentActivity activity, Fragment selectedFragment, String fragment_tag) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, selectedFragment, fragment_tag)
                .addToBackStack(null)
                .commit();
    }
    public static void loadFragments(FragmentActivity activity, Fragment selectedFragment, String fragment_tag) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, selectedFragment, fragment_tag)
                .commit();
    }

    public static int getRandomColor(){
        Random rnd = new Random();
        return  Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static int convertValueToInt(String value){
        int convValue=0;
        try {
            convValue = Integer.parseInt(value);
            return convValue;
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            return convValue;
        }

    }

    public  static String covertBase64File(String img_file){
            Bitmap bm = getResizedBitmap(BitmapFactory.decodeFile(img_file), 400);
            if (bm != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteArrayImage = baos.toByteArray();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            }
        return null;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String convertBase64(String imgage_file) {
        Bitmap bm = BitmapFactory.decodeFile(imgage_file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bm != null) {
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        }
        return null;
    }

    public static void showMDToast(Context context,String message,int error_type){
        if(context!=null) {
            MDToast mdToast = MDToast.makeText(context, message, MDToast.LENGTH_SHORT,error_type);
            mdToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 50);
            mdToast.show();
        }
    }

    public static void showMDToastOnTop(Context context,String message,int error_type){
        if(context!=null) {
            MDToast mdToast = MDToast.makeText(context, message, MDToast.LENGTH_SHORT,error_type);
            mdToast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 50);
            mdToast.show();
        }
    }
    public static int generateRandomNumber(){
        Random random = new Random();
//return the next pseudorandom integer value
        int val = random.nextInt(1000);
        Log.e("rand_num",String.valueOf(val));
        return val;

    }
    public static void showMessage(Context context,String message, int type) {
        if(context!=null) {
            MDToast.makeText(context, message, MDToast.LENGTH_SHORT, type).show();
        }
    }

    public static void LoadMenuChidData(SessionManagement sessionManagement, FragmentActivity fragmentActivity, String group_name, String child_name, String pass_key_to_child_fragment, String pass_data_to_child_fragment) throws Exception {
        MenuData selected_Group = null;
        String selected_Group_child = null;
        List<MenuData> model = new Gson().fromJson(sessionManagement.getMenu(), new TypeToken<List<MenuData>>() {
        }.getType());

        for (int i = 0; i < model.size(); i++) {
            if (model.get(i).title.equalsIgnoreCase(group_name)) {
                selected_Group = model.get(i);
                selected_Group_child = child_name;
                break;
            }
        }
        if(selected_Group_child==null){
            MDToast.makeText(fragmentActivity,"Menu Does Not Have Sub Menu.",MDToast.TYPE_ERROR).show();
            return;
        }
        if(selected_Group==null){
            MDToast.makeText(fragmentActivity,"Menu Does Not Have Menu Group.",MDToast.TYPE_ERROR).show();
            return;
        }

        if (selected_Group != null && selected_Group_child != null) {
            sessionManagement.setSelectedGroupMenuName(group_name);
            sessionManagement.setSelectedSubGroupMenuName(child_name);
            MenuMainPageFragment menuMainPageFragment = MenuMainPageFragment.newInstance(pass_key_to_child_fragment);
            Bundle bundle = new Bundle();
            bundle.putString("selected_Group", new Gson().toJson(selected_Group));
            bundle.putString("selected_Group_child", selected_Group_child);
            bundle.putString(pass_key_to_child_fragment, pass_data_to_child_fragment);
            menuMainPageFragment.setArguments(bundle);
            loadFragments(menuMainPageFragment, "MenuMainFragment",fragmentActivity);
        }

    }
    public static void loadFragments(Fragment selectedFragment, String fragment_tag,FragmentActivity fragmentActivity) {
        boolean check_verify=false;
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible()) {
                    if(fragment.getTag()!=null && fragment.getTag().equalsIgnoreCase(fragment_tag)){
                        check_verify=true;
                        break;
                    }
                }
            }
        }
        if(check_verify){
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment, fragment_tag).commit();
        }else{
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment, fragment_tag)
                    .addToBackStack(null).commit();
        }
    }
}
