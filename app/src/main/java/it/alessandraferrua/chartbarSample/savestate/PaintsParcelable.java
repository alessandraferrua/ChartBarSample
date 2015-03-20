package it.alessandraferrua.chartbarSample.savestate;

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alessandraferrua on 12/02/15.
 */
public class PaintsParcelable implements Parcelable {
    HashMap<Integer, Paint> paint_maps = new HashMap<Integer, Paint>();

    public PaintsParcelable() {
    }

    public int describeContents() {
        return 0;
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

    public static final Creator<PaintsParcelable> CREATOR = new Creator<PaintsParcelable>() {
        public PaintsParcelable createFromParcel(Parcel source) {
            return new PaintsParcelable(source);
        }

        public PaintsParcelable[] newArray(int size) {
            return new PaintsParcelable[size];
        }
    };

    private PaintsParcelable(Parcel source) {
        final int N = source.readInt();
        for (int i = 0; i < N; i++) {
            int key = source.readInt();
            Paint paint = new Paint();
            paint.setColor(source.readInt());
            paint_maps.put(key, paint);
        }
    }
}
