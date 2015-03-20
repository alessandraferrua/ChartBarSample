package it.alessandraferrua.chartbarSample.savestate;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alessandraferrua on 19/03/15.
 */
public class SaveStatePower extends View.BaseSavedState {
    HashMap<Integer, Rect> rect_maps = new HashMap<Integer, Rect>();
    HashMap<Integer, Paint> paint_maps = new HashMap<Integer, Paint>();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        final int NRECT = rect_maps.size();
        final int NPAINT = paint_maps.size();

        dest.writeInt(NRECT + NPAINT);

        if (NRECT > 0) {
            for (Map.Entry<Integer, Rect> entry : rect_maps.entrySet()) {
                dest.writeInt(entry.getKey());
                Rect rect = entry.getValue();
                dest.writeInt(rect.top);
                dest.writeInt(rect.bottom);
                dest.writeInt(rect.right);
                dest.writeInt(rect.left);

            }
        }
        if (NPAINT > 0) {
            for (Map.Entry<Integer, Paint> entry : paint_maps.entrySet()) {
                dest.writeInt(entry.getKey());
                Paint paint = entry.getValue();
                dest.writeInt(paint.getColor());

            }
        }

    }

    public static final Parcelable.Creator<SaveStatePower> CREATOR = new Parcelable.Creator<SaveStatePower>() {
        public SaveStatePower createFromParcel(Parcel source) {
            return new SaveStatePower(source);
        }
        public SaveStatePower[] newArray(int size) {
            return new SaveStatePower[size];
        }
    };

    public SaveStatePower(Parcelable superState) {
        super(superState);
    }

    public SaveStatePower(Parcel source) {
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

}
