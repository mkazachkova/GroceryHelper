package com.example.grocery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


  //  private Fragment shoppingFrag;
    private Fragment receiptsFrag;
    private Fragment expirationFrag;
    private Fragment statsFrag;
    private Fragment settingsFrag;
    private FragmentTransaction transaction;
    private Spinner quantitySpinner;
    private Spinner reminderSpinner;
    private MyShoppingListDBAdapter dbAdapt;
    private AlertDialog b;
    private static SharedPreferences myPrefs;
    private static TextView itemsNumb;
    private static ItemListFragment itemFragment = new ItemListFragment();
    FloatingActionButton fab;

    public static ReceiptDBAdapter rdbAdapt;  // ref to our database
    public static int rcurrID; //current receipt ID
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CharSequence text = "Swipe left when you place an item in your cart!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(MainActivity.this, text, duration);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        dbAdapt = MyShoppingListDBAdapter.getInstance(MainActivity.this);
//       dbAdapt.clear();
        dbAdapt.open();



       // dbAdaptExp = MyExpirationListDBAdapter.getInstance(MainActivity.this);
        //dbAdaptExp.open();

        rdbAdapt = new ReceiptDBAdapter(this);
       // rdbAdapt.clear();
        rdbAdapt.open();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater =  getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                dialogBuilder.setView(dialogView);

                final EditText title = (EditText) dialogView.findViewById(R.id.editTitle);
                title.setText("");
                title.setHint("Enter Item Name");

                Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                delete.setText("Cancel");
                delete.setBackgroundColor(Color.GRAY);

                Button save = (Button) dialogView.findViewById(R.id.btn_save);

                final AlertDialog dialog = dialogBuilder.create();

                final ScrollableNumberPicker daysScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_days);
                daysScroll.setValue(0);

                final ScrollableNumberPicker quantityScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_quantity);
                quantityScroll.setValue(1);

                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = MainActivity.this;
                        CharSequence text = "Cancel Pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b.dismiss();
                    }
                });


                save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = MainActivity.this;
                        CharSequence text = "Save pressed!";
                        int duration = Toast.LENGTH_SHORT;




                        //  Toast toast = Toast.makeText(context, text, duration);
                       // toast.show();



                        int quantity = quantityScroll.getValue();
                        int reminder = daysScroll.getValue();
                        String newTitle = title.getText().toString();
        //                int quantity = Integer.parseInt(quantitySpinner.getItemAtPosition(quantitySpinner.getSelectedItemPosition()).toString());
        //                int reminder = Integer.parseInt(reminderSpinner.getItemAtPosition(reminderSpinner.getSelectedItemPosition()).toString());

                        if (newTitle.equals("")) {
                            Context context2 = MainActivity.this;
                            CharSequence text2 = "Must enter item name in order to save item";
                            int duration2 = Toast.LENGTH_SHORT;

                            Toast toast2 = Toast.makeText(context2, text2, duration2);
                            toast2.show();
                        } else {
                            System.out.println("should be adding to database");

                            String returned = checkIfExists(newTitle);
                            if (returned.equals("")) {
                                ShoppingItem temp = new ShoppingItem(newTitle, quantity,reminder, "", true);
                                long id = dbAdapt.insertItem(temp);
                                System.out.println("this is the returned id: " + id);
                                boolean suc = dbAdapt.updateField(id, 4, id+"");
                                System.out.println(suc);
                                ShoppingItem t = dbAdapt.getItem(id);
                                System.out.println("the iID here is: " + t.getID());
                                System.out.println("this is the returned status: " + t.inList());
                            } else {
                                dbAdapt.updateField(Long.parseLong(returned), 2, quantity+"");
                                dbAdapt.updateField(Long.parseLong(returned), 5, true+"");
                            }

                            b.dismiss();

                            transaction = getSupportFragmentManager().beginTransaction();
                         //   transaction.replace(R.id.fragment_container, itemFragment);
                            transaction.detach(itemFragment);
                            transaction.attach(itemFragment);
                            //transaction.addToBackStack(null);
                            transaction.commit();

                            int howMany = 0;
                            Cursor curse = dbAdapt.getAllItems();
                            if (curse.moveToFirst())
                                do {
                                    ShoppingItem result = new ShoppingItem(curse.getString(1), Integer.parseInt(curse.getString(2)),Integer.parseInt(curse.getString(3)),curse.getString(4), Boolean.parseBoolean(curse.getString(5)));
                                    if (result.inList) {
                                        howMany++;
                                    }
                                } while (curse.moveToNext());

                            SharedPreferences.Editor peditor = myPrefs.edit();
                            peditor.putInt("ItemNumbers", howMany);
                            peditor.commit();

                            updateProgress();
                        }
                    }
                });

/*                quantitySpinner = (Spinner) dialogView.findViewById(R.id.quantity_spinner);

                final ArrayAdapter<CharSequence> quanAdapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.quantityTypes,android.R.layout.simple_spinner_item);
                quanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                quantitySpinner.setAdapter(quanAdapter);
                quantitySpinner.setSelection(0);



                reminderSpinner = (Spinner) dialogView.findViewById(R.id.reminder_spinner);

                final ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.reminderTypes,android.R.layout.simple_spinner_item);
                remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                reminderSpinner.setAdapter(remindAdapter);
                reminderSpinner.setSelection(0);
*/

                b = dialogBuilder.create();
                b.show();
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


        navigationView.getMenu().getItem(0).setChecked(true);

        View headerView = navigationView.getHeaderView(0);
        itemsNumb = (TextView)headerView.findViewById(R.id.numbItems);
        myPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

//        shoppingFrag = new ContentShoppingFrag();
        receiptsFrag = new ContentReceiptFrag();
        expirationFrag = new ContentExpirationFrag();
        statsFrag = new  ContentStatsFrag();

        updateProgress();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, itemFragment).commit();

       // android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       // fragmentTransaction.replace(R.id.content_frame, itemFragment);
       // fragmentTransaction.commit();

    }
/*
    @Override
    public void onResume() {
        super.onResume();

        //TODO: enable toolbar?
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getActionBar().setDisplayHomeAsUpEnabled(true);


        Cursor curse = dbAdapt.getAllItems();
        int listNumb = 0;
        if (curse.moveToFirst())
            do {
                ShoppingItem result = new ShoppingItem(curse.getString(1), Integer.parseInt(curse.getString(2)),Integer.parseInt(curse.getString(3)),curse.getString(4), Boolean.parseBoolean(curse.getString(5)));
                if (result.inList) {
                    listNumb++;
                }
            } while (curse.moveToNext());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView itemsNumb = (TextView)headerView.findViewById(R.id.numbItems);
*/
    public static void updateProgress() {
        int listNumb = myPrefs.getInt("ItemNumbers", 0);
        String lastNumbItem = Integer.toString(listNumb) + " Items";
        itemsNumb.setText(lastNumbItem);
        
        //TODO: refresh the navdrawer
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
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater =  getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                    dialogBuilder.setView(dialogView);

                    final EditText title = (EditText) dialogView.findViewById(R.id.editTitle);
                    title.setText("");
                    title.setHint("Enter Item Name");

                    Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                    delete.setText("Cancel");
                    delete.setBackgroundColor(Color.GRAY);

                    final AlertDialog dialog = dialogBuilder.create();

                    final ScrollableNumberPicker daysScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_days);
                    daysScroll.setValue(0);

                    final ScrollableNumberPicker quantityScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_quantity);
                    quantityScroll.setValue(1);



                    delete.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view)
                        {
                            Context context = MainActivity.this;
                         //   CharSequence text = "Cancel Pressed!";
                         //   int duration = Toast.LENGTH_SHORT;

                         //   Toast toast = Toast.makeText(context, text, duration);
                         //   toast.show();

                            b.dismiss();
                        }
                    });


                    Button save = (Button) dialogView.findViewById(R.id.btn_save);
/*                    final Spinner quantitySpinner = (Spinner) dialogView.findViewById(R.id.quantity_spinner);
                    final Spinner reminderSpinner = (Spinner) dialogView.findViewById(R.id.reminder_spinner);
*/                    //save.setGravity(Gravity.RIGHT);

                    save.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view)
                        {
                            Context context = MainActivity.this;
                            CharSequence text = "Save pressed!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();


                            String newTitle = title.getText().toString();
                   //         int quantity = Integer.parseInt(quantitySpinner.getItemAtPosition(quantitySpinner.getSelectedItemPosition()).toString());
                    //        int reminder = Integer.parseInt(reminderSpinner.getItemAtPosition(reminderSpinner.getSelectedItemPosition()).toString());

                            int quantity = quantityScroll.getValue();
                            int reminder = daysScroll.getValue();

                            if (newTitle.equals("")) {
                                Context context2 = MainActivity.this;
                                CharSequence text2 = "Must enter item name in order to save item";
                                int duration2 = Toast.LENGTH_SHORT;

                                Toast toast2 = Toast.makeText(context2, text2, duration2);
                                toast2.show();
                            } else {
                                System.out.println("should be adding to database");
                                String returned = checkIfExists(newTitle);
                                System.out.println("this is returned: " + returned);
                                if (returned.equals("")) {
                                    ShoppingItem temp = new ShoppingItem(newTitle, quantity,reminder, "", true);
                                    long id = dbAdapt.insertItem(temp);
                                    System.out.println("this is the returned id: " + id);
                                    boolean suc = dbAdapt.updateField(id, 4, id+"");
                                    System.out.println(suc);
                                    ShoppingItem t = dbAdapt.getItem(id);
                                    System.out.println("the iID here is: " + t.getID());
                                    System.out.println("this is the returned status: " + t.inList());
                                } else {
                                    dbAdapt.updateField(Long.parseLong(returned), 2, quantity+"");
                                    dbAdapt.updateField(Long.parseLong(returned), 5, true+"");
                                }

                                b.dismiss();
                                transaction = getSupportFragmentManager().beginTransaction();
                              //  transaction.replace(R.id.fragment_container, itemFragment);
                                transaction.detach(itemFragment);
                                transaction.attach(itemFragment);
                              //  transaction.addToBackStack(null);
                                transaction.commit();

                                int howMany = 0;
                                Cursor curse = dbAdapt.getAllItems();
                                if (curse.moveToFirst())
                                    do {
                                        ShoppingItem result = new ShoppingItem(curse.getString(1), Integer.parseInt(curse.getString(2)),Integer.parseInt(curse.getString(3)),curse.getString(4), Boolean.parseBoolean(curse.getString(5)));
                                        if (result.inList) {
                                            howMany++;
                                        }
                                    } while (curse.moveToNext());

                                SharedPreferences.Editor peditor = myPrefs.edit();
                                peditor.putInt("ItemNumbers", howMany);
                                peditor.commit();

                                updateProgress();
                            }

                        }
                    });



 //                   final ArrayAdapter<CharSequence> quanAdapter = ArrayAdapter.createFromResource(MainActivity.this,
  //                          R.array.quantityTypes,android.R.layout.simple_spinner_item);
   //                 quanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

 //                   quantitySpinner.setAdapter(quanAdapter);
 //                   quantitySpinner.setSelection(0);



//                    final ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(MainActivity.this,
  //                          R.array.reminderTypes,android.R.layout.simple_spinner_item);
    //                remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//                    reminderSpinner.setAdapter(remindAdapter);
  //                  reminderSpinner.setSelection(0);

                    b = dialogBuilder.create();
                    b.show();

                }
            });

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, itemFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_receipts) {
            fab.setVisibility(View.VISIBLE);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, receiptsFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_statistics) {
            fab.setVisibility(View.GONE);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, statsFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_expiration) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater =  MainActivity.this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                    dialogBuilder.setView(dialogView);

                    Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                    delete.setText("Cancel");
                    delete.setBackgroundColor(Color.GRAY);
                    // final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                    final AlertDialog dialog = dialogBuilder.create();


//                    Spinner quantitySpinner = (Spinner) dialogView.findViewById(R.id.quantity_spinner);
  //
                    //             quantitySpinner.setVisibility(View.GONE);

                /*final ArrayAdapter<CharSequence> quanAdapter = ArrayAdapter.createFromResource(act,
                        R.array.quantityTypes,android.R.layout.simple_spinner_item);
                quanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                quantitySpinner.setAdapter(quanAdapter);
                quantitySpinner.setSelection(0);*/



//                    final Spinner reminderSpinner = (Spinner) dialogView.findViewById(R.id.reminder_spinner);

 //                   final ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(MainActivity.this,
   //                         R.array.quantityTypes,android.R.layout.simple_spinner_item);
   //                 remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

     //               reminderSpinner.setAdapter(remindAdapter);
       //             reminderSpinner.setSelection(0);


                    final ScrollableNumberPicker daysScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_days);
                    daysScroll.setValue(1);

                    final ScrollableNumberPicker quantityScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_quantity);
                    quantityScroll.setVisibility(View.GONE);



                    delete.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view)
                        {
                          //  Context context = MainActivity.this;
                           // CharSequence text = "Delete Pressed!";
                            //int duration = Toast.LENGTH_SHORT;

                        //    Toast toast = Toast.makeText(context, text, duration);
                        //    toast.show();
                            b.dismiss();
                        }
                    });

                    TextView quantity = (TextView) dialogView.findViewById(R.id.textView);
                    final EditText name = (EditText) dialogView.findViewById(R.id.editTitle);

                    name.setText("");
                    name.setHint("Enter Item Name");
                    quantity.setVisibility(View.GONE);


                    Button save = (Button) dialogView.findViewById(R.id.btn_save);
                    save.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view)
                        {
                            Context context = MainActivity.this;
                            CharSequence text = "Save pressed!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();


                  //        int reminder = Integer.parseInt(reminderSpinner.getItemAtPosition(reminderSpinner.getSelectedItemPosition()).toString());
                            int reminder = daysScroll.getValue();
                            String newTitle = name.getText().toString();


                            if (newTitle.equals("")) {
                                Context context2 = MainActivity.this;
                                CharSequence text2 = "Must enter item name in order to save item";
                                int duration2 = Toast.LENGTH_SHORT;

                                Toast toast2 = Toast.makeText(context2, text2, duration2);
                                toast2.show();
                            } else {
                                String returned = checkIfExists(newTitle);
                                if (returned.equals("")) {
                                    System.out.println("should be adding to database");
                                    System.out.println(newTitle);
                                    ShoppingItem temp = new ShoppingItem(newTitle, 0,reminder,"", false);
                                    long id = dbAdapt.insertItem(temp);
                                     System.out.println("this is the returned id: " + id);
                                    boolean suc = dbAdapt.updateField(id, 4, id+"");
                                    System.out.println(suc);
                                    ShoppingItem t = dbAdapt.getItem(id);
                                    System.out.println("the iID here is: " + t.getID());
                                } else {
                                    dbAdapt.updateField(Long.parseLong(returned), 3, reminder+"");
                                }



                                b.dismiss();
                                transaction = getSupportFragmentManager().beginTransaction();
                                //  transaction.replace(R.id.fragment_container, itemFragment);
                                transaction.detach(expirationFrag);
                                transaction.attach(expirationFrag);
                                //  transaction.addToBackStack(null);
                                transaction.commit();
                            }


                        }
                    });


                    b = dialogBuilder.create();
                    b.show();
                }
            });


            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, expirationFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String checkIfExists(String name) {
        Cursor curse = dbAdapt.getAllItems();
        //  myItems.clear();
        if (curse.moveToFirst())
            do {
                ShoppingItem result = new ShoppingItem(curse.getString(1), Integer.parseInt(curse.getString(2)),Integer.parseInt(curse.getString(3)),curse.getString(4), Boolean.parseBoolean(curse.getString(5)));
                if (result.getName().equalsIgnoreCase(name)) { //only add if has reminder day set for it
                    return result.getID();  // puts in reverse order
                }
            } while (curse.moveToNext());
        return "";
        // shopAdapt.notifyDataSetChanged();
    }

    public FloatingActionButton getFloatingActionButton() {
        return fab;
    }

}
