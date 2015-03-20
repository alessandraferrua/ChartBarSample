package it.alessandraferrua.chartbarSample.savestate;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alessandraferrua on 12/02/15.
 */
public class RectsParcelable implements Parcelable {
    HashMap<Integer, Rect> rect_maps = new HashMap<Integer, Rect>();

    public RectsParcelable() {
    }

    public int describeContents() {
        return 0;
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

    public static final Creator<RectsParcelable> CREATOR = new Creator<RectsParcelable>() {
        public RectsParcelable createFromParcel(Parcel source) {
            return new RectsParcelable(source);
        }

        public RectsParcelable[] newArray(int size) {
            return new RectsParcelable[size];
        }
    };

    private RectsParcelable(Parcel source) {
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
}
