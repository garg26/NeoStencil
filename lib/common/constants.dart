import 'package:NeoStencil/common/preferences.dart';
import 'package:flutter/material.dart';
import 'dart:convert';

final String baseUrl = 'https://neostencil.com/';
final String apiUrl = 'api/v1/';

void navigate(String route, BuildContext context) {
  Navigator.of(context).pushNamed('/$route');
}

String authToken = Preferences.getData(Preferences.KEY_AUTH_TOKEN);

Map<String, dynamic> parseJwt() {
  final parts = authToken.split('.');
  if (parts.length != 3) {
    throw Exception('invalid token');
  }

  final payload = _decodeBase64(parts[1]);
  final payloadMap = json.decode(payload);
  if (payloadMap is! Map<String, dynamic>) {
    throw Exception('invalid payload');
  }
  return payloadMap;
}

String _decodeBase64(String str) {
  String output = str.replaceAll('-', '+').replaceAll('_', '/');

  switch (output.length % 4) {
    case 0:
      break;
    case 2:
      output += '==';
      break;
    case 3:
      output += '=';
      break;
    default:
      throw Exception('Illegal base64url string!"');
  }

  return utf8.decode(base64Url.decode(output));
}



orangeButtonCommon({String text, Function action}) {
  return MaterialButton(
      onPressed: action,
      splashColor: Color.fromARGB(40, 255, 255, 255),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          new Text(
            text,
            style: TextStyle(
                color: Colors.white,
                fontSize: 16.0,
                fontFamily: 'Rubik-Regular',
                fontWeight: FontWeight.w500),
          )
        ],
      ));
}

greyButton({String text, Function action}) {
  return MaterialButton(
      onPressed: action,
      splashColor: Color.fromARGB(40, 255, 255, 255),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          new Text(
            text,
            style: TextStyle(
                color: Colors.white,
                fontSize: 16.0,
                fontFamily: 'Rubik-Regular',
                fontWeight: FontWeight.w500),
          )
        ],
      ));
}

buildNeoStencilTheme() {
  final ThemeData base = ThemeData.light();
  return base.copyWith(textTheme: customTextTheme(base.textTheme),accentColor:Colors.grey,primaryColor:Colors.grey,cursorColor: Colors.grey );
}

customTextTheme(TextTheme base) {
  return base.copyWith(
      body1: TextStyle(fontFamily: "Roboto-Regular", color: Colors.black),
      button: TextStyle(fontFamily: "Roboto-Regular"));
}

smallSizeTextTheme(){

}

customSmallTextStyle() {
  return TextStyle(fontSize: 12.0);
}
customSmallGreyColorTextStyle() {
  return TextStyle(fontSize: 12.0,color: Colors.grey);
}
customSmallItalicTextStyle() {
  return TextStyle(fontSize: 12.0,fontStyle: FontStyle.italic,color: Colors.grey);
}

heading(String s) {
  return Text(
    s,
    style: TextStyle(fontSize: 26.0,color: Colors.black,fontWeight: FontWeight.w500),
  );
}
