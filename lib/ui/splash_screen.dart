import 'dart:async';

import 'package:NeoStencil/blocs/bloc_provider.dart';
import 'package:NeoStencil/blocs/user_account_bloc.dart';
import 'package:NeoStencil/blocs/user_course_bloc.dart';
import 'package:NeoStencil/common/preferences.dart';
import 'package:NeoStencil/ui/signup.dart';
import 'package:NeoStencil/ui/user_courses_list.dart';
import 'package:flutter/material.dart';

class WelcomeScreen extends StatefulWidget {
  @override
  _WelcomeScreenState createState() {
    return _WelcomeScreenState();
  }
}

class _WelcomeScreenState extends State<WelcomeScreen> {

  @override
  void initState() {
    super.initState();
    Timer(
        Duration(seconds: 5),
            () {
          if(Preferences.getData(Preferences.KEY_AUTH_TOKEN)!=null){
            Navigator.of(context).pushReplacement(
                  MaterialPageRoute(builder: (BuildContext context) {
                    return BlocProvider<UserCourseBloc>(
                      bloc: UserCourseBloc(),
                      child: UserCourseList(),
                    );
                  }));
          }
          else{
            Navigator.of(context)
                .pushReplacement(MaterialPageRoute(builder: (BuildContext context) {
              return BlocProvider<UserAccountBloc>(
                bloc: UserAccountBloc(),
                child: SignUp(),
              );
            }));
          }
        }
    );
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: Center(
        child: new Image(
          image: AssetImage("images/neostencil_logo.png"),
        ),
      ),

    );
  }
}
