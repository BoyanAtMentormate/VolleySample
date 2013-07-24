package com.example.volleysample.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class HeaderRequest extends JsonArrayRequest {
	
	public HeaderRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
	}

	public Map<String, String> getHeaders() throws AuthFailureError {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("Accept", "*/*");
		params.put("NGIN-API-TOKEN", "c4ecd5d34ce10d5d77794915626e856c89055b84254f475903c3419469002930");
		params.put("NGIN-API-VERSION", "0.1");

		return params;
	}

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        JSONArray jsonArrayResponse = null;
        try {
            jsonArrayResponse = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Response.success(jsonArrayResponse, CustomHeadersParser.parseIgnoreCacheHeaders(response));
    }
}
