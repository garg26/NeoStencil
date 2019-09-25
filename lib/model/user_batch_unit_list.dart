import 'package:NeoStencil/model/unit.dart';
import 'package:json_annotation/json_annotation.dart';

part 'user_batch_unit_list.g.dart';

@JsonSerializable()
class UserBatchUnitModel{

  int _createdAt;
  int _updatedAt;
  int _linkageId;
  bool _active;
  String _watchStatus;
  int _noOfClicks;
  double _resumeFrom;
  UnitModel _unit;

  UserBatchUnitModel();

  UnitModel get unit => _unit;

  factory UserBatchUnitModel.fromJson(Map<String, dynamic> json) => _$UserBatchUnitModelFromJson(json);


  Map<String, dynamic> toJson() => _$UserBatchUnitModelToJson(this);

  set unit(UnitModel value) {
    _unit = value;
  }

  double get resumeFrom => _resumeFrom;

  set resumeFrom(double value) {
    _resumeFrom = value;
  }

  int get noOfClicks => _noOfClicks;

  set noOfClicks(int value) {
    _noOfClicks = value;
  }

  String get watchStatus => _watchStatus;

  set watchStatus(String value) {
    _watchStatus = value;
  }

  bool get active => _active;

  set active(bool value) {
    _active = value;
  }

  int get linkageId => _linkageId;

  set linkageId(int value) {
    _linkageId = value;
  }

  int get updatedAt => _updatedAt;

  set updatedAt(int value) {
    _updatedAt = value;
  }

  int get createdAt => _createdAt;

  set createdAt(int value) {
    _createdAt = value;
  }


}
