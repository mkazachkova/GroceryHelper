package com.example.grocery;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment {

    private ListView shoppingListView;
    protected static ArrayList<ShoppingItem> myItems;
    private MyShoppingListDBAdapter dbAdapt;
    protected ShoppingAdapter shopAdapt;
    private AlertDialog b;
    private View view2;
    private static SharedPreferences myPrefs;
    int count = 0;
    int multiplier = 86400000;
    FloatingActionButton fab;
    private FragmentTransaction transaction;
    private static TextView itemsNumb;

    public ItemListFragment() {
        // Required empty public constructor
    }



    public void onResume() {
        super.onResume();
        final Fragment currFrag = this;


        fab = ((MainActivity) getActivity()).getFloatingActionButton();
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =  getActivity().getLayoutInflater();
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
                        Context context = getActivity();
                        CharSequence text = "Cancel Pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        //   toast.show();
                        b.dismiss();
                    }
                });


                save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = getActivity();
                        CharSequence text = "Save pressed!";
                        int duration = Toast.LENGTH_SHORT;


                        int quantity = quantityScroll.getValue();
                        int reminder = daysScroll.getValue();
                        String newTitle = title.getText().toString();

                        if (newTitle.equals("")) {
                            Context context2 = getActivity();
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

                            transaction = getFragmentManager().beginTransaction();
                            //   transaction.replace(R.id.fragment_container, itemFragment);
                            transaction.detach(currFrag);
                            transaction.attach(currFrag);
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


                b = dialogBuilder.create();
                b.show();
            }
        });


        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        View headerView = navigationView.getHeaderView(0);
        itemsNumb = (TextView)headerView.findViewById(R.id.numbItems);
        myPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = getActivity();


        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        dbAdapt = MyShoppingListDBAdapter.getInstance(getActivity().getApplicationContext());
    //    dbAdapt.clear();
        dbAdapt.open();


        view2 = inflater.inflate(R.layout.fragment_item_list, container, false);
        getActivity().setTitle("My Shopping List");

        final FragmentActivity act = this.getActivity();
        final Fragment frag = this;



        //get our list view
        shoppingListView = (ListView) view2.findViewById(R.id.itemsList);
        myItems = new ArrayList<ShoppingItem>();
        shopAdapt = new ShoppingAdapter(getActivity().getApplicationContext(), R.layout.single_item, myItems);
        shoppingListView.setAdapter(shopAdapt);
        updateArray();


        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                Object shoppingItem = shoppingListView.getItemAtPosition(position);
               // Object shoppingItem = dbAdapt.getItem(id);
                final ShoppingItem castItem = (ShoppingItem) shoppingItem;
                String idString = castItem.getID();


                final long whyId = Long.parseLong(idString);



               // final long whyId = id;

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =  getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.editTitle);
                edt.setText(castItem.getName());

                final ScrollableNumberPicker daysScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_days);
                daysScroll.setValue(castItem.getDays());

                final ScrollableNumberPicker quantityScroll = (ScrollableNumberPicker) dialogView.findViewById(R.id.number_picker_quantity);
                quantityScroll.setValue(castItem.getQuantity());




                Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = getActivity();
                        CharSequence text = "Delete Pressed!";
                        int duration = Toast.LENGTH_SHORT;


                        if (castItem.reminderDays >= 1) {
                            dbAdapt.updateField(whyId,5,false+""); //represents that is no longer in shopping list
                        } else {
                            dbAdapt.removeItem(whyId);
                        }

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(frag).attach(frag).commit();
                        b.dismiss();
                        onChange(view);
                    }
                });

                Button save = (Button) dialogView.findViewById(R.id.btn_save);
                save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {


                        Context context = getActivity();
                        CharSequence text = "Save pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        String editedTitle = edt.getText().toString();

                        int quantity = quantityScroll.getValue();
                        int reminder = daysScroll.getValue();

                        System.out.println(editedTitle);

                        dbAdapt.updateField(whyId,1,editedTitle);
                        dbAdapt.updateField(whyId,2,quantity+"");
                        dbAdapt.updateField(whyId,3,reminder+"");
                        System.out.println(whyId);


                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(frag).attach(frag).commit();
                        b.dismiss();
                        onChange(view);
                    }
                });

                b = dialogBuilder.create();
                b.show();
            }
        });


        ListView listView = (ListView) view2.findViewById(R.id.itemsList);



        final ShoppingListAdapter listViewAdapter = new ShoppingListAdapter(
                getActivity(),
                R.layout.single_item,
                myItems
        );

        listView.setAdapter(listViewAdapter);


        shoppingListView.setOnTouchListener(new OnSwipeTouchListener(getActivity(),listViewAdapter,listView) {
            public void onSwipeTop() {
             //   Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
             //   Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                ShoppingItem swiped = getItemFromSwipe();
                String id = swiped.getID();
                ShoppingItem temp = dbAdapt.getItem(Long.parseLong(id));

                if (temp.reminderDays >= 1) {
                    dbAdapt.updateField(Long.parseLong(id), 5, false + ""); //represents that is no longer in shopping list
                } else {
                    dbAdapt.removeItem(Long.parseLong(id));
                }

                String pushName = swiped.getName();
                int pushDays = swiped.getDays();

                Calendar setTime = Calendar.getInstance();
                Calendar currentTime = Calendar.getInstance();

                setTime.set(Calendar.HOUR, 8);
                setTime.set(Calendar.MINUTE, 0);
                setTime.set(Calendar.SECOND, 0);
                setTime.set(Calendar.AM_PM, Calendar.AM);

                long eightTime = setTime.getTimeInMillis();
                long nowTime = currentTime.getTimeInMillis();
                long total;

                if (eightTime > nowTime) {
                    total = eightTime - nowTime;
                    total = total + (pushDays*multiplier);
                } else {
                    setTime.add(Calendar.DAY_OF_MONTH, 1);
                    eightTime = setTime.getTimeInMillis();
                    total = eightTime - nowTime;
                    if (pushDays != 1) {
                        total = total + ((pushDays - 1) * multiplier);
                    }
                }

                if (pushDays != 0) {
                    GenerateNotifications generateNotifications =
                            new GenerateNotifications(getNotification(pushName + " will expire soon!!!"),(int) total);
                    generateNotifications.start();
                }

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frag).attach(frag).commit();

                onChange(view2);

            }
            public void onSwipeBottom() {
               // Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
            }
        });

        return view2;
    }

    public void onChange(View v) {
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
        MainActivity.updateProgress();
    }

    class GenerateNotifications extends Thread{

        Notification notification;
        int delay;
        String content;

        public GenerateNotifications(Notification notification, int delay){
            this.notification = notification;
            this.delay = delay;
        }

        @Override
        public void run(){
            try {
                Thread.sleep(delay);
                this.schedule();
            }catch(Exception e){

            }
        }

        private void schedule() {
            Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID,0);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            long future = SystemClock.elapsedRealtime() + delay;
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,future,pendingIntent);

            NotificationManager NM = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            NM.notify(count++,notification);
        }

    }

    private Notification getNotification(String content) {
        Calendar wantTime = Calendar.getInstance();
        wantTime.set(Calendar.HOUR, 8);
        wantTime.set(Calendar.MINUTE, 0);
        wantTime.set(Calendar.SECOND, 0);
        wantTime.set(Calendar.AM_PM, Calendar.AM);
        long numb = wantTime.getTimeInMillis();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setContentTitle("Expiration Reminder");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_shopping);
        builder.setWhen(numb);
        Intent intent = new Intent(getActivity(), ItemListFragment.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    public void updateArray() {
        Cursor curse = dbAdapt.getAllItems();
        myItems.clear();
        if (curse.moveToFirst())
            do {
                ShoppingItem result = new ShoppingItem(curse.getString(1), Integer.parseInt(curse.getString(2)),Integer.parseInt(curse.getString(3)),curse.getString(4), Boolean.parseBoolean(curse.getString(5)));
                if (result.inList()) { //only display if actually in list
                    myItems.add(0, result);  // puts in reverse order
                }
            } while (curse.moveToNext());

        Collections.sort(myItems);
        shopAdapt.notifyDataSetChanged();
    }

    public int DetailClick(View v) {
        ListView lv = (ListView) view2.findViewById(R.id.itemsList);
        int position = lv.getPositionForView(v);
        return position;
    }

    public String checkIfExists(String name) {
        Cursor curse = dbAdapt.getAllItems();
        if (curse.moveToFirst())
            do {
                ShoppingItem result = new ShoppingItem(curse.getString(1), Integer.parseInt(curse.getString(2)),Integer.parseInt(curse.getString(3)),curse.getString(4), Boolean.parseBoolean(curse.getString(5)));
                if (result.getName().equalsIgnoreCase(name)) { //only add if has reminder day set for it
                    return result.getID();  // puts in reverse order
                }
            } while (curse.moveToNext());
        return "";
    }

    public static void updateProgress() {
        int listNumb = myPrefs.getInt("ItemNumbers", 0);
        String lastNumbItem = Integer.toString(listNumb) + " Items";
        itemsNumb.setText(lastNumbItem);

    }

}

