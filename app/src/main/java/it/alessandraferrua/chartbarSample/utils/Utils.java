package it.alessandraferrua.chartbarSample.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class Utils {
	private static String TAG = "Utils";
	static File fName = null;
	private static Map<Object, String> mp;

	public static String getFormatCoord(double d) {

		int decimal = (int) (d);
		double minutes = (d - decimal) * 60;
		double seconds = (minutes - ((int) minutes));
		DecimalFormat df = new DecimalFormat("##.##");
		return String.valueOf(decimal + "° " + (int) minutes + "\' " + df.format(seconds * 60) + "\"");

	}

	public static void writeFile(String txtData) throws IOException {
		fName.createNewFile();
		FileOutputStream fOut = new FileOutputStream(fName);
		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		myOutWriter.append(txtData);
		myOutWriter.close();
		fOut.close();

	}

	public static ArrayList<String> readFile(BufferedReader myReader) throws IOException {
		ArrayList<String> sList = new ArrayList<String>();
		String aDataRow = "";
		while ((aDataRow = myReader.readLine()) != null) {
			sList.add(aDataRow);
		}

		myReader.close();
		return sList;
	}


	public static String getFormatDateJson(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (d != null)
			return formatter.format(d) + " 00:00:00.0 EST";
		else
			return "";
	}

	public static String getFormatDisplayDate(Date d) {
		SimpleDateFormat sdfFormatter = new SimpleDateFormat("dd-MM-yyyy");
		String s = sdfFormatter.format(d);
		return s;
	}

	public static String getFormatDisplayDate(String sDate) {
		SimpleDateFormat sdfFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = (Date) sdfFormatter.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getFormatDisplayDate(d);
	}





	public static void dialogInfo(String msg, Context context, String title, int drawable) {
		// prepare the alert box
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

		// set the message to display
		alertbox.setMessage(msg);

		// add a neutral button to the alert box and assign a click listener
		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

			// click listener on the alert box
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});

		alertbox.setTitle(title);
		alertbox.setIcon(drawable);
		alertbox.show();
	}


	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	public static String getPrn(int i) {
		if(i<9)
			return "0" + String.valueOf(i);
		else
			return String.valueOf(i);
	}

}
