import 'dart:async';

import 'package:shared_preferences/shared_preferences.dart';

class Preferences {
  static final String KEY_AUTH_TOKEN = "Authorization";
  static final String USER_LOGIN_TYPE = "LoginType";
  static SharedPreferences preferences;

  static initPreference() async {
    preferences =  await SharedPreferences.getInstance();
  }

  static saveData(String key, String value) {
    preferences.setString(key, value);
  }

  static getData(String key) {
    return preferences.get(key);
  }

  static removeData(String key) {
    return preferences.remove(key);
  }

}
