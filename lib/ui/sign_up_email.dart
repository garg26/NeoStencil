
import 'dart:io';

import 'package:NeoStencil/blocs/user_account_bloc.dart';
import 'package:NeoStencil/common/api_request.dart';
import 'package:NeoStencil/common/constants.dart';
import 'package:NeoStencil/common/preferences.dart';
import 'package:NeoStencil/model/SignUpModel.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:NeoStencil/blocs/bloc_provider.dart';

class SignUpScreen extends StatelessWidget {
  @override
  Widget build(BuildContext buildContext) {
    // TODO: implement build
    final userAccountBloc = BlocProvider.of<UserAccountBloc>(buildContext);

    return  new Scaffold(
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
                new Container(child: heading("Sign Up")),
                nameField(userAccountBloc),
                emailField(userAccountBloc),
                passwordField(userAccountBloc),
                submitButton(userAccountBloc, buildContext)
              ],
            ),
          );
        },
      ),
    );
  }

  Widget nameField(UserAccountBloc userAccountBloc) {
    return StreamBuilder(
      stream: userAccountBloc.getName,
      builder: (context, snapshot) {
        return new Container(
          decoration: BoxDecoration(
              border: Border.all(
                  color: Color(0xffd8d8d8),
                  width: 1.0,
                  style: BorderStyle.solid)),
          margin: EdgeInsets.only(right: 32.0, left: 32.0, top: 40.0),
          child: TextField(
              keyboardType: TextInputType.text,
              decoration: InputDecoration(
                  border: InputBorder.none,
                  hintText: "Name",
                  errorText: snapshot.error,
                  hintStyle: TextStyle(color: Color.fromARGB(40, 0, 0, 0))),
              onChanged: userAccountBloc.changeName),
          padding:
              EdgeInsets.only(left: 8.0, right: 8.0, top: 4.0, bottom: 4.0),
        );
      },
    );
  }

  Widget emailField(UserAccountBloc userAccountBloc) {
    return StreamBuilder(
      stream: userAccountBloc.getEmail,
      builder: (context, snapshot) {
        return new Container(
          decoration: BoxDecoration(
              border: Border(
                  left: BorderSide(
                    color: Color(0xffd8d8d8),
                    width: 1.0,
                  ),
                  right: BorderSide(
                    width: 1.0,
                    color: Color(0xffd8d8d8),
                  ))),
          margin: EdgeInsets.only(right: 32.0, left: 32.0),
          child: TextField(
              keyboardType: TextInputType.emailAddress,
              decoration: InputDecoration(
                  border: InputBorder.none,
                  hintText: "Email",
                  errorText: snapshot.error,
                  hintStyle: TextStyle(color: Color.fromARGB(40, 0, 0, 0))),
              onChanged: userAccountBloc.changeEmail),
          padding:
              EdgeInsets.only(left: 8.0, right: 8.0, top: 4.0, bottom: 4.0),
        );
      },
    );
  }

  Widget passwordField(UserAccountBloc userAccountBloc) {
    return StreamBuilder(
      stream: userAccountBloc.getPassword,
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
                suffixIcon: IconButton(
                  onPressed: () {},
                  tooltip: "Show Password",
                  icon: Icon(FontAwesomeIcons.eye),
                  iconSize: 16.0,
                ),
              ),
              onChanged: userAccountBloc.changePassword),
          padding: EdgeInsets.only(left: 8.0, right: 8.0, top: 4.0),
        );
      },
    );
  }

  Widget submitButton(
      UserAccountBloc userAccountBloc, BuildContext buildContext) {
    return StreamBuilder(
        stream: userAccountBloc.isLoading,
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
                    text: "Sign up with email",
                    action: () {
                      SignUpModel model = userAccountBloc.onSubmit();
                      if (model!=null) {
                        _onSignUp(model).then((value) {
                          if (value['loginSucces']) {
                            Preferences.saveData(Preferences.KEY_AUTH_TOKEN, value['accessToken']);
                            Navigator.pop(buildContext);
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
                          userAccountBloc.hideLoader;
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
      url: "signup",
      postParams: signUpModel.toJson(),
      headers: {HttpHeaders.contentTypeHeader: 'application/json'});

  print(response);

  return response;
}
