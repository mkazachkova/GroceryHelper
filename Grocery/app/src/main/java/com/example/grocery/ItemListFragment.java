package com.example.grocery;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    int count = 0;
    int multiplier = 86400000;

    public ItemListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = getActivity();
       // CharSequence text = "Swipe left when you place an item in your cart!";
        //int duration = Toast.LENGTH_SHORT;

        dbAdapt = MyShoppingListDBAdapter.getInstance(getActivity().getApplicationContext());
    //    dbAdapt.clear();
        dbAdapt.open();

        //Toast toast = Toast.makeText(context, text, duration);
        //toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        //toast.show();

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


                System.out.println(castItem.getName());
                System.out.println(castItem.getDays());
                System.out.println(castItem.getQuantity());
                System.out.println("string id: " + idString);

                final long whyId = Long.parseLong(idString);



               // final long whyId = id;

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =  getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.editTitle);
                edt.setText(castItem.getName());




                final Spinner quantitySpinner = (Spinner) dialogView.findViewById(R.id.quantity_spinner);

                final ArrayAdapter<CharSequence> quanAdapter = ArrayAdapter.createFromResource(act,
                        R.array.quantityTypes,android.R.layout.simple_spinner_item);
                quanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                quantitySpinner.setAdapter(quanAdapter);
                quantitySpinner.setSelection(castItem.getQuantity() - 1);



                final Spinner reminderSpinner = (Spinner) dialogView.findViewById(R.id.reminder_spinner);

                final ArrayAdapter<CharSequence> remindAdapter = ArrayAdapter.createFromResource(act,
                        R.array.reminderTypes,android.R.layout.simple_spinner_item);
                remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                reminderSpinner.setAdapter(remindAdapter);
                reminderSpinner.setSelection(castItem.getDays());

                Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = getActivity();
                        CharSequence text = "Delete Pressed!";
                        int duration = Toast.LENGTH_SHORT;

                      //  Toast toast = Toast.makeText(context, text, duration);
                      //  toast.show();

                        if (castItem.reminderDays >= 1) {
                            dbAdapt.updateField(whyId,5,false+""); //represents that is no longer in shopping list
                        } else {
                            dbAdapt.removeItem(whyId);
                        }

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(frag).attach(frag).commit();
                       // shoppingListView = (ListView) view2.findViewById(R.id.itemsList);
                       // myItems = new ArrayList<ShoppingItem>();
                       // shopAdapt = new ShoppingAdapter(getActivity().getApplicationContext(), R.layout.single_item, myItems);
                       // shoppingListView.setAdapter(shopAdapt);
                       // updateArray();
                        b.dismiss();
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
                        int quantity = Integer.parseInt(quantitySpinner.getItemAtPosition(quantitySpinner.getSelectedItemPosition()).toString());
                        int reminder = Integer.parseInt(reminderSpinner.getItemAtPosition(reminderSpinner.getSelectedItemPosition()).toString());


                        System.out.println(editedTitle);
                        System.out.println(quantity);
                        System.out.println(reminder);


                        dbAdapt.updateField(whyId,1,editedTitle);
                        dbAdapt.updateField(whyId,2,quantity+"");
                        dbAdapt.updateField(whyId,3,reminder+"");
                        System.out.println(whyId);
                       // Toast toast = Toast.makeText(context, text, duration);
                       // toast.show();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(frag).attach(frag).commit();
                        //shoppingListView = (ListView) view2.findViewById(R.id.itemsList);
                        //myItems = new ArrayList<ShoppingItem>();
                        //shopAdapt = new ShoppingAdapter(getActivity().getApplicationContext(), R.layout.single_item, myItems);
                        //shoppingListView.setAdapter(shopAdapt);
                        //updateArray();
                        b.dismiss();
                    }
                });

                b = dialogBuilder.create();
                b.show();
            }
        });


        ListView listView = (ListView) view2.findViewById(R.id.itemsList);

     //   populateMyItems();

        final ShoppingListAdapter listViewAdapter = new ShoppingListAdapter(
                getActivity(),
                R.layout.single_item,
                myItems
        );

        listView.setAdapter(listViewAdapter);


      //  OnSwipeTouchListener x = new OnSwipeTouchListener(getActivity(),listViewAdapter,listView);

        shoppingListView.setOnTouchListener(new OnSwipeTouchListener(getActivity(),listViewAdapter,listView) {
            public void onSwipeTop() {
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                ShoppingItem swiped = getItemFromSwipe();
                String id = swiped.getID();
                ShoppingItem temp = dbAdapt.getItem(Long.parseLong(id));
                // listViewAdapter.notifyDataSetChanged();

                if (temp.reminderDays >= 1) {
                    dbAdapt.updateField(Long.parseLong(id), 5, false + ""); //represents that is no longer in shopping list
                } else {
                    dbAdapt.removeItem(Long.parseLong(id));
                }

                String pushName = swiped.getName();
                int pushDays = swiped.getDays();
                //TODO: make notification go off at 8 am

                Calendar setTime = Calendar.getInstance();
                Calendar currentTime = Calendar.getInstance();

                setTime.set(Calendar.HOUR, 8);
                setTime.set(Calendar.MINUTE, 0);
                setTime.set(Calendar.SECOND, 0);

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
                            new GenerateNotifications(getNotification(pushName + " will expire soon!!!"), (int) total);
                    generateNotifications.start();
                }
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frag).attach(frag).commit();

          //      Toast.makeText(getActivity(), "You have swiped left!", Toast.LENGTH_SHORT).show();
           //     listViewAdapter.remove(listViewAdapter.getItem(0));
            //    listViewAdapter.notifyDataSetChanged();
            }
            public void onSwipeBottom() {
                Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
            }
        });

        return view2;
    }
    /*
    public void populateMyItems() {

        myItems = new ArrayList<ShoppingItem>();
        ShoppingItem one = new ShoppingItem("Apples", 1, 0);
        ShoppingItem two = new ShoppingItem("Watermelon", 1, 0);
        ShoppingItem three = new ShoppingItem("Cheese", 1, 0);
        ShoppingItem four = new ShoppingItem("Lemon", 1, 0);
        ShoppingItem five = new ShoppingItem("Milk", 1, 0);
        ShoppingItem six = new ShoppingItem("Chicken breast", 1, 0);
        ShoppingItem seven = new ShoppingItem("Garlic", 1, 0);
        ShoppingItem eight = new ShoppingItem("Bread", 1, 0);

        myItems.add(one);
        myItems.add(eight);
        myItems.add(three);
        myItems.add(six);
        myItems.add(seven);
        myItems.add(four);
        myItems.add(five);
        myItems.add(two);

    }*/

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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setContentTitle("Expiration Reminder");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_shopping);
        Intent intent = new Intent(getActivity(), MainActivity.class);
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
}

