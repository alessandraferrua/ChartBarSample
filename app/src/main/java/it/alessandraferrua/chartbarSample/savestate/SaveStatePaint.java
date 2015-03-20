package it.alessandraferrua.chartbarSample.savestate;

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alessandraferrua on 19/03/15.
 */
public class SaveStatePaint extends View.BaseSavedState {
    HashMap<Integer, Paint> paint_maps = new HashMap<Integer, Paint>();

    public SaveStatePaint(Parcelable superState) {
        super(superState);
    }

    private SaveStatePaint(Parcel source) {
        super(source);
        final int N = source.readInt();
        for (int i = 0; i < N; i++) {
            int key = source.readInt();
            Paint paint = new Paint();
            paint.setColor(source.readInt());
            paint_maps.put(key, paint);
        }
    }

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        final int N = paint_maps.size();
        dest.writeInt(N);
        if (N > 0) {
            for (Map.Entry<Integer, Paint> entry : paint_maps.entrySet()) {
                dest.writeInt(entry.getKey());
                Paint paint = entry.getValue();
                dest.writeInt(paint.getColor());

            }
        }
    }

//    public static final Creator<SaveStateRect> CREATOR = new Creator<SaveStateRect>() {
//        public SaveStateRect createFromParcel(Parcel source) {
//            return new SaveStateRect(source);
//        }
//
//        public SaveStateRect[] newArray(int size) {
//            return new SaveStateRect[size];
//        }
//    };

    //required field that makes Parcelables from a Parcel
    public static final Creator<SaveStatePaint> CREATOR =
            new Creator<SaveStatePaint>() {
                public SaveStatePaint createFromParcel(Parcel in) {
                    return new SaveStatePaint(in);
                }

                public SaveStatePaint[] newArray(int size) {
                    return new SaveStatePaint[size];
                }
            };

    

}

