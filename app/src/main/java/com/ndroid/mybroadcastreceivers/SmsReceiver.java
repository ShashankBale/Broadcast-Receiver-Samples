package com.ndroid.mybroadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        try {
            final Bundle bundle = intent.getExtras();

            if (bundle == null) throw new Exception();

            final Object[] pdusObj = (Object[]) bundle.get("pdus");
            readSms(context, pdusObj);
        } catch (Exception e) {
            e.printStackTrace();
            notifyUserOfUnknownSms(context);
        }
    }


    private void readSms(Context context, Object[] pdusObjs) {
        for (Object pdusObj : pdusObjs) {
            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj);
            String senderPhoneNumber = currentMessage.getDisplayOriginatingAddress();
            String message = currentMessage.getDisplayMessageBody();

            notifyUser(context, senderPhoneNumber, message);
        } // end for loop
    }

    private void notifyUser(Context context, String senderPhoneNumber, String message) {
        new MyNotificationUtil(context,
                "SMS from " + senderPhoneNumber,
                "" + message,
                "" + message,
                true).show();
    }

    private void notifyUserOfUnknownSms(Context context) {
        new MyNotificationUtil(context,
                "SMS Alert!!",
                "New SMS received, please check them in SMS App.",
                "New SMS received, please check them in SMS App.").show();
    }
}
