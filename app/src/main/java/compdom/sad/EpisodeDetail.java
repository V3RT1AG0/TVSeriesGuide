package compdom.sad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class EpisodeDetail extends AppCompatActivity
{
    LinearLayout linearLayout;
    //ImageView imageView;

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
    TextView text28;

    TextView text31;
    TextView text41;
    TextView text42;

    ImageView imgv;
    RatingBar ratingBar;
Toolbar toolbar;


    Singelton singelton=Singelton.instantinate();
    ImageLoader imageLoader=singelton.getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episdoe_detail_2);
        //imageView= (ImageView) findViewById(R.id.upperimage);
        linearLayout = (LinearLayout) findViewById(R.id.epi);
        Intent intent = getIntent();
        String Seasonurl = intent.getStringExtra("SeasonUrl");
        int episode = intent.getIntExtra("EpisodeTitle", 0);
        String id = intent.getStringExtra("imdbid");
       //int idsecond=intent.getIntExtra("idsecond",0);
        //int seasonsecond=intent.getIntExtra("Seasom",0);


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
        text28= (TextView) findViewById(R.id.t28);

        text31= (TextView) findViewById(R.id.t31);
        imgv= (ImageView) findViewById(R.id.img);

        text41= (TextView) findViewById(R.id.t41);
        text42= (TextView) findViewById(R.id.t42);

        toolbar= (Toolbar) findViewById(R.id.toolmain5);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String link = generateEpisodeURL(Seasonurl, episode);
        JsonConnectEpisode(link);
       // String urlfinalsecond=genrateURlsecond(idsecond,seasonsecond,episode);
        //JsonConnectSecond(urlfinalsecond);
        //String linke=genrateURL(episode)
        String linke=genrateURlsecond(id);
        JsonConnectSecond(linke);
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


//primary url code starts here
    String generateEpisodeURL(String Season, int epi)
    {
        return (Season + "&episode=" + epi);
    }

    private void JsonConnectEpisode(final String url)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JsonEpisodeContentProcess(response);

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

    private void JsonEpisodeContentProcess(JSONObject res)
    {
        try
        {
            String Title = res.getString("Title");   //Important note here:if the first getString fails the remainder code will also fail so check for spellings
           // String Year = res.getString("Year");
            int episode=res.getInt("Episode");
           // String Genres = res.getString("Genre");
            String Relelesed = res.getString("Released");
            String Runtime = res.getString("Runtime");
            String Actors = res.getString("Actors");
            String Writers = res.getString("Writer");
            String Language = res.getString("Language");
            String Country = res.getString("Country");
            String rated=res.getString("Rated");
            String Summary=res.getString("Plot");
            String director=res.getString("Director");
            String urlthumb = res.getString("Poster");
            int ses=res.getInt("Season");

            String rating=res.getString("imdbRating");
            int rat=res.getInt("imdbRating");

           // ratingBar.setRating(rat);
           // text14.setText("IMDB Rating: " + rating);

            text1.setText(Title);
            text12.setText("Season "+ses+" / Episode "+episode);
           // text13.setText(Genres);   //automated padding issue
            text21.setText(Html.fromHtml("<b>Writer: </b>" + Writers));
            text22.setText(Html.fromHtml("<b>Actors: </b>" + Actors));
            text23.setText(Html.fromHtml("<b>Language: </b>"+Language));
            text24.setText(Html.fromHtml("<b>Country: </b>"+Country));
            text25.setText(Html.fromHtml("<b>Runtime: </b>"+Runtime));
            text26.setText(Html.fromHtml("<b>Rating: </b>"+rated));
            text27.setText(Html.fromHtml("<b>Released: </b>"+Relelesed));
            text28.setText(Html.fromHtml("<b>Director: </b>"+director));
            text31.setText(Summary);
           // text41.setText("Seasons: "+seaso);
            //new Toastnotify(getApplicationContext(), title);
          /*  if (urlthumb!=(null))
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
            }  */
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }


    String genrateURlsecond(String id)
    {

        return ("http://api.themoviedb.org/3/find/"+id+"?api_key=ee043ecf67e133bbe5cd2797481e9c50&external_source=imdb_id");

    }

    private void JsonConnectSecond(String url)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JsonEpisodeContentProcessSecond(response);


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

    private void JsonEpisodeContentProcessSecond(JSONObject resp)
    {
        try
        {
            JSONArray jobj =resp.getJSONArray("tv_episode_results");
            JSONObject jo=jobj.getJSONObject(0);
            String imgurl=jo.getString("still_path");

            if (imgurl!=(null))
            {
                imageLoader.get("http://image.tmdb.org/t/p/w500"+imgurl, new ImageLoader.ImageListener() {
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
            else
            {
                new Toastnotify(getApplicationContext(),"hola");
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}

    //secondary url code starts here
    /*String genrateURlsecond(int id,int season,int episode)
    {
        return ("http://api.tvmaze.com/shows/"+id+"/episodebynumber?season="+season+"&number="+episode);
    }

    private void JsonConnectSecond(String url)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JsonEpisodeContentProcessSecond(response);

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

    private void JsonEpisodeContentProcessSecond(JSONObject resp)
    {
        try
        {
            JSONObject jobj =resp.getJSONObject("image");
            String imgurl=jobj.getString("original");
            if (imgurl!=(null))
            {
                imageLoader.get(imgurl, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
                    {
                        imageView.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });
            }
            new Toastnotify(getApplicationContext(),""+imgurl);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


*/