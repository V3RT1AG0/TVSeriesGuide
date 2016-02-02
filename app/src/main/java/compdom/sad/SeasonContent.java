package compdom.sad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SeasonContent extends AppCompatActivity
{
    //LinearLayout ln;
   // int idSecond;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_content);
        //ln = (LinearLayout) findViewById(R.id.ln);
        Intent intent = getIntent();
        int seasonno = intent.getIntExtra("seasonno", 0);
        String id = intent.getStringExtra("Imdbid");
        //String title=intent.getStringExtra("title");
        //String urlA=genrateTv(title);

        String link=generateSeasonURL(seasonno, id);
        JsonConnect(link);




        // JsonConnectSecond(urlA);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);

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


//primary code starts here
    private String generateSeasonURL(int num, String id)
    {
        return ("http://www.omdbapi.com/?i=" + id + "&Season=" + num); //http://www.omdbapi.com/?t=daredevil&Season=1  //daredevil id


    }

    public void JsonConnect(final String url)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JsonSesonContentProcess(response,url);

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void JsonSesonContentProcess(JSONObject response, final String url2)
    {


        try
        {
           JSONArray jsonarray = response.getJSONArray("Episodes");
            final int str=response.getInt("Season");//Daredevil has only 2 seasons thus only 2 will come //PROBLEM DETECTED there is no synch i.e season 5 is displayed before season 1 etc
            LayoutInflater layoutInflater= (LayoutInflater) getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout parent= (LinearLayout)layoutInflater.inflate(R.layout.activity_season_content, null);
            ScrollView scrollView= (ScrollView) parent.findViewById(R.id.scroll);
            LinearLayout linearLayout= (LinearLayout)scrollView.findViewById(R.id.ln);
            linearLayout.setBackgroundColor(Color.parseColor("#607D8B"));
            for (int y=0;y<jsonarray.length();y++)
            {
               JSONObject jobject= jsonarray.getJSONObject(y);
                View v= layoutInflater.inflate(R.layout.textlayout,null);
                TextView abc= (TextView) v.findViewById(R.id.temp);
                //TextView abc= new TextView(this);
               // abc.setLayoutParams(new ViewGroup.LayoutParams(
                //        ViewGroup.LayoutParams.MATCH_PARENT,
                //        ViewGroup.LayoutParams.WRAP_CONTENT));

                abc.setText("Episode"+jobject.getInt("Episode"));
                final int tit=jobject.getInt("Episode");
                final String id=jobject.getString("imdbID");
                linearLayout.addView(v);

                setContentView(parent);





                abc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(v.getContext(), EpisodeDetail.class);     //did not understand the logic
                        intent.putExtra("EpisodeTitle", tit);
                        intent.putExtra("SeasonUrl", url2);
                        intent.putExtra("imdbid",id);
                       // intent.putExtra("idsecond",idSecond); //global variable
                        intent.putExtra("Seasom",str);
                        v.getContext().startActivity(intent);




                    }
                });
            }
            toolbar= (Toolbar) findViewById(R.id.toolmain4);
            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        } catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}


/*TextView abc= new TextView(this);
abc.setLayoutParams(new ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
        abc.setText("abc");
        ln.addView(abc);*/


/*//second code
    private String genrateTv(String tit)
    {
        return("http://api.tvmaze.com/singlesearch/shows?q="+tit+"&embed=episodes");
    }

    public void JsonConnectSecond(final String url)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JsonSesonContentProcessSecond(response);

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void JsonSesonContentProcessSecond(JSONObject responsea)
    {
        try
        {
            idSecond = responsea.getInt("id");
            //new Toastnotify(getApplicationContext(),""+idSecond);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }*/