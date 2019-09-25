import 'package:NeoStencil/common/preferences.dart';

class HttpParamObject extends Object {
  String _url;
  Map<String, String> _headers = new Map<String, String>();
  String _method;
  Map<String, dynamic> _postParams;
  dynamic _classType;

  String get url => _url;

  Map<String, String> get headers => _headers;

  String get method => _method;

  Map<String, dynamic> get postParams => _postParams;

  dynamic get classType => _classType;

  set url(String url) => _url = url;

  set headers(Map<String, String> headers) {
    _headers.addAll(headers);
  }

  set method(String method) => _method = method;

  set postParam(Map<String, dynamic> postParam) => _postParams = postParam;

  HttpParamObject() {
    _addAuthToken();
  }

  void _addAuthToken() {
    String authToken = Preferences.getData(Preferences.KEY_AUTH_TOKEN);
    if (authToken != null && authToken.isNotEmpty) {
      _addHeader(Preferences.KEY_AUTH_TOKEN, "Bearer " + authToken);
    }
  }

  void _addHeader(String key_auth_token, String token) {
    headers[key_auth_token] = token;
  }
}
