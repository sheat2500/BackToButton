package me.solebrity.rindt.testallthirdpartylogin;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {


    private FloatingActionButton fab;

    private ListView lv;

    // Default Direction is to down (False);
    private boolean scrollDirection = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (fab.isVisible()) {
                    final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                    fab.startAnimation(anim);
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        lv = (ListView) findViewById(R.id.list);
        fab.attachToListView(lv, new ScrollDirectionListener() {

            Timer timer = new Timer();
            boolean isTimeStart = false;

            @Override
            public void onScrollDown() {
                if (scrollDirection == false) {
                    fab.setVisibility(View.VISIBLE);
                    final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    fab.startAnimation(anim);
                    scrollDirection = true;
                    if (isTimeStart == false) {
                        isTimeStart = true;
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                System.out.println("Timer Runnning");
                                isTimeStart = false;
                                mHandler.sendEmptyMessage(0);
                            }
                        }, 3000);
                    }
                }
            }

            @Override
            public void onScrollUp() {
                if (fab.isVisible()) {
                    if (scrollDirection == true) {
                        fab.setVisibility(View.INVISIBLE);
                        final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                        fab.startAnimation(anim);
                    }
                }
                scrollDirection = false;
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
