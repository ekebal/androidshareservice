package ikbal_jimmy.shareservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimmymunoz on 17/04/16.
 */
public class ServiceModel extends RestModel{

    //http://www.tutorialspoint.com/android/android_json_parser.htm
    public ServiceModel()
    {
        super();//Jimmy -> RestModel -> Instance UrlServer
    }


    public String getServiceList()
    {
        String responseUrl = RestHelper.executeGET(this.urlServer + "services");
        String data = "";
        try {
            JSONObject jsonRootObject = new JSONObject(responseUrl);

            //data += jsonRootObject.getString("response");

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("services");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(jsonObject.optString("id_service").toString());
                String titre = jsonObject.optString("titre").toString();
                //float salary = Float.parseFloat(jsonObject.optString("salary").toString());

                data += "Service"+i+" : \n id_service= "+ id +" \n Titre= "+ titre +" \n ";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
        //return responseUrl;

    }
}
