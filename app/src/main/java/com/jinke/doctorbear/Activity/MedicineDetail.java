package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.jinke.doctorbear.Bean.MedicineDetailBean;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by wyf on 2016/6/21.
 */
public class MedicineDetail extends Activity {

    private TextView mMedicineTitle_textView;
    private TextView mMedicineName_textView;
    private TextView mMedicineDescription_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_medicine_detail);
        //初始化控件
        initView();
        //初始化链接数据
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        mMedicineName_textView = (TextView) findViewById(R.id.search_medicine_name_tv);
        mMedicineTitle_textView =(TextView)findViewById(R.id.search_medicine_title_tv);
        mMedicineDescription_textView = (TextView) findViewById(R.id.search_medicine_symptoms_tv);
    }

    /**
     * 绑定数据，建立连接
     */
    private void initData(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("MedicineID");
        System.out.println("In DiseaseDetail MedicineID is :"+id);
        String url = GlobalAddress.SERVER + "/doctuser/medicine.php?" + "MedicineID=" + id ;
        getDiseaseDetail(this, url);
    }

    /**
     * 建立连接，重写成功和失败的方法
     * @param context
     * @param url
     */
    public void getDiseaseDetail(final Context context, String url) {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseDiseaseDetailData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
    }

    /**
     * 链接成功，获取数据后对数据进行处理
     * 由于只有一个数据，所以可以不用采用列表的形式，只用一个类来接受
     * @param result
     */
    private void parseDiseaseDetailData(String result) {
        Gson gson = new Gson();
        MedicineDetailBean medicineValue = gson.fromJson(result, MedicineDetailBean.class);
        mMedicineTitle_textView.setText(medicineValue.MedicineName);
        mMedicineName_textView.setText(medicineValue.MedicineName);
        //System.out.println();

        String strHtml=new String (medicineValue.MedicineCont);

        strHtml = strHtml.replace("\n","<br>");
        strHtml = strHtml.replace("<bold>","<font color='#000000'><b>");
        strHtml = strHtml.replace("</bold>","</b></font>");
        CharSequence charSequence=Html.fromHtml(strHtml);

        mMedicineDescription_textView.setText(charSequence);
    }
}
