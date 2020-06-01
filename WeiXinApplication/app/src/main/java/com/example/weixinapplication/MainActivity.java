package com.example.weixinapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts.Data;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity implements View.OnClickListener {

    private Fragment mTab01 = new weixinFragment();
    private Fragment mTab02 = new frdFragment();
    private Fragment mTab03 = new contactFragment();
    private Fragment mTab04 = new settingsFragment();

    private FragmentManager fm;

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabContact;
    private LinearLayout mTabSettings;

    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgContact;
    private ImageButton mImgSettings;
    private ImageButton mImgAdd;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "未授权，无法实现预定的功能！", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            initView();
            initFragment();
            selectFragment(0);
            initEvent();
        }
    }

    private void initFragment(){
        fm =getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.add(R.id.id_content,mTab01);
        transaction.add(R.id.id_content,mTab02);
        transaction.add(R.id.id_content,mTab03);
        transaction.add(R.id.id_content,mTab04);
        transaction.commit();
    }

    private void initView(){
        mTabWeixin=(LinearLayout)findViewById(R.id.id_tab_weixin);
        mTabFrd=(LinearLayout)findViewById(R.id.id_tab_frd);
        mTabContact=(LinearLayout)findViewById(R.id.id_tab_contact);
        mTabSettings=(LinearLayout)findViewById(R.id.id_tab_settings);

        mImgWeixin=(ImageButton)findViewById(R.id.id_tab_weixin_img);
        mImgFrd=(ImageButton)findViewById(R.id.id_tab_frd_img);
        mImgContact=(ImageButton)findViewById(R.id.id_tab_contact_img);
        mImgSettings=(ImageButton)findViewById(R.id.id_tab_settings_img);
        mImgAdd=(ImageButton)findViewById(R.id.id_add_img);
    }

    private void hideFragment(FragmentTransaction transaction){
        transaction.hide(mTab01);
        transaction.hide(mTab02);
        transaction.hide(mTab03);
        transaction.hide(mTab04);
    }

    private void selectFragment(int i){
        FragmentTransaction transaction=fm.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:
                transaction.show(mTab01);
                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                transaction.show(mTab02);
                mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 2:
                transaction.show(mTab03);
                mImgContact.setImageResource(R.drawable.tab_address_pressed);
                break;
            case 3:
                transaction.show(mTab04);
                mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        resetImgs();
        switch(v.getId()){
            case R.id.id_tab_weixin:
                selectFragment(0);
                break;
            case R.id.id_tab_frd:
                selectFragment(1);
                break;
            case R.id.id_tab_contact:
                selectFragment(2);
                break;
            case R.id.id_tab_settings:
                selectFragment(3);
                break;
            case R.id.id_add_img:
                final View addDialog=getLayoutInflater().inflate(R.layout.add_contact,null);

                new AlertDialog.Builder(MainActivity.this).setView(addDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText mname=(EditText)addDialog.findViewById(R.id.name);
                        EditText mphone=(EditText)addDialog.findViewById(R.id.phone);
                        final String name=mname.getText().toString();
                        final String phone=mphone.getText().toString();
                        // 创建一个空的ContentValues
                        ContentValues values = new ContentValues();
                        // 向RawContacts.CONTENT_URI执行一个空值插入
                        // 目的是获取系统返回的rawContactId
                        Uri rawContactUri = getContentResolver().insert(
                                ContactsContract.RawContacts.CONTENT_URI, values);
                        long rawContactId = ContentUris.parseId(rawContactUri);
                        values.clear();
                        values.put(Data.RAW_CONTACT_ID, rawContactId);
                        // 设置内容类型
                        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                        // 设置联系人名字
                        values.put(StructuredName.GIVEN_NAME, name);
                        // 向联系人URI添加联系人名字
                        getContentResolver().insert(ContactsContract
                                .Data.CONTENT_URI, values);
                        values.clear();
                        values.put(Data.RAW_CONTACT_ID, rawContactId);
                        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                        // 设置联系人的电话号码
                        values.put(Phone.NUMBER, phone);
                        // 设置电话类型
                        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
                        // 向联系人电话号码URI添加电话号码
                        getContentResolver().insert(ContactsContract
                                .Data.CONTENT_URI, values);
                        values.clear();

                        selectFragment(2);
                        Toast.makeText(MainActivity.this, "联系人数据添加成功",
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            default:
                break;
        }

    }

    private void resetImgs(){
        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
        mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
        mImgContact.setImageResource(R.drawable.tab_address_normal);
        mImgSettings.setImageResource(R.drawable.tab_settings_normal);
    }

    private void initEvent(){
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabContact.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);
        mImgAdd.setOnClickListener(this);
    }
}
