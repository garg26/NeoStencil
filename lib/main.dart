import 'package:NeoStencil/common/constants.dart';
import 'package:NeoStencil/common/preferences.dart';
import 'package:NeoStencil/ui/splash_screen.dart';
import 'package:NeoStencil/ui/user_courses_list.dart';
import 'package:NeoStencil/ui/landing.dart';
import 'package:NeoStencil/ui/sign_up_email.dart';
import 'package:flutter/material.dart';

void main() {
  Preferences.initPreference();
  runApp(
    new MaterialApp(
      debugShowCheckedModeBanner: false,
      home: WelcomeScreen(),
      routes: <String, WidgetBuilder>{
        '/signUpScreen': (BuildContext context) => SignUpScreen(),
        '/landing': (BuildContext context) => Landing(),
        '/home':(BuildContext context) => UserCourseList()
      },
      theme: buildNeoStencilTheme(),
    ),
  );
}
