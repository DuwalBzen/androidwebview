package np.com.bijenduwal.aafnobrowser;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frame_presearch_layout_id;
private EditText baseUrl,redirectUrl,generatedResult;
private Button generateBtn,generate_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        baseUrl=findViewById(R.id.base_url_id);
        redirectUrl=findViewById(R.id.redirect_url_id);
        generateBtn=findViewById(R.id.generate_redirect_url_id);
        generate_id=findViewById(R.id.generate_id);
        generatedResult=findViewById(R.id.generate_url_id);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frame_presearch_layout_id = findViewById(R.id.main_frame_layout_id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InetAddress urlAddress = null;
                String ipAddr = "";
                try {

                    urlAddress = InetAddress.getByName(redirectUrl.getText().toString());

                    byte[] addr = urlAddress.getAddress();

                    // Convert to dot representation

                    for (int i = 0; i < addr.length; i++) {
                        if (i > 0) {
                            ipAddr += ".";
                        }
                        ipAddr += addr[i] & 0xFF;
                    }



                }
                catch (UnknownHostException e) {
                    System.out.println("Host not found: " + e.getMessage());
                }



                InetAddress address = null;
                try {
                     address = InetAddress.getByName(redirectUrl.getText().toString());
                }catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                String host=String.valueOf(address.getHostAddress());




                String[] ipAddressInArray = host.split("\\.");

                long result = 0;
                for (int i = 0; i < ipAddressInArray.length; i++) {

                    int power = 3 - i;
                    int ip = Integer.parseInt(ipAddressInArray[i]);
                    result += ip * Math.pow(256, power);

                }
                generatedResult.setText(baseUrl.getText().toString() + "@" + ipAddr);


            }
        });

        generate_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(generatedResult.getText().toString()));

                startActivity(httpIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.yandex_id) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.main_frame_layout_id, new Yandex_Fragment());
            fragmentTransaction.commit();

        } else if (id == R.id.presearch_id) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.main_frame_layout_id, new Presearch_Fragment());
            fragmentTransaction.commit();

        } else if (id == R.id.piratebay_id) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("pirate");
            fragmentTransaction.replace(R.id.main_frame_layout_id, new Piratebay_Fragment());
            fragmentTransaction.commit();
        } else if (id == R.id.Zenblog_id) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
