package cn.com.oasis;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.com.oasis.service.TestService;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity---->";
    private Button btnBind;
    private Button btnUnBind;
    private Button btnStart;
    private Button btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_test);
        //onbind();
        getWindow().getDecorView().getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("wwww", "changed");
                View view = findViewById(R.id.btn);
                view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                    @Override
                    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                        super.onInitializeAccessibilityNodeInfo(host, info);
                    }

                    @Override
                    public void sendAccessibilityEvent(View host, int eventType) {
                        if (host instanceof TextView) {
                            Log.e("wwww", ((TextView) host).getText().toString() + eventType);
                        }

                        super.sendAccessibilityEvent(host, eventType);
                    }

                });
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("wwww", "click");

            }
        });

        final LinearLayout rootView = (LinearLayout) findViewById(R.id.root_view);
        findViewById(R.id.root_view).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("wwww", "add");
                rootView.addView(new TextView(MainActivity.this));
            }
        }, 2000);
    }

    private class TrackAcceDelegate extends View.AccessibilityDelegate {
        private View.AccessibilityDelegate mRealDelegate;

        public TrackAcceDelegate(View.AccessibilityDelegate mRealDelegate) {
            this.mRealDelegate = mRealDelegate;
        }

        public View.AccessibilityDelegate getRealDelegate() {
            return mRealDelegate;
        }

        @Override
        public void sendAccessibilityEvent(View host, int eventType) {
            if (host instanceof TextView) {
                Log.e("wwww", ((TextView) host).getText().toString() + eventType);
            }
            if (null != mRealDelegate) {
                mRealDelegate.sendAccessibilityEvent(host, eventType);
            }
        }

    }

    private View.AccessibilityDelegate getOldDelegate(View v) {
        View.AccessibilityDelegate ret = null;
        try {
            Class<?> klass = v.getClass();
            Method m = klass.getMethod("getAccessibilityDelegate");
            ret = (View.AccessibilityDelegate) m.invoke(v);
        } catch (NoSuchMethodException e) {
            // In this case, we just overwrite the original.
        } catch (IllegalAccessException e) {
            // In this case, we just overwrite the original.
        } catch (InvocationTargetException e) {
            Log.w(TAG, "getAccessibilityDelegate threw an exception when called.", e);
        }

        return ret;
    }


    public void onbind() {
        findViewById(R.id.bt_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onbinservicee();
            }
        });
        findViewById(R.id.bt_unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onuninservice();
            }
        });

        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartSerive();
            }
        });

        findViewById(R.id.bt_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopSerive();
            }
        });
    }

    private void onuninservice() {
        Log.w(TAG, "onunbinservice");
        Intent intent = new Intent(this, TestService.class);
        unbindService(connetion);
    }

    private void onbinservicee() {
        Log.w(TAG, "onbinservice");
        Intent intent = new Intent(this, TestService.class);
        bindService(intent, connetion, Context.BIND_AUTO_CREATE);
    }

    private void onStartSerive() {
        Log.w(TAG, "onStartSerivee");
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }

    private void onStopSerive() {
        Log.w(TAG, "onStopSerive");
        Intent intent = new Intent(this, TestService.class);
        stopService(intent);
    }

    private ServiceConnection connetion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
