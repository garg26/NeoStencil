import 'package:NeoStencil/model/BaseModel.dart';
import 'package:json_annotation/json_annotation.dart';

part 'login_response.g.dart';


@JsonSerializable()
class LoginResponse extends BaseModel{

  bool loginSucces;
  String accessToken;
  String userName;
  String errorMessage;


  LoginResponse(this.loginSucces, this.accessToken, this.userName,
      this.errorMessage);

  factory LoginResponse.fromJson(Map<String, dynamic> json) => _$LoginResponseFromJson(json);


  Map<String, dynamic> toJson() => _$LoginResponseToJson(this);

}