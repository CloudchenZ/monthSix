package com.example.yuekao0316.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.yuekao0316.R;
import com.example.yuekao0316.adapter.PopwindowAdapter;
import com.example.yuekao0316.db.DaoMaster;
import com.example.yuekao0316.db.DaoSession;
import com.example.yuekao0316.db.GoodsEntity;
import com.example.yuekao0316.db.GwcEntity;
import com.example.yuekao0316.pay.AuthResult;
import com.example.yuekao0316.pay.PayResult;
import com.example.yuekao0316.pay.util.OrderInfoUtil2_0;

import java.util.List;
import java.util.Map;

public class MoreActivity extends AppCompatActivity {

    private ImageView morephoto;
    private TextView moreprice;
    private Button entergwc;
    private Button checkon;
    private ImageView moreMenu;
    private TextView mua;
    private String pic;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        initView();
        final Intent intent = getIntent();
        pic = intent.getStringExtra("pic");
        title = intent.getStringExtra("title");
        Glide.with(this)
                .load(pic)
                .transform(new CenterCrop())
                .into(morephoto);

        moreprice.setText(title + "价格：1000");
        moreMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindow popupWindow = new PopupWindow();
                popupWindow.setOutsideTouchable(true);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                View pophistory = getLayoutInflater().inflate(R.layout.popwindoshistory, null);
                TextView gwclist = pophistory.findViewById(R.id.historyGwc);
                gwclist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        PopupWindow windowgwc = new PopupWindow();
                        windowgwc.setOutsideTouchable(true);
                        windowgwc.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        windowgwc.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                        View popgwc = getLayoutInflater().inflate(R.layout.popwindosgwc, null);
                        RecyclerView rvhistory = popgwc.findViewById(R.id.rv_gwc);
                        DaoSession daoSession = DaoMaster.newDevSession(MoreActivity.this, "goods.db");
                        List<GoodsEntity> goodsEntities = daoSession.loadAll(GoodsEntity.class);
                        PopwindowAdapter popwindowAdapter = new PopwindowAdapter(R.layout.popwindoslist, goodsEntities);
                        rvhistory.setAdapter(popwindowAdapter);
                        rvhistory.setLayoutManager(new LinearLayoutManager(MoreActivity.this));
                        windowgwc.setContentView(popgwc);
                        windowgwc.showAsDropDown(moreMenu);
                    }
                });
                popupWindow.setContentView(pophistory);
                popupWindow.showAsDropDown(moreMenu);
            }
        });
        DaoSession daoSession = DaoMaster.newDevSession(MoreActivity.this, "akb.db");
        List<GwcEntity> gwcEntities = daoSession.loadAll(GwcEntity.class);
        int size = gwcEntities.size();
        mua.setText(size+"");

        entergwc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gwcinit();
            }
        });
        checkon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payV2();
                Intent intent1 = new Intent(MoreActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    private int i = 1;
    private void gwcinit() {
        PopupWindow buttomPop = new PopupWindow();
        buttomPop.setOutsideTouchable(true);
        buttomPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        buttomPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View bottomPop = getLayoutInflater().inflate(R.layout.bottompopwindos, null);
        ImageView imageView = bottomPop.findViewById(R.id.ivplus);
        final TextView textView = bottomPop.findViewById(R.id.tvnumm);
        Button button = bottomPop.findViewById(R.id.bton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(i+"");
                    }
                });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoSession daoSession = DaoMaster.newDevSession(MoreActivity.this, "akb.db");
                GwcEntity gwcEntity = new GwcEntity();
                gwcEntity.setPic(pic);
                gwcEntity.setTitile(title);
                gwcEntity.setNum(Integer.parseInt(textView.getText().toString()));
                daoSession.insert(gwcEntity);
                Intent intent = new Intent(MoreActivity.this, GwcActivity.class);
                startActivity(intent);
            }
        });
        buttomPop.setContentView(bottomPop);
        buttomPop.showAsDropDown(moreprice,0,0, Gravity.BOTTOM);
    }

    private void initView() {
        morephoto = findViewById(R.id.morephoto);
        moreprice = findViewById(R.id.moreprice);
        entergwc = findViewById(R.id.entergwc);
        checkon = findViewById(R.id.checkon);
        moreMenu = findViewById(R.id.more_menu);
        mua = findViewById(R.id.mua);
    }


    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2021000116676519";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088522585386983";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "123468799";

    /**
     * pkcs8 格式的商户私钥。
     * <p>
     * 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * RSA2_PRIVATE。
     * <p>
     * 建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCGPjz09RdGz0l/0B1NMy/o+ykCDGDe50wHONStjk/c3j3MCOcv+AM1jvnL6tNk6+6zjxltoGIGOJsyIggZfXBBuGup+CmIclGWvsVO7BnxoByM9u5MnLMfNZFclzesaLHUI0ttwnBl/PLJ4N5XLxsuj2muz9j80CdEcwdk5iy4Pduws/4RvhJifO8LW2wCxSj6hlLqsSSb66BJNbhZITScP2OleGW/+KorD8Kqzt1Njx51nEPa91597jbomwTUOxXMumpKM8sxWhk28vzLqfQd14smI2yIgusK+xtEjOe4tTzbUNe5AQSKXQHuauMdhIEIOLhDHHHjRG6JcX9/fhH5AgMBAAECggEAPfNS5iiFCWR3oshD/XB116qwjgVwXu2WpG6Rl1ZpfKADHLmvFu/5ayoEFRrVW2mVyr9rGLA/DGfrFwOlgT3mJC4bYWDoqdM3271GCNnkTyF4i4UZC7n035406unOA359Mkznbkug2zAUSx2+/bT7ck00lDvPAzx8IpaC/wlJ8evOuJBIHKpW6tTOrdaQwaPxQ3jHpteUGrPWJ5pTX1/ut8AFUFDE23tmUGenMYnjYkQ1hlS4zSzDBhv3tL7Luqr3gTNPSapjXxqVitJ7/+5CMyuiEp/dQ7s5ba+MAnvLxoKHUeCBTkZK5hBm3PrLV5t6GXNjtLARhmsMtsRDY+24QQKBgQDC+zE/Al7sSF9bkbk5U+PgoSL9x3xZPJMKm90R/rAaGRVjpWlaDTkC1gx2HOTquUlp2cNnnsbJaYRFuc6tnxdz73JYG+zQ+RNZsk898NllXi2jj/szj5WadXNBScqrh3TczHyxVcLTQHBbvp5kGJG/Z3w1rZOFIQePpVqJpd7hJQKBgQCwQQw83kHDf2u9xnJeW69ioL5ITv23aSpT1AIMwluWB2Par5g6a4sOmbpWqvRvjC8Fvqr9d7LmYdsDJ8S39hKmq7k4XUIGf2U2/a59DF0VfcK2RS+0wq6znGVEphTlqnGFgFsdW6DYPUR4BtEQc+ko/QVC1DU1kO+L48bVjEfnRQKBgQCEdvth/05V4GVIZJwd4mxyc4a+OwSz639KOdHbI9ioIlpxtDL6xvjwgFM++ypafT7u9s7WWvxaYaFHbZCITAk27noUegRNhKosygMj5bkMMQt4j5ztzqKoqRqARI24MvKl/xbHs6gEtUagTcR15GtASiIMpbwPOf1VDdWQpmvhMQKBgBSlegaDTpbVDUzsuyb8fH4EMGYOAsCCbDfnpNnTxqC7euH6Ic3uHRr3vRE782hBtpxoqmUIqYc9PUeQWAXoqGSnxwmoOvzB3ZxkeRUi5qCG29E4J494IrjdgCtbfSUuIyAtWf8FZcEO5D9fJQxVV6G6iew7KYR91uG/K+KGDUI1AoGBAKTjfrkIprLsMsWGasaoCvjPaWg5VQojt1L4CtI8g/BLC6l3kvVXHRGgkhtTH9d5YA5jhkpZ3cIxcNk75GLb8EfrnLTK5l+3ZcIrCAv+KetHjf2JvsVR+6EkFhZqy4jJFl4NqclkOOQmQ/J9cOiIlusZQCBrn+sPkPcZrgGYq5uy";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(MoreActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(MoreActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(MoreActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(MoreActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 支付宝支付业务示例
     */
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MoreActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务示例
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            showAlert(this, getString(R.string.error_auth_missing_partner_appid_rsa_private_target_id));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(MoreActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * 获取支付宝 SDK 版本号。
     */
    public void showSdkVersion(View v) {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        showAlert(this, getString(R.string.alipay_sdk_version_is) + version);
    }

    /**
     * 将 H5 网页版支付转换成支付宝 App 支付的示例
     */

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            sb.append(key).append("=>").append(bundle.get(key)).append("\n");
        }
        return sb.toString();
    }


}
