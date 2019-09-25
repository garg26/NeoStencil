
import 'dart:io';
import 'package:NeoStencil/blocs/login_email_bloc.dart';
import 'package:NeoStencil/blocs/user_course_bloc.dart';
import 'package:NeoStencil/common/api_request.dart';
import 'package:NeoStencil/common/constants.dart';
import 'package:NeoStencil/common/preferences.dart';
import 'package:NeoStencil/model/SignUpModel.dart';
import 'package:NeoStencil/ui/user_courses_list.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:NeoStencil/blocs/bloc_provider.dart';

class LoginEmailScreen extends StatelessWidget {
  @override
  Widget build(BuildContext buildContext) {
    // TODO: implement build
    final loginEmailBloc = BlocProvider.of<LoginEmailBloc>(buildContext);

    return new Scaffold(
          appBar: AppBar(
            leading: IconButton(
              color: Color.fromARGB(80, 0, 0, 0),
              icon: Icon(Icons.keyboard_arrow_left),
              onPressed: () {
                Navigator.pop(buildContext);
              },
            ),
            elevation: 0.0,
            backgroundColor: Colors.transparent,
          ),
          body: Builder(
            builder: (BuildContext bodyContext) {
              return new Center(
                child: new Column(
                  children: <Widget>[
                    new Container(child: heading("Login")),
                    emailField(loginEmailBloc),
                    passwordField(loginEmailBloc),
                    submitButton(loginEmailBloc, buildContext)
                  ],
                ),
              );
            },
          ),
        );
  }


  Widget emailField(LoginEmailBloc loginEmailBloc) {
    return StreamBuilder(
      stream: loginEmailBloc.getEmail,
      builder: (context, snapshot) {
        return new Container(
            decoration: BoxDecoration(
                border: Border.all(
                    color: Color(0xffd8d8d8),
                    width: 1.0,
                    style: BorderStyle.solid)),
          margin: EdgeInsets.only(right: 32.0, left: 32.0, top: 40.0),
          child: TextField(
              keyboardType: TextInputType.emailAddress,
              decoration: InputDecoration(
                  border: InputBorder.none,
                  hintText: "Email",
                  errorText: snapshot.error,
                  hintStyle: TextStyle(color: Color.fromARGB(40, 0, 0, 0))),
              onChanged: loginEmailBloc.changeEmail),
          padding:
          EdgeInsets.only(left: 8.0, right: 8.0, top: 4.0, bottom: 4.0),
        );
      },
    );
  }

  Widget passwordField(LoginEmailBloc loginEmailBloc) {
    return StreamBuilder(
      stream: loginEmailBloc.getPassword,
      builder: (context, snapshot) {
        return new Container(
          decoration: BoxDecoration(
              border: Border.all(
                  color: Color(0xffd8d8d8),
                  width: 1.0,
                  style: BorderStyle.solid)),
          margin: EdgeInsets.only(right: 32.0, left: 32.0, bottom: 16.0),
          child: TextField(
              obscureText: true,
              decoration: InputDecoration(
                border: InputBorder.none,
                hintText: "Password",
                errorText: snapshot.error,
                hintStyle: TextStyle(color: Color.fromARGB(40, 0, 0, 0)),

              ),
              onChanged: loginEmailBloc.changePassword),
          padding: EdgeInsets.only(left: 8.0, right: 8.0, top: 4.0),
        );
      },
    );
  }

  Widget submitButton(
      LoginEmailBloc loginEmailBloc, BuildContext buildContext) {
    return StreamBuilder(
        stream: loginEmailBloc.isLoading,
        builder: (context, snapshot) {
          if ((snapshot.connectionState == ConnectionState.waiting &&
              snapshot.data == null) ||
              (snapshot.connectionState == ConnectionState.active &&
                  !snapshot.data)) {
            return new Container(
                decoration: BoxDecoration(
                  gradient: new LinearGradient(
                      colors: [Color(0xFFf7a053), Color(0xFFe24d08)]),
                  boxShadow: [
                    new BoxShadow(
                        color: Colors.grey,
                        offset: new Offset(0, 4.0),
                        blurRadius: 8.0,
                        spreadRadius: 0.0)
                  ],
                ),
                margin: EdgeInsets.only(top: 10.0, left: 32, right: 32),
                height: 48,
                child: orangeButtonCommon(
                    text: "Login",
                    action: () {
                      SignUpModel model = loginEmailBloc.onSubmit();
                      if (model!=null) {
                        _onSignUp(model).then((value) {
                          if (value['loginSucces']) {
                            Preferences.saveData(Preferences.KEY_AUTH_TOKEN, value['accessToken']);
                            Preferences.saveData(Preferences.USER_LOGIN_TYPE, "EMAIL");
                            Navigator.of(context).pop();
                            Navigator.of(context).pushReplacement(
                                MaterialPageRoute(builder: (BuildContext context) {
                                  return BlocProvider<UserCourseBloc>(
                                    bloc: UserCourseBloc(),
                                    child: UserCourseList(),
                                  );
                                }));
                          } else {
                            Scaffold.of(context).showSnackBar(SnackBar(
                                content: Text(value['errorMessage']),
                                duration: Duration(seconds: 2)));
                          }
                        }, onError: (e) {
                          Scaffold.of(context).showSnackBar(SnackBar(
                              content: Text('Something went wrong'),
                              duration: Duration(seconds: 5)));
                        }).whenComplete(() {
                          loginEmailBloc.hideLoader;
                        });
                      }
                    }));
          } else {
            return new CircularProgressIndicator();
          }
        });
  }
}

Future<Map<String, dynamic>> _onSignUp(SignUpModel signUpModel) async {
  Map<String, dynamic> response = await ApiRequest.registerUser(
      url: "login",
      postParams: signUpModel.toJson(),
      headers: {HttpHeaders.contentTypeHeader: 'application/json'});

  print(response);

  return response;
}
