package compdom.sad;

import android.os.CountDownTimer;

/**
 * Created by adity on 29/02/2016.
 */
public class ctd
{
    public static int SECONDS_IN_A_DAY = 24 * 60 * 60;
    CountDownTimer countDownTimer;
    ctd(long diff, final myadapter.myfavviewholder myvi, final int position)
    {

        countDownTimer = new CountDownTimer(diff, 1)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {
                if (position == (Integer) myvi.textView1.getTag())
                {
                    long diffSec = millisUntilFinished / 1000;
                    long days = diffSec / SECONDS_IN_A_DAY;
                    long secondsDay = diffSec % SECONDS_IN_A_DAY;
                    long seconds = secondsDay % 60;
                    long minutes = (secondsDay / 60) % 60;
                    long hours = (secondsDay / 3600);
                    long countmilliforsec = millisUntilFinished % 1000;
                    long milliseconds = countmilliforsec % (SECONDS_IN_A_DAY);

                    myvi.textView1.setText(days + " days " + hours + " hours " + minutes + " minutes \n" + seconds + " secs " + String.format("%03d", milliseconds) +  " millisconds " + "remaining ");
                    myvi.textView3.setText("Position" + position);
                }

            }

            @Override
            public void onFinish()
            {
                myvi.textView1.setText("");
            }
        };
        // myvi.textView3.setText("Position" + position);
        //if(position!=(Integer)myvi.textView1.getTag())
        // {
        //    notifyItemChanged((Integer)myvi.textView1.getTag());
        // }
        countDownTimer.start();
    }

}