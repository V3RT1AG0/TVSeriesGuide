package compdom.sad;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.todddavies.components.progressbar.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import compdom.sad.RotatingCirlce.CountdownTimerActivity;
import compdom.sad.RotatingCirlce.Logger;

public class Main2Activity extends AppCompatActivity
{

    Singelton singelton=Singelton.instantinate();
    ImageLoader imageLoader=singelton.getImageLoader();
    TextView text1;
    TextView text12;
    TextView text13;
    TextView text14;

    TextView text21;
    TextView text22;
    TextView text23;
    TextView text24;
    TextView text25;
    TextView text26;
    TextView text27;

    TextView text31;
    TextView text41;
    TextView text42;

    ImageView imgv;
    RatingBar ratingBar;

    /**********************************/
    private static final String TAG = "CountdownTimer";
    // Timer setup
    Time conferenceTime = new Time(Time.getCurrentTimezone());
    int hour = 22;
    int minute = 33;
    int second = 0;
    int monthDay = 28;
    // month is zero based...7 == August
    int month = 7;
    int year;
    private TextView mCountdownNote;
    private ProgressWheel mDaysWheel;
    private TextView mDaysLabel;
    private ProgressWheel mHoursWheel;
    private TextView mHoursLabel;
    private ProgressWheel mMinutesWheel;
    private TextView mMinutesLabel;
    private ProgressWheel mSecondsWheel;
    private TextView mSecondsLabel;
    // Values displayed by the timer
    private int mDisplayDays;
    private int mDisplayHours;
    private int mDisplayMinutes;
    private int mDisplaySeconds;
    /***********************************/

    //TextView mDaysLabel;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        new Toastnotify(getApplicationContext(),"subclass executed");
        setContentView(R.layout.activity_main2);
        text1 = (TextView) findViewById(R.id.t);
        text12 = (TextView) findViewById(R.id.t12);
        text13 = (TextView) findViewById(R.id.t13);
        text14=(TextView) findViewById(R.id.t14);
        ratingBar= (RatingBar) findViewById(R.id.star);

        text21 = (TextView) findViewById(R.id.t21);
        text22 = (TextView) findViewById(R.id.t22);
        text23 = (TextView) findViewById(R.id.t23);
        text24 = (TextView) findViewById(R.id.t24);
        text25 = (TextView) findViewById(R.id.t25);
        text26=(TextView) findViewById(R.id.t26);
        text27=(TextView) findViewById(R.id.t27);

        text31= (TextView) findViewById(R.id.t31);
        imgv= (ImageView) findViewById(R.id.img);

        text41= (TextView) findViewById(R.id.t41);
        text42= (TextView) findViewById(R.id.t42);


        toolbar= (Toolbar) findViewById(R.id.toolmain2);
        setSupportActionBar(toolbar);
        /**********************************/
        configureViews();
        configureConferenceDate();

       // mDaysLabel = (TextView) findViewById(R.id.activity_countdown_timer_days_text);
        // mDaysLabel.setText("hola");
/**********************************/


        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        //Bundle bundle=intent.getExtras();
        int season = intent.getIntExtra("numofseason", 0);
        String id = intent.getStringExtra("message");

        //new Toastnotify(getApplicationContext(),""+season);
        //String status=bundle.getString("skey");
        String message="http://www.omdbapi.com/?i=" + id + "&plot=full&r=json";
        createContentJSON(message, season);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            finish();
        }
        if(id==R.id.home_menu)
        {
            MyApplication.setglobalflag(1);
            startActivity(new Intent(this, MainActivity.class));

        }
        return true;  //note that:return onOptionsItemSelected(item); does no work
    }

    private void createContentJSON(String showUrl, final int season)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();                     //1st request queue

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl,
                new Response.Listener<JSONObject>()                                                 //this is a parameter
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        parseJSONContentResponse(response, season);
                        //new Toastnotify(getApplicationContext(), "happy");


                    }
                },
                new Response.ErrorListener()                                                         //this is a parameter
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //new Toastnotify(getApplicationContext(), "happy");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }


    private void parseJSONContentResponse(final JSONObject res, final int seaso)
    {

        try
        {

            String Title = res.getString("Title");   //Important note here:if the first getString fails the remainder code will also fail so check for spellings
            String Year = res.getString("Year");
            String Genres = res.getString("Genre");
            String Relelesed = res.getString("Released");
            String Runtime = res.getString("Runtime");
            String Actors = res.getString("Actors");
            String Writers = res.getString("Writer");
            String Language = res.getString("Language");
            String Country = res.getString("Country");
            String rated=res.getString("Rated");
            String Summary=res.getString("Plot");
            String urlthumb = res.getString("Poster");

            String rating=res.getString("imdbRating");
            int rat=res.getInt("imdbRating");

           ratingBar.setRating(rat);
            text14.setText("IMDB Rating: " + rating);

            text1.setText(Title);
            text12.setText(Year );
            text13.setText( Genres);   //automated padding issue
            text21.setText(Html.fromHtml("<b>Writer: </b>" + Writers));
            text22.setText(Html.fromHtml("<b>Actors: </b>" + Actors));
            text23.setText(Html.fromHtml("<b>Language: </b>"+Language));
            text24.setText(Html.fromHtml("<b>Country: </b>"+Country));
            text25.setText(Html.fromHtml("<b>Runtime: </b>"+Runtime));
            text26.setText(Html.fromHtml("<b>Rating: </b>"+rated));
            text27.setText(Html.fromHtml("<b>Released: </b>"+Relelesed));
            text31.setText(Summary);
            text41.setText(Html.fromHtml("<b>Seasons: </b>"+seaso));

            new Toastnotify(getApplicationContext(),"subclass executed2");


            if (urlthumb!=(null))
            {
                imageLoader.get(urlthumb, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
                    {
                        imgv.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });
            }

            text42.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)            //perform this later intent to start main3activity
                {
                    Intent intent = new Intent(v.getContext(), SeasonActivity.class);
                    try
                    {
                        intent.putExtra("imdbid", res.getString("imdbID"));
                        intent.putExtra("season", seaso);
                        intent.putExtra("title", res.getString("Title"));

                        //new Toastnotify(getApplicationContext(),""+seaso);
                        startActivity(intent);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }











    /**************************************************************************************/


     private void configureViews()
    {

        this.conferenceTime.setToNow();
        this.year = conferenceTime.year;

        // this.mCountdownNote = (TextView) findViewById(R.id.activity_countdown_timer_note);
        this.mDaysWheel = (ProgressWheel) findViewById(R.id.activity_countdown_timer_days);
        this.mHoursWheel = (ProgressWheel) findViewById(R.id.activity_countdown_timer_hours);
        this.mMinutesWheel = (ProgressWheel) findViewById(R.id.activity_countdown_timer_minutes);
        this.mSecondsWheel = (ProgressWheel) findViewById(R.id.activity_countdown_timer_seconds);
        this.mDaysLabel = (TextView) findViewById(R.id.activity_countdown_timer_days_text);
        this.mHoursLabel = (TextView) findViewById(R.id.activity_countdown_timer_hours_text);
        this.mMinutesLabel = (TextView) findViewById(R.id.activity_countdown_timer_minutes_text);
        this.mSecondsLabel = (TextView) findViewById(R.id.activity_countdown_timer_seconds_text);


    }

    private void configureConferenceDate()
    {
        conferenceTime.set(second, minute, hour, monthDay, month, year);
        conferenceTime.normalize(true);
        long confMillis = conferenceTime.toMillis(true);
        new Toastnotify(getApplicationContext(), "superclass executed 3");
        Time nowTime = new Time(Time.getCurrentTimezone());
        nowTime.setToNow();
        nowTime.normalize(true);
        long nowMillis = nowTime.toMillis(true);

        long milliDiff = confMillis - nowMillis;

        new CountDownTimer(milliDiff, 1000)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {
                // decompose difference into days, hours, minutes and seconds
                Main2Activity.this.mDisplayDays = (int) ((millisUntilFinished / 1000) / 86400);
                Main2Activity.this.mDisplayHours = (int) (((millisUntilFinished / 1000) - (Main2Activity.this.mDisplayDays * 86400)) / 3600);
                Main2Activity.this.mDisplayMinutes = (int) (((millisUntilFinished / 1000) - ((Main2Activity.this.mDisplayDays * 86400) + (Main2Activity.this.mDisplayHours * 3600))) / 60);
                Main2Activity.this.mDisplaySeconds = (int) ((millisUntilFinished / 1000) % 60);

                Main2Activity.this.mDaysWheel.setText(String.valueOf(Main2Activity.this.mDisplayDays));
                Main2Activity.this.mDaysWheel.setProgress(Main2Activity.this.mDisplayDays);

                Main2Activity.this.mHoursWheel.setText(String.valueOf(Main2Activity.this.mDisplayHours));
                Main2Activity.this.mHoursWheel.setProgress(Main2Activity.this.mDisplayHours * 15);

                Main2Activity.this.mMinutesWheel.setText(String.valueOf(Main2Activity.this.mDisplayMinutes));
                Main2Activity.this.mMinutesWheel.setProgress(Main2Activity.this.mDisplayMinutes * 6);

                Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
                an.setFillAfter(true);

                Main2Activity.this.mSecondsWheel.setText(String.valueOf(Main2Activity.this.mDisplaySeconds));
                Main2Activity.this.mSecondsWheel.setProgress(Main2Activity.this.mDisplaySeconds * 6);
            }

            @Override
            public void onFinish()
            {
                //TODO: this is where you would launch a subsequent activity if you'd like.  I'm currently just setting the seconds to zero
                Logger.d(TAG, "Timer Finished...");
                Main2Activity.this.mSecondsWheel.setText("0");
                Main2Activity.this.mSecondsWheel.setProgress(0);
            }
        }.start();
    }


/***************************************************************************************/



}
///////////////////unable to respond error was genetrated after passing seaso to this activity//////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@@