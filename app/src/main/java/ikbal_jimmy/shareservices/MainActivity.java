package ikbal_jimmy.shareservices;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fragments.RegisterFragment;
import fragments.ServicesFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String DEBUG_TAG = "HttpExample";//http://46.101.40.23/shareserviceserver/v1/register
    private EditText urlText;
    private TextView textView;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        Authenticate.loadApiKey(myContext);

        /*
        LinearLayout tab1 = (LinearLayout)findViewById(R.id.tab1);
        LinearLayout tab2 = (LinearLayout)findViewById(R.id.tab2);
        LinearLayout tab3 = (LinearLayout)findViewById(R.id.tab3);
        LinearLayout tab4 = (LinearLayout)findViewById(R.id.tab4);
        LinearLayout tab5 = (LinearLayout)findViewById(R.id.tab5);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
        */

        if (getIntent().hasExtra("position")) {
            selectItem(getIntent().getExtras().getInt("position"));
        }else
            selectItem(2);

        //textView = (TextView) findViewById(R.id.myText);
        setContentView(R.layout.activity_main);
        //Button btnOk = (Button) findViewById(R.id.btn_test_rest);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickHandler();

            }
        };
        //btnOk.setOnClickListener(oclBtnOk);
        //((LinearLayout) findViewById(R.id.tab1))
        LinearLayout Register = (LinearLayout) findViewById(R.id.tab1);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intentDisplayContacts.putExtra("display_from", "activity");
                //startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                startActivity(new Intent(getApplicationContext(), MessagesActivity.class));
            }
        });


        LinearLayout Acount = (LinearLayout) findViewById(R.id.tab2);
        Acount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intentDisplayContacts.putExtra("display_from", "activity");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
       // layout.setOnClickListener(new View.OnClickListener()

        LinearLayout addService = (LinearLayout) findViewById(R.id.tab3);
        // layout.setOnClickListener(new View.OnClickListener()
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intentDisplayContacts.putExtra("display_from", "activity");

                String api_key = Authenticate.getApiKey();
                if (api_key != null) {
                    startActivity(new Intent(getApplicationContext(), AddSerciceActivity.class));

                } else {
                    startActivity(new Intent(getApplicationContext(), noidentifiActivity.class));

                }
            }
        });

        LinearLayout TestFragment = (LinearLayout) findViewById(R.id.tab4);
        TestFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new RegisterFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

    }
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
        protected void onPostExecute(String responseUrl) {
            //Toast.makeText(myContext, "Response Server OK :)", Toast.LENGTH_LONG).show();
            /*
            textView = (TextView) findViewById(R.id.myText);
            textView.setText(responseUrl);
            */

            ArrayList<ServiceShare> ListService = new ArrayList<ServiceShare>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                JSONArray jsonArray = jsonRootObject.optJSONArray("services");
                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id_service = Integer.parseInt(jsonObject.optString("id_service").toString());
                    int active = Integer.parseInt (jsonObject.optString("active").toString());
                    String titre = jsonObject.optString("titre").toString();
                    String description = jsonObject.optString("description").toString();

                    ServiceShare objService = new ServiceShare(id_service, titre, active, description);
                    ListService.add(objService);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<String> serviceList = new ArrayList<String>();
            for(ServiceShare objServiceShare : ListService ){
                serviceList.add(objServiceShare.getTitre());
            }
            /*
            ListAdapter adapter = new ArrayAdapter(myContext, android.R.layout.simple_list_item_1, serviceList);
            ListView vue = (ListView) findViewById(R.id.listViewServices);
            vue.setAdapter(adapter);
            */
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void selectItem(int position) {

        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = new WebviewFragment();
                break;
            case 1:
                //fragment = new AccountFragment();
                break;
            case 2:
                fragment = new ServicesFragment();
                break;
            case 3:
                //fragment = new MyJobsFragment();
                break;
            case 4:
                //fragment = new WebviewFragment();

                break;

            default:
                break;
        }
        Bundle args = new Bundle();
        fragment.setArguments(args);
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        /*
        switch (v.getId()) {
            case R.id.tab1:
                selectItem(0);
                break;
            case R.id.tab2:
                selectItem(1);
                break;
            case R.id.tab3:
                selectItem(2);
                break;
            case R.id.tab4:
                selectItem(3);
                break;
            case R.id.tab5:
                selectItem(4);
                break;
            default:
                break;
        }
        */
    }
}
