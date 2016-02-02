package compdom.sad;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import compdom.sad.SqlLiteContent.MySqliteAdapter;

/**
 * Created by adity on 24/01/2016.
 */
public class MyFavActivityAdapter extends RecyclerView.Adapter<MyFavActivityAdapter.myfavviewholder>
{
    public static int SECONDS_IN_A_DAY = 24 * 60 * 60;
    List<FavourtesInformation> favinfo;

    Singelton singelton = Singelton.instantinate();
    ImageLoader imageLoader = singelton.getImageLoader();
    String x;

    MySqliteAdapter mySqliteAdapter;

    MyFavActivityAdapter(List<FavourtesInformation> favinfo)
    {
        this.favinfo = favinfo;
        mySqliteAdapter =new MySqliteAdapter(MyApplication.getContext());
        new Toastnotify(MyApplication.getContext(),"context obtained");

    }

    @Override
    public myfavviewholder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardcontent, parent, false);
        myfavviewholder pvh = new myfavviewholder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final myfavviewholder holder,int position)
    {
        FavourtesInformation in = favinfo.get(position);
        holder.to = in;
        // holder.textView3.setText("Season" + in.sesa);//TEMP trick load JSONobjectRequest object here isnead of n main
        Calendar targetdate = Calendar.getInstance();                     //start of timer code

        targetdate.set(in.a, in.b, in.c, in.d, in.e);
        holder.textView1.setTag(position);
        //  long diffsec;
        Calendar currentDate = Calendar.getInstance();
        final long diff = targetdate.getTimeInMillis() - currentDate.getTimeInMillis();
        ctd(diff, holder, (Integer) holder.textView1.getTag());
        holder.textView2.setText("position"+position);
        x = genrate(in.mid);
        createJSON(x, holder);

    }
    @Override
    public int getItemCount()
    {
        return favinfo.size();
    }


    public String genrate(String str)
    {
        return ("http://www.omdbapi.com/?i=" + str + "&plot=full&r=json");
    }

    private void createJSON(String str, final myfavviewholder my)
    {
        RequestQueue requestQueue = Singelton.instantinate().getRequestqueue();                     //1st request queue

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, str,
                new Response.Listener<JSONObject>()                                                 //this is a parameter
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        //new Toastnotify(getApplicationContext(), "sad"+response.toString());
                        parseMovieResponse(response, my);


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

    private void parseMovieResponse(JSONObject response, final myfavviewholder mi)
    {

        if (response == null || response.length() == 0)
        {
            return;
        }
        try
        {
            String s = response.getString("Title");
            mi.textView2.setText(s);
            String urlthumb = response.getString("Poster");
            //if (urlthumb != (null))
            //{
            imageLoader.get(urlthumb, new ImageLoader.ImageListener()
            {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
                {
                    mi.imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error)
                {

                }
            });
            // }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    public class myfavviewholder extends RecyclerView.ViewHolder
    {
        //  CardView cardView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        View v;
       FavourtesInformation to;
        ImageView imageView;
        ImageView bookmarkimage;

        public myfavviewholder(View itemView)
        {
            super(itemView);
            //   cardView=(CardView)itemView.findViewById(R.id.card);
            v = itemView;
            bookmarkimage= (ImageView) itemView.findViewById(R.id.bookmarkstar);
            imageView = (ImageView) itemView.findViewById(R.id.imgv);
            textView1 = (TextView) itemView.findViewById((R.id.textview1));
            textView2 = (TextView) itemView.findViewById((R.id.textview2));
            textView3 = (TextView) itemView.findViewById((R.id.textview3));
            v.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(v.getContext(), Main2Activity.class);     //did not understand the logic
                    intent.putExtra("numofseason", to.sesa);

                    intent.putExtra("message", to.mid);   //message is complete url

                    //Bundle bundle=new Bundle();
                    // bundle.putString("skey",to.x);


                    v.getContext().startActivity(intent);                             //this too

                }
            });
            bookmarkimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int id=mySqliteAdapter.deleteData(to.TableID);
                    new Toastnotify(MyApplication.getContext(),"deleleteData id:"+id);
                    favinfo.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                }
            });

            bookmarkimage.setImageResource(R.drawable.ic_remove);




        }


    }

    void ctd(long diff, final myfavviewholder myvi, final int position)
    {

        CountDownTimer countDownTimer=new CountDownTimer(diff,1) {

            @Override
            public void onTick(long millisUntilFinished)
            {
                if(position==(Integer)myvi.textView1.getTag())
                {
                    long diffSec = millisUntilFinished / 1000;
                    long days = diffSec / SECONDS_IN_A_DAY;
                    long secondsDay = diffSec % SECONDS_IN_A_DAY;
                    long seconds = secondsDay % 60;
                    long minutes = (secondsDay / 60) % 60;
                    long hours = (secondsDay / 3600);
                    long countmilliforsec = millisUntilFinished % 1000;
                    long milliseconds = countmilliforsec % (SECONDS_IN_A_DAY);

                    myvi.textView1.setText(days + " days " + hours + " hours " + minutes + " minutes \n" + seconds + " secs " + String.format("%03d", milliseconds) + " millisconds " + "remaining ");
                    myvi.textView3.setText("Position" +position);
                }

            }

            @Override
            public void onFinish()
            {
                myvi.textView1.setText("done");
            }
        };
        myvi.textView3.setText("Position" + position);
        //if(position!=(Integer)myvi.textView1.getTag())
        // {
        //    notifyItemChanged((Integer)myvi.textView1.getTag());
        // }
        countDownTimer.start();
    }

    }
