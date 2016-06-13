package com.jinke.doctorbear.Receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.jinke.doctorbear.Utils.GlobalAddress;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by sdh on 2016/6/12.
 */
public class NotificationReceiver extends PushMessageReceiver {
    public NotificationReceiver() {
        super();
    }

    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
       System.out.println(pushNotificationMessage.getSenderId()+"kkkkkkkkkkkkk");
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        if (RongIM.getInstance() != null ) {
            startPrivateChat(context,pushNotificationMessage.getSenderId(),null);
        }
        return true;
    }
    public void startPrivateChat(Context context, String targetUserId, String title) {
        if(context != null && !TextUtils.isEmpty(targetUserId)) {
            if(RongContext.getInstance() == null) {
                throw new ExceptionInInitializerError("RongCloud SDK not init");
            } else {
                Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon().appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase()).appendQueryParameter("targetId", targetUserId).appendQueryParameter("title", title).build();
                Intent it=new Intent("android.intent.action.VIEW", uri);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
