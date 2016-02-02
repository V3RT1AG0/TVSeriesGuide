package compdom.sad;

import android.app.Application;
import android.content.Context;

/**
 * Created by adity on 16/12/2015.
 */
public class MyApplication extends Application
{
    public static MyApplication sinstance;



    @Override
    public void onCreate()
    {
        super.onCreate();
        sinstance=this;  //or this also works


    }

    public static MyApplication getInstance()
    {
        return(sinstance);

    }

    public static Context getContext()
    {
        return(sinstance.getApplicationContext());

    }


    //My manual code required in Thread.sleep in main and implemented across all activities
    static int globalflag;
    public static void setglobalflag(int a)
    {
        globalflag=a;
    }

    public static int returnglobalflag()
    {
        return(globalflag);
    }

}
