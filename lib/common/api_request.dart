import 'package:NeoStencil/network/http_param_object.dart';
import 'package:NeoStencil/common/constants.dart';
import 'package:NeoStencil/network/http_service.dart';

class ApiRequest {
  static Future<dynamic> registerUser({String url, Map<String, String> headers,
      String method,  Map<String, dynamic>  postParams}) {
    HttpParamObject httpParamObject = new HttpParamObject();
    httpParamObject.url = baseUrl + apiUrl + url;
    httpParamObject.headers = headers;
    httpParamObject.method = 'post';
    httpParamObject.postParam = postParams;

    return HttpService.getData(httpParamObject);
  }

  static Future<dynamic> facebookLogin({String url, Map<String, String> headers,
    String method,  Map<String, dynamic>  postParams}) {
    HttpParamObject httpParamObject = new HttpParamObject();
    httpParamObject.url = baseUrl + url;
    httpParamObject.headers = headers;
    httpParamObject.method = 'get';
    httpParamObject.postParam = postParams;

    return HttpService.getData(httpParamObject);
  }

  static Future<dynamic> googleLogin({String url, Map<String, String> headers,
    String method,  Map<String, dynamic>  postParams}) {
    HttpParamObject httpParamObject = new HttpParamObject();
    httpParamObject.url = baseUrl + url;
    httpParamObject.headers = headers;
    httpParamObject.method = 'post';
    httpParamObject.postParam = postParams;

    return HttpService.getData(httpParamObject);
  }

  static Future<dynamic> getUserCourse({String url, Map<String, String> headers,
    String method,  Map<String, dynamic>  postParams}){

    HttpParamObject httpParamObject = new HttpParamObject();
    httpParamObject.url = baseUrl + apiUrl + url;
    httpParamObject.headers = headers;
    httpParamObject.method = 'get';
    httpParamObject.postParam = postParams;

    return HttpService.getData(httpParamObject);

  }

  static Future<dynamic> getUserBatchUnit({String url, Map<String, String> headers,
    String method,  Map<String, dynamic>  postParams}){

    HttpParamObject httpParamObject = new HttpParamObject();
    httpParamObject.url = baseUrl + apiUrl + url;
    httpParamObject.headers = headers;
    httpParamObject.method = 'get';
    httpParamObject.postParam = postParams;

    return HttpService.getData(httpParamObject);

  }
}
