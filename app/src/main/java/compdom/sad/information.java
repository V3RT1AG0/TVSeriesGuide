package compdom.sad;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by adity on 12/12/2015.
 */
public class information
{
    //String name;
    //String description;
    int a;
    int b;
    int c;
    int d;
    int e;
    String mid;
    int sesa;
    int TableID;
    String title;

    information(int a, int b, int c, int d, int e, String mid, int sesa, int TableID, String title)
    {

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.mid = mid;
        this.sesa = sesa;
        this.TableID = TableID;
        this.title = title;
        //this.name=name;
        // this.description=description;
    }

}

/*class input
        {

            private List<information> info;
            private void initializedata()
            {
                info=new ArrayList<>();
                info.add(new information("Aditya"));
                info.add(new information("Kunal"));
                info.add(new information("Kukya"));
                info.add(new information("raymond"));
                info.add(new information("funk"));
            }

        } */


