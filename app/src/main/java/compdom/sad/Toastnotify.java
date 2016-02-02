package compdom.sad;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by adity on 16/12/2015.
 */
public class Toastnotify
{
    public Toastnotify(Context contexa, String s)
    {
        Context context = contexa;
        String text = s;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}




/*
 public String createmovieJSON(String strurl)
    {
        final String[] str = new String[1];
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();                     //1st request queue

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, strurl,
                new Response.Listener<JSONObject>()                                                 //this is a parameter
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        new Toastnotify(getApplicationContext(),str[0]);
                       // str[0] = parseMovieResponse(response);
                        new Toastnotify(getApplicationContext(), str[0]);


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
        new Toastnotify(getApplicationContext(), str[0]);
        return(str[0]);
    }
 */
