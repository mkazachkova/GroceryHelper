package com.example.grocery;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import android.support.v7.app.AlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


  //  private Fragment shoppingFrag;
    private Fragment receiptsFrag;
    private Fragment expirationFrag;
    private Fragment statsFrag;
    private Fragment settingsFrag;
    private FragmentTransaction transaction;

    private static ItemListFragment itemFragment = new ItemListFragment();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater =  getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                dialogBuilder.setView(dialogView);

                EditText title = (EditText) dialogView.findViewById(R.id.editTitle);
                title.setText("");
                title.setHint("Enter Item Name");

                Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                delete.setText("Cancel");
                delete.setBackgroundColor(Color.GRAY);

                Button save = (Button) dialogView.findViewById(R.id.btn_save);



                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = MainActivity.this;
                        CharSequence text = "Cancel Pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });


                save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = MainActivity.this;
                        CharSequence text = "Save pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

                Spinner quantitySpinner = (Spinner) dialogView.findViewById(R.id.quantity_spinner);

                final ArrayAdapter<CharSequence> quanAdapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.quantityTypes,android.R.layout.simple_spinner_item);
                quanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                quantitySpinner.setAdapter(quanAdapter);
                quantitySpinner.setSelection(0);



                Spinner reminderSpinner = (Spinner) dialogView.findViewById(R.id.reminder_spinner);

                final ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.reminderTypes,android.R.layout.simple_spinner_item);
                remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                reminderSpinner.setAdapter(remindAdapter);
                reminderSpinner.setSelection(0);


                dialogBuilder.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

//        shoppingFrag = new ContentShoppingFrag();
        receiptsFrag = new ContentReceiptFrag();
        expirationFrag = new ContentExpirationFrag();
        statsFrag = new  ContentStatsFrag();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, itemFragment).commit();

       // android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       // fragmentTransaction.replace(R.id.content_frame, itemFragment);
       // fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater =  getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                    dialogBuilder.setView(dialogView);

                    EditText title = (EditText) dialogView.findViewById(R.id.editTitle);
                    title.setText("");
                    title.setHint("Enter Item Name");

                    Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                    delete.setText("Cancel");
                    delete.setBackgroundColor(Color.GRAY);

                    delete.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view)
                        {
                            Context context = MainActivity.this;
                            CharSequence text = "Cancel Pressed!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    });


                    Button save = (Button) dialogView.findViewById(R.id.btn_save);
                    //save.setGravity(Gravity.RIGHT);

                    save.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view)
                        {
                            Context context = MainActivity.this;
                            CharSequence text = "Save pressed!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    });

                    Spinner quantitySpinner = (Spinner) dialogView.findViewById(R.id.quantity_spinner);

                    final ArrayAdapter<CharSequence> quanAdapter = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.quantityTypes,android.R.layout.simple_spinner_item);
                    quanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    quantitySpinner.setAdapter(quanAdapter);
                    quantitySpinner.setSelection(0);



                    Spinner reminderSpinner = (Spinner) dialogView.findViewById(R.id.reminder_spinner);

                    final ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.reminderTypes,android.R.layout.simple_spinner_item);
                    remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    reminderSpinner.setAdapter(remindAdapter);
                    reminderSpinner.setSelection(0);

                    dialogBuilder.show();

                }
            });

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, itemFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_receipts) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, receiptsFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_statistics) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, statsFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_expiration) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, expirationFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
