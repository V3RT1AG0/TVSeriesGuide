package compdom.sad;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class SeasonActivity extends AppCompatActivity
{
   // LinearLayout linearLayout;
   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        Intent intent = getIntent();
        int numseason = intent.getIntExtra("season", 0);
        final String id = intent.getStringExtra("imdbid");
        final String title=intent.getStringExtra("title");


       // linearLayout = (LinearLayout) findViewById(R.id.layout_season);
        LayoutInflater layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout parent=(LinearLayout) layoutInflater.inflate(R.layout.activity_season, null);
        LinearLayout thislayout= (LinearLayout) parent.findViewById(R.id.layout_season);
        thislayout.setBackgroundColor(Color.parseColor("#607D8B"));
        for (int i = 1; i <= numseason; i++)
        {
            View custom = layoutInflater.inflate(R.layout.textlayout, null);
            TextView tv = (TextView)custom.findViewById(R.id.temp);
           // tv.setLayoutParams(new ViewGroup.LayoutParams(
          //          ViewGroup.LayoutParams.MATCH_PARENT,
           //         ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText("Season " + i);


            thislayout.addView(custom);
            setContentView(parent);
           // tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);

            toolbar= (Toolbar) findViewById(R.id.toolmain3);
            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //linearLayout.addView(tv);
            final int finalI = i;

            tv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(v.getContext(), SeasonContent.class);     //did not understand the logic
                    intent.putExtra("seasonno", finalI);
                    intent.putExtra("Imdbid", id);
                    intent.putExtra("title", title);

                    v.getContext().startActivity(intent);
                }
            });
        }


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

}
/*
    private String generate(int num, String id)
    {
        return ("https://www.omdbapi.com/?i=" + id + "&Season=" + num);


    }

    public void JsonConnect(final String url)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JsonSesonProcess(response);
                new Toastnotify(getApplicationContext(),url);
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

    private void JsonSesonProcess(JSONObject response)
    {

        TextView tv=new TextView(this);
        try
        {
            tv.setText("Season"+response.getString("Season"));  //Daredevil has only 2 seasons thus only 2 will come
            //PROBLEM DETECTED there is no synch i.e season 5 is displayed before season 1 etc
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        tv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
       linearLayout.addView(tv);
       new Toastnotify(getApplicationContext(),tv.getText().toString());

    }
}
*/


/*
package compdom.sad;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class SeasonActivity extends AppCompatActivity
{
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        Intent intent = getIntent();
        int numseason = intent.getIntExtra("season", 0);
        String id = intent.getStringExtra("imdbid");
        linearLayout= (LinearLayout) findViewById(R.id.layout_season);
        for(int i=1;i<=numseason;i++)
        {
            String url = generate(i, id);
            new Toastnotify(getApplicationContext(),url);
            JsonConnect(url);
        }



    }

    private String generate(int num, String id)
    {
        return ("https://www.omdbapi.com/?i=" + id + "&Season=" + num);


    }

    public void JsonConnect(final String url)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JsonSesonProcess(response);
                new Toastnotify(getApplicationContext(),url);
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

    private void JsonSesonProcess(JSONObject response)
    {

        TextView tv=new TextView(this);
        try
        {
            tv.setText("Season"+response.getString("Season"));  //Daredevil has only 2 seasons thus only 2 will come
            //PROBLEM DETECTED there is no synch i.e season 5 is displayed before season 1 etc
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        tv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
       linearLayout.addView(tv);
       new Toastnotify(getApplicationContext(),tv.getText().toString());

    }
}

 */