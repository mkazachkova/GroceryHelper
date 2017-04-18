package com.example.grocery;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment {

    private ListView shoppingListView;

    public ItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        getActivity().setTitle("My Shopping List");

        //get our list view
        shoppingListView = (ListView) view.findViewById(R.id.itemsList);

        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =  getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialogue_edit_item, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                //dialogBuilder.setTitle("Apples");
          //      TextView title = new TextView(getActivity());
           //     title.setText("Apples");
             //   title.setGravity(Gravity.CENTER);
               // title.setTextSize(25);
                //title.setTextColor(Color.BLACK);


                //dialogBuilder.setCustomTitle(title);

                Button delete = (Button) dialogView.findViewById(R.id.btn_delete);
                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = getActivity();
                        CharSequence text = "Delete Pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

                Button save = (Button) dialogView.findViewById(R.id.btn_save);
                save.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Context context = getActivity();
                        CharSequence text = "Save pressed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });


                //dialogBuilder.setMessage("Enter text below");

              //  dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                 //   public void onClick(DialogInterface dialog, int whichButton) {
                        //do something with edt.getText().toString();
              //      }
              //  });
               // dialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                //    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
               //     }
              //  });
                AlertDialog b = dialogBuilder.create();
                b.show();
             //   Button nbutton = b.getButton(DialogInterface.BUTTON_NEGATIVE);
             //   nbutton.setBackgroundColor(Color.RED);
             //   Button pbutton = b.getButton(DialogInterface.BUTTON_POSITIVE);
             //   pbutton.setBackgroundColor(Color.GREEN);
            }
        });

        String[] groceryItems = {"Apples", "Watermelon", "Cheese", "Lemon", "Milk"};

        ListView listView = (ListView) view.findViewById(R.id.itemsList);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                groceryItems
        );

        listView.setAdapter(listViewAdapter);

        return view;
    }

}