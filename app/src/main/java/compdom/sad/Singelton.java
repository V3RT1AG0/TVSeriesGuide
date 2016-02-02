package compdom.sad;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by adity on 16/12/2015.
 */
public class Singelton
{

    public static Singelton sinstance = null;
    RequestQueue requestQueue;
    ImageLoader imageLoader;

    private Singelton()
    {
        requestQueue = Volley.newRequestQueue((MyApplication.getContext()));
        imageLoader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String,Bitmap> cache=new LruCache<>((int) (Runtime.getRuntime().maxMemory()/1024/8));
            @Override
            public Bitmap getBitmap(String url)
            {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap)
            {
                    cache.put(url,bitmap);
            }
        });

    }

    public static Singelton instantinate()
    {
        if (sinstance == null)
        {
            sinstance = new Singelton();
        }
        return (sinstance);
    }

    public RequestQueue getRequestqueue()
    {

        return (requestQueue);
    }

    public ImageLoader getImageLoader()
    {
    return imageLoader;
}

}
