package com.kdlc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.InputStream;
/*
* DOTO
* 这个dome主要是把获得的图片保存到本地学习
* 1、拍照————int  PHONE = 101;直接设置到img上
* 2、从相册获取-----裁剪-----保存-----现实到img中
*
* */

public class MainActivity extends Activity implements View.OnClickListener{
    private Button btn;
    private Button btn2;
    private Button btn3;
    private ImageView img;
    private ImageView gif;
    public String filePath;
    private int  CAMERACAPTURETASL = 100;
    private int  PHONE = 101;
    private int  PHONE_TAILOR = 102;
    private String mTempPhotoPath;
    // 照片所在的Uri地址
    Bitmap bm;//要处理的图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setview();
        //设置gif
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(this).load(R.drawable.gifpicture).apply(options).into(gif);

    }
    private void setview() {
        btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(this);

        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        img = (ImageView) findViewById(R.id.img);
        gif=(ImageView)findViewById(R.id.gif);
        Bitmap bitmap0=BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        img.setImageBitmap(bitmap0);
        //保存图片的路径
        // Environment.getExternalStorageDirectory()：获取的目录中新建一个hkapp文件夹
        filePath = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/A.png";

        //读取保存的位图（图片）
        Bitmap bitmap1= readImg();
        if(bitmap1!=null){
            img.setImageBitmap(bitmap1);
        }else{
            return;
        }
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            //TODO打开手机图库
            case R.id.btn1:
                Log.i("Text","btn1:.........");
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,CAMERACAPTURETASL);
                break;
            //TODO拍照
            /*
            *  MediaStore这个类是android系统提供的一个多媒体数据库，android中多媒体信息都可以从这里提取。
            * */

            case R.id.btn3:
                int width = bm.getWidth();
                int height = bm.getHeight();
                int newWidth = 300;
                int newHeight = 300;
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
                int color0;
                int color1;
                int color2;
                int color3;
                int color4;
                int color5;
                int colorb;

                colorb=bm.getPixel(150,10);
                color1=bm.getPixel(15,150);
                color0=bm.getPixel(250,150);
                color2=bm.getPixel(80,150);
                color3=bm.getPixel(130,150);
                color4=bm.getPixel(200,150);
                color5=bm.getPixel(290,150);

                int gb=255-Color.green(colorb);
                int rb=255-Color.red(colorb);
                int bb=255-Color.blue(colorb);

                int R= 0-Color.red(color0);
                int G= 0-Color.green(color0);
                int B=255-Color.blue(color0);

                int r1=Color.red(color1);
                int b1=Color.blue(color1);
                int g1=Color.green(color1);

                int r2=Color.red(color2);
                int b2=Color.blue(color2);
                int g2=Color.green(color2);

                int r3=Color.red(color3);
                int b3=Color.blue(color3);
                int g3=Color.green(color3);

                int r4=Color.red(color4);
                int b4=Color.blue(color4);
                int g4=Color.green(color4);

                int r5=Color.red(color5);
                int b5=Color.blue(color5);
                int g5=Color.green(color5);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERACAPTURETASL) {
            if (data != null) {
                Uri uri = data.getData();
                Log.i("Text","uri:........."+uri);
                getImg(uri);//对获取的uri进行处理。这里进行了裁剪处理，没有直接通过图片工具进行直接给img设置
            } else {
                return;
            }
        }
        /*裁剪图片之后*/
        if(requestCode==UCrop.REQUEST_CROP&&resultCode==RESULT_OK)
        {
            Uri croppedFileUri = UCrop.getOutput(data);
            Bitmap selectBitmap = BitmapFactory.decodeFile(croppedFileUri.getPath());
            bm=selectBitmap;
            img.setImageBitmap(bm);
        }
        /*if (requestCode == PHONE_TAILOR) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                //得到图片
                Uri uri = data.getData();
                Bitmap bitmaps = bundle.getParcelable("data");
                Log.i("Text","bitmaps：-------------"+bitmaps);
                //保存到图片到本地--才见过的图片
                cutImg(uri);
                saveImg(bitmaps);//获得的图片进行保存，方便下次登录直接用，不用多次下载浪费青春
                //设置图片
                img.setImageBitmap(bitmaps);
            } else {
                return;
            }
        }*/
        if (requestCode == PHONE) {
            if (data != null) {

                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");


                img.setImageBitmap(bitmap);

            } else {
                return;
            }
        }
    }
    //读取位图（图片）
    private Bitmap readImg() {
        File mfile = new File(filePath);
        Bitmap bm = null;
        if (mfile.exists()) {        //若该文件存在
            bm = BitmapFactory.decodeFile(filePath);
        }
        return bm;
    }

    private void getImg(Uri uri) {
        //把获得图片进行裁剪保存，把裁剪处理过的图片进行保存到内存和现实到img
        if(uri!=null){
            try {
                InputStream inputstream = getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputstream);//从输入流中解码位图
//                Log.i("Text","bitmap________:"+bitmap);
//                img.setImageBitmap(bitmap);
                cutImg(uri);
                //关闭流
                inputstream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Log.i("Text","uri为空了");
        }

    }

    //对图片裁剪
    /*private void cutImg(Uri uri) {//调用以下代码会跳转到Android系统自带的一个图片剪裁页面，点击确定之后就会得到一张图片。
        if(uri!=null){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //true:出现裁剪的框
        intent.putExtra("crop", "true");
        //裁剪宽高时的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪后的图片的大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);  // 返回数据
        intent.putExtra("output", uri);
        intent.putExtra("scale", true);
        startActivityForResult(intent, PHONE_TAILOR);
    } else {
        return ;
    }
}*/

    private void cutImg(Uri uri) {
        Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(),"SampleCropImage.jpg"));
        UCrop uCrop = UCrop.of(uri,mDestinationUri).withAspectRatio(5,5);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);


        options.setAllowedGestures(UCropActivity.SCALE,UCropActivity.ALL,UCropActivity.NONE);
        options.setMaxScaleMultiplier(20);
        options.setShowCropFrame(true);


        uCrop.withOptions(options);

        try {
            Thread.sleep(200);//休眠3秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        uCrop.start(MainActivity.this);

    }


}