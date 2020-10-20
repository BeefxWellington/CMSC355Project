package com.example.my2cents;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link analytics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class analytics extends Fragment {

    /** pie chart variables **/
    //private AnyChartView anyChartView;
    private String[] months = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private int[] expenditures = {20, 50, 100, 500, 1000, 1200, 1500, 50, 180, 200, 70, 350};

    /** notifications test variables **/
    private Button btn_notifications;
    private final String CHANNEL_ID = "bills";
    public final int NOTIFICATIONS_ID = 001;
    public Context mContext;
    private Resources mResources;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public analytics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment analytics.
     */
    // TODO: Rename and change types and number of parameters
    public static analytics newInstance(String param1, String param2) {
        analytics fragment = new analytics();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(mContext);
        mContext = context;
    }

    //treat this as regular onCreate to store Java code
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_analytics, container, false);

        btn_notifications = v.findViewById(R.id.btn_notifications);
        btn_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Hello! I'm a notification!";
                //creating notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        //set icon in the status bar for
                        .setSmallIcon(R.drawable.ic_money)
                        //set title of notification
                        .setContentTitle("My2Cents Notification")
                        //set message of notification
                        .setContentText("This should be a message below the notification")
                        //dismiss notification on tap
                        .setAutoCancel(true);

                /** This code is for when you want to tap the notification and be transported to another screen **/
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("message", message);
//
//                PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.setContentIntent(contentIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                notificationManager.notify(NOTIFICATIONS_ID, builder.build());
            }
        });
        return v;
    }

    private void createNotificationChannel(NotificationCompat.Builder builder) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "bills";
            String description = "Includes all notifications regarding required outgoing expenses";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channelBills = new NotificationChannel(CHANNEL_ID, name, importance);
            channelBills.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channelBills);
            channelBills.enableLights(true);
            channelBills.enableVibration(true);

            notificationManager.notify(NOTIFICATIONS_ID, builder.build());
        }
    }
}