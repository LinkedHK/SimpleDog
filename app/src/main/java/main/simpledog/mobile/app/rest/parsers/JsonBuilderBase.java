package main.simpledog.mobile.app.rest.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

abstract class JsonBuilderBase<T> {

    protected JSONArray  responseResult;

    protected List<T> preparedList;

    public  JsonBuilderBase(){
        preparedList = new ArrayList<T>();
    }
    public String getFirst(JSONObject res,String key, String from) throws JSONException{
        JSONArray parent = res.getJSONArray(from);
        JSONObject parent_obj =(JSONObject) parent.get(0);
        String data = parent_obj.getString(key);
       return   parent_obj.getString(key);
    }

    abstract List<T> Parse(JSONArray response)throws  JSONException;

    public List<T> getPreparedList() {
        return preparedList;
    }
}
