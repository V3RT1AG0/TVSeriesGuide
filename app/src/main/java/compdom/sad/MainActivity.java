package compdom.sad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import compdom.sad.RotatingCirlce.CountdownTimerActivity;

public class MainActivity extends AppCompatActivity
{

    //String showUrl = "http://192.168.56.1/GetData.php";
    Toolbar toolbar;
    RecyclerView recyclerView;
    myadapter adapter;
    CircularProgressView progressView;
    LinearLayoutManager layout;
    String showUrl = "http://tvseriescountdown.netai.net/GetData.php";

    private List<information> info;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);//Cardlayout code
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.startAnimation();
        //new MyTask().execute();                //Code for AsyncTask
        info = new ArrayList<>();
        createJSON();
        toolbar = (Toolbar) findViewById(R.id.toolmain);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layout);
       /*  if (MyApplication.returnglobalflag() != 1)
        {
            try
            {

                Thread.sleep(10000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        } */
        /*recyclerView.setLayoutManager(layout);
        adapter = new myadapter(info);
        recyclerView.setAdapter(adapter); */


    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener()
    {
        @Override
        public boolean onQueryTextSubmit(String query)
        {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText)
        {
            newText = newText.toLowerCase();

            final List<information> filteredList = new ArrayList<>();

            for (int i = 0; i < info.size(); i++)
            {

                information in = info.get(i);
                final String text = in.title.toLowerCase();
                if (text.contains(newText))
                {

                    filteredList.add(new information(in.a, in.b, in.c, in.d, in.e, in.mid, in.sesa, in.TableID, in.title));
                }
            }

            recyclerView.setLayoutManager(layout);
            adapter = new myadapter(filteredList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_withsearch, menu);
        MenuItem search = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        if (searchView != null)
        {
            searchView.setOnQueryTextListener(listener);
        }

        MenuItem favoriteItem = menu.findItem(R.id.starred);
        Drawable newIcon =favoriteItem.getIcon();
        newIcon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        favoriteItem.setIcon(newIcon);
        return true;
        // return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.starred)
        {

            startActivity(new Intent(this, fav_activity.class));

        }
        if(id==R.id.About)
        {
            startActivity(new Intent(this,CountdownTimerActivity.class));
        }
        return true;  //note that:return onOptionsItemSelected(item); does no work
    }


    private void createJSON()
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();                     //1st request queue

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl,
                new Response.Listener<JSONObject>()                                                 //this is a parameter
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        //new Toastnotify(getApplicationContext(), "sad"+showUrl);
                        parseJSONResponse(response);



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
        Log.d("C", Thread.currentThread().getName());
    }


   private void parseJSONResponse(JSONObject response)
    {
        if (response == null || response.length() == 0)//uncomment later
        {

            return;
        }
        try
        {
            JSONArray jsonArray = response.getJSONArray("students");

            for (int i = 0; i <= jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int a = jsonObject.getInt("Year");
                int b = jsonObject.getInt("Month");
                int c = jsonObject.getInt("Day");
                int d = jsonObject.getInt("Hours");
                int e = jsonObject.getInt("Minutes");
                String mid = jsonObject.getString("Movieid");
                // String x = genrate(mid);
                int sesa = jsonObject.getInt("ses");
                int TableId = jsonObject.getInt("_id");
                String title = jsonObject.getString("Title");
                //new Toastnotify(getApplicationContext(),""+sesa);


                //new Toastnotify(getApplicationContext(), "sad");
                info.add(new information(a, b - 1, c, d, e, mid, sesa, TableId, title));
                Log.d("Called n times", Thread.currentThread().getName());

            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        } finally
        {
            progressView.setVisibility(View.GONE);
            adapter = new myadapter(info);
            recyclerView.setAdapter(adapter);
        }

    }

    private class MyTask extends AsyncTask<Void, Void, Void>
{


    @Override
    protected Void doInBackground(Void... params)
    {
        info = new ArrayList<>();
        createJSON();
        Log.d("a", "doInBackground: ");
        return null;
    }

    @Override
    protected void onPreExecute()
    {
        new Toastnotify(MainActivity.this,"pre");
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        Log.d("b", "d0afterdoInBackground: ");
        toolbar = (Toolbar) findViewById(R.id.toolmain);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layout);
       new Toastnotify(MainActivity.this,"post");
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {

    }
}





  /*  private void stringjson()
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();    //2nd request queue but will remain in same queue as singelton is used
        StringRequest stringRequest = new StringRequest(Request.Method.POST, showUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //new Toastnotify(getApplicationContext(), "sad" + response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //new Toastnotify(getApplicationContext(), "not sad");
            }
        }
        );
        requestQueue.add(stringRequest);
    }  */


    //   public String genrate(String str)
    //   {
    //      return ("https://www.omdbapi.com/?i=" + str + "&plot=short&r=json");
    //  }


}




/*    public void intitializData()
    {



        {
            //in.name = "hello" + i;
            //in.description = "hola" + i;


            //  for(int i=1;i<maximum elements in database;i++)
            //  {
            //          int a,b,c  load this from database
            //     info.add(new information(a, b - 1, c, d, e));
            //  }
            // info.add(new information(2015, 12 - 1, 20, 13, 12));
            // info.add(new information(2017, 12 - 1, 20, 13, 12));
            // info.add(new information(2018, 12 - 1, 20, 13, 12));
            //info.add(new information(2019, 12 - 1, 20, 13, 12));
            //info.add(new information(2020, 12 - 1, 20, 13, 12));


        }
    }
}



/*

RequestQueue requestQueue;






final TextView textView= (TextView) findViewById(R.id.txt);
requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl, new Response.Listener<JSONObject>()
        {
@Override
public void onResponse(JSONObject response)
        {
        try
        {
        JSONArray students = response.getJSONArray("students");
        for (int i = 0; i < students.length(); i++)
        {
        JSONObject student = students.getJSONObject(i);

        int a = Integer.parseInt(student.getString("Year"));
        int b = student.getInt("Month");
        int c = student.getInt("Day");
        int d = student.getInt("Hours");
        int e = student.getInt("Minutes");
        textView.append(""+a);


        }
        } catch (JSONException e)
        {
        e.printStackTrace();
        }
        }
        },
        new Response.ErrorListener()
        {
@Override
public void onErrorResponse(VolleyError error)
        {

        }
        });
        requestQueue.add(jsonObjectRequest);*/