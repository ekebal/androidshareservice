package ikbal_jimmy.shareservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "HttpExample";//http://46.101.40.23/shareserviceserver/v1/register
    private EditText urlText;
    private TextView textView;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        textView = (TextView) findViewById(R.id.myText);
        setContentView(R.layout.activity_main);
        Button btnOk = (Button) findViewById(R.id.btn_test_rest);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickHandler();

            }
        };
        btnOk.setOnClickListener(oclBtnOk);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }


    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void myClickHandler() {
        // Gets the URL from the UI's text field.
        String task = "";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttpRequestTask().execute(task);
        } else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
            //textView.setText("No network connection available.");
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            ServiceModel serviceModel = new ServiceModel();
            return serviceModel.getServiceList();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(myContext, "Response Server OK :)", Toast.LENGTH_LONG).show();
            textView = (TextView) findViewById(R.id.myText);
            //textView.setText(result);

            // Faire quelque chose de cette liste

            ArrayList<String> serviceList = new ArrayList<String>();
            serviceList.add("Service 1");
            serviceList.add("Service 2");
            serviceList.add("Service 3");

            ListAdapter adapter = new ArrayAdapter(myContext, android.R.layout.simple_list_item_1, serviceList);
            ListView vue = (ListView) findViewById(R.id.listViewServices);
            vue.setAdapter(adapter);
        }
    }
}
