import 'package:json_annotation/json_annotation.dart';

part 'unit.g.dart';


@JsonSerializable()
class UnitModel{

  int _unitId;
  String _type;
  int _typeId;
  String _title;
  String _description;
  bool _product;
  double _price;
  String _topic;
  int _position;
  bool _free;
  String _status;


  int get unitId => _unitId;

  set unitId(int value) {
    _unitId = value;
  }

  UnitModel();

  factory UnitModel.fromJson(Map<String, dynamic> json) => _$UnitModelFromJson(json);


  Map<String, dynamic> toJson() => _$UnitModelToJson(this);

  String get type => _type;

  set type(String value) {
    _type = value;
  }

  int get typeId => _typeId;

  set typeId(int value) {
    _typeId = value;
  }

  String get title => _title;

  set title(String value) {
    _title = value;
  }

  String get description => _description;

  set description(String value) {
    _description = value;
  }

  bool get product => _product;

  set product(bool value) {
    _product = value;
  }

  double get price => _price;

  set price(double value) {
    _price = value;
  }

  String get topic => _topic;

  set topic(String value) {
    _topic = value;
  }

  int get position => _position;

  set position(int value) {
    _position = value;
  }

  bool get free => _free;

  set free(bool value) {
    _free = value;
  }

  String get status => _status;

  set status(String value) {
    _status = value;
  }

}