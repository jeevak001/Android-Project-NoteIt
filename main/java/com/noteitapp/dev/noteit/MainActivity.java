package com.noteitapp.dev.noteit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView categoryList;
    static int gradients[] = {R.drawable.bg_one,R.drawable.bg_two,R.drawable.bg_three,R.drawable.bg_four,R.drawable.bg_five,
            R.drawable.bg_six,R.drawable.bg_seven,R.drawable.bg_eight,R.drawable.bg_nine,R.drawable.bg_ten,
            R.drawable.bg_eleven,R.drawable.bg_twelve,R.drawable.bg_thirteen,R.drawable.bg_fourteen,R.drawable.fifteen,
            R.drawable.bg_sixteen,R.drawable.bg_seventeen,R.drawable.bg_eighteen,R.drawable.bg_nineteen,R.drawable.bg_twenty,
            R.drawable.bg_tone,R.drawable.bg_ttwo,R.drawable.bg_tthree,R.drawable.bg_tfour,R.drawable.bg_tfive,
            R.drawable.bg_tsix,R.drawable.bg_tseven,R.drawable.bg_teight,R.drawable.bg_tnine,R.drawable.bg_tten,
            R.drawable.bg_ttone,R.drawable.bg_tttwo
                        };
    static String fontColors[] = {"#FFFFFF","#fafafa","#F5F5F5","#EEEEEE","#E0E0E0",
            "#BDBDBD","#ECEFF1","#CFD8DC","#9E9E9E","#263238",
            "#757575", "#616161","#424242","#212121","#000000"};

    static String glasses[] = {"#00000000","#1A000000","#33000000","#4D000000","#66000000",
            "#80000000","#99000000","#B3000000","#CC000000","#E6000000",
            "#00FFFFFF", "#1aFFFFFF","#33FFFFFF","#4dFFFFFF","#66FFFFFF",
            "#80FFFFFF", "#99FFFFFF","#b3FFFFFF","#ccFFFFFF","#e6FFFFFF"};

    Typeface helvetica;
    boolean isHtml = false;
    View colorSelectorView;
    TextView fontCheckText;
    TextView testText;
    TextView titleDemo;
    TextView contentDemo;
    int glassTmp = 0;

    Button selectorButton;
    ArrayList<Category> categories;
    Button audioNotesButton,actionButton,addMainButton,createNote;
    ViewGroup createCategoryButtonI,alarmNotesButton,alarmNotesButtonI,audioNotesButtonI,actionButtonI,addMainButtonI;
    ViewGroup drawerSubstitute,themeButton,fontTheme,glassTheme;
    android.app.AlertDialog alertDialog;
    NavigationView nav,navr;
    EditText search;
    ViewGroup searchGroup;
    ImageView closeSearch;
    ViewGroup deleteCategoryButton,createCategoryButton,emptyAddNote,putButton,getButton,addButton;
    ViewGroup deleteCategoryButtonI,emptyAddNoteI,createNoteI,putButtonI,getButtonI,addButtonI,notesFragmentBg;
    static MyDBHelper helper;
    DrawerLayout drawer;
    ViewGroup navri;
    static Toolbar toolbar;
    static int tmpPos = 0;
    String addNoteCategory = "";
    static int isAlarm = 0,aTheme = 0,title_size = 16,content_size = 15,font_theme = 0,font_bold = 0;
    int mTitle = 16,mContent = 15,mFont = 0,mBold = 0;

    private ArrayList<Integer> gradsets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        }catch (Exception e){

        }
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences("theme", Context.MODE_PRIVATE);
        aTheme = settings.getInt("theme",0);
        title_size = settings.getInt("title",16);
        content_size = settings.getInt("content",15);
        font_theme = settings.getInt("font",0);
        mTitle = settings.getInt("title",16);
        mContent = settings.getInt("content",15);
        mFont = settings.getInt("font",0);
        font_bold = settings.getInt("font_bold",0);
        glassTmp = settings.getInt("glass",0);
        Log.d("JEEVA_THEME","title Size:" + String.valueOf(title_size));
        Log.d("JEEVA_THEME","content Size:" + String.valueOf(content_size));
        Log.d("JEEVA_THEME","font theme:" + String.valueOf(font_theme));

        categoryList = (ListView) findViewById(R.id.category_list);
        nav = (NavigationView) findViewById(R.id.nav_view);
        navr = (NavigationView) findViewById(R.id.nav_viewr);
        navri = (ViewGroup) findViewById(R.id.nav_ri);
        createCategoryButtonI = (ViewGroup) findViewById(R.id.create_category);
        addMainButtonI = (ViewGroup) findViewById(R.id.add_note);
        deleteCategoryButtonI = (ViewGroup) findViewById(R.id.delete_category);
        alarmNotesButtonI = (ViewGroup) findViewById(R.id.alarm_notes);
        putButtonI = (ViewGroup) findViewById(R.id.put_button);
        getButtonI = (ViewGroup) findViewById(R.id.get_button);
        actionButton = (Button) findViewById(R.id.actions);
        drawerSubstitute = (ViewGroup) findViewById(R.id.substitute_categories);
        drawerSubstitute.setVisibility(View.GONE);
        fontTheme = (ViewGroup) findViewById(R.id.font_theme);
        glassTheme = (ViewGroup) findViewById(R.id.glass_theme);

        Log.d("JeevaIsAlarm",String.valueOf(isAlarm));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper = new MyDBHelper(this);

        nav.setBackground(getResources().getDrawable(gradients[aTheme]));
        navr.setBackground(getResources().getDrawable(gradients[aTheme]));


        Log.d("JeevaAlarm", "On Create Called");
        Intent i = new Intent(MainActivity.this,MyNoteService.class);
        i.putExtra("type", "first");
        //stopService(i);
        MainActivity.helper.setAllZero();
        //startService(i);

        addMainButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                i.putExtra("category", addNoteCategory);
                startActivityForResult(i, 102);
            }
        });



        putButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, OnlineUpdateActivity.class);
                startActivity(i);
            }
        });

        getButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, GetOnlineActivity.class);
                startActivity(i);
            }
        });



        createCategoryButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Jeeva", "Create Category");
                Intent i = new Intent(MainActivity.this, CreateCategoryActivity.class);
                startActivityForResult(i, 3001);
            }
        });

        deleteCategoryButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Jeeva", "Delete Category");
                Intent i = new Intent(MainActivity.this, DeleteCategoryActivity.class);
                tmpPos = 0;
                startActivityForResult(i, 3000);
            }
        });

        alarmNotesButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ThemeActivity.class);
                startActivity(i);

            }
        });

        glassTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,GlassThemeActivity.class);
                startActivity(i);
            }
        });

        fontTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.font_theme, null);
                Button fontButton = (Button) dialogView.findViewById(R.id.font_button);
                final TextView titleSizeText = (TextView) dialogView.findViewById(R.id.title_size_text);
                titleDemo = (TextView) dialogView.findViewById(R.id.title_demo);
                contentDemo = (TextView) dialogView.findViewById(R.id.content_demo);
                final TextView contentSizeText = (TextView) dialogView.findViewById(R.id.content_size_text);
                final SeekBar titleSize = (SeekBar) dialogView.findViewById(R.id.title_size);
                final SeekBar contentSize = (SeekBar) dialogView.findViewById(R.id.content_size);
                final CheckBox bold = (CheckBox) dialogView.findViewById(R.id.bold_check);
                Log.d("JEEVA_THEME","BOLD preSENT: " + String.valueOf(font_bold));
                if(font_bold == 1){
                    bold.setChecked(true);
                }else{
                    bold.setChecked(false);
                }
                titleSize.setProgress(title_size);
                contentSize.setProgress(content_size);
                titleSizeText.setText(String.valueOf(title_size));
                titleDemo.setTextColor(Color.parseColor(fontColors[font_theme]));
                contentDemo.setTextColor(Color.parseColor(fontColors[font_theme]));
                titleDemo.setTextSize((float)title_size);
                contentDemo.setTextSize((float)content_size);
                contentSizeText.setText(String.valueOf(content_size));
                titleSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mTitle = progress;
                        titleSizeText.setText(String.valueOf(mTitle));
                        Log.d("JEEVA_THEME","Title Size Changed" + String.valueOf(mTitle));
                        titleDemo.setTextSize((float)mTitle);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                contentSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mContent = progress;
                        contentSizeText.setText(String.valueOf(mContent));
                        Log.d("JEEVA_THEME","Content Size Changed" + String.valueOf(mContent));
                        contentDemo.setTextSize((float)mContent);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                bold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            mBold = 1;
                            Log.d("JEEVA_THEME","BOLD preSENT: " + String.valueOf(mBold));
                        }else{
                            mBold = 0;
                            Log.d("JEEVA_THEME","BOLD preSENT: " + String.valueOf(mBold));
                        }
                    }
                });

                fontButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        SharedPreferences sharedpreferences = getSharedPreferences("theme", Context.MODE_PRIVATE);
                        final SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("title", mTitle);
                        editor.putInt("content", mContent);
                        editor.putInt("font", mFont);
                        editor.putInt("font_bold", mBold);
                        Log.d("JEEVA_THEME","Editor Title: " + String.valueOf(mTitle));
                        Log.d("JEEVA_THEME","Editor Content: " + String.valueOf(mContent));
                        Log.d("JEEVA_THEME","Editor Font: " + String.valueOf(mFont));
                        editor.commit();
                        resetAll();

                    }
                });
                dialogBuilder.setView(dialogView);
                alertDialog = dialogBuilder.create();
                alertDialog.show();

            }
        });





        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.action_buttons, null);
                createCategoryButton = (ViewGroup) dialogView.findViewById(R.id.create_category);
                deleteCategoryButton = (ViewGroup) dialogView.findViewById(R.id.delete_category);
                ViewGroup actionsBg = (ViewGroup) dialogView.findViewById(R.id.actions_bg);
                actionsBg.setBackground(getResources().getDrawable(MainActivity.gradients[MainActivity.aTheme]));
                alarmNotesButton = (ViewGroup) dialogView.findViewById(R.id.alarm_notes);
                putButton = (ViewGroup) dialogView.findViewById(R.id.put_button);
                getButton = (ViewGroup) dialogView.findViewById(R.id.get_button);
                addButton = (ViewGroup) dialogView.findViewById(R.id.add_note);





                dialogBuilder.setView(dialogView);
                alertDialog = dialogBuilder.create();
                alertDialog.show();

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                        alertDialog.dismiss();
                        i.putExtra("category", addNoteCategory);
                        startActivityForResult(i, 102);
                    }
                });

                putButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(MainActivity.this, OnlineUpdateActivity.class);
                        alertDialog.dismiss();
                        startActivity(i);
                    }
                });

                getButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(MainActivity.this, GetOnlineActivity.class);
                        alertDialog.dismiss();
                        startActivity(i);
                    }
                });



                createCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Jeeva", "Create Category");
                        Intent i = new Intent(MainActivity.this, CreateCategoryActivity.class);
                        alertDialog.dismiss();
                        startActivityForResult(i, 3001);
                    }
                });

                deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Jeeva", "Delete Category");
                        Intent i = new Intent(MainActivity.this, DeleteCategoryActivity.class);
                        alertDialog.dismiss();
                        startActivityForResult(i, 3000);
                    }
                });

                alarmNotesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isAlarm = 1;
                        Log.d("JeevaIsAlarm", String.valueOf(isAlarm));
                        alertDialog.dismiss();
                        Log.d("Jeeva", "Alarm Notes");
                        drawer.closeDrawer(GravityCompat.START);

                    }
                });


            }
        });




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setBackground(getResources().getDrawable(getRandomGradient()));
        categoryList.setBackgroundColor(Color.parseColor("#33000000"));
        navri.setBackgroundColor(Color.parseColor("#33000000"));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        resetDrawer();
    }

    public void setFontTheme(View v){

        int id = v.getId();
        SharedPreferences sharedpreferences = getSharedPreferences("theme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.commit();

        if(id == R.id.f_1){
            mFont = 0;
            Log.d("JEEVA_THEME",String.valueOf(0));
        }
        if(id == R.id.f_2){
            mFont = 1;
        }
        if(id == R.id.f_3){
            mFont = 2;
        }
        if(id == R.id.f_4){
            mFont = 3;
        }
        if(id == R.id.f_5){
            mFont = 4;
        }
        if(id == R.id.f_6){
            mFont = 5;
        }
        if(id == R.id.f_7){
            mFont = 6;
        }
        if(id == R.id.f_8){
            mFont = 7;
        }
        if(id == R.id.f_9){
            mFont = 8;
        }
        if(id == R.id.f_10){
            mFont = 9;
        }
        if(id == R.id.f_11){
            mFont = 10;
        }
        if(id == R.id.f_12){
            mFont = 11;
        }
        if(id == R.id.f_13){
            mFont = 12;
        }
        if(id == R.id.f_14){
            mFont = 13;
        }
        if(id == R.id.f_15){
            mFont = 14;
        }
        titleDemo.setTextColor(Color.parseColor(fontColors[mFont]));
        contentDemo.setTextColor(Color.parseColor(fontColors[mFont]));

    }

    public void resetDrawer(){



        if(isAlarm == 1){
            drawer.closeDrawer(GravityCompat.START);
        }else{

            categories = helper.getAllCategory();
            Category catAll = new Category();
            categories.add(0, catAll);

            if(categories.size() == 1){
                categoryList.setVisibility(View.GONE);
                categoryList.setBackgroundColor(Color.argb(100, 0, 0, 0));
                drawerSubstitute.setVisibility(View.VISIBLE);
            }else{
                drawerSubstitute.setVisibility(View.GONE);
                categoryList.setVisibility(View.VISIBLE);
                categoryList.setAdapter(new CategoryListAdapter());
            }



            if(tmpPos == 0){
                NoteFragment fragment = new NoteFragment(MainActivity.this);
                toolbar.setTitle("All Notes");
                getSupportFragmentManager().beginTransaction().replace(R.id.notes_fragment, fragment).commit();
            }else{

                NoteFragment fragment = new NoteFragment(MainActivity.this,categories.get(tmpPos));
                toolbar.setTitle(String.valueOf(categories.get(tmpPos).getCategory()));
                getSupportFragmentManager().beginTransaction().replace(R.id.notes_fragment, fragment).commit();
            }

            categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    if(position == 0){

                        drawer.closeDrawer(GravityCompat.START);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                addNoteCategory = "";
                                NoteFragment fragment = new NoteFragment(MainActivity.this);
                                toolbar.setTitle("All Notes");
                                getSupportFragmentManager().beginTransaction().replace(R.id.notes_fragment, fragment).commit();
                            }
                        }, 200);

                    }else{

                        drawer.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                addNoteCategory = categories.get(position).getCategory().trim();
                                tmpPos = position;
                                NoteFragment fragment = new NoteFragment(MainActivity.this, categories.get(tmpPos));
                                toolbar.setTitle(categories.get(tmpPos).getCategory());
                                getSupportFragmentManager().beginTransaction().replace(R.id.notes_fragment, fragment).commit();
                            }
                        }, 200);

                    }
                }
            });
        }
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

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_update) {
            Intent i = new Intent(MainActivity.this,OnlineUpdateActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_fetch) {
            Intent i = new Intent(MainActivity.this,GetOnlineActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.add_note) {

            Log.d("JeevaIsAlarm",String.valueOf(isAlarm));

            if(isAlarm == 1){

                Intent i = new Intent(MainActivity.this, AlarmNoteActivity.class);
                isAlarm = 1;
                startActivityForResult(i, 9001);

            }else{
                Intent i = new Intent(MainActivity.this,AddNoteActivity.class);
                i.putExtra("category",addNoteCategory);
                startActivityForResult(i, 100);
            }


        }

        if (id == R.id.action_search) {

            if(search != null){
                isHtml = true;
                searchGroup.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                searchGroup.startAnimation(animation);

            }

        }

        return super.onOptionsItemSelected(item);
    }





    public class CategoryListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (View) inflater.inflate(R.layout.category_list_item_main, null);
            TextView text = (TextView) view.findViewById(R.id.list_text);
            TextView count = (TextView) view.findViewById(R.id.category_count);
            View categoryColor = view.findViewById(R.id.list_color);



            if(position == 0){
                count.setText(String.valueOf(MainActivity.helper.getNotesCountAll()));
                count.setTextColor(Color.parseColor("#0b6257"));
                text.setText(String.valueOf("All"));
                text.setTextColor(Color.parseColor(fontColors[font_theme]));
                categoryColor.setBackgroundColor(Color.parseColor("#0b6257"));
            }else{
                Category category = categories.get(position);
                count.setText(category.getCount());
                text.setTextColor(Color.parseColor(fontColors[font_theme]));
                count.setTextColor(Color.parseColor(category.getColor()));
                text.setText(String.valueOf(category.getCategory()));
                categoryColor.setBackgroundColor(Color.parseColor(category.getColor()));
            }
            return view;
        }
    }


    ////////////////////////////////////////////////////////////////

    public class NoteFragment extends Fragment {

        private Context context;
        private Category category;
        private GridView notesList;
        private View noteColor;
        private ArrayList<Note> notes;
        private CardView notesSubstitute;
        private TextView notesSubstituteText;
        private boolean isAll = false;
        long duration = 100;

        public NoteFragment(Context ctx){
            context = ctx;
            isAll = true;
        }

        public NoteFragment(Context ctx,Category cat) {
            context = ctx;
            category = cat;


            MainActivity.toolbar.setTitle(category.getCategory());

            Category category = new Category();
            category.setCategory(cat.getCategory().trim());
            category.setColor(cat.getColor());

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_note, container, false);
            view.setBackground(getResources().getDrawable(gradients[aTheme]));
            notesSubstitute = (CardView) view.findViewById(R.id.notes_substitute);
            notesSubstituteText = (TextView) view.findViewById(R.id.notes_substitute_text);
            notesSubstitute.setVisibility(View.GONE);
            notesList = (GridView) view.findViewById(R.id.notes_list);
            createNote = (Button) view.findViewById(R.id.create_note);
            ViewGroup noNote = (ViewGroup) view.findViewById(R.id.no_notes);
            noNote.setBackground(getResources().getDrawable(MainActivity.getRandomGradient()));
            closeSearch = (ImageView) view.findViewById(R.id.close_search);
            searchGroup = (ViewGroup) view.findViewById(R.id.search_group);
            search = (EditText) view.findViewById(R.id.search);
            searchGroup.setVisibility(View.GONE);


            closeSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isHtml = false;
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                    searchGroup.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            searchGroup.setVisibility(View.GONE);
                            resetNotes();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d("JeevaQ", "SEARCH_CHANGE: " + s);
                    findandChangeAdapter(String.valueOf(s));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });

            createNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                    i.putExtra("category",addNoteCategory);
                    startActivityForResult(i, 102);
                }
            });


            if(isAll){
                notes =  MainActivity.helper.getAllNotesByCategoryAll();
            }else{
                notes =  MainActivity.helper.getAllNotesByCategory(category);
            }
            if(notes.size() != 0){
                NotesAdapter notesAdapter = new NotesAdapter(notes);
                notesList.setAdapter(notesAdapter);
            }else{
                notesSubstitute.setVisibility(View.VISIBLE);
                notesList.setVisibility(View.GONE);
            }

            return view;
        }



        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }

        class NotesAdapter extends BaseAdapter{

            ArrayList<Note> notesInAdapter;

            NotesAdapter(ArrayList<Note> tmp){
                notesInAdapter = tmp;

                gradsets = new ArrayList<Integer>();
                gradsets.clear();
                for(int i=0;i<notesInAdapter.size();i++){
                    gradsets.add(0);
                }

            }

            @Override
            public int getCount() {
                return notesInAdapter.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view = convertView;
                final long[] counter = {0};

                LayoutInflater inflater = (LayoutInflater)LayoutInflater.from(getActivity());
                view = inflater.inflate(R.layout.notes_list_item, parent, false);
                ViewGroup noteWrapper = (ViewGroup) view.findViewById(R.id.note_wrapper);
                ViewGroup noteWrapperInner = (ViewGroup) view.findViewById(R.id.note_wrapper_inner);
                final TextView title = (TextView) view.findViewById(R.id.note_title);
                final TextView content = (TextView) view.findViewById(R.id.notes_content);
                TextView time = (TextView) view.findViewById(R.id.time);
                TextView rating = (TextView) view.findViewById(R.id.note_rating);
                final ImageView deleteNote = (ImageView) view.findViewById(R.id.delete_note);
                ImageView editNote = (ImageView) view.findViewById(R.id.edit_note);

                noteColor = view.findViewById(R.id.note_color);
                final View finalView = view;
                noteWrapper.setBackgroundColor(Color.parseColor(glasses[glassTmp]));


                title.setTextColor(Color.parseColor(fontColors[font_theme]));
                content.setTextColor(Color.parseColor(fontColors[font_theme]));
                title.setTextSize((float)title_size);
                content.setTextSize((float)content_size);
                time.setVisibility(View.GONE);

                if(font_bold == 1){
                    title.setTypeface(Typeface.DEFAULT_BOLD);
                    content.setTypeface(Typeface.DEFAULT_BOLD);
                }


                if(notesInAdapter.get(position).getPriority().equalsIgnoreCase("1.0")){
                    rating.setText("R1");
                }else if(notesInAdapter.get(position).getPriority().equalsIgnoreCase("2.0")){
                    rating.setText("R2");
                }else if(notesInAdapter.get(position).getPriority().equalsIgnoreCase("3.0")){
                    rating.setText("R3");
                }else if(notesInAdapter.get(position).getPriority().equalsIgnoreCase("4.0")){
                    rating.setText("R4");
                }else if(notesInAdapter.get(position).getPriority().equalsIgnoreCase("5.0")){
                    rating.setText("R5");
                }else{
                    rating.setText("R0");
                }



                deleteNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.delete_alert, null);
                        Button deleteNote = (Button) dialogView.findViewById(R.id.delete_alert_button);

                        deleteNote.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                alertDialog.dismiss();
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
                                finalView.startAnimation(animation);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Note note = new Note();
                                        note.setId(notesInAdapter.get(position).getId());
                                        note.setCategory(notesInAdapter.get(position).getCategory());
                                        MainActivity.helper.deleteNote(note);
                                        resetNotes();
                                        resetDrawer();
                                    }
                                }, 300);
                            }
                        });

                        dialogBuilder.setView(dialogView);
                        alertDialog = dialogBuilder.create();
                        alertDialog.show();

                    }
                });

                editNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), EditActivity.class);
                        i.putExtra("id", notesInAdapter.get(position).getId());
                        i.putExtra("category", notesInAdapter.get(position).getCategory());
                        i.putExtra("priority", notesInAdapter.get(position).getPriority());
                        i.putExtra("title", notesInAdapter.get(position).getTitle());
                        i.putExtra("content", notesInAdapter.get(position).getContent());
                        i.putExtra("color", notesInAdapter.get(position).getColor());
                        startActivityForResult(i, 2300);
                    }
                });




                if(isAll){
                    //title.setTextColor(Color.parseColor(notesInAdapter.get(position).getColor()));
                    noteColor.setBackgroundColor(Color.parseColor(notesInAdapter.get(position).getColor()));
                }else{
                    //title.setTextColor(Color.parseColor(category.getColor()));
                    noteColor.setBackgroundColor(Color.parseColor(category.getColor()));
                }





                if(isHtml){
                    title.setText(Html.fromHtml(notesInAdapter.get(position).getTitle()));
                    String tmp = String.valueOf(notesInAdapter.get(position).getContent()).replaceAll("\n","<br />");
                    content.setText(Html.fromHtml(tmp));
                }else{
                    title.setText((notesInAdapter.get(position).getTitle()));
                    content.setText((notesInAdapter.get(position).getContent()));
                }

                if(String.valueOf(title.getText()).equalsIgnoreCase("")){
                    title.setVisibility(View.GONE);
                }
                if(String.valueOf(content.getText()).equalsIgnoreCase("")){
                    content.setVisibility(View.GONE);
                }

                return view;
            }
        }

        private void findandChangeAdapter(String search) {


            boolean present = false;

            String[] tags = search.split(" ");

            for(int j = 0;j < tags.length; j++){
                Log.d("JeevaQ","TAGS: " + tags[j]);
            }

            if(isAll){
                notes =  MainActivity.helper.getAllNotesByCategoryAll();
            }else{
                notes =  MainActivity.helper.getAllNotesByCategory(category);
            }

            ArrayList<Note> searchNotes = notes;
            ArrayList<Integer> index = new ArrayList<>();
            ArrayList<Note> newNotes = new ArrayList<>();

            for(int i = 0; i < searchNotes.size(); i++){


                for(int j = 0;j < tags.length; j++){

                    if(searchNotes.get(i).getTitle().toLowerCase().contains(tags[j].toLowerCase())){
                        present = true;
                        break;

                    }else if(searchNotes.get(i).getContent().toLowerCase().contains(tags[j].toLowerCase())){
                        present = true;
                        break;

                    }else{
                        present = false;
                    }


                }

                Log.d("JeevaQ","SEARCH_PRESENT: " + String.valueOf(i) +  "," +  searchNotes.get(i).getTitle() + "," + searchNotes.get(i).getContent() + "," + String.valueOf(present));

                if(present == false){
                    index.add(new Integer(i));
                }
            }

            for(int i = 0;i < searchNotes.size();i++){

                if(index.contains(i)){
                    Log.d("JeevaQ","NOT_INCLUDED: " + searchNotes.get(i).getTitle() + ", " + searchNotes.get(i).getContent());
                }else{


                    newNotes.add(searchNotes.get(i));

                    for(int k = 0; k < newNotes.size(); k++){

                        for(int l = 0;l < tags.length; l++){

                            if(newNotes.get(k).getTitle().toLowerCase().contains(tags[l].toLowerCase())){

                            }else if(newNotes.get(k).getContent().toLowerCase().contains(tags[l].toLowerCase())){
                                if(!tags[l].equalsIgnoreCase("")) {
                                    Log.d("JEEVA_TAGS", "Content  Present");
                                    Note note = newNotes.get(k);
                                    note.setContent(String.valueOf(newNotes.get(k).getContent().toLowerCase()).replaceAll(tags[l].toLowerCase(), "<font color='red'>" + tags[l].toLowerCase() + "</font>"));
                                    break;
                                }

                            }else{
                            }


                        }

                        Log.d("JEEVA_TAGS_TITLE",String.valueOf(newNotes.get(k).getTitle()));
                        Log.d("JEEVA_TAGS_CONTENT",String.valueOf(newNotes.get(k).getContent()));
                    }

                }
            }

            if(newNotes.size() != 0){
                NotesAdapter notesAdapter = new NotesAdapter(newNotes);
                notesList.setVisibility(View.VISIBLE);
                notesList.setAdapter(notesAdapter);
            }else{

                notesList.setVisibility(View.GONE);
            }
        }



        private void resetNotes() {

            if (isAll) {

                notes = MainActivity.helper.getAllNotesByCategoryAll();
            } else {

                notes = MainActivity.helper.getAllNotesByCategory(category);
            }

            for(int i=0;i<notes.size();i++){

                notes.get(i).getTitle().replaceAll("\n","\n<br/>");
                notes.get(i).getContent().replaceAll("\n","\n<br/>");
                Log.d("JEEVA_BREAK",notes.get(i).getTitle());
                Log.d("JEEVA_BREAK",notes.get(i).getContent());
            }
            if (notes.size() != 0) {
                NotesAdapter notesAdapter = new NotesAdapter(notes);
                notesList.setVisibility(View.VISIBLE);
                notesList.setAdapter(notesAdapter);
            } else {
                tmpPos = 0;
                notesSubstitute.setVisibility(View.VISIBLE);
                notesList.setVisibility(View.GONE);
            }
        }
    }

    ///////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("JeevaIsAlarm", String.valueOf(isAlarm));
        drawer.closeDrawers();

        if(isAlarm == 1){

            Log.d("Jeeva", "Alarm Notes");

        }else{
            resetDrawer();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    public void resetAll(){

        try {
            alertDialog.dismiss();
        }catch (Exception e){

        }
        resetDrawer();
        SharedPreferences settings = getSharedPreferences("theme", Context.MODE_PRIVATE);
        aTheme = settings.getInt("theme",0);
        title_size = settings.getInt("title",16);
        content_size = settings.getInt("content",15);
        font_theme = settings.getInt("font",0);
        mTitle = settings.getInt("title",16);
        mContent = settings.getInt("content",15);
        mFont = settings.getInt("font",0);
        font_bold = settings.getInt("font_bold",0);
        glassTmp = settings.getInt("glass",0);
        font_bold = settings.getInt("font_bold",0);
        glassTmp = settings.getInt("glass",0);
        Log.d("JEEVA_THEME","RESETTING");
        nav.setBackground(getResources().getDrawable(gradients[aTheme]));
        navr.setBackground(getResources().getDrawable(gradients[aTheme]));
        if(tmpPos == 0){
            NoteFragment fragment = new NoteFragment(MainActivity.this);
            toolbar.setTitle("All Notes");
            getSupportFragmentManager().beginTransaction().replace(R.id.notes_fragment, fragment).commit();
        }else{

            NoteFragment fragment = new NoteFragment(MainActivity.this,categories.get(tmpPos));
            toolbar.setTitle(String.valueOf(categories.get(tmpPos).getCategory()));
            getSupportFragmentManager().beginTransaction().replace(R.id.notes_fragment, fragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetAll();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static int getRandomGradient(){

        Random ran = new Random();
        int i = 0 + (int)(Math.random() * ((21 - 0) + 1));
        Log.d("JEEVA_GRAD",String.valueOf(i));
        Log.d("JEEVA_GRAD",String.valueOf(gradients[i]));
        return gradients[i];
    }






}
