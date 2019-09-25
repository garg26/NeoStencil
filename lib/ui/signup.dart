import 'package:NeoStencil/blocs/bloc_provider.dart';
import 'package:NeoStencil/blocs/login_email_bloc.dart';
import 'package:NeoStencil/blocs/user_account_bloc.dart';
import 'package:NeoStencil/blocs/user_course_bloc.dart';
import 'package:NeoStencil/common/api_request.dart';
import 'package:NeoStencil/common/constants.dart';
import 'package:NeoStencil/common/preferences.dart';
import 'package:NeoStencil/model/google_signin_response.dart';
import 'package:NeoStencil/ui/login_email.dart';
import 'package:NeoStencil/ui/user_courses_list.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_svg/svg.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:flutter_facebook_login/flutter_facebook_login.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:io';
import 'package:NeoStencil/model/login_response.dart';
import 'package:google_sign_in/google_sign_in.dart';

class SignUp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final userAccountBloc = BlocProvider.of<UserAccountBloc>(context);
    SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
    return new Scaffold(
      bottomNavigationBar: new Container(
        margin: EdgeInsets.only(bottom: 28.0, left: 58.0, right: 58.0),
        child: RichText(
            textAlign: TextAlign.center,
            text: new TextSpan(
              children: [
                new TextSpan(
                  text:
                      'By continuing you indicate that you’ve read and agree to our ',
                  style:
                      new TextStyle(color: Color(0xFFd5d5d5), fontSize: 12.0),
                ),
                new TextSpan(
                  text: 'Terms of Services.',
                  style: new TextStyle(
                      color: Color(0xFF4a90e2),
                      fontSize: 12.0,
                      decoration: TextDecoration.underline),
                  recognizer: new TapGestureRecognizer()
                    ..onTap = () {
                      launch("https://neostencil.com/terms");
                    },
                ),
              ],
            )),
      ),
      body: new Center(
          child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          imageLogo(),
          accountText(),
          facebookLoginButton(userAccountBloc),
          googleLoginButton(userAccountBloc),
          new Container(
            child: Text(
              'OR',
              style: TextStyle(
                color: Color(0xff999999),
                fontSize: 12.0,
              ),
            ),
            margin: EdgeInsets.only(top: 2.0, bottom: 2.0),
          ),
          loginWithEmail(context),
        ],
      )),
    );
  }

  Widget imageLogo() {
    return new Image(
      image: AssetImage("images/neostencil_logo.png"),
    );
  }

  Widget accountText() {
    return new Container(
        child: new Text(
          "Create a neostencil account to start learning",
          style: TextStyle(
            fontSize: 14.0,
            color: Color.fromARGB(80, 0, 0, 0),
          ),
          textAlign: TextAlign.center,
        ),
        padding:
            EdgeInsets.only(left: 80.0, right: 80.0, top: 12.0, bottom: 12.0));
  }

  Widget facebookLoginButton(UserAccountBloc userAccountBloc) {
    return StreamBuilder(
      stream: userAccountBloc.isLoading,
      builder: (context, snapshot) {
        if ((snapshot.connectionState == ConnectionState.waiting &&
                snapshot.data == null) ||
            (snapshot.connectionState == ConnectionState.active &&
                !snapshot.data)) {
          return new Container(
            child: FlatButton(
              onPressed: () {
                initiateFacebookLogin().then((accessToken) {
                  userAccountBloc.showLoader;
                  _onLoginWithFacebook(accessToken).then((loginResponse) {
                    if (loginResponse.loginSucces) {
                      Preferences.saveData(Preferences.KEY_AUTH_TOKEN,
                          loginResponse.accessToken);

                      Preferences.saveData(Preferences.USER_LOGIN_TYPE,
                          "FACEBOOK");

                      Navigator.of(context).pushReplacement(
                          MaterialPageRoute(builder: (BuildContext context) {
                        return BlocProvider<UserCourseBloc>(
                          bloc: UserCourseBloc(),
                          child: UserCourseList(),
                        );
                      }));
                    } else {
                      Scaffold.of(context).showSnackBar(SnackBar(
                          content: Text(loginResponse.errorMessage),
                          duration: Duration(seconds: 5)));
                    }
                  }, onError: (e) {
                    Scaffold.of(context).showSnackBar(SnackBar(
                        content: Text('Something went wrong'),
                        duration: Duration(seconds: 5)));
                  }).whenComplete(() {
                    userAccountBloc.hideLoader;
                  });
                });
              },
              splashColor: Color.fromARGB(40, 255, 255, 255),
              color: Color(0xff1e599b),
              child: Row(
                children: <Widget>[
                  new Container(
                    child: SvgPicture.asset('icons/icon_facebook.svg',
                        height: 24.0),
                    padding: EdgeInsets.only(right: 8.0),
                  ),
                  new Text(
                    "Connect with Facebook",
                    style: TextStyle(color: Colors.white, fontSize: 16.0),
                  ),
                ],
                mainAxisAlignment: MainAxisAlignment.center,
              ),
              padding: EdgeInsets.only(top: 12.0, bottom: 12.0),
            ),
            padding: EdgeInsets.only(left: 32.0, right: 32.0),
            margin: EdgeInsets.only(top: 8.0, bottom: 8.0),
          );
        } else {
          return new CircularProgressIndicator();
        }
      },
    );
  }

  Widget googleLoginButton(UserAccountBloc userAccountBloc) {
    return StreamBuilder(
        stream: userAccountBloc.isGoogleLogin,
        builder: (context, snapshot) {
          if ((snapshot.connectionState == ConnectionState.waiting &&
              snapshot.data == null) ||
              (snapshot.connectionState == ConnectionState.active &&
                  !snapshot.data)) {
            return new Container(
              child: OutlineButton(
                onPressed: () {
                  initiateGoogleLogin().then((googleSignInAccount) {
                    if(googleSignInAccount!=null) {
                      userAccountBloc.showFacebookLoader;
                      GoogleSignInResponse googleSignInResponse =
                      new GoogleSignInResponse(
                          googleSignInAccount.displayName,
                          googleSignInAccount.email,
                          googleSignInAccount.photoUrl);
                      _onLoginWithGoogle(googleSignInResponse).then(
                              (loginResponse) {
                            if (loginResponse.loginSucces) {
                              Preferences.saveData(Preferences.KEY_AUTH_TOKEN,
                                  loginResponse.accessToken);

                              Preferences.saveData(Preferences.USER_LOGIN_TYPE,
                                  "GOOGLE");

                              Navigator.of(context).pushReplacement(
                                  MaterialPageRoute(
                                      builder: (BuildContext context) {
                                        return BlocProvider<UserCourseBloc>(
                                          bloc: UserCourseBloc(),
                                          child: UserCourseList(),
                                        );
                                      }));
                            } else {
                              Scaffold.of(context).showSnackBar(SnackBar(
                                  content: Text(loginResponse.errorMessage),
                                  duration: Duration(seconds: 5)));
                            }
                          }, onError: (e) {
                        Scaffold.of(context).showSnackBar(SnackBar(
                            content: Text('Something went wrong'),
                            duration: Duration(seconds: 5)));
                      }).whenComplete(() {
                        userAccountBloc.hideGoogleLoader;
                      });
                    }
                  }, onError: (e) {
                    Scaffold.of(context).showSnackBar(SnackBar(
                        content: Text('Something went wrong'),
                        duration: Duration(seconds: 5)));
                  });
                },
                splashColor: Color.fromARGB(40, 255, 255, 255),
                color: Color(0xffffffff),
                child: Row(
                  children: <Widget>[
                    new Container(
                      child:
                      SvgPicture.asset('icons/icon_google.svg', height: 24.0),
                      padding: EdgeInsets.only(right: 8.0),
                    ),
                    new Text(
                      "Connect with Google",
                      style: TextStyle(
                          color: Color(0xff444444), fontSize: 16.0),
                    ),
                  ],
                  mainAxisAlignment: MainAxisAlignment.center,
                ),
                borderSide: BorderSide(color: Color(0xffd8d8d8), width: 1.0),
                highlightedBorderColor: Color(0xffd8d8d8),
                highlightColor: Colors.transparent,
                highlightElevation: 0,
                padding: EdgeInsets.only(top: 12.0, bottom: 12.0),
              ),
              padding: EdgeInsets.only(left: 32.0, right: 32.0),
              margin: EdgeInsets.only(top: 8.0, bottom: 8.0),
            );
          }else{
            return new CircularProgressIndicator(

            );
          }
        });
  }

  Widget loginWithEmail(context) {
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
            text: "Login with email",
            action: () {

              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (BuildContext context) {
                return BlocProvider<LoginEmailBloc>(
                  bloc: LoginEmailBloc(),
                  child: LoginEmailScreen(),
                );
              }));
            }));
  }

  Future<String> initiateFacebookLogin() async {
    String accessToken;

    final facebookLogin = FacebookLogin();

    facebookLogin.loginBehavior = FacebookLoginBehavior.webViewOnly;

    var facebookLoginResult =
        await facebookLogin.logInWithReadPermissions(['email']);

    switch (facebookLoginResult.status) {
      case FacebookLoginStatus.error:
        break;
      case FacebookLoginStatus.cancelledByUser:
        break;
      case FacebookLoginStatus.loggedIn:
        accessToken = facebookLoginResult.accessToken.token;

        break;
    }

    return accessToken;
  }

  Future<LoginResponse> _onLoginWithFacebook(String accessToken) async {
    Map<String, dynamic> response = await ApiRequest.facebookLogin(
        url: "facebook/login/mobile?token=" + accessToken,
        headers: {HttpHeaders.contentTypeHeader: 'application/json'});

    return LoginResponse.fromJson(response);
  }

  Future<GoogleSignInAccount> initiateGoogleLogin() async {
    GoogleSignIn _googleSignIn = GoogleSignIn(
      scopes: [
        'email',
        'https://www.googleapis.com/auth/contacts.readonly',
      ],
    );
    return await _googleSignIn.signIn();
  }

  Future<LoginResponse> _onLoginWithGoogle(
      GoogleSignInResponse googleSignInResponse) async {
    Map<String, dynamic> response = await ApiRequest.googleLogin(
        url: "google/login/mobile",
        postParams: googleSignInResponse.toJson(),
        headers: {HttpHeaders.contentTypeHeader: 'application/json'});

    return LoginResponse.fromJson(response);
  }
}
