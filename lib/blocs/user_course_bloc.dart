
import 'dart:io';

import 'package:NeoStencil/blocs/bloc_base.dart';
import 'package:NeoStencil/common/api_request.dart';
import 'package:flutter/services.dart';
import 'package:rxdart/rxdart.dart';


class UserCourseBloc implements BlocBase{
  int batchId;
  int unitId;

  static const channel = const MethodChannel("flutter.neostencilmobileapp.com/playUnit");

  UserCourseBloc();

  UserCourseBloc.batchId(this.batchId);

  UserCourseBloc.unitId(this.unitId);


  Stream<dynamic> get isUserCourseList => Observable.fromFuture(_getUserCourse());
  Stream<dynamic> get isUserBatchUnitList => Observable.fromFuture(_getUserBatchUnitList());
  Stream<dynamic> get isUnitById => Observable.fromFuture(_getUnitByUnitId());



  _getUserCourse() async {
      dynamic response = await ApiRequest.getUserCourse(
          url: "user/courses",
          headers: {HttpHeaders.contentTypeHeader: 'application/json'});

      return response;

    }

  _getUserBatchUnitList() async{
    dynamic response = await ApiRequest.getUserBatchUnit(
        url: "user/batches/"+batchId.toString()+"/units",
        headers: {HttpHeaders.contentTypeHeader: 'application/json'});

    print(response);

    return response;
  }

  _getUnitByUnitId() async{

    final response = await channel.invokeMethod("playunit",[unitId]);

    print(response);

  }


  @override
  void dispose() {
    // TODO: implement dispose
  }




}