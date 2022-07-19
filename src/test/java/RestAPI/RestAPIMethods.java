package RestAPI;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAPIMethods {

    public Response getAuthMethod(String baseURI, String endPoint, String userName, String password)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().auth().preemptive().basic(userName,password);
        Response response = httpRequest.request(Method.GET,endPoint);
        return response;
    }

    public Response getMethod(String baseURI, String endPoint)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().relaxedHTTPSValidation();
        httpRequest.header("Content-Type","application/json");
        Response response = httpRequest.get(endPoint);
        return response;
    }
    public Response getMethodWithAuthToken(String baseURI, String endPoint,String authToken)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().relaxedHTTPSValidation();
        httpRequest.header("Content-Type","application/json");
        httpRequest.header("Authorization",authToken);
        Response response = httpRequest.get(endPoint);
        return response;
    }
    public Response getMethodWithHeader(String baseURI, String endPoint, Map<String,String> headers)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().relaxedHTTPSValidation();
        addHeaders(httpRequest,headers);
        Response response = httpRequest.get(endPoint);
        return response;
    }

    private RequestSpecification addHeaders(RequestSpecification httpRequest, Map<String, String> headers) {

        for(Map.Entry<String,String> oneHeader:headers.entrySet())
        {
            httpRequest.header(oneHeader.getKey(),oneHeader.getValue());
        }
        return httpRequest;
    }

    public Response postMethod(String baseURI, String endPoint,Map<String, String> bodyParams)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().relaxedHTTPSValidation();
        httpRequest.header("Content-Type","application/json");
        httpRequest.body(addBody(bodyParams));
        Response response = httpRequest.post(endPoint);
        return response;
    }

    private JSONObject addBody(Map<String, String> bodyParams) {

        JSONObject requestParams=new JSONObject();
        for(Map.Entry<String,String> oneParam:bodyParams.entrySet()) {
            requestParams.put(oneParam.getKey(),oneParam.getValue());
        }
        return requestParams;
    }
    public Response postMethodWithHeaders(String baseURI, String endPoint,Map<String, String> headers,Map<String, String> bodyParams)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().relaxedHTTPSValidation();
        addHeaders(httpRequest,headers);
        httpRequest.body(addBody(bodyParams));
        Response response = httpRequest.post(endPoint);
        return response;
    }
    public Response postMethodWithAuth(String baseURI, String endPoint,String authToken,Map<String, String> bodyParams)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().relaxedHTTPSValidation();
        httpRequest.header("Content-Type","application/json");
        httpRequest.header("Authorization",authToken);
        httpRequest.body(addBody(bodyParams));
        Response response = httpRequest.post(endPoint);
        return response;
    }

    public Response postMethodAuthWithoutBody(String baseURI, String endPoint,String authToken)
    {
        RestAssured.baseURI=baseURI;
        RequestSpecification httpRequest = given().relaxedHTTPSValidation();
        httpRequest.header("Content-Type","application/json");
        httpRequest.header("Authorization",authToken);
        Response response = httpRequest.post(endPoint);
        return response;
    }

    public void baseAuthSch(String loginUser, String userPassword, String jiraBaseURL)
    {
        RestAssured.baseURI=jiraBaseURL;
        PreemptiveBasicAuthScheme authSch = new PreemptiveBasicAuthScheme();
        authSch.setUserName(loginUser);
        authSch.setPassword(userPassword);
        RestAssured.authentication=authSch;
        RequestSpecification spec = given().relaxedHTTPSValidation();
    }
    public Response getMethodSch(String jiraBaseURL, String endPoint)
    {
        Response response=null;
        try
        {
            RestAssured.baseURI=jiraBaseURL;
            RequestSpecification spec = given().relaxedHTTPSValidation();
            spec.header("Content-Type","application/json");
            response = spec.request(Method.GET,endPoint);
        }catch(Exception E)
        {

        }
        return response;
    }
    public Response postMethodSch(String jiraBaseURL, Map<String,String> bodyData,String endPoint)
    {
        Response response=null;
        try
        {
            RestAssured.baseURI=jiraBaseURL;
            RequestSpecification spec = given().relaxedHTTPSValidation();
            spec.header("Content-Type","application/json");
            spec.body(addBody(bodyData));
            response = spec.post(endPoint);
        }catch(Exception E)
        {

        }
        return response;
    }
    public Response postMethodWithAttachmentSch(String jiraBaseURL, String filePath,String endPoint)
    {
        Response response=null;
        try
        {
            RestAssured.baseURI=jiraBaseURL;
            RequestSpecification spec = given().relaxedHTTPSValidation();
            spec.header("Content-Type","multipart/form-data");
            spec.multiPart(new File(filePath));
            response = spec.post(endPoint);
        }catch(Exception E)
        {

        }
        return response;
    }










}
