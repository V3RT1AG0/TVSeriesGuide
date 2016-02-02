package compdom.sad;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import compdom.sad.SqlLiteContent.MySqliteAdapter;

public class fav_activity extends AppCompatActivity
{
    MySqliteAdapter mySqliteAdapter;
    //String showUrl = "http://192.168.56.1/GetData.php";
    String showUrl = "http://tvseriescountdown.netai.net/GetData.php";
    ArrayList<Integer> intarrA = new ArrayList<Integer>();
    Toolbar toolbar;
    // int temp = 0;
    private List<FavourtesInformation> favinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_activity);

        favinfo = new ArrayList<>();
        mySqliteAdapter = new MySqliteAdapter(this);
        intarrA = mySqliteAdapter.selectData();
        createJSON();
        //new Toastnotify(getApplicationContext(),""+intarrA.get(1));




        try
        {

            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

          /*  try
            {

                Thread.sleep(10000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } */

        toolbar= (Toolbar) findViewById(R.id.toolbarfav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fav_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        MyFavActivityAdapter adapter = new MyFavActivityAdapter(favinfo);
        recyclerView.setAdapter(adapter);


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



    private void createJSON()
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();                     //1st request queue

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl,
                new Response.Listener<JSONObject>()                                                 //this is a parameter
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // new Toastnotify(getApplicationContext(), "sad"+showUrl);
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
            for (int i = 0; i < intarrA.size(); i++)
            {
                //The Great PEDA's Solution
                int j = 0;
                while ((intarrA.get(i)) != jsonArray.getJSONObject(j).getInt("_id"))
                    j++;

                JSONObject jsonObject = jsonArray.getJSONObject(j);//jsonObject.getInt("_id");
                //   JSONObject jsonObject = jsonArray.getJSONObject(i);
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
               // new Toastnotify(getApplicationContext(), sesa + "season" + intarrA.get(i) + "id");


                //new Toastnotify(getApplicationContext(), "sad");
                favinfo.add(new FavourtesInformation(a, b - 1, c, d, e, mid, sesa, TableId,title));
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }


}
/* while(temp<str.indexOf("!")+1)
         {
        String DatabaseID = str.substring(temp,temp+ str.indexOf(" "));
        String ImdbID = str.substring(temp+str.indexOf(" ") + 1, temp+str.indexOf("*"));

        int length = str.substring(temp, str.indexOf("*")).length();
        temp = temp + length;
        //new Toastnotify(getApplicationContext(), "Db:" + DatabaseID + "imdbid" + ImdbID);

         }*/