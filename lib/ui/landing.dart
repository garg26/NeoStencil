import 'package:NeoStencil/blocs/bloc_provider.dart';
import 'package:NeoStencil/blocs/user_account_bloc.dart';
import 'package:NeoStencil/ui/signup.dart';
import 'package:flutter/material.dart';

import 'package:swipedetector/swipedetector.dart';

class Landing extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final Shader linearGradient = LinearGradient(
      colors: <Color>[Color(0xffa360ff), Color(0xff7a9eff)],
    ).createShader(Rect.fromLTWH(0.0, 0.0, 200.0, 70.0));
    // TODO: implement build
    return new Scaffold(
        body: Center(
            child: Row(
      children: <Widget>[
        new Expanded(
          child: Stack(
            children: <Widget>[
              new Center(
                child: new Image(
                  image: AssetImage("images/neostencil_logo.png"),
                ),
              ),
              new Center(
                child: new Container(
                  child: Text(
                    "Explore 1100+ new courses titles list and recommmendations",
                    textAlign: TextAlign.center,
                    style:
                        TextStyle(fontFamily: "Roboto-Medium", fontSize: 18.0),
                  ),
                  margin: EdgeInsets.only(top: 91.0),
                  padding: EdgeInsets.only(left: 32.0, right: 32.0),
                ),
              ),
              new Align(
                child: SwipeDetector(
                  child: Container(
                    child: new Text(
                      "Swipe to slide <",
                      style: TextStyle(
                          fontSize: 18.0,
                          foreground: Paint()..shader = linearGradient),
                      textAlign: TextAlign.center,
                    ),
                    padding: EdgeInsets.only(top: 32.0, bottom: 32.0),
                    width: double.infinity,
                  ),
                  onSwipeLeft: () {
                    Navigator.of(context)
                        .push(MaterialPageRoute(builder: (BuildContext context) {
                      return BlocProvider<UserAccountBloc>(
                        bloc: UserAccountBloc(),
                        child: SignUp(),
                      );
                    }));
                  },
                ),
                alignment: Alignment.bottomCenter,
              )
            ],
          ),
        )
      ],
    )));
  }
}
