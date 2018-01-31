package magnet.house.godbeom.com.annotationbus.bus.impl;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BeomChul on 2017-04-30. <br><br>
 *
 *
 */
public class BrodCastPacket implements Parcelable {

    /** Do not modify, Set from MagnetEvent */
    String sender ;

    /** Brodcast Event type */
    int eventType ;

    /** Brodcast Event Action */
    String action =null;

    /** Message, objects */
    Bundle body;



    public BrodCastPacket() {

    }

    protected BrodCastPacket(Parcel in) {
        sender = in.readString();
        eventType = in.readInt();
        action = in.readString();
        body = in.readBundle();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeInt(eventType);
        dest.writeString(action);
        dest.writeBundle(body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BrodCastPacket> CREATOR = new Creator<BrodCastPacket>() {
        @Override
        public BrodCastPacket createFromParcel(Parcel in) {
            return new BrodCastPacket(in);
        }

        @Override
        public BrodCastPacket[] newArray(int size) {
            return new BrodCastPacket[size];
        }
    };

    public String getAction() {
        return action;
    }

    public BrodCastPacket setAction(String action) {
        this.action = action;
        return this;
    }

    public BrodCastPacket setEvent(int eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getEventType() {
        return eventType;
    }

    public BrodCastPacket setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public BrodCastPacket setBody(Bundle body) {
        this.body = body;
        return this;
    }

    public Bundle getBody() {
        return body;
    }

    /**
     * 필수는 아니지만 어디서 보낸 이벤트 인지 Trace용으로 명시하는 것을 권장합니다.
     **/
    public boolean senderFilter(Class guessSender){
        return getSender().contains(guessSender.getName());
    }
}
