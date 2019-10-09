package global.imas.bintouch;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class secret extends AppCompatActivity {

    int flag=0;
    private DatabaseReference mDatabase;
    Button BAddCat,BAddQuote,BView1,Baddcat2,BView2;
    EditText Ecat, Equote,Ecatlevel2;
    Spinner Sp1,Sp2,Sp3;
    List<String> spinnerArray =  new ArrayList<String>();
    List<String> spinnerArray3 =  new ArrayList<String>();
ListView LV,LVview;
    ArrayAdapter adapter1, adapter3;
    List<String> Lbcodes = new ArrayList<String>();
    List<String> L22code = new ArrayList<String>();
    List<Catt> LbcodesClass = new ArrayList<Catt>();
    List<Cat> LbcodesClassCat = new ArrayList<Cat>();

    ArrayAdapter adapter1V;
    List<String> LbcodesV = new ArrayList<String>();
    List<String> L22codeV = new ArrayList<String>();
    List<Users> LbcodesClassV = new ArrayList<Users>();
    List<Catt> LbcodesClassVV = new ArrayList<Catt>();
    List<Cat> LbcodesClassCatV = new ArrayList<Cat>();
    String xito;
    int countcode=0;
    int countcodeV=0;
    ArrayAdapter<String> AAScodes,AAScodesV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        mToolbar.setTitle("Secret place to add quotes");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        Ecatlevel2= (EditText)findViewById(R.id.editText12);

        Equote = (EditText)findViewById(R.id.editText11);
        Ecat = (EditText)findViewById(R.id.editText9);

        Baddcat2 = (Button)findViewById(R.id.button56);
        Baddcat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!Ecatlevel2.getText().toString().equals("")) {
                    if (!Sp2.getSelectedItem().toString().equals("<none>    ")) {

                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("name", Ecatlevel2.getText().toString());
                        userMap.put("Under_Cat_level1", Sp2.getSelectedItem().toString());

                        mDatabase.child("Quotes_Cat_level2").push().setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(secret.this, "Done! New Category level 2 is added!", Toast.LENGTH_LONG).show();
                                Ecatlevel2.setText("");

                            }
                        });
                    }
                    else
                        Toast.makeText(secret.this, "Choose a category!" , Toast.LENGTH_LONG).show();

                }


            }
        });

        BView2= (Button)findViewById(R.id.button57);
        BView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(secret.this);
                dialog.setContentView(R.layout.msglayout_listview);
                dialog.setTitle("Delete:");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                Button Bcancel = (Button) dialog.findViewById(R.id.button10);
                LVview = (ListView) dialog.findViewById(R.id.listView);

                dialog.show();

                Bcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                AAScodesV = new ArrayAdapter<String> (secret.this, android.R.layout.simple_list_item_1, LbcodesV){
                    @Override
                    public View getView(int position, View c, ViewGroup p)
                    {
                        View view = super.getView(position,c,p);
                        TextView tv = (TextView)view.findViewById(android.R.id.text1);

                        tv.setText(Html.fromHtml(tv.getText().toString()));

                        if (flag==0) {
                            tv.setBackgroundColor(Color.argb(0, 50, 50, 50));
                            flag=1;
                        }
                        else
                        {
                            tv.setBackgroundColor(Color.argb(18, 50, 50, 50));
                            flag=0;
                        }
                        tv.setTextSize(16);
                        return view;

                    }
                };

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Quotes_Cat_level2"))
                        {

                            mDatabase.child("Quotes_Cat_level2").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                    countcodeV = 0;
                                    flag=0;
                                    LbcodesClassVV.clear();
                                    L22codeV.clear();
                                    for (DataSnapshot child : children) {
                                        LbcodesClassVV.add(child.getValue(Catt.class));
                                        countcodeV++;
                                        L22codeV.add(child.getKey());
                                    }

                                    LbcodesV.clear();
                                    for (int i = countcodeV-1; i > -1; i--) {
                                        String l1 = LbcodesClassVV.get(i).getName();
                                        String l2 = LbcodesClassVV.get(i).getUnder_Cat_level1();
                                        LbcodesV.add("<br>Category: "+l1+"<br>Under category: "+l2+"<br>");
                                    }

                                    LVview.setAdapter(AAScodesV);


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                LVview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView <? > arg0, View arg1, final int arg2, long arg3)
                    {
                        xito = (String)LVview.getItemAtPosition(arg2);
                        xito = xito.substring(xito.indexOf(":")+2, xito.indexOf("Under category:"));


                        final Dialog dialog = new Dialog(secret.this);
                        dialog.setContentView(R.layout.msglayout_search2);
                        dialog.setTitle("Delete:");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button BByes = (Button) dialog.findViewById(R.id.button15);
                        Button BBno = (Button) dialog.findViewById(R.id.button10);

                        TextView TT = (TextView) dialog.findViewById(R.id.textView59);

                        dialog.show();
                        TT.setText("NOTE: Deleting a level 2 category will delete all quotes under that category");

                        BByes.setText("Delete");
                        BBno.setText("Cancel");

                        BByes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                mDatabase.child("Quotes_Cat_level2").child(L22codeV.get(countcodeV-1-arg2)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(secret.this, "Deleted!" , Toast.LENGTH_LONG).show();

                                    }
                                });


                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild("Quotes"))
                                        {

                                            mDatabase.child("Quotes").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                                    for (DataSnapshot child : children) {
                                                        if(child.child("Under_Cat_level2").getValue(String.class).equals(xito))
                                                        {
                                                            mDatabase.child("Quotes").child(child.getKey().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    // Toast.makeText(secret.this, "Deleted!" , Toast.LENGTH_LONG).show();
                                                                    dialog.dismiss();
                                                                }
                                                            });

                                                        }
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                dialog.dismiss();

                            }
                        });
                        BBno.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                            }
                        });


                    }
                });






            }
        });

        BView1 = (Button)findViewById(R.id.button55);
        BView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(secret.this);
                dialog.setContentView(R.layout.msglayout_listview);
                dialog.setTitle("Delete:");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                Button Bcancel = (Button) dialog.findViewById(R.id.button10);
                LVview = (ListView) dialog.findViewById(R.id.listView);

                dialog.show();

                Bcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                AAScodesV = new ArrayAdapter<String> (secret.this, android.R.layout.simple_list_item_1, LbcodesV){
                    @Override
                    public View getView(int position, View c, ViewGroup p)
                    {
                        View view = super.getView(position,c,p);
                        TextView tv = (TextView)view.findViewById(android.R.id.text1);

                        tv.setText(Html.fromHtml(tv.getText().toString()));

                        if (flag==0) {
                            tv.setBackgroundColor(Color.argb(0, 50, 50, 50));
                            flag=1;
                        }
                        else
                        {
                            tv.setBackgroundColor(Color.argb(18, 50, 50, 50));
                            flag=0;
                        }
                        tv.setTextSize(16);
                        return view;

                    }
                };

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Quotes_Cat_level1"))
                        {

                            mDatabase.child("Quotes_Cat_level1").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                    countcodeV = 0;
                                    flag=0;
                                    LbcodesClassV.clear();
                                    L22codeV.clear();
                                    for (DataSnapshot child : children) {
                                        LbcodesClassV.add(child.getValue(Users.class));
                                        countcodeV++;
                                        L22codeV.add(child.getKey());
                                    }

                                    LbcodesV.clear();
                                    for (int i = countcodeV-1; i > -1; i--) {
                                        String l1 = LbcodesClassV.get(i).getName();
                                        LbcodesV.add("<br>"+l1+"<br>");
                                    }

                                    LVview.setAdapter(AAScodesV);


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                LVview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView <? > arg0, View arg1, final int arg2, long arg3)
                    {
                         xito = (String)LVview.getItemAtPosition(arg2);
                        xito = xito.replace("<br>","");


                        final Dialog dialog = new Dialog(secret.this);
                        dialog.setContentView(R.layout.msglayout_search2);
                        dialog.setTitle("Delete:");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button BByes = (Button) dialog.findViewById(R.id.button15);
                        Button BBno = (Button) dialog.findViewById(R.id.button10);

                        TextView TT = (TextView) dialog.findViewById(R.id.textView59);

                        dialog.show();
                        TT.setText("NOTE: Deleting a level 1 category will delete all level 2 categories and quotes under that category");

                        BByes.setText("Delete");
                        BBno.setText("Cancel");

                        BByes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mDatabase.child("Quotes_Cat_level1").child(L22codeV.get(countcodeV-1-arg2)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(secret.this, "Deleted!" , Toast.LENGTH_LONG).show();
                                    }
                                });

                                dialog.dismiss();

                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild("Quotes_Cat_level2"))
                                        {
                                            mDatabase.child("Quotes_Cat_level2").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                                    for (DataSnapshot child : children) {
                                                        if(child.child("Under_Cat_level1").getValue(String.class).equals(xito))
                                                        {
                                                            mDatabase.child("Quotes_Cat_level2").child(child.getKey().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    // Toast.makeText(secret.this, "Deleted!" , Toast.LENGTH_LONG).show();
                                                                }
                                                            });

                                                        }
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild("Quotes"))
                                        {

                                mDatabase.child("Quotes").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            if(child.child("Under_Cat_level1").getValue(String.class).equals(xito))
                                            {
                                                mDatabase.child("Quotes").child(child.getKey().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        // Toast.makeText(secret.this, "Deleted!" , Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                dialog.dismiss();

                            }
                        });
                        BBno.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                            }
                        });


                    }
                });










            }
        });

        BAddCat = (Button)findViewById(R.id.button53);
        BAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Ecat.getText().toString().equals(""))
                mDatabase.child("Quotes_Cat_level1").push().child("name").setValue(Ecat.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(secret.this, "Done! New category level 1 is added!", Toast.LENGTH_LONG).show();
                        Ecat.setText("");
                    }
                });

            }
        });


        Sp1 = (Spinner) findViewById(R.id.spinner) ;
        Sp2 = (Spinner) findViewById(R.id.spinner2) ;
        Sp3 = (Spinner) findViewById(R.id.spinner3) ;
        adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerArray.clear();
        spinnerArray.add(0, "<none>    ");

        spinnerArray3.clear();
        spinnerArray3.add(0, "<none>    ");

        Sp1.setAdapter(adapter1);
        Sp2.setAdapter(adapter1);
        Sp3.setAdapter(adapter3);



        Sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Quotes_Cat_level2"))
                        {
                            mDatabase.child("Quotes_Cat_level2").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                    spinnerArray3.clear();


                                    for (DataSnapshot child: children) {
                                        if(child.child("Under_Cat_level1").getValue(String.class).equals(Sp1.getSelectedItem().toString()))
                                            spinnerArray3.add(child.child("name").getValue(String.class));
                                    }

                                    Collections.sort(spinnerArray3, String.CASE_INSENSITIVE_ORDER);
                                    spinnerArray3.add(0, "<none>    ");
                                    Sp3.setAdapter(adapter3);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Quotes_Cat_level1"))
                {

        mDatabase.child("Quotes_Cat_level1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                spinnerArray.clear();


                for (DataSnapshot child: children) {
                    spinnerArray.add(child.child("name").getValue(String.class));
                }

                Collections.sort(spinnerArray, String.CASE_INSENSITIVE_ORDER);
                spinnerArray.add(0, "<none>    ");
                Sp1.setAdapter(adapter1);
                Sp2.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        BAddQuote = (Button)findViewById(R.id.button54);
        BAddQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Equote.getText().toString().equals("")) {
                    if (!Sp1.getSelectedItem().toString().equals("<none>    ")) {
                        if (!Sp3.getSelectedItem().toString().equals("<none>    ")) {
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", Equote.getText().toString());
                            userMap.put("Under_Cat_level1", Sp1.getSelectedItem().toString());
                            userMap.put("Under_Cat_level2", Sp3.getSelectedItem().toString());

                            mDatabase.child("Quotes").push().setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(secret.this, "Done! New quote is added!", Toast.LENGTH_LONG).show();
                                    Equote.setText("");

                                }
                            });
                        }
                        else
                            Toast.makeText(secret.this, "Choose a category!" , Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(secret.this, "Choose a category!" , Toast.LENGTH_LONG).show();

                }
            }
        });



        LV= (ListView)findViewById(R.id.list);


        AAScodes = new ArrayAdapter<String> (secret.this, android.R.layout.simple_list_item_1, Lbcodes){
            @Override
            public View getView(int position, View c, ViewGroup p)
            {
                View view = super.getView(position,c,p);
                TextView tv = (TextView)view.findViewById(android.R.id.text1);

                tv.setText(Html.fromHtml(tv.getText().toString()));

                if (flag==0) {
                    tv.setBackgroundColor(Color.argb(0, 50, 120, 50));
                    flag=1;
                }
                else
                {
                    tv.setBackgroundColor(Color.argb(18, 50, 50, 70));
                    flag=0;
                }
                tv.setTextSize(16);
                return view;

            }
        };

        mDatabase.child("Quotes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                countcode = 0;
                flag=0;
                LbcodesClass.clear();
                L22code.clear();
                for (DataSnapshot child : children) {
                    LbcodesClass.add(child.getValue(Catt.class));
                    countcode++;
                    L22code.add(child.getKey());
                }

                Lbcodes.clear();
                for (int i = countcode-1; i > -1; i--) {
                    String l1 = LbcodesClass.get(i).getName();
                    String l2 = LbcodesClass.get(i).getUnder_Cat_level1();
                    String l3 = LbcodesClass.get(i).getUnder_Cat_level2();

                    Lbcodes.add("<br>Quote: <b>"+l1+"</b><br>Under category level 1: "+l2+"<br>Under category level 2: "+l3+"<br>");
                }

                LV.setAdapter(AAScodes);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView <? > arg0, View arg1, final int arg2, long arg3)
            {
                String x = (String)LV.getItemAtPosition(arg2);


                final Dialog dialog = new Dialog(secret.this);
                dialog.setContentView(R.layout.msglayout_search);
                dialog.setTitle("Delete:");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button BByes = (Button) dialog.findViewById(R.id.button15);
                Button BBno = (Button) dialog.findViewById(R.id.button10);

                dialog.show();
                BByes.setText("Delete");
                BBno.setText("Cancel");

                BByes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mDatabase.child("Quotes").child(L22code.get(countcode-1-arg2)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(secret.this, "Deleted!" , Toast.LENGTH_LONG).show();
                            }
                        });

                        dialog.dismiss();

                    }
                });
                BBno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });


            }
        });



    }






}
