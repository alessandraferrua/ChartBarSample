package it.alessandraferrua.chartbarSample.savestate;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alessandraferrua on 19/03/15.
 */
public class SaveStateRect extends View.BaseSavedState {
    HashMap<Integer, Rect> rect_maps = new HashMap<Integer, Rect>();

    public SaveStateRect(Parcelable superState) {
        super(superState);
    }

    private SaveStateRect(Parcel source) {
        super(source);
        final int N = source.readInt();
        for (int i = 0; i < N; i++) {
            int key = source.readInt();
            Rect rect = new Rect();
            rect.top = source.readInt();
            rect.bottom = source.readInt();
            rect.right = source.readInt();
            rect.left = source.readInt();
            rect_maps.put(key, rect);
        }
    }

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        final int N = rect_maps.size();
        dest.writeInt(N);
        if (N > 0) {
            for (Map.Entry<Integer, Rect> entry : rect_maps.entrySet()) {
                dest.writeInt(entry.getKey());
                Rect rect = entry.getValue();
                dest.writeInt(rect.top);
                dest.writeInt(rect.bottom);
                dest.writeInt(rect.right);
                dest.writeInt(rect.left);

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
    public static final Parcelable.Creator<SaveStateRect> CREATOR =
            new Parcelable.Creator<SaveStateRect>() {
                public SaveStateRect createFromParcel(Parcel in) {
                    return new SaveStateRect(in);
                }

                public SaveStateRect[] newArray(int size) {
                    return new SaveStateRect[size];
                }
            };



}

