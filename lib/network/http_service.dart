import 'dart:async';
import 'dart:convert';
import 'package:NeoStencil/network/http_param_object.dart';
import 'package:http/http.dart' as http;
import 'dart:io';

class HttpService {
  static Future<dynamic> getData(HttpParamObject param) async {
    if (param != null) {
      if ("POST".toLowerCase() == param.method) {
        String url = param.url;
        Map<String, String> headers = param.headers;
        Map<String, dynamic> body = param.postParams;

        HttpClient client = new HttpClient();

        client.getUrl(Uri.parse(url));

        http.Response response = await http
            .post(url, body: json.encode(body), headers: headers)
            .timeout(const Duration(seconds: 20), onTimeout: () {
          throw 'Something went wrong';
        });

        if (response.statusCode >= 500) {
          throw 'Something went wrong';
        } else {
          var convert = JsonDecoder().convert(response.body);
          return convert;
        }
      }

      if ("GET".toLowerCase() == param.method) {
        String url = param.url;
        Map<String, String> headers = param.headers;

        HttpClient client = new HttpClient();

        client.getUrl(Uri.parse(url));

        http.Response response = await http
            .get(url, headers: headers)
            .timeout(const Duration(seconds: 10), onTimeout: () {
          throw 'Something went wrong';
        });

        if (response.statusCode >= 500) {
          throw 'Something went wrong';
        } else {
          var convert = JsonDecoder().convert(response.body);
          return convert;
        }
      }
    }
  }
}
