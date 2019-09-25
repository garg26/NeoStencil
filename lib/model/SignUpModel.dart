import 'package:NeoStencil/model/BaseModel.dart';
import 'package:json_annotation/json_annotation.dart';

part 'SignUpModel.g.dart';


@JsonSerializable()
class SignUpModel extends BaseModel{

  String _name;
  String _emailId;
  String _password;


  SignUpModel();


  SignUpModel.name(this._name,this._emailId,this._password);

  set name(String name) => _name = name;
  set emailId(String email) => _emailId = email;
  set password(String password) => _password = password;

  String get name => _name;
  String get emailId => _emailId;
  String get password => _password;

  factory SignUpModel.fromJson(Map<String, dynamic> json) => _$SignUpModelFromJson(json);


  Map<String, dynamic> toJson() => _$SignUpModelToJson(this);



}