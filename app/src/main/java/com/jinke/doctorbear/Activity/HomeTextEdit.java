package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.NiceSpinner;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyf on 2016/5/17.
 * 连接home_textedit.xml即主页跳转提问页面
 */
public class HomeTextEdit extends Activity implements View.OnClickListener {
    private static final String[] diseasesType={"精神","普外科","骨科","眼科","耳鼻喉科"};
    private TextView cancel_textV ;
    private TextView  submit_textV;
    private TextView  count_textV;
    private EditText title_editT;
    private EditText main_editT;
    private NiceSpinner niceSpinner;
    private ImageView add_imageV;
    private ImageView set_imageV;
    private ImageView keyboard_imageV;
    private Bitmap bitmap;

    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_edit);
        //初始化控件
        initView();
        //初始化监听
        initListener();

    }

    /**
     * 初始化控件
     * 获取控件id并初始化spinner
     */
    private void initView()
    {
        cancel_textV = (TextView)findViewById(R.id.home_edit_cancel_tv) ;
        submit_textV = (TextView)findViewById(R.id.home_edit_submit_tv);
        count_textV = (TextView)findViewById(R.id.home_edit_titleBar_count_tv);

        title_editT = (EditText)findViewById(R.id.home_edit_title_editText);
        main_editT = (EditText)findViewById(R.id.home_edit_main_editText);

        add_imageV =(ImageView)findViewById(R.id.home_edit_add_iV) ;
        set_imageV = (ImageView)findViewById(R.id.home_edit_set_iV);
        keyboard_imageV = (ImageView)findViewById(R.id.home_edit_keybroad_iV);

        niceSpinner = (NiceSpinner) findViewById(R.id.home_edit_spinner);
        //将可选内容与ArrayAdapter连接起来
        List<String> dataset = new LinkedList<>(Arrays.asList("疾病分类", "普外科", "骨科",
                "眼科", "消化科","心脏内科", "神经内科","儿科","皮肤科"," 肿瘤科"));
        niceSpinner.attachDataSource(dataset);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        cancel_textV.setOnClickListener(this);
        submit_textV.setOnClickListener(this);
        add_imageV.setOnClickListener(this);
        set_imageV.setOnClickListener(this);
        keyboard_imageV.setOnClickListener(this);

    }

    /**
     * 监听事件的实现
     * 取消的监听事件,搜索框的监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_edit_cancel_tv:
                finish();
//                overridePendingTransition(R.anim.alphaout,R.anim.alphain);
                break;
            case R.id.home_edit_submit_tv:
                Toast.makeText(v.getContext(), "提交到数据库", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.home_edit_add_iV:
                goPhotoChooser();
                break;
        }

    }

    /**
     * 打开系统相册
     */
    private void goPhotoChooser(){
        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
        getImage.addCategory(Intent.CATEGORY_OPENABLE);
        getImage.setType("image/*");
        //startActivityForResult(Intent.createChooser(getImage,"选择图片"), 2);
        //onActivityResult(1,1,getImage);
        startActivityForResult(getImage, 1);
    }

    /**
     * 调用相册后获取图片.
     * 调用到的自定义函数有getBitmapMime/insertIntoEditText/resizeImage
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            // TODO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, intent);

            ContentResolver resolver = getContentResolver();
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    Uri originalUri = intent.getData();
                    Toast.makeText(HomeTextEdit.this, originalUri.toString(), Toast.LENGTH_SHORT).show();
                    try {
                        Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                    //    bitmap = originalBitmap;
                        bitmap = resizeImage(originalBitmap, 100, 100);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null) {
                        insertIntoEditText(getBitmapMime(bitmap, originalUri));
                    } else {
                        Toast.makeText(HomeTextEdit.this, "获取图片失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (bitmap != null) {
            }

        }


    /**
     * 建立SpannableString,从SD卡获得图片，使用setSpan设置插入位置
   除缓冲区里原有的text，这里即为path。
     * @param pic
     * @param uri
     * @return  SpannableString
     */
    private SpannableString getBitmapMime(Bitmap pic, Uri uri) {
        String path = uri.getPath();
        SpannableString spannableString = new SpannableString(path);
        ImageSpan span = new ImageSpan(this, pic);
        //4个参数，插入的对象span图片，起始位置0，终止位置path长度，标记为path
        spannableString.setSpan(span, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 将图片插入EditText
     * @param spannableString
     */
    private void insertIntoEditText(SpannableString spannableString) {
        Editable et = main_editT.getText();// 先获取Edittext中的内容
        int start = main_editT.getSelectionStart();
        et.insert(start, spannableString);// 设置ss要添加的位置
        main_editT.setText(et);// 把et添加到Edittext中
        main_editT.setSelection(start + spannableString.length());// 设置Edittext中光标在最后面显示
    }

    /**
     * 等比例缩放bitmap图像
     * @return Bitmap
     */
    public Bitmap resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

}
