import 'dart:async';

import 'package:NeoStencil/blocs/bloc_provider.dart';
import 'package:NeoStencil/blocs/user_account_bloc.dart';
import 'package:NeoStencil/blocs/user_course_bloc.dart';
import 'package:NeoStencil/common/constants.dart';
import 'package:NeoStencil/common/preferences.dart';
import 'package:NeoStencil/common/string_constants.dart';
import 'package:NeoStencil/model/user_course_card.dart';
import 'package:NeoStencil/ui/signup.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_facebook_login/flutter_facebook_login.dart';
/*
import 'package:google_sign_in/google_sign_in.dart';
*/

class UserCourseList extends StatelessWidget {
  static const MethodChannel _methodChannel =
      MethodChannel('samples.flutter.io/platform_view');

  @override
  Widget build(BuildContext context) {
    // TODO: implement build

    final userAccountBloc = BlocProvider.of<UserCourseBloc>(context);

    return Scaffold(

      appBar: AppBar(

            title: new Container(
              child: new Text("Hi, " + parseJwt()['name'].toString().split(" ")[0],
                  style: TextStyle(
                      fontSize: 24.0,
                      color: Colors.white,
                      fontFamily: 'Roboto-Medium')),
            ),
            flexibleSpace: new Container(
              decoration: new BoxDecoration(
                  gradient: new LinearGradient(
                      colors: [Color(0xFFa360ff), Color(0xFF7a9eff)])),

            ),
            automaticallyImplyLeading: false,
            actions: <Widget>[
              Container(
                height: 200.0,
                  child: PopupMenuButton<String>(
                icon: Icon(Icons.settings),
                offset: Offset(12.0, 12.0),
                onSelected: (String result) {
                  openMenu(result, context);
                },
                itemBuilder: (BuildContext context) => <PopupMenuItem<String>>[
                      new PopupMenuItem(
                        child: Container(
                          padding: EdgeInsets.all(8.0),
                          child: new Text("Feedback"),
                        ),
                        value: "Feedback",
                      ),
                      new PopupMenuItem(
                          child: Container(
                            padding: EdgeInsets.all(8.0),
                            child: new Text("Logout"),
                          ),
                          value: "Logout")
                    ],
              ))
            ],
          ),
      /*appBar: AppBar(
          title: new Text("Home"),
          automaticallyImplyLeading: false,
          actions: <Widget>[
            PopupMenuButton<String>(
              icon: Icon(Icons.settings),
              offset: Offset(12.0, 12.0),
              onSelected: (String result) {
                openMenu(result, context);
              },
              itemBuilder: (BuildContext context) => <PopupMenuItem<String>>[
                    new PopupMenuItem(
                      child: Container(
                        padding: EdgeInsets.all(8.0),
                        child: new Text("Feedback"),
                      ),
                      value: "Feedback",
                    ),
                    new PopupMenuItem(
                        child: Container(
                          padding: EdgeInsets.all(8.0),
                          child: new Text("Logout"),
                        ),
                        value: "Logout")
                  ],
            )
          ]),*/
      body: StreamBuilder(
        stream: userAccountBloc.isUserCourseList,
        initialData: null,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done &&
              snapshot.data != null) {
            List responseList = snapshot.data;

            List<UserCourseCard> userCourseCardList = responseList
                .map((m) => new UserCourseCard.fromJson(m))
                .toList();

            return new ListView.builder(
              itemCount: userCourseCardList.length,
              itemBuilder: (BuildContext context, int index) {
                return Card(
                  key: new ObjectKey(userCourseCardList[index].courseBatchId),
                  child: InkWell(
                    onTap: () {
                      userCourseCardList[index].remainingValidity <= 0
                          ? Scaffold.of(context).showSnackBar(SnackBar(
                              content: Text(StringUtils.courseExpire),
                              duration: Duration(milliseconds: 500)))
                          : _launchPlatformCount(
                              userCourseCardList[index].courseBatchId,
                              Preferences.getData(Preferences.KEY_AUTH_TOKEN));
                    },
                    splashColor: Color(0xFFEEEEEE),
                    child: new Column(
                      children: <Widget>[
                        new Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: <Widget>[
                            new Image.network(
                                userCourseCardList[index].courseImage,
                                height: 96.0,
                                width: 108.0,
                                fit: BoxFit.fitHeight),
                            new Expanded(
                                child: new Container(
                              margin: EdgeInsets.only(
                                  left: 8.0, right: 8.0, top: 8.0),
                              child: new Column(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceEvenly,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: <Widget>[
                                  new Text(
                                    userCourseCardList[index].courseTitle,
                                    overflow: TextOverflow.ellipsis,
                                    softWrap: false,
                                  ),
                                  new Container(
                                    margin: EdgeInsets.only(top: 4.0),
                                    child: new Row(
                                      mainAxisSize: MainAxisSize.min,
                                      children: <Widget>[
                                        new Text(
                                          "by ",
                                          style: customSmallItalicTextStyle(),
                                        ),
                                        new Expanded(
                                          child: new Text(
                                            userCourseCardList[index]
                                                .instructorName,
                                            overflow: TextOverflow.ellipsis,
                                            softWrap: false,
                                            style: customSmallTextStyle(),
                                          ),
                                        )
                                      ],
                                    ),
                                  ),
                                  new Align(
                                    alignment: Alignment.bottomCenter,
                                    heightFactor: 2.6,
                                    child: new Row(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      mainAxisAlignment:
                                          MainAxisAlignment.spaceBetween,
                                      children: <Widget>[
                                        new Container(
                                          margin: EdgeInsets.only(top: 4.0),
                                          child: new Text(
                                            "Starts on " +
                                                userCourseCardList[index]
                                                    .startDate,
                                            style:
                                                customSmallGreyColorTextStyle(),
                                            overflow: TextOverflow.ellipsis,
                                            softWrap: false,
                                          ),
                                        ),
                                        new Container(
                                          margin: EdgeInsets.only(top: 4.0),
                                          child: new Text(
                                            userCourseCardList[index]
                                                        .remainingValidity ==
                                                    0
                                                ? "Course inactive"
                                                : userCourseCardList[index]
                                                        .remainingValidity
                                                        .toString() +
                                                    " days left",
                                            style:
                                                customSmallGreyColorTextStyle(),
                                            overflow: TextOverflow.ellipsis,
                                            softWrap: false,
                                          ),
                                        )
                                      ],
                                    ),
                                  )
                                ],
                              ),
                            )),
                          ],
                        )
                      ],
                    ),
                  ),
                );
              },
            );
          } else if (!snapshot.hasError) {
            return new Center(
              child: CircularProgressIndicator(),
            );
          } else {
            Scaffold.of(context).showSnackBar(SnackBar(
                content: Text(StringUtils.error),
                duration: Duration(seconds: 2)));
          }
        },
      ),
    );
  }

  Future<void> _launchPlatformCount(int courseBatchId, String authToken) async {
    final int platformCounter = await _methodChannel.invokeMethod(
        'switchView', {"courseBatchId": courseBatchId, "authToken": authToken});
  }

  void onLogout(context) {
    if (Preferences.removeData(Preferences.KEY_AUTH_TOKEN) != null) {
      String loginType = Preferences.getData(Preferences.USER_LOGIN_TYPE);
      switch (loginType) {
        case "FACEBOOK":
          FacebookLogin().logOut();
          break;
        case "GOOGLE":
          /*GoogleSignIn().signOut().then((value) {
            print(value);
          });*/
          break;
        case "EMAIL":
          print("Kartikeya Garg");
          break;
      }

      Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (BuildContext context) {
        return BlocProvider<UserAccountBloc>(
          bloc: UserAccountBloc(),
          child: SignUp(),
        );
      }), (Route<dynamic> route) => false);
    }
  }

  Future openFeedbackEmail() async {
    await _methodChannel.invokeMethod('sendMail');
  }

  void openMenu(String result, BuildContext context) {
    if (result == "Logout") {
      onLogout(context);
    } else if (result == 'Feedback') {
      openFeedbackEmail();
    }
  }
}
