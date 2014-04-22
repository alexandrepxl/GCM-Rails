package sbu.mad.android2rails;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ShareExternalServer appUtil;
	String regId;
	AsyncTask<Void, Void, String> shareRegidTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appUtil = new ShareExternalServer();

		regId = getIntent().getStringExtra("regId");
		Log.d("MainActivity", "regId: " + regId);
		String marco = getIntent().getStringExtra("Marco");
		Log.w("MainActivity", "From Intent: "+marco);
		//TextView tv = (TextView) findViewById(R.id.logger);
		//tv.append(getIntent().getStringExtra("Marco"));
		final Context context = this;
		
		shareRegidTask = new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String result = appUtil.shareRegIdWithAppServer(context, regId);
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				shareRegidTask = null;
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
				TextView tv = (TextView) findViewById(R.id.logger);
				tv.append(result);
			}

		};
		shareRegidTask.execute(null, null, null);
	}

}
